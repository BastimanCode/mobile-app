module.exports = {
    refresh(connection, params) {
        return connection.query("select * from account");
    }
};