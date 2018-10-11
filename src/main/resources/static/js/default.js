$(function () {
    initTable();
    showFilter();
});

function start() {
    patch("/start")
        .then(response => response.text())
        .then(data => alert(data))
}

function shutdown() {
    patch("/shutdown").then((response) => alert(response.text()));
}

function showFilterEditor() {
    document.getElementById("FilterEditor").style.visibility = "";
}

function setFilter() {
    $.post("/filter", $('#FilterEditor').serialize(), function (data) {
        $.ajaxSuccess(alert(data));
        showFilter();
    });
}

function showFilter() {
    $.get("/filter", function (data) {
        $.ajaxSuccess($("#info-panel").append("<h4>当前过滤器为 " + data));
    })
}

function addTable(response) {
    const status = getStatus(response.method);
    const tableBody = getTableBody(response.method);
    const data = response.data;
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
        case contain(response.method, "get"):
            addTable(response);
            break;
        case contain(response.method, "update"):
            updateTable(response);
            break;
        default :
            console.log(response);
    }

}

function contain(method, keyword) {
    if (method.indexOf(keyword) !== -1) {
        return method;
    } else {
        return false;
    }
}

function getStatus(method) {
    let status;
    switch (method) {
        case contain(method, "filtered"):
            status = "等待加入下载队列";
            break;
        case contain(method, "downloaded"):
            status = "下载完成";
            break;
        case contain(method, "toDownload"):
            status = "正在下载";
            break;
        case contain(method, "downloadError"):
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
        case contain(method, "filtered"):
            tableBody = $("#info-table-body-filtered");
            break;
        case contain(method, "downloaded"):
            tableBody = $("#info-table-body-downloaded");
            break;
        case contain(method, "toDownload"):
            tableBody = $("#info-table-body-toDownloaded");
            break;
        case contain(method, "downloadError"):
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
    $.get("/info");
}

function patch(path) {
    return fetch(path, {method: 'PATCH'});
}

function put(path) {
    return fetch(path, {method: 'PUT'})
}
