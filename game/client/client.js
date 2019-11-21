const http = require('http');

var url = 'http://127.0.0.1:8000/'

var querytype = '?type=refresh'
var querytype2 = '?type=attack'
//'?type=buildings'

var queryplayer = '&playerid=1'
var queryplanet = '&planetid=1'

const options = {
    hostname: '127.0.0.1',
    port: 8000,
    path: '/',
    method: 'GET'
};
    
const request = http.request(url + querytype + queryplayer + queryplanet, function(res) {
    console.log('statusCode: ' + res.statusCode);

    var body ='';

    res.on('data', function(d) {
        body += d;   
    });

    res.on('end', function(){
        body = JSON.parse(body);
        console.log(body);        
    });
});

request.on('error', function(error) {
    console.error(error);
});

request.end();