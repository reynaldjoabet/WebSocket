const inputField=document.getElementById("chats");
const message=document.getElementById("message-input");
const socketRoute=document.getElementById("route").value;
/* create a socket and replace http with ws, which is for sockets */
const socket= new WebSocket(socketRoute.replace("http","ws"));
inputField.onkeydown=(event)=>{
    if(event.key==='Enter') {
        socket.send(inputField.value);
        inputField.value = '';
    }
}
   socket.onopen= ()=>socket.send("Hello from browser");
socket.onmessage=(event)=>{
    message.value += '\n'+ event.data;
}