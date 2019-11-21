const http = require('http');
const url = require('url');
//var mysql = require('mysql');
const mysql = require('promise-mysql');
const querys = require('./query');

let con = null;
let server = null;

const hostname = '127.0.0.1';
const port = 8000;

var body;
var body2;

/*con.connect(function(err) {
  if (err) throw err;
})*/

const defaultbody = {
  zahl1: '2',
  zahl2: '3'
};

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
    if (request.method == 'GET') {
      const queryObject = url.parse(request.url,true).query;
      var query = queryObject.type;

      var httpbody;
      
      switch(query){
        case 'refresh':        
          httpbody = body;
          querys.refresh(connection, null)
          .then(e => {
            console.log(e);
          })
          break;
        case 'attack':
          httpbody = body;
          break;
        case 'buildings':
          httpbody = body;
          break;
        default:
          httpbody = defaultbody;
      }

      response.writeHead(200, {'Content-Type': 'application/json'})
      response.end(JSON.stringify(httpbody));
    }
  });
}



/*const server = http.createServer(function(request, response) {
  if (request.method == 'GET') {
    const queryObject = url.parse(request.url,true).query;
    var query = queryObject.type;

    var httpbody;
    
    switch(query){
      case 'refresh':        
        httpbody = body;
        break;
      case 'attack':
        httpbody = body;
        break;
      case 'buildings':
        httpbody = body;
        break;
      default:
        httpbody = defaultbody;
    }

    response.writeHead(200, {'Content-Type': 'application/json'})
    response.end(JSON.stringify(httpbody));
  }
});

server.listen(port, hostname, function() {
  console.log(`Server running at http://${hostname}:${port}/`);
});*/