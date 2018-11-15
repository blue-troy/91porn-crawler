$(function () {
    initTable();
    showFilter();
});

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
    }).finally(() => filerEditor.style.visibility = "hidden");
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
