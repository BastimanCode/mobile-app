const http = require('http');

var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "password",
  database: "mydb"
});

const hostname = '127.0.0.1';
const port = 8000;

con.connect(function(err) {
  if (err) throw err;
	
	//JOIN
	var sql = "SELECT * FROM account JOIN planet WHERE account.id = planet.Account_id";
  con.query(sql, function (err, result) {
  if (err) throw err;
  body = result;
  });
});

const body2 = JSON.stringify({
    Spielername: 'Tim',
    Metall: '43405',
    Kristall: '63758',
    Deuterium: '12031'
});

const server = http.createServer(function(request, response) {
  if (request.method == 'GET') {
    console.log('GET'); 
    response.writeHead(200, {'Content-Type': 'application/json'})
    response.end(body);
  }
});

server.listen(port, hostname, function() {
  console.log(`Server running at http://${hostname}:${port}/`);
});
