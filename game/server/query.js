function database(connection, queries){
    const queryobject = queries;
    var string
    switch(queryobject.type){
            case "refresh":
                string = "SELECT * FROM account JOIN planet ON account.id = planet.Account_id WHERE account.id = " + queryobject.playerid + " AND planet.id = " + queryobject.planetid;
                break;
            case "research":
                string = "SELECT * FROM forschung WHERE Account_id = " + queryobject.playerid;
                break; 
    }
    return connection.query(string);
}

module.exports.database = database;

