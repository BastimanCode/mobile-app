function database(connection, queries){
    const queryobject = queries;
    var string = "";
    switch(queryobject.type){
            case "refresh":
                string = "SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE account.id = " + queryobject.playerid + " AND planet.id = " + queryobject.planetid;
                break;
            case "research":
                string = "SELECT * FROM research WHERE Account_id = " + queryobject.playerid;
                break;
            case "researches":
                string = "SELECT name, level, bonus, material, electronics, fuel, researches.description FROM researches JOIN researchdata ON researches.id = researchdata.Researches_id"
                break;
            case "buildings":
                string = "SELECT name, level, material, electronics, fuel, buildings.description FROM buildings JOIN buildingsdata ON buildings.id = buildingsdata.Buildings_id"
                break;
            case "productions":
                string = "SELECT name, level, material, electronics, fuel, production.description FROM production JOIN productiondata ON production.id = productiondata.Production_id"
                break;
            case "build":
                string = "SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE account.id = " + queryobject.playerid + " AND planet.id = " + queryobject.planetid;
                break;
            case "buildproduction":
                string = "SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE account.id = " + queryobject.playerid + " AND planet.id = " + queryobject.planetid;
                break;
            case "defensebuild":
                string = "SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE account.id = " + queryobject.playerid + " AND planet.id = " + queryobject.planetid;
                break;
            case "shipbuild":
                string = "SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE account.id = " + queryobject.playerid + " AND planet.id = " + queryobject.planetid;
                break;
            case "researching":
                string = "SELECT research.industry, research.laser, research.logistics, research.engine, research.weapon, research.shield, research.armor, account.id, planet.material, planet.electronics, planet.fuel from research join account on account.id = research.account_id join planet on account.id = planet.account_id";
                break;
            case "attack":
                string = "SELECT * from planet";
                break;          
            case "planets":
                string = "SELECT * FROM planet JOIN account ON planet.Account_id = account.id"
            case "shipdata":
                string = "SELECT * FROM shipdata"
    }
    return connection.query(string);    
}

function databasePost(connection, queries, data, planet){
    const queryobject = queries;
    var string;
    var time = new Date();
    switch(queryobject.type){
        case "login":
            string = "UPDATE account SET last_online = " + time.getTime() + " WHERE email = '" + data.email + "';" +
            " SELECT account.id, account.email, account.username, account.password, account.last_online, planet.id AS planet_id FROM account JOIN planet ON account.id = planet.Account_id WHERE account.email = '" + data.email + "'";
            break;
        case "register":
            string = "INSERT INTO account (email, username, password, last_online) VALUES ('" + data.email + "', '" + data.username + "', '" + data.password + "', '" + time.getTime() + "');" +
            " INSERT INTO planet (name, size, temperature, x, y, material, electronics, fuel, account_id) VALUES ('placeholder', " + (100 + (Math.random() * 551)) + ", " + ((Math.random() * -50) + (Math.random() *  80)) + ", " + planet.x + ", " + planet.y + ", 500, 500, 300, (SELECT id FROM account WHERE username = '" + data.username + "'));" + 
            " INSERT INTO research (account_id) VALUES ((SELECT id FROM account WHERE username = '" + data.username + "'));" + 
            " SELECT * FROM account where email = '" + data.email + "' and password = '" + data.password + "';";
            break;
    }
    return connection.query(string);
}

module.exports.database = database;
module.exports.databasePost = databasePost;
module.exports.databaseUpdate = databasePost;