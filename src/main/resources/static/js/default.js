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

function setProxy() {
    const form = new FormData(document.getElementById('set-proxy-form'));
    fetch("/proxy", {
        method: 'PATCH',
        body: form,
    }).then(response => {
        if (response.ok) {
            alert("代理设置成功");
            $('#setProxyModal').modal('hide');
        }
    })

}

function setFilter() {
    $.post("/filter", $('#FilterEditor').serialize(), function (data) {
        alert(data);
        showFilter();
        document.getElementById("FilterEditor").style.visibility = "hidden";
    });
}

function showFilter() {
    $.get("/filter", function (data) {
        $("#filter-info").html("<h4>当前过滤器为 " + data);
    })
}

function showScannedMovieCount(numberOfScannedMovies) {
    $("#scan-count").html("<h4>扫描到了 " + numberOfScannedMovies + "个视频");
}

function handleTable(response) {
    const data = response.data;
    for (let index in data) {
        const movie = new Movie(data[index], response.method);
        $("#" + movie.id).remove();
        movie.tableBody.append(`<tr id=${movie.id}>
                <th>${movie.title}</th>
                <th>${movie.collect}</th>
                <th>${movie.author}</th>
                <th>${movie.status}</th>
            </tr>`
        );
    }
}

//todo 下面两个方法可以改成一个方法
class Movie {
    constructor(data, method) {
        this.title = data.title;
        this.collect = data.collect;
        this.author = data.author;
        this.id = Movie.getId(data.key);
        switch (method) {
            case "/filteredMovies" :
                this.status = "扫描过滤";
                this.tableBody = $("#info-table-body-filtered");
                break;
            case "/toDownloadMovies":
                this.status = "等待加入下载队列";
                this.tableBody = $("#info-table-body-toDownload");
                break;
            case "/downloadingMovies" :
                this.status = "正在下载";
                this.tableBody = $("#info-table-body-downloading");
                break;
            case "/downloadedMovies":
                this.status = "下载完成";
                this.tableBody = $("#info-table-body-downloaded");
                break;
            case "/downloadErrorMovies":
                this.status = "下载失败";
                this.tableBody = $("#info-table-body-downloadError");
                break;
            default:
                console.log("无法匹配状态");
                break;
        }
    }

    static getId(key) {
        return key.substring(key.indexOf("=") + 1, key.indexOf("&"));
    }
}

function login() {
    $.post("/user/login", $("#login-form").serialize()).done(function (res) {
        alert(res);
        $('#loginModal').modal('hide');
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
