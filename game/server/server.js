const http = require('http');
const url = require('url');
const mysql = require('promise-mysql');
const queries = require('./query');

let server = null;

const hostname = '192.168.0.80';
const port = 8000;

var resourcelist;
var freeplanets;
var givennames;
var shipdata;
var defensepricelist;
var defenderwon;

function productionbuildings(number){
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

function buildings(number){
  switch(number){
    case "0":
      return "spaceshipyard";
    case "1":
      return "researchfacility";
    case "2":
      return "cyborgfactory";
    case "3":
      return "powerplant";
  }
}

function defense(number){
  switch(number){
    case "0":
      return "rocketlauncher";
    case "1":
      return "lasergun";
    case "2":
      return "iongun";
    case "3":
      return "shockwavecannon";
    case "4":
      return "plasmacannon";
    case "5":
      return "antimatterradiator";
    case "6":
      return "spacemines";
    case "7":
      return "planetshield";
  }
}

function ships(number){
  switch(number){
    case "0":
      return "scout";
    case "1":
      return "hunter";
    case "2":
      return "cruiser";
    case "3":
      return "battleship";
    case "4":
      return "destroyer";
    case "5":
      return "bomber";
    case "6":
      return "mothership";
    case "7":
      return "colonisationship";
  }
}

function researches(number){
  switch(number){
    case "0":
      return "industry";
    case "1":
      return "laser";
    case "2":
      return "logistics";
    case "3":
      return "engine";
    case "4":
      return "weapon";
    case "5":
      return "shield";
    case "6":
      return "armor";
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

  con.query("SELECT production.name, production.description, productiondata.level, productiondata.output, productiondata.material, productiondata.electronics, productiondata.fuel FROM production JOIN productiondata ON production.id = productiondata.Production_id")
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

  con.query("SELECT * FROM defense")
  .then(array => {
    defensepricelist = array;
  })
  .error(e =>{
    console.log(e);
  });

  con.query("SELECT * FROM researchdata")
  .then(array => {
    researchlist = array;
  })
  .error(e =>{
    console.log(e);
  });

  con.query("SELECT buildings.name, buildings.description, buildingsdata.level, buildingsdata.material, buildingsdata.electronics, buildingsdata.fuel FROM buildingsdata JOIN buildings ON buildings.id = buildingsdata.buildings_id")
  .then(array => {
    buildingslist = array;
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
              connection.query("UPDATE account set last_online = " + time.getTime() + " WHERE id = " + queryObject.playerid);
            }
          } else if(queryObject.type == "build"){
            costm = buildingslist[parseInt((queryObject.buildid * 11)) + parseInt(queryObject.level)].material;
            coste = buildingslist[parseInt((queryObject.buildid * 11)) + parseInt(queryObject.level)].electronics;
            costf = buildingslist[parseInt((queryObject.buildid * 11)) + parseInt(queryObject.level)].fuel;
            if(costm > e[0].material || coste > e[0].electronics || costf > e[0].fuel){
              response.writeHead(400);
              response.end();
            } else{        
              connection.query("UPDATE planet SET material = " + (e[0].material - costm) + ", electronics = " + (e[0].electronics - coste) + ", fuel = " + (e[0].fuel - costf) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
              connection.query("UPDATE planet SET " + buildings(queryObject.buildid) + " = " + (parseInt(queryObject.level) + 1) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
            }
          } else if(queryObject.type == "buildproduction"){
            costm = resourcelist[parseInt((queryObject.buildid * 26)) + parseInt(queryObject.level)].material;
            coste = resourcelist[parseInt((queryObject.buildid * 26)) + parseInt(queryObject.level)].electronics;
            costf = resourcelist[parseInt((queryObject.buildid * 26)) + parseInt(queryObject.level)].fuel;
            if(costm > e[0].material || coste > e[0].electronics || costf > e[0].fuel){
              response.writeHead(400);
              response.end();
            } else{        
              connection.query("UPDATE planet SET material = " + (e[0].material - costm) + ", electronics = " + (e[0].electronics - coste) + ", fuel = " + (e[0].fuel - costf) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
              connection.query("UPDATE planet SET " + productionbuildings(queryObject.buildid) + " = " + (parseInt(queryObject.level) + 1) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
            }
          } else if(queryObject.type == "researching"){
            costm = researchlist[parseInt((queryObject.buildid * 11)) + parseInt(queryObject.level)].material;
            coste = researchlist[parseInt((queryObject.buildid * 11)) + parseInt(queryObject.level)].electronics;
            costf = researchlist[parseInt((queryObject.buildid * 11)) + parseInt(queryObject.level)].fuel;
            if(costm > e[0].material || coste > e[0].electronics || costf > e[0].fuel){
              response.writeHead(400);
              response.end();
            } else{        
              connection.query("UPDATE planet SET material = " + (e[0].material - costm) + ", electronics = " + (e[0].electronics - coste) + ", fuel = " + (e[0].fuel - costf) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
              connection.query("UPDATE research SET " + researches(queryObject.buildid) + " = " + (parseInt(queryObject.level) + 1) + " WHERE account_id = " + queryObject.playerid);
            }
          } else if(queryObject.type == "defensebuild"){
            costm = defensepricelist[parseInt(queryObject.buildid)].material * parseInt(queryObject.amounttobuild);
            coste = defensepricelist[parseInt(queryObject.buildid)].electronics * parseInt(queryObject.amounttobuild);
            costf = defensepricelist[parseInt(queryObject.buildid)].fuel * parseInt(queryObject.amounttobuild);
            if(costm > e[0].material || coste > e[0].electronics || costf > e[0].fuel){
              response.writeHead(400);
              response.end();
            } else{        
              connection.query("UPDATE planet SET material = " + (e[0].material - costm) + ", electronics = " + (e[0].electronics - coste) + ", fuel = " + (e[0].fuel - costf) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
              connection.query("UPDATE planet SET " + defense(queryObject.buildid) + " = " + (parseInt(queryObject.amount) + parseInt(queryObject.amounttobuild)) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
            }
          } else if(queryObject.type == "shipbuild"){
            costm = shipdata[parseInt(queryObject.buildid)].material * parseInt(queryObject.amounttobuild);
            coste = shipdata[parseInt(queryObject.buildid)].electronics * parseInt(queryObject.amounttobuild);
            costf = shipdata[parseInt(queryObject.buildid)].fuel * parseInt(queryObject.amounttobuild);
            if(costm > e[0].material || coste > e[0].electronics || costf > e[0].fuel){
              response.writeHead(400);
              response.end();
            } else{        
              connection.query("UPDATE planet SET material = " + (e[0].material - costm) + ", electronics = " + (e[0].electronics - coste) + ", fuel = " + (e[0].fuel - costf) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
              connection.query("UPDATE planet SET " + ships(queryObject.buildid) + " = " + (parseInt(queryObject.amount) + parseInt(queryObject.amounttobuild)) + " WHERE id = " + queryObject.planetid + " AND Account_id = " + queryObject.playerid);
            }
          } else if(queryObject.type == "attack"){
            connection.query("SELECT scout, hunter, cruiser, battleship, destroyer, bomber, mothership, colonisationship FROM planet WHERE id = " + queryObject.planetid)
            .then(attacker => {
              connection.query("SELECT planet.scout, planet.hunter, planet.cruiser, planet.battleship, planet.destroyer, planet.bomber, planet.mothership, planet.colonisationship," + 
              " planet.rocketlauncher, planet.lasergun, planet.iongun, planet.shockwavecannon, planet.plasmacannon, planet.antimatterradiator, planet.spacemines, planet.planetshield" + 
              " FROM planet JOIN account ON planet.account_id = account.id WHERE planet.name = '" + queryObject.defendername + "'")
              .then(defender => {
                defenderwon = simulateFight(connection,queryObject, attacker, defender);
                e = defenderwon;
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

function simulateFight(connection, queryObject, attacker, defender){

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

  var defenses = [
    new Spaceship(defender[0].rocketlauncher),
    new Spaceship(defender[0].lasergun),
    new Spaceship(defender[0].iongun),
    new Spaceship(defender[0].shockwavecannon),
    new Spaceship(defender[0].plasmacannon),
    new Spaceship(defender[0].antimatterradiator),
    new Spaceship(defender[0].spacemines),
    new Spaceship(defender[0].planetshield)
  ]

  let attackerweapon = getAttack(shipdata, spaceshipsattacker);
  let attackerhitpoints = getHitpoints(shipdata, spaceshipsattacker);
  let attackershield = getShield(shipdata, spaceshipsattacker);
  
  let defenderweapon = getAttack(shipdata, spaceshipsdefender) + getAttack(defensepricelist, defenses);
  let defenderhitpoints = getHitpoints(shipdata, spaceshipsdefender) + getHitpoints(defensepricelist, defenses);
  let defendershield = getShield(shipdata, spaceshipsdefender) + getShield(defensepricelist, defenses);

  let atkMaxhitpoints = attackerhitpoints;
  let defMaxhitpoints = defenderhitpoints;

  let fightover = false;
  while(!fightover){

    attackerhitpoints += attackershield - defenderweapon;
    defenderhitpoints += defendershield - attackerweapon;
    if(attackerhitpoints <= 0){
      fightover = true;
      connection.query("UPDATE planet SET scout = 0, hunter = 0, cruiser = 0, battleship = 0, destroyer = 0, bomber = 0, mothership = 0, colonisationship = 0 WHERE id = " + queryObject.planetid);
      console.log("defender wins")
      return true;
    }
    if(defenderhitpoints <= 0){
      fightover = true;
      connection.query("UPDATE planet SET scout = 0, hunter = 0, cruiser = 0, battleship = 0, destroyer = 0, bomber = 0, mothership = 0, colonisationship = 0, rocketlauncher = 0, lasergun = 0, iongun = 0, shockwavecannon = 0, plasmacannon = 0, antimatterradiator = 0, spacemines = 0, planetshield = 0 WHERE name = '" + queryObject.defendername + "'");
      connection.query("SELECT material, electronics, fuel FROM planet WHERE name = '" + queryObject.defendername + "'")
      .then(result => {
        console.log("UPDATE planet SET material = material + " + result[0].material + ", electronics = electronics + " + result[0].electronics + ", fuel = fuel + " + result[0].fuel + " WHERE id = " + queryObject.planetid)
        connection.query("UPDATE planet SET material = material + " + result[0].material + ", electronics = electronics + " + result[0].electronics + ", fuel = fuel + " + result[0].fuel + " WHERE id = " + queryObject.planetid);
      })
      connection.query("UPDATE planet SET material = 0, electronics = 0, fuel = 0 WHERE name = '" + queryObject.defendername + "'");
      console.log("attacker wins")
      return false;
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
    fleet[i].amount = Math.round(fleet[i].amount * (100 - (100 * percentloses)) / 100);
  }
  return fleet;
}
