const http = require('http');

const options = {
    hostname: '127.0.0.1',
    port: 8000,
    path: '/test',
    method: 'GET'  
};

const request = http.request(options, function(res) {
    console.log('statusCode: ' + res.statusCode);

    var body ='';

    res.on('data', function(d) {
        body += d;   
    });

    res.on('end', function(){
        body = JSON.parse(body);
        console.log(body[0].id);
        console.log(body[0].email);
        console.log(body[0].username);
        console.log(body[0].password);
        console.log(body[0].name);
        console.log(body[0].Account_id);
    });
});

request.on('error', function(error) {
    console.error(error);
});

request.end();