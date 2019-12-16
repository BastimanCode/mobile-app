const http = require('http');
const url = require('url');
const mysql = require('promise-mysql');
const queries = require('./query');

let server = null;

const hostname = '192.168.0.80';
const port = 8000;

var researchlist;
var resourcelist;

mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "password",
  database: "game2"
})
.then( con => {
  SetupServer(con);
  con.query("SELECT forschungen.name, forschungsdaten.stufe, forschungsdaten.bonus FROM forschungen JOIN forschungsdaten ON forschungen.id = forschungsdaten.forschungen_id")
  .then(array => {
    researchlist = array;
  })
  .error(e =>{
    console.log(e);
  });

  con.query("SELECT abbau.name, abbaudaten.stufe, abbaudaten.fördermenge FROM abbau JOIN abbaudaten ON abbau.id = abbaudaten.abbau_id")
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

    queries.database(connection, queryObject)
    .then(e => {
      if(queryObject.type == "refresh"){
        let time = new Date();
        e[0].metall = Math.round (e[0].metall + ((time.getTime() - e[0].last_online)/3600000*resourcelist[e[0].abbau1].fördermenge));
      }
      response.writeHead(200, {'Content-Type': 'application/json'})
      response.end(JSON.stringify(e));
    });
  });
}