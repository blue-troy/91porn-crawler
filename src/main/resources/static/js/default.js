$(function () {
    initTable();
    showFilter();
});

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

function addTable(response) {
    const status = getStatus(response.method);
    const tableBody = getTableBody(response.method);
    for (let key in data) {
        tableBody.append("<tr id=" + key +
            ">\n" +
            "                <th>" + data[key].title + "</th>\n" +
            "                <th>" + data[key].collect + "</th>\n" +
            "                <th>" + data[key].author + "</th>\n" +
            "                <th>" + status + "</th>\n" +
            "            </tr>"
        )
    }
}

function updateTable(response) {
    const status = getStatus(response.method);
    const tableBody = getTableBody(response.method);
    for (let key in data) {
        $("#" + key).remove();
        tableBody.append("<tr id=" + key +
            ">\n" +
            "                <th>" + data[key].title + "</th>\n" +
            "                <th>" + data[key].collect + "</th>\n" +
            "                <th>" + data[key].author + "</th>\n" +
            "                <th>" + status + "</th>\n" +
            "            </tr>"
        )
    }
}

function handleTable(response) {
    switch (response.method) {
        case method.match("get"):
            addTable(response);
            break;
        case method.match("update"):
            updateTable(response);
            break;
        default :
            console.log(response);
    }

}

function getStatus(method) {
    let status;
    switch (method) {
        case method.match("filtered"):
            status = "等待加入下载队列";
            break;
        case method.match("downloaded"):
            status = "下载完成";
            break;
        case method.match("toDownload"):
            status = "正在下载";
            break;
        case method.match("downloadError"):
            status = "下载失败";
            break;
        default:
            console.log("无法匹配状态");
            break;
    }
    return status;
}

function getTableBody(method) {
    let tableBody;
    switch (method) {
        case method.match("filtered"):
            tableBody = $("#info-table-body-filtered");
            break;
        case method.match("downloaded"):
            tableBody = $("#info-table-body-downloaded");
            break;
        case method.match("toDownload"):
            tableBody = $("#info-table-body-toDownloaded");
            break;
        case method.match("downloadError"):
            tableBody = $("#info-table-body-downloadError");
            break;
        default:
            console.log("无法匹配状态");
            break;
    }
    return tableBody;
}

function login() {
    $.post("/user/login", $("#login-form").serialize()).done(function (res) {
        $('#myModal').modal('hide');
    });
}

function initTable() {
    $("#info-table-body").empty();
    $.get("/info/get");
}

function echo() {
    fetch("/echo").then((response) => {
        return response.text()
    }).then((response) => {
        console.log(response);
    })
}