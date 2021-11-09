var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
  $("#message").html("");
}

function connect() {
  var socket = new SockJS('/websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnected(true);
    console.log('Connected: ' + frame);
    // /receive/messageでメッセージ受信&表示
    stompClient.subscribe('/receive/message', function (response) {
      showMessage(JSON.parse(response.body));
    });
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendMessage() {
  // /send/messageエンドポイントにメッセージを送信する
  stompClient.send("/send/message", {}, JSON.stringify(
      {'name': $("#name").text(), 'statement': $("#statement").val()}));
  $("#statement").val('');
}

function showMessage(message) {
  // 受信したメッセージを整形して表示
  $("#message").append(
      "<tr><td>" + message.name + ": " + message.statement + "</td></tr>");
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $("#connect").click(function () {
    connect();
  });
  $("#disconnect").click(function () {
    disconnect();
  });
  $("#send").click(function () {
    sendMessage();
  });

//過去メッセージのやり取り情報を取得
  var request = new XMLHttpRequest();
    request.open("GET", "/history", true);
    request.onload = function(){
      const messageJson = JSON.parse(request.response);
      messageJson.historyMessages.reverse().forEach(function(hmessage){
        $("#history").append("<tr><td>" + hmessage + "</td></tr>");
      });
    }
    request.send();
});

setTimeout("connect()", 3000);
