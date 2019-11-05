const http = require('http');

const hostname = '127.0.0.1';
const port = 8000;

const server = http.createServer(function(request, response) {
  if (request.method == 'POST') {
    console.log('POST');
    var body = '';
    request.on('data', function(data) {
      body += data;
      //console.log('Partial Body: ' + body);
    });
    request.on('end', function() {
      var newBody = JSON.parse(body);
      var zahl1 = newBody.zahl1;
      var zahl2 = newBody.zahl2;
      var resBody = Number(zahl1) + Number(zahl2);
      console.log('Nachricht: ' + zahl1 + ', ' + zahl2);
      response.writeHead(200, {'Content-Type': 'text/html'})
      response.end('message recieved\n' + resBody);
    });
  }
});

server.listen(port, hostname, function() {
  console.log(`Server running at http://${hostname}:${port}/`);
});