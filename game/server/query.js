module.exports = {
    refresh(connection, planet) {
        return connection.query("select * from account where planet.id = " + planet);
    }
};