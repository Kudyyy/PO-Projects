/**
 * Created by daniel on 21.01.2017.
 */
//Establish the WebSocket connection and set up event handlers
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/main/");
webSocket.onmessage = function (msg) { updateChat(msg); };
webSocket.onopen = function () {
    checkCookie();
}
//Send message if "Send" is clicked
id("send").addEventListener("click", function () {
    sendMessage(id("message").value);
});

//Send message if enter is pressed in the input field
id("message").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendMessage(e.target.value); }
});

//Send a message if it's not empty, then clear the input field
function sendMessage(message) {
    if (message !== "") {
        var username = getCookie("username");
        webSocket.send(message);
        id("message").value = "";
    }
}

function setCookie(cname, cvalue) {
    var d = new Date();
    document.cookie = cname + "=" + cvalue + ";path=/";
}

function checkCookie() {
    var username = getCookie("username");
    if (username == "") {
        username = prompt("Please enter your name:", "");
        if (username != "" && username != null) {
            setCookie("username", username);
        }
    }
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

//Update the chat-panel, and the list of connected users
function updateChat(msg) {
    var data = JSON.parse(msg.data);
    insert("channels", "\<a href=\"channel.html?channel="+ data.channelNumber + "\" \style\=\"text\-decoration\: none\"  >"+ data.userMessage+"\</a>");
}

//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}

