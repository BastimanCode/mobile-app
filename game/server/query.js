function database(connection, queries){
    const queryobject = queries;
    var string
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
    }
    return connection.query(string);    
}

function databasePost(connection, queries, data){
    const queryobject = queries;
    var string;
    switch(queryobject.type){
        case "login":
                string = "SELECT * FROM account where email = '" + data.email + "' and password = '" + data.password + "'";
                break;
            case "register":
                string = "INSERT INTO account (email, username, password) VALUES ('" + data.email + "', '" + data.username + "', '" + data.password + "');" +
                " SELECT * FROM account where email = '" + data.email + "' and password = '" + data.password + "';";
                break;
    }
    return connection.query(string);
}

module.exports.database = database;
module.exports.databasePost = databasePost;