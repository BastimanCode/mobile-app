const http = require('http');

const data = JSON.stringify({
    zahl1: '4',
    zahl2: '6'
});

const options = {
    hostname: '127.0.0.1',
    port: 8000,
    path: '/test',
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Content-Lenght': data.length
    }
}

const request = http.request(options, function(res) {
    console.log('statusCode: ' + res.statusCode);

    res.on('data', function(d) {
        process.stdout.write(d);
    });
});

request.on('error', function(error) {
    console.error(error);
});

request.write(data);
request.end();