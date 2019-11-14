var mysql = require('mysql');

//abfragen
var con = mysql.createConnection({
  host: "localhost",
  user: "admin",
  password: "password",
  database: "mydb"
});

con.connect(function(err) {
  if (err) throw err;
	
	//SELECT
  con.query("SELECT name, address FROM customers", function (err, result, fields) {
	if (err) throw err;
	console.log(result);
  });
	
	//WHERE
	var name = 'Amy';
	var adr = 'M%';
	//? = Placeholdermethode um webhacking zu vermeiden
	//Alternative: ...name = ' + mysql.escape(name);
	//Ohne Escape einfach in String schreiben
	var sql = 'SELECT * FROM customers WHERE name = ' + mysql.escape(name) + ' OR address LIKE ' + mysql.escape(adr);
	con.query(sql, [name, adr], function (err, result) {
  	if (err) throw err;
		console.log(result);
	});
	
	//UPDATE
	var update = "UPDATE customers SET address = 'Canyon 123' WHERE address = 'Valley 345'";
	con.query(update, function(err, result) {
		if (err) throw err;
		console.log(result.affectedRows + "record(s) updated");
	});
	
	//JOIN
	var sql = "SELECT users.name AS user, products.name AS favorite FROM users JOIN products ON users.favorite_product = products.id";
  con.query(sql, function (err, result) {
	if (err) throw err;
	console.log(result);
  });
});