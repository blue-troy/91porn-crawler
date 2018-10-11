//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://localhost:8080/websocket");
    console.log("link success")
} else {
    alert('Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function () {
    alert("与服务器失去连接");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    console.log("webSocket open");
};
//接收到消息的回调方法
websocket.onmessage = function (event) {
    const response = JSON.parse(event.data);
    console.log(response);
    switch (response.method) {
        case "/scanner/count":
            $("#scan-count").html("<h4>扫描到了" + response.data + "个视频</h4>");
            break;
        case "/filteredMovies/get":
            handleTable(response);
    }
};

//连接关闭的回调方法
websocket.onclose = function () {
    console.log("webSocket close");
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    websocket.close();
};

//关闭连接
function closeWebSocket() {
    websocket.close();
}