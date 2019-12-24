const http = require('http');
const url = require('url');
const mysql = require('promise-mysql');
const queries = require('./query');

let server = null;

const hostname = '192.168.0.80';
const port = 8000;

var researchlist;
var resourcelist;

function buildings(number){
  switch(number){
    case "0":
      return "mine";
    case "1":
      return "factory";
    case "2":
      return "oilrig";
    case "3":
      return "materialstorage";
    case "4":
      return "electronicstorage";
    case "5":
      return "fueltank";
  }
}

mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "password",
  database: "game2",
  multipleStatements: true
})
.then( con => {
  SetupServer(con);
  /*con.query("SELECT researches.name, researchdata.level, researchdata.bonus FROM researches JOIN researchdata ON researches.id = researchdata.researches_id")
  .then(array => {
    researchlist = array;
  })
  .error(e =>{
    console.log(e);
  });*/

  con.query("SELECT production.name, productiondata.level, productiondata.output, productiondata.material, productiondata.electronics, productiondata.fuel FROM production JOIN productiondata ON production.id = productiondata.Production_id")
  .then(array => {
    resourcelist = array;
  })
  .error(e =>{
    console.log(e);
  });

  server.listen(port, hostname, function() {
    console.log(`Server running at http://${hostname}:${port}/`);
  });
});



function SetupServer(connection) {
    server = http.createServer(function(request, response) {
      const queryObject = url.parse(request.url,true).query;

      if (request.method == "GET") {       
        queries.database(connection, queryObject)
        .then(e => {
          if(queryObject.type == "refresh"){
            let time = new Date();
            changed = e[0].material;
            e[0].material = Math.round (e[0].material + ((time.getTime() - e[0].last_online)/3600000 * resourcelist[e[0].mine].output));
            e[0].electronics = Math.round (e[0].electronics + ((time.getTime() - e[0].last_online)/3600000*resourcelist[e[0].factory +26].output));
            e[0].fuel = Math.round (e[0].fuel + ((time.getTime() - e[0].last_online)/3600000*resourcelist[e[0].oilrig + 52].output));
            connection.query("UPDATE planet set material = " + e[0].material + ", electronics = " + e[0].electronics + ", fuel = " + e[0].fuel + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
            if(changed != e[0].material){
              connection.query("UPDATE account set last_online = " + time.getTime() + " WHERE id = 1");
            }
          } else if(queryObject.type == "build"){
            costm = resourcelist[parseInt((queryObject.buildid * 26)) + parseInt(queryObject.level)].material;
            coste = resourcelist[parseInt((queryObject.buildid * 26)) + parseInt(queryObject.level)].electronics;
            costf = resourcelist[parseInt((queryObject.buildid * 26)) + parseInt(queryObject.level)].fuel;
            if(costm > e[0].material || coste > e[0].electronics || costf > e[0].fuel){
              response.writeHead(400);
              response.end();
            } else{        
              connection.query("UPDATE planet set material = " + (e[0].material - costm) + ", electronics = " + (e[0].electronics - coste) + ", fuel = " + (e[0].fuel - costf) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
              connection.query("UPDATE planet SET " + buildings(queryObject.buildid) + " = " + (parseInt(queryObject.level) + 1) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
            }
            
          }
          response.writeHead(200, {'Content-Type': 'application/json'})
          response.end(JSON.stringify(e));
        });
      } else if (request.method == "POST") {
        let body = [];
        request.on('data', (chunk) => {
          body.push(chunk);
        }).on('end', () => {
          body = Buffer.concat(body).toString();
          body = JSON.parse(body);
          //console.log(body);

          queries.databasePost(connection, queryObject, body)
          .then(e => {
            //console.log(e);
            if(e[0] == null) {
              response.writeHead(400);
              response.end();
            } else {
              response.writeHead(200, {'Content-Type': 'application/json'})
              response.end(JSON.stringify(e));
            }
          })
          .catch((err) => {
            console.error("Faulty database query!", err);
            response.writeHead(400);
            response.end();
          });
        }); 
      }
  });
}