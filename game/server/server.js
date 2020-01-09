const http = require('http');
const url = require('url');
const mysql = require('promise-mysql');
const queries = require('./query');

let server = null;

const hostname = '192.168.178.25';
const port = 8000;

var resourcelist;
var freeplanets;
var givennames;
var shipdata;
var defensedata;
var defenderwon;

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

function Spaceship(amount){
  this.amount = amount;
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

  con.query("SELECT production.name, productiondata.level, productiondata.output, productiondata.material, productiondata.electronics, productiondata.fuel FROM production JOIN productiondata ON production.id = productiondata.Production_id")
  .then(array => {
    resourcelist = array;
  })
  .error(e =>{
    console.log(e);
  });

  con.query("SELECT * FROM shipdata")
  .then(array => {
    shipdata = array;
  })
  .error(e =>{
    console.log(e);
  });

  con.query("SELECT username FROM account")
  .then(array => {
    givennames = array;
  })
  .error(e =>{
    console.log(e);
  });

  con.query("SELECT x,y,free FROM freeplanets WHERE free = 1")
  .then(array => {
    freeplanets = array;
    freeplanets = freeplanets.sort(function(a,b){return 0.5 - Math.random()});
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
              connection.query("UPDATE planet SET material = " + (e[0].material - costm) + ", electronics = " + (e[0].electronics - coste) + ", fuel = " + (e[0].fuel - costf) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
              connection.query("UPDATE planet SET " + buildings(queryObject.buildid) + " = " + (parseInt(queryObject.level) + 1) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
            }
            
          } else if(queryObject.type == "attack"){
            connection.query("SELECT scout, hunter, cruiser, battleship, destroyer, bomber, mothership, colonisationship FROM planet WHERE id = " + queryObject.planetid)
            .then(result => {
              connection.query("UPDATE planet SET scout = " + (result[0].scout - queryObject.scout) + ", hunter = " + (result[0].hunter - queryObject.hunter) + ", cruiser = " + (result[0].cruiser - queryObject.cruiser) + ", battleship = " + (result[0].battleship  - queryObject.battleship) + ", destroyer = " + (result[0].destroyer - queryObject.destroyer) + ", bomber = " + (result[0].bomber - queryObject.bomber) + ", mothership = " + (result[0].mothership - queryObject.mothership) + ", colonisationship = " + (result[0].colonisationship  - queryObject.colonisationship) + " WHERE id = " + queryObject.planetid);
            })
            connection.query("INSERT INTO fleets (scout, hunter, cruiser, battleship, destroyer, bomber, mothership, colonisationship, destinationplanet, startplanet) VALUES (" + queryObject.scout + ", " + queryObject.hunter + ", " +  queryObject.cruiser + ", " +  queryObject.battleship + ", " +  queryObject.destroyer + ", " +  queryObject.bomber + ", " +  queryObject.mothership + ", " +  queryObject.colonisationship  + ", " + queryObject.destination  + ", " + queryObject.planetid + " )");
            connection.query("SELECT scout, hunter, cruiser, battleship, destroyer, bomber, mothership, colonisationship FROM fleets WHERE startplanet = " + queryObject.planetid)
            .then(attacker => {
              connection.query("SELECT scout, hunter, cruiser, battleship, destroyer, bomber, mothership, colonisationship FROM planet WHERE id =  (SELECT destinationplanet FROM fleets WHERE startplanet = " + queryObject.planetid + ")")
              .then(defender => {
                result = simulateFight(attacker,defender)                
                if (defenderwon = true){
                  connection.query("UPDATE planet SET scout = " + result[0].amount + ", hunter = " + result[1].amount + ", cruiser = " + result[2].amount + ", battleship = " + result[3].amount + ", destroyer = " + result[4].amount + ", bomber = " + result[5].amount + ", mothership = " + result[6].amount + ", colonisationship = " + result[7].amount + " WHERE id = (SELECT destinationplanet FROM fleets WHERE startplanet = " + queryObject.planetid + ")");
                } else {
                connection.query("SELECT scout, hunter, cruiser, battleship, destroyer, bomber, mothership, colonisationship FROM fleets WHERE startplanet = " + queryObject.planetid)
                .then(fleet => {
                  console.log("UPDATE planet SET scout = " + (fleet.scout + result.scout) + ", hunter = " + (fleet.hunter + result.hunter) + ", cruiser = " + (fleet.cruiser + result.cruiser) + ", battleship = " + (fleet.battleship + result.battleship) + ", destroyer = " + (fleet.destroyer + result.destroyer) + ", bomber = " + (fleet.bomber + result.bomber) + ", mothership = " + (fleet.mothership + result.mothership) + ", colonisationship = " + (fleet.colonisationship + result.colonisationship) + " WHERE id = " + queryObject.id);
                  connection.query("UPDATE planet SET scout = " + (fleet.scout + result.scout) + ", hunter = " + (fleet.hunter + result.hunter) + ", cruiser = " + (fleet.cruiser + result.cruiser) + ", battleship = " + (fleet.battleship + result.battleship) + ", destroyer = " + (fleet.destroyer + result.destroyer) + ", bomber = " + (fleet.bomber + result.bomber) + ", mothership = " + (fleet.mothership + result.mothership) + ", colonisationship = " + (fleet.colonisationship + result.colonisationship) + " WHERE id = " + queryObject.id);
                  connection.query("UPDATE planet SET scout = 0, hunter = 0, cruiser = 0, battleship = 0, destroyer = 0, bomber = 0, mothership = 0, colonisationship = 0 WHERE id = (SELECT destinationplanet FROM fleets WHERE startplanet = " + queryObject.planetid );
                })
                }
                connection.query("DELETE FROM fleets where startplanet = " + queryObject.planetid);                                
              })
            })            
          }
          response.writeHead(200, {'Content-Type': 'application/json'})
          response.end(JSON.stringify(e));
        });
      } else if (request.method == "POST") {
        let body = [];
        let coordinates;
        let nameerror = false;
        request.on('data', (chunk) => {
          body.push(chunk);
        }).on('end', () => {
          body = Buffer.concat(body).toString();
          body = JSON.parse(body);
          //console.log(body);
          for(let i = 0; i < givennames.length; i++){
            if(givennames[i].username == body.username){
              nameerror = true;
            }
          }
          if(nameerror != true){
            if(queryObject.type == "register"){
              planet = freeplanets.pop();
              coordinates = planet;            
            }
            queries.databasePost(connection, queryObject, body, coordinates)
            .then(e => {
              
              //console.log(e);
              if(e[0] == null) {
                response.writeHead(400);
                response.end();
              } else {
                if(queryObject.type == "register"){
                  connection.query("UPDATE freeplanets SET free = 0 WHERE x = " + planet.x + " AND y = " + planet.y);
                }
                response.writeHead(200, {'Content-Type': 'application/json'})
                response.end(JSON.stringify(e));
              }
            })          
            .catch((err) => {
              console.error("Faulty database query!", err);
              response.writeHead(400);
              response.end();
            });
          } else {
              response.writeHead(400);
              response.end();
          }
        });
      }
    
  });
}

function simulateFight(attacker, defender){
  var spaceshipsattacker = [
    new Spaceship(attacker[0].scout),
    new Spaceship(attacker[0].hunter),
    new Spaceship(attacker[0].cruiser),
    new Spaceship(attacker[0].battleship),
    new Spaceship(attacker[0].destroyer),
    new Spaceship(attacker[0].bomber),
    new Spaceship(attacker[0].mothership),
    new Spaceship(attacker[0].colonisationship)
  ];

  var spaceshipsdefender = [
    new Spaceship(defender[0].scout),
    new Spaceship(defender[0].hunter),
    new Spaceship(defender[0].cruiser),
    new Spaceship(defender[0].battleship),
    new Spaceship(defender[0].destroyer),
    new Spaceship(defender[0].bomber),
    new Spaceship(defender[0].mothership),
    new Spaceship(defender[0].colonisationship)
  ];

  let attackerweapon = getAttack(shipdata, spaceshipsattacker);
  let attackerhitpoints = getHitpoints(shipdata, spaceshipsattacker);
  let attackershield = getShield(shipdata, spaceshipsattacker);

  let defenderweapon = getAttack(shipdata, spaceshipsdefender);
  let defenderhitpoints = getHitpoints(shipdata, spaceshipsdefender);
  let defendershield = getShield(shipdata, spaceshipsdefender);

  let atkMaxhitpoints = attackerhitpoints;
  let defMaxhitpoints = defenderhitpoints;

  let fightover = false;
  while(!fightover){
    attackerhitpoints += attackershield - defenderweapon;
    defenderhitpoints += defendershield - attackerweapon;
    if(attackerhitpoints <= 0){
      fightover = true;
      defenderwon = true;
      console.log("defender wins")
      return calculateLoses(spaceshipsdefender,(defenderhitpoints/defMaxhitpoints));
    }
    if(defenderhitpoints <= 0){
      fightover = true;
      defenderwon = false;
      console.log("attacker wins")
      return calculateLoses(spaceshipsattacker,(attackerhitpoints/atkMaxhitpoints));
    }
  }
}

function getAttack(ship, amount){
  let totalStrength = 0;
  for(let i = 0;i < ship.length; i++){
    totalStrength += ship[i].attack * ship[i].firerate * amount[i].amount; 
  }
  return totalStrength;
}

function getHitpoints(ship, amount){
  let totalHitpoints = 0;
  for(let i = 0;i < ship.length; i++){
    totalHitpoints += ship[i].hitpoints * amount[i].amount;
  }
  return totalHitpoints;
}

function getShield(ship, amount){
  let totalShield = 0;
  for(let i = 0;i < ship.length; i++){
    totalShield += ship[i].shield * amount[i].amount; 
  }
  return totalShield;
}

function getTotalAmount(amount){
  let totelAmount = 0;
  for(let i = 0;i < amount.length; i++){
    totalAmount += amount[i];
  }
  return totelAmount;
}

function calculateLoses(fleet, percentloses){
  for (let i = 0; i < fleet.length; i++) {
    fleet[i].amount = Math.round(fleet[i].amount * (100 - percentloses) / 100);
  }
  return fleet;
}
