function refresh(connection, queries) {
    const queryobject = queries;
    return connection.query("SELECT * FROM account");
}
// "SELECT * FROM account JOIN planet WHERE account.id = planet.Account_id AND planet.id = " + queryobject.planetid

function database(connection, queries){
    const queryobject = queries;
    var string
    switch(queryobject.type){
            case "refresh":
                string = "SELECT * FROM account";
                break;
    }
    return connection.query(string);
}

module.exports.refresh = refresh;
module.exports.database = database;

