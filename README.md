# WebSocketChat
Android chat app with WSS (WebSocket Secure) connection. MVP architectural pattern has been used. 

<h3>Building a WebSocket Server With NodeJS</h3>

Run the server.js file including the code block:

```javascript
// Importing the required modules
const WebSocketServer = require('ws');
 
// Creating a new websocket server
const wss = new WebSocketServer.Server({ port: 8080 })
 
// Creating connection using websocket
wss.on("connection", ws => {
    console.log("new client connected");
    // sending message
    ws.on("message", data => {
	const obj = JSON.parse(data);
        console.log(`Client -${obj.clientName}- has sent us: ${obj.message}`)
    });
    // handling what to do when clients disconnects from server
    ws.on("close", () => {
        console.log("the client has disconnected");
    });
    // handling client connection error
    ws.onerror = function () {
        console.log("Some Error occurred")
    }
});
console.log("The WebSocket server is running on port 8080");
```

<img width="582" alt="Screen Shot 2022-06-22 at 01 19 58" src="https://user-images.githubusercontent.com/34041050/174913234-6867ef27-2e76-4a0d-985c-740a9aec4451.png">

<h3>Running the Mobile WebSocket Client</h3>

![websocketchat](https://user-images.githubusercontent.com/34041050/174913584-dbfe654d-dbf1-485f-af67-a0359d01bc6c.gif)

<img width="585" alt="Screen Shot 2022-06-22 at 01 21 12" src="https://user-images.githubusercontent.com/34041050/174913623-9a3b2157-383c-49cf-b64e-71ec79fd852c.png">

<h3>Running a Web WebSocket Client With NodeJS</h3>

Run the client.html file including the code block:

```javascript
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>NodeJS WebSocket Client</title>
</head>
<body>
	<h1>Hello world</h1>
<script>
const ws = new WebSocket("ws://localhost:8080");
ws.addEventListener("open", () =>{
  console.log("We are connected");
  const data = JSON.stringify({ clientName: 'web client', message: 'How are you?' })
  ws.send(data);
});
 
ws.addEventListener('message', function (event) {
    console.log(event.data);
});
</script>
</body>
```

</html><img width="582" alt="Screen Shot 2022-06-22 at 01 22 17" src="https://user-images.githubusercontent.com/34041050/174913889-32b2396c-d479-4033-ba85-4843ffbfe8df.png">








