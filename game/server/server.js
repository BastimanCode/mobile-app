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

var body = con.connect(function(err) {
  if (err) throw err;
  
	var sql = "SELECT * FROM account JOIN planet WHERE account.id = planet.Account_id";
  con.query(sql, function (err, result) {
  if (err) throw err;
  return result;
  });
});

const server = http.createServer(function(request, response) {
  if (request.method == 'GET') {
    response.writeHead(200, {'Content-Type': 'application/json'})
    response.end(JSON.stringify(body));
  }
});

server.listen(port, hostname, function() {
  console.log(`Server running at http://${hostname}:${port}/`);
});
