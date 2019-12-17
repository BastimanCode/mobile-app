const http = require('http');
const url = require('url');
const mysql = require('promise-mysql');
const queries = require('./query');

let server = null;

const hostname = '192.168.178.25';
const port = 8000;




mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "password",
  database: "mydb",
  multipleStatements: true
})
.then( con => {
  SetupServer(con);
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
          //console.log(e);
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
            console.error("Faulty database query!");
            response.writeHead(400);
            response.end();
          });
        });
        
      }
  });
}