const http = require('http')

const data = JSON.stringify({
  email: 'pussydestroyer1905@email.de',
  username: 'gameMaster32',
  password: 'abccba'
})
var url = 'http://127.0.0.1:8000/'
var querytype = '?type=register'
var queryplayer = '&playerid=1'
var queryplanet = '&planetid=1'
  
  const options = {
    hostname: '192.168.178.25',
    port: 8000,
    path: '/' + querytype + queryplayer + queryplanet,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Content-Length': data.length
    }
  }
  
  const req = http.request(options, (res) => {
    console.log(`statusCode: ${res.statusCode}`)
  
    res.on('data', (d) => {
      process.stdout.write(d)
    })
  })
  
  req.on('error', (error) => {
    console.error(error)
  })
  
  req.write(data)
  req.end()