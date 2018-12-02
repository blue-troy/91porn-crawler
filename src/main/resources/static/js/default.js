$(function () {
    initTable();
    showFilter();
});

function download(key) {
    fetch(`/download/${key}`, {method: 'PATCH'})
        .then(response => {
            if (response.ok) {
                //
            } else {
                alert("下载失败")
            }
        });
}

function start() {
    fetch("/start", {method: 'PATCH'})
        .then(response => {
            if (response.ok) {
                alert("成功开启服务器")
            } else {
                alert("服务器开启失败")
            }
        })
}

function shutdown() {
    fetch("/shutdown", {method: 'PATCH'}).then(response => {
        if (response.ok) {
            alert("服务器关闭成功");
        } else {
            alert("服务器关闭失败");
        }
    });
}

function showFilterEditor() {
    document.getElementById("FilterEditor").style.visibility = "";
}

function toJSONString(form) {
    let obj = {};
    let elements = form.querySelectorAll("input, select, textarea");
    for (let i = 0; i < elements.length; ++i) {
        const element = elements[i];
        const name = element.name;
        const value = element.value;
        if (name) {
            obj[name] = value;
        }
    }
    return JSON.stringify(obj);
}

function setProxy() {
    fetch("/proxy", {
        method: 'PATCH',
        body: toJSONString(document.getElementById('set-proxy-form')),
        headers: {
            'content-type': 'application/json'
        },
    }).then(response => {
        if (response.ok) {
            alert("代理设置成功");
        } else {
            alert("代理设置失败,请输入正确的代理信息");
        }
    }).finally($('#setProxyModal').modal('hide'));

}

function setFilter() {
    const filerEditor = document.getElementById("FilterEditor");
    fetch("/filter", {
        method: 'POST',
        body: toJSONString(filerEditor),
        headers: {
            'content-type': 'application/json'
        },
    }).then(response => {
        if (response.ok) {
            alert("filter设置成功");
            showFilter();
        } else {
            alert("filter设置失败");
        }
    }).finally($('#filterModal').modal('hide'));
}

function showFilter() {
    $.get("/filter", function (data) {
        $("#filter-info").html("<h6>当前过滤器为 " + data);
    })
}

function showScannedMovieCount(numberOfScannedMovies) {
    $("#scan-count").html("<h5>当前爬取" + numberOfScannedMovies + "个视频");
}

function handleTable(response) {
    const data = response.data;
    for (let index in data) {
        const movie = new Movie(data[index], response.method);
        setTable(movie);
    }
}

function setTable(movie) {
    $("#" + movie.key).remove();
    movie.tableBody.append(`<tr id=${movie.key} class="${movie.status}">
                <th title="${movie.title}" >${movie.title.substring(0, 10)}</th>
                <th>${movie.length}</th>
                <th>${movie.addTime}</th>
                <th>${movie.author}</th>
                <th>${movie.view}</th>
                <th>${movie.collect}</th>
                <th>${movie.messageNumber}</th>
                <th>${movie.integration}</th>
                <th><a href="${movie.detailURL}">地址</a></th>
                <th>${!movie.downloadURL ? " " : `<a href="${movie.downloadURL}">地址</a>`} </th>
                <th>${movie.result}</th>
                <th onclick="download('${movie.key}')">${movie.command}</th>
            </tr>`
    );
}


class Movie {
    constructor(data, method) {
        const that = this;
        for (let key in data) {
            that[key] = data[key];
        }
        switch (method) {
            case "/scannedMovies":
                this.result = "扫描";
                this.tableBody = $("#info-table-body-scanned");
                this.command = "下载";
                break;
            case "/filteredMovies" :
                this.result = "过滤";
                this.tableBody = $("#info-table-body-filtered");
                break;
            case "/toDownloadMovies":
                this.result = "等待加入下载队列";
                this.tableBody = $("#info-table-body-toDownload");
                break;
            case "/downloadingMovies" :
                this.status = "table-info";
                this.result = "正在下载";
                this.tableBody = $("#info-table-body-downloading");
                break;
            case "/downloadedMovies":
                this.status = "table-success";
                this.result = "下载完成";
                this.command = "打开";
                this.tableBody = $("#info-table-body-downloaded");
                break;
            case "/downloadErrorMovies":
                this.status = "table-warning";
                this.result = "下载失败";
                this.command = "重新下载";
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
