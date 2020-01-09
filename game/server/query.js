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
            case "planets":
                string = "SELECT * FROM planet JOIN account ON planet.Account_id = account.id"
    }
    return connection.query(string);    
}

function databasePost(connection, queries, data){
    const queryobject = queries;
    var string;
    let datetime = new Date();
    switch(queryobject.type){
        case "login":
            string = "UPDATE Account SET last_online = '" + datetime.getTime() + "' WHERE email = '" + data.email + "';" + 
            " SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE email = '" + data.email + "' AND password = '" + data.password + "'";
            break;
        case "register":
            string = "INSERT INTO account (email, username, password, last_online) VALUES ('" + data.email + "', '" + data.username + "', '" + data.password + "', '" + datetime.getTime() + "');" +
            " INSERT INTO Research (Account_id) VALUES (SELECT id FROM Account WHERE email = '" + data.email + "');" +
            //" INSERT INTO Planet ..."
            " SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE email = '" + data.email + "' and password = '" + data.password + "';";
            
            break;
    }
    return connection.query(string);
}

function databaseUpdate(connection, queries, material, electronics, fuel){
    const queryobject = queries;
    let time = new Date();    
    //connection.query("UPDATE planet set material = " + material + ", electronics = " + electronics + ", fuel = " + fuel + " WHERE Account_id = " + 1 + " AND id = " + 1);
    //connection.query("UPDATE account set last_online = " + time.getTime() + " WHERE id = " + queryobject.playerid);
    //return connection.query("SELECT * FROM planet");

}

module.exports.database = database;
module.exports.databasePost = databasePost;
module.exports.databaseUpdate = databasePost;