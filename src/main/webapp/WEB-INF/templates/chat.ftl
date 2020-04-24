<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script>
        var webSocket;

        function connect() {
            webSocket = new SockJS('http://localhost:8080/chat');
            webSocket.onmessage = function receiveMessage(response) {
                let data = response['data'];
                let json = JSON.parse(data);
                $('#messages').append('<li>' + json['userId'] + ':' + json['text'] + '</li>');
            };
            getChat();
        }

        function sendMessage(userId, roomToken, text) {
            let body = {
                userId: userId,
                roomToken: roomToken,
                text: text
            };
            webSocket.send(JSON.stringify(body))
        }

        function getChat() {
            $.ajax({
                url: "/api/messages",
                method: "GET",
                dataType: "json",
                data: {
                    "token": '${room.token}'
                },
                contentType: "application/json",
                success: function (response) {
                    for (let i in response) {
                        $('#messages').append('<li>' + response[i]['userId'] + ':' + response[i]['text'] + '</li>')
                    }
                }
            })
        }
    </script>
</head>
<body onload="connect()">
<h1>Username : ${user.username}</h1>
<h1>Room: ${room.id}</h1>
<div>
    <input id="message" placeholder="Your message">
    <button onclick="sendMessage('${user.id}', '${room.token}', $('#message').val())">Send</button>
</div>
<div>
    <ul id="messages">

    </ul>
</div>
</body>
</html>