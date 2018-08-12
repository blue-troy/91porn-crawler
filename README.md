# crawler91
## 自动爬取下载91优秀视频
定时自动爬取91热门视频，默认自动下载收藏量200以上并且标题带有露脸字眼的视频。
* 定时时间可由com.bluetroy.crawler91.command.ScanCommand 进行调整。
* 过滤器可由com.bluetroy.crawler91.crawler.Filter 进行配置。
## 技术栈
- [Spring-Boot](https://github.com/spring-projects/spring-boot)
- [JsoupXpath](https://github.com/zhegexiaohuozi/JsoupXpath)
* 运行本程序需要安装>=java8
## 开发
导入IDE里时需要安装lombok插件。
### 环境
  ![](https://img.shields.io/badge/JAVA-1.8%2B-brightgreen.svg) ![](https://img.shields.io/badge/maven-3.0%2B-brightgreen.svg)
### 运行
```
java -jar proxyee-down.jar
