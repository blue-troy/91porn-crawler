package com.bluetroy.crawler91.crawler.tools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-11-12
 * Time: 6:22 PM
 */
public class XpathToolTest {

    @Test
    public void getLoginError() throws Exception {
        String loginResult = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Chinese homemade video</title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n" +
                "<meta name=\"description\" content=\"Free porn video, free amateur video, free xxx video\" />\n" +
                "<meta name=\"keywords\" content=\"Free porn video, free amateur video, free xxx video\" />\n" +
                "<meta name=\"RATING\" content=\"RTA-5042-1996-1400-1577-RTA\" />\n" +
                "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"RSS - 20 newest videos\" href=\"rss/new/\" />\n" +
                "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"RSS - 20 most viewed videos\" href=\"rss/views/\" />\n" +
                "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"RSS - 20 most commented videos\" href=\"rss/comments/\" />\n" +
                "<!--!!!!!!!!!!!!!!!!!!!!!!!! LIBRARY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->\n" +
                "<script type=\"text/javascript\">\n" +
                "var baseurl = 'http://91porn.com/';\n" +
                "var imgurl = 'http://91porn.com/images';\n" +
                "</script>\n" +
                "<script src=\"/ajax/cpaint2.inc.js\" type=\"text/javascript\"></script>\n" +
                "<script src=\"/js/myjavascriptfx.js\" type=\"text/javascript\"></script>\n" +
                "<!-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->\n" +
                "<!--!!!!!!!!!!!!!!!!!!!!!!!!! Processing SCRIPT !!!!!!!!!!!!!!!!!!!-->\n" +
                "<script language=\"JavaScript\" src=\"/js/indexonly.js\" type=\"text/javascript\"></script>\n" +
                "<script language=\"JavaScript\" src=\"/js/myjavascriptajax.js\" type=\"text/javascript\"></script>\n" +
                "<script language=\"JavaScript\" src=\"/js/myjavascript.js\" type=\"text/javascript\"></script>\n" +
                "\n" +
                "<link href=\"/css/tpl_style.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"/css/upload-progress.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "<!--[if IE]><link href=\"/css/tpl_ie_6.css\"  rel=\"stylesheet\" type=\"text/css\" media=\"screen\" /><![endif]-->\n" +
                "<!--[if IE 7]><link rel=\"stylesheet\" type=\"text/css\" href=\"/css/tpl_ie_7.css\" /><![endif]-->\n" +
                "<script type=\"text/javascript\"  src=\"/include/topbar.js\"></script>\n" +
                "\n" +
                "<!-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->\n" +
                "</head>\n" +
                "<body >\n" +
                "\t\t<script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.11.3.min.js\"></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"/js/jquery.cookie.js\"></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"/js/overHang.min5.js\"></script>\n" +
                "\t\t\n" +
                "<div id=\"topbar\">\n" +
                "<a href=\"\" onClick=\"closebar(); return false\"><img src=\"/images/close.gif\" border=\"0\" /></a>\n" +
                "<font color=red>★★★ <a href=http://h.p03.space/viewthread.php?tid=97046 target=blank>为了避免找不到回家的路，请收藏好地址发布页</a>★★★<br></font>&nbsp; <a href=pay.php>购买91VIP帐号</a>&nbsp;想与美女激情互动?<a href=http://pay.t7k.space/p2.php target=bkank>91PORN旗下视频聊天</a> \n" +
                "</div>\n" +
                "<div id=\"head\">\n" +
                "  <h3>\n" +
                "    \n" +
                "    \n" +
                "        <a href=\"http://91porn.com/signup.php\">注册</a> |\n" +
                "    <a href=\"http://91porn.com/login.php\">登录</a> \n" +
                "      <form name=\"language_form\" method=\"POST\" action=\"\">\n" +
                "    <select name=\"session_language\" onChange=\"document.language_form.submit();\">\n" +
                "    <option value=\"en_US\">English</option>\n" +
                "<option value=\"cn_CN\" selected=\"selected\">简体中文</option>\n" +
                "<option value=\"zh_ZH\">繁體中文</option>\n" +
                "    </select>\n" +
                "    </form>\n" +
                "      </h3>\n" +
                "    <h1><a href=\"http://91porn.com/\" title=\"Free porn video\" class=\"logo\">Free porn video</a></h1>\n" +
                "    <h2>最好的自拍视频</h2>\n" +
                "</div>\n" +
                "<!-- end div head -->\n" +
                "<div class=\"clear\"></div>\n" +
                "<div id=\"headnav\">\n" +
                "<table width=800 align=center  border=0>\n" +
                "<tr><td background=\"images/tpl_navbar_bg.gif\">\n" +
                "\n" +
                "    <div id=\"navcontainer\">\n" +
                "      <ul>\n" +
                "        <li><a href=\"http://91porn.com/index.php\" >首页</a></li>\n" +
                "        <li><a href=\"http://up.91p26.space/upload.php?user_upload=|6985e79c24bd42f4730d205892e1d69d\" >上传</a></li>        <li><a  href=\"http://91porn.com/v.php?next=watch\" >视频</a></li>                    \n" +
                "                <li><a target=blank href=\"http://h.p03.space\" >自拍论坛</a></li>        \n" +
                "<li><a target=blank href=\"https://cams.com/webcam/asian-babes-webcams?pid=p181878\" >美女视频</a></li>   \n" +
                "          <li><a target=blank href=\" http://adultfriendfinder.com/p/video/manager.cgi?action=sort_score&date_range=30_day&pid=g780824-ppc&ip=auto&race_video=2\" >性爱交友</a></li><li><a target=blank href=\"http://www.831no1.com\">电影中心</a></li> <li><a target=blank href=\"pay.php\">VIP会员</a></li>           \n" +
                "            \n" +
                "      </ul>\n" +
                "    </div>\n" +
                "      </td>\n" +
                "      </tr>\n" +
                "      <tr><td align=center>\n" +
                "                  <table><tr><td align=center>\n" +
                "   <IFRAME frameborder=0 scrolling=no SRC=\"http://fans.91p20.space/fans/header_cn.php\" height=720 width=1000></iframe>\n" +
                "        </td></tr>\n" +
                "        <tr><td align=center><font size=2 color=red>\n" +
                "通告:快速获取积分2万点的方法，<a href=my_profile.php>点击这里</a>。<br>上传有风险，注意保护隐私，你的影片可能会被其他人下载或转发，成为网红我们不负责任(当然也不收推广费)！  <br>请不要上传未成年及血腥暴力内容.<a href=http://h.p03.space/viewthread.php?tid=106624 target=blank>91porn地址永久发布页</a>。<a href=http://h.p03.space/viewthread.php?tid=187760>关于上传视频的新规定</a></font></td></tr>\n" +
                "        </table>     </td>\n" +
                "      </tr>\n" +
                "      </table>\n" +
                "  </div>\n" +
                "  <div id=\"navsubbar\">\n" +
                "    <p>\n" +
                "            <a href=\"http://91porn.com/my_profile.php\">我的档案</a>|\n" +
                "<a href=\"http://91porn.com/my_video.php\">我的视频</a>|\n" +
                "<a href=\"http://91porn.com/my_favour.php\">我的收藏</a>|\n" +
                "<a href=\"http://91porn.com/my_subs_users.php\">我关注的用户</a>\n" +
                "<a href=\"http://91porn.com/my_subs.php\">我关注用户的视频</a>\n" +
                "<a href=\"http://91porn.com/my_comment.php\">我的留言</a>\n" +
                "\n" +
                "\n" +
                "                </p>\n" +
                "  </div>\n" +
                "</div>\n" +
                "<!-- end div headnav -->\n" +
                "<div id=\"container\"><div class=\"errorbox\">\n" +
                "无效的验证码\n" +
                "</div>\n" +
                "<script src=\"js/fingerprint.js\"></script>\n" +
                "<script src=\"js/fingerprint2.min.js\"></script>\n" +
                "\n" +
                "<div id=\"leftside\">\n" +
                "  <div id=\"mediumbox\">\n" +
                "\t\t<div id=\"mediumbox-title\">Information of 91porn</div>\n" +
                "\t\t<div id=\"mediumbox-content\">\n" +
                "  \t\t\t<div class=\"arrow-general\">&nbsp;</div>\n" +
                "\t \t\t <a href=\"#\" title=\"Previous\"></a>\n" +
                "\t  \t\t<div class=\"contentbox\">\n" +
                "欢迎来到91porn，你可以观看免费的成人影片，而且你还可以上传你自己拍摄的视频，包括手机拍摄的视频。你还可以通过这里结交朋友。<br>请不要上传未成年人士的视频。也不要试图通过91porn做广告，你的帐号会被删除并封IP地址。<br>本站网址随时会被屏蔽，请记住<a href=http://h.p03.space/viewthread.php?tid=106624 target=blank>91porn地址发布页</a> 不要怪小编没有提醒你哦！<br>如果你还没有注册，<a href=signup.php>点击这里注册</a>\n" +
                "\t  \t\t</div>\n" +
                "\t\t</div>\n" +
                "\t  <div class=\"clear\"></div>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "<div id=\"rightside\">\n" +
                "\t<div id=\"login\">\n" +
                "        <div id=\"login-title\">登录</div>\n" +
                "\t\t\t<div id=\"login-content\">\n" +
                "  \t\t\t<div class=\"arrow-general\">&nbsp;</div>\n" +
                "\t\t\t\t<form id=\"loginForm\" name=\"loginForm\" method=\"post\" action=\"http://91porn.com/login.php\">\n" +
                "\t\t\t\t  <p>\n" +
                "\t\t\t\t\t<label for=\"name\">用户名:</label>\n" +
                "\t\t\t\t\t<br />\n" +
                "\t\t\t\t\t<input type=\"text\" name=\"username\" size=\"22\" tabindex=\"3\" id=\"name\" class=\"logintext\" />\n" +
                "\t\t\t\t\t<br />\n" +
                "\t\t\t\t\t<label for=\"password\">密码:</label>\n" +
                "\t\t\t\t\t<br />\n" +
                "\t\t\t\t\t<input name=\"password\" type=\"password\" class=\"logintext\" id=\"password\" tabindex=\"4\" size=\"22\" />\n" +
                "\t\t\t\t\t<input name=\"fingerprint\" type=\"hidden\" id=\"fingerprint\"  />\n" +
                "\t\t\t\t\t<br />\n" +
                "\t\t\t\t\t   <script type=\"text/javascript\">\n" +
                "       var fp1 = new Fingerprint();\n" +
                "      document.getElementById(\"fingerprint\").value = fp1.get();\n" +
                "   </script>\n" +
                "     <input name=\"fingerprint2\" type=\"hidden\" id=\"fingerprint2\"  />\n" +
                "\n" +
                "  <script type=\"text/javascript\">\n" +
                " \n" +
                "   \t   new Fingerprint2().get(function(result, components){\n" +
                "   \t\t\t\t   document.getElementById(\"fingerprint2\").value = result;\n" +
                "      });\n" +
                "   \n" +
                "      </script>\n" +
                "\n" +
                "     \n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t<script type=\"text/javascript\">reloadcode();</script>\n" +
                "\t\t\t\t\t<label for=\"password\">验证码:</label><input size=\"6\" tabindex=\"5\" name=\"captcha_input\" id=\"captcha_input\" value=\"\" onfocus=\"this.value='';this.style.fontStyle='normal'\"/><img onClick=\"reloadcode()\" id=\"safecode\" style=\"cursor:hand;\" src=\"captcha.php\" />\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\n" +
                "\t\t\t\t\t<br />\t\t\t\t\t\n" +
                "\t\t\t\t\t<a href=\"http://91porn.com/recoverpass.php\" title=\"Forgot!\">忘了密码或用户名?</a><br />\n" +
                "\t\t\t\t\t<font color=red>最近发现有假冒91PORN网站骗取用户密码，请仔细辨别真伪。如果没有验证码，或者验证码字体不同，就是假的。<br>如果看不清验证码，点击验证码，会刷新新的验证码</font>\n" +
                "\t\t\t\t\t<br>\n" +
                "\t\t\t\t\t<input type=\"hidden\" name=\"action_login\"  value=\"Log In\" />\n" +
                "\t\t\t\t\t<input type=\"image\" src=\"images/login.gif\" tabindex=\"6\" class=\"loginbutton\" />\n" +
                "\t\t\t\t  </p>\n" +
                "\t\t\t\t</form>\n" +
                "\t\t\t</div>\n" +
                "      </div>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div></div> <!-- end div container -->\n" +
                "\n" +
                "\n" +
                "<div id=\"footer\">\n" +
                "\t<div id=\"footcontainer\">\n" +
                "    \t\t\t<p>\n" +
                " <IFRAME frameborder=0 scrolling=no SRC=http://fans.91p20.space/fans/footer_ad.html height=350 width=810></iframe>\n" +
                "    \n" +
                "    警告︰此网站只适合十八岁或以上人士观看。此网站内容可能令人反感；不可将此区的内容派发、传阅、出售、出租、交给或借予年龄未满18岁的人士或将本网站内容向该人士出示、播放或放映.如果你发现某些影片内容不合适，或者你是该影片的版权所有者而要求删除影片的，请联系我们，我们会很快做出回复。\n" +
                "     \t\t\t</p> \n" +
                "                <p> <A href=\"http://91porn.com\">Chinese homemade video</A> <A href=\"faq.php\">常见问题</A> | <a href=contact.php>联系我们</a> | <A href=\"dmca.php\">dmca</A> |\n" +
                "                    <A href=\"2257.php\">2257</A> | <a href=privacy.php>隐私政策</a>| <a href=terms.php>使用协议</a> \n" +
                "               </p>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "<br>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div align=center>\n" +
                "\t\n" +
                "</div>\n" +
                "<script type=\"text/javascript\">\n" +
                "var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\n" +
                "document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\n" +
                "</script>\n" +
                "<script type=\"text/javascript\">\n" +
                "var pageTracker = _gat._getTracker(\"UA-5740185-1\");\n" +
                "pageTracker._setSampleRate('10');\n" +
                "pageTracker._trackPageview();\n" +
                "</script>\n" +
                "\n" +
                "<div style=\"visibility:hidden\">\n" +
                "<script id=\"_wauy78\">var _wau = _wau || []; _wau.push([\"classic\", \"ba1gxc64qnfu\", \"y78\"]);\n" +
                "(function() { var s=document.createElement(\"script\"); s.async=true;\n" +
                "s.src=\"http://widgets.amung.us/classic.js\";\n" +
                "document.getElementsByTagName(\"head\")[0].appendChild(s);\n" +
                "} )();</script>\n" +
                "</div>\n" +
                "<script language=\"javascript\" type=\"text/javascript\" src=\"http://js.users.51.la/3878067.js\"></script>\n" +
                "<noscript><a href=\"http://www.51.la/?3878067\" target=\"_blank\"><img alt=\"&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;\" src=\"http://img.users.51.la/3878067.asp\" style=\"border:none\" /></a></noscript>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        String loginError = new XpathTool().getLoginError(loginResult);
        System.out.println(loginError);
    }
}