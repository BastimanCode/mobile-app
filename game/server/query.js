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
                string = "SELECT name, level, bonus, material, electronics, fuel FROM researches JOIN researchdata ON researches.id = researchdata.Researches_id"
                break;
            case "buildings":
                string = "SELECT name, level, material, electronics, fuel FROM buildings JOIN buildingsdata ON buildings.id = buildingsdata.Buildings_id"
                break;
            case "productions":
                string = "SELECT name, level, material, electronics, fuel FROM production JOIN productiondata ON production.id = productiondata.Production_id"
                break;
            case "build":
                string = "SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE account.id = " + queryobject.playerid + " AND planet.id = " + queryobject.planetid;
                break;
            case "attack":
                string = "select * from planet";
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
            string = "SELECT * FROM account where email = '" + data.email + "' and password = '" + data.password + "'";
            break;
        case "register":
            string = "INSERT INTO account (email, username, password, last_online) VALUES ('" + data.email + "', '" + data.username + "', '" + data.password + "', '" + time.getTime() + "');" +
            " SELECT * FROM account where email = '" + data.email + "' and password = '" + data.password + "';" + 
            " INSERT INTO planet (name, size, temperature, x, y, material, electronics, fuel, account_id) VALUES ('placeholder', " + (100 + (Math.random() * 551)) + ", " + ((Math.random() * -50) + (Math.random() *  80)) + ", " + planet.x + ", " + planet.y + ", 500, 500, 300, (SELECT id FROM account WHERE username = '" + data.username + "'));" + 
            " INSERT INTO research (account_id) VALUES ((SELECT id FROM account WHERE username = '" + data.username + "'))";
            break;
    }
    return connection.query(string);
}

module.exports.database = database;
module.exports.databasePost = databasePost;
module.exports.databaseUpdate = databasePost;