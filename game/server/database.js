var mysql = require('mysql');

//abfragen
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "password",
  database: "mydb"
});

con.connect(function(err) {
  if (err) throw err;
	
	//JOIN
	var sql = "SELECT * FROM account JOIN planet WHERE account.id = planet.Account_id";
  con.query(sql, function (err, result) {
	if (err) throw err;
	console.log(result);
  });
});