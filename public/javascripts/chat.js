const inputField=document.getElementById("chats");
const message=document.getElementById("message-input");
const socketRoute=document.getElementById("route").value;
/* create a socket and replace http with ws, which is for sockets */
const socket= new WebSocket(socketRoute.replace("http","ws"));
//("ws://localhost:9000/ws")
inputField.onkeydown=(event)=>{
    if(event.key==='Enter') {
        //convert to js object
        let message= {key:inputField.value};
        //convert js object to JSON
        socket.send(JSON.stringify(message));
        //just to console log,used to check form of json
        //console.log(JSON.stringify(message));
        inputField.value = '';


    }
}/* this is to send String to server
   socket.onopen= ()=>socket.send("Hello from browser");
*/
/*
this is to get String replies from the actor and display
socket.onmessage=(event)=>{
    message.value += '\n'+ event.data;
}

 */
// this is to send JSON to server
//()=>socket.send(JSON.stringify("Hello from browser"));
// this like others with (),represent and anonymous function

var imessage= {key:"Hello from browser"};
socket.onopen= ()=>socket.send(JSON.stringify(imessage));

// We get JSON data from actor and parse before displaying
socket.onmessage=(event)=>{
    const outputJson = JSON.parse(event.data);
    message.value += '\n'+outputJson.body;
}