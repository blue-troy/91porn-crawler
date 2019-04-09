# crawler91
## 自动爬取下载91优秀视频
定时自动爬取91热门视频，自动下载符合的视频。
## 技术栈
- [Spring-Boot](https://github.com/spring-projects/spring-boot)
- [jsoup](https://github.com/jhy/jsoup)
## demo
![image](https://github.com/blue-troy/crawler91/blob/master/demo.png)
## 使用
### 普通用户
1. 安装java11
2. [下载release](https://github.com/blue-troy/91porn-crawler/releases)
3. 双击运行程序或使用命令行 java -jar crawler91.jar
4. 用浏览器打开网址http://localhost:8080 即可
5. 若无法爬取下载视频,则说明在墙内,需要[配置代理](https://github.com/blue-troy/91porn-crawler/blob/master/user-guide/配置代理.md)。
## 开发
导入IDE里时需要安装lombok插件。
### 环境
  ![](https://img.shields.io/badge/JAVA-11%2B-brightgreen.svg) ![](https://img.shields.io/badge/maven-3.0%2B-brightgreen.svg)
### 问题
当前项目存在的问题请查看[issue](https://github.com/blue-troy/91porn-crawler/issues).若遇到未知问题请在[issue](https://github.com/blue-troy/91porn-crawler/issues)中指出，谢谢
#### 重大问题
经反馈当前版本在windows系统无法正确运行，将在下个版本中修复此bug。详情查看[issue](https://github.com/blue-troy/91porn-crawler/issues/25)

