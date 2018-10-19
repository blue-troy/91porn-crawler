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
        $.ajaxSuccess($("#filter-info").append("<h4>当前过滤器为 " + data));
    })
}

function showScannedMovieCount(numberOfScannedMovies) {
    $("#scan-count").append("<h4>扫描到了 " + numberOfScannedMovies + "个视频");
}

function handleTable(response) {
    console.log(response);
    const status = getStatus(response.method);
    const tableBody = getTableBody(response.method);
    const data = response.data;
    console.log(status, tableBody, data);

    function getId(key) {
        return key.substring(key.indexOf("=") + 1, key.indexOf("&"));
    }

    for (let key in data) {
        const id = getId(key);
        $("#" + id).remove();
        tableBody.append("<tr id=" + id +
            ">\n" +
            "                <th>" + data[key].title + "</th>\n" +
            "                <th>" + data[key].collect + "</th>\n" +
            "                <th>" + data[key].author + "</th>\n" +
            "                <th>" + status + "</th>\n" +
            "            </tr>"
        )
    }
}

function contain(method, keyword) {
    if (method.indexOf(keyword) !== -1) {
        return method;
    } else {
        return false;
    }
}

//todo 下面两个方法可以改成一个方法

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
