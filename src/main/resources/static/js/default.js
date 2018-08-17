function start() {
    $.get("/start", function (data) {
        $.ajaxSuccess(alert(data))
    })
}

function shutdown() {
    $.get("/shutdown", function (data) {
        $.ajaxSuccess(alert(data))
    })
}

function showFilterEditor() {
    document.getElementById("FilterEditor").style.visibility = "";
}

function setFilter() {
    $.post("/filter/set", $('#FilterEditor').serialize(), function (data) {
        $.ajaxSuccess(alert(data));
        showFilter();
    });
}

function showFilter() {
    $.post("/filter/get", function (data) {
        $.ajaxSuccess($("#info-panel").append("<h4>当前过滤器为 " + data));
    })
}

