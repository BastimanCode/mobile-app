const express = require('express');
const app = express();


app.get('/refresh', (req, res) => {
    res.send(req.param.user);
});


app.listen(3000);