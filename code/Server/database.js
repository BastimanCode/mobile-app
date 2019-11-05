var mysql = require('mysql');

//anlegen
var con = mysql.createConnection({
  host: "localhost",
	user: "admin",
  password: "password",
  database: "mydb"
});

con.connect(function(err) {
	if (err) throw err;
	console.log("Connected!");
	
	//Datenbank anlegen
	/*con.query("CREATE DATABASE mydb", function (err, result) {
		if (err) throw err;
	console.log("Database created");
  });*/
	
	//Tabelle anlegen
	var customers = "CREATE TABLE customers (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), address VARCHAR(255))";
	con.query(customers, function(err, result) {
		if (err) throw err;
		console.log("Table created");
	});
	
	//Einzel Insert
	/*var customer1 = "INSERT INTO customers (name, address) VALUES ('Company Inc', 'highway 37')";
	con.query(customer1, function(err, result) {
		if (err) throw err;
		console.log("1 record inserted");
	});*/
	
	//Multi Insert
	var customers = "INSERT INTO customers (name, address) VALUES ?";
	var values = [
	['John', 'Highway 71'],
	['Peter', 'Lowstreet 4'],
	['Amy', 'Apple st 652'],
	['Hannah', 'Mountain 21'],
	['Michael', 'Valley 345'],
	['Sandy', 'Ocean blvd 2'],
	['Betty', 'Green Grass 1'],
	['Richard', 'Sky st 331'],
	['Susan', 'One way 98'],
	['Vicky', 'Yellow Garden 2'],
	['Ben', 'Park Lane 38'],
	['William', 'Central st 954'],
	['Chuck', 'Main Road 989'],
	['Viola', 'Sideway 1633']
	];
	con.query(customers, [values], function(err, result) {
		if (err) throw err;
		console.log("Number of records inserted: " + result.affectedRows);
	});
	//Result Object
	/*{
  fieldCount: 0,
  affectedRows: 14,
  insertId: 0,
  serverStatus: 2,
  warningCount: 0,
  message: '\'Records:14  Duplicated: 0  Warnings: 0',
  protocol41: true,
  changedRows: 0
	}*/
});

//con.end();