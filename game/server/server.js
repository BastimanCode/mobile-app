const http = require('http');
const url = require('url');
const mysql = require('promise-mysql');
const queries = require('./query');

let server = null;

const hostname = '127.0.0.1';
const port = 8000;




mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "password",
  database: "mydb"
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
            
    queries.database(connection, queryObject)
    .then(e => {
      console.log(e);
      response.writeHead(200, {'Content-Type': 'application/json'})
      response.end(JSON.stringify(e));
    });
  });
}