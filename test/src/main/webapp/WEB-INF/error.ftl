<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据后台-问题页面</title>
    <script type="text/javascript">
        var num = 5;
        function redirect() {
            num--;
            document.getElementById("num").innerHTML = num;
            if (num < 0) {
                document.getElementById("num").innerHTML = 0;
                location.href = "login.html";
            }
        }
        setInterval("redirect()", 1000);
    </script>
</head>
<body>
<div class="admin-content">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">404</strong> /
            <small>That’s an error</small>
        </div>
    </div>

    <div class="am-g">
        <div class="am-u-sm-12">
            <h2 class="am-text-center am-text-xxxl am-margin-top-lg">404. Not Found</h2>

            <p class="am-text-center">
                没有找到你要的页面
            </p>
				<pre class="page-404">          .----.
       _.'__    `.
   .--($)($$)---/#\
 .' @          /###\
 :         ,   #####
  `-..__.-' _.-\###/
        `;_:    `"'
      .'"""""`.
     /,  ya ,\\
    //  404!  \\
    `-._______.-'
    ___`. | .'___
   (______|______)
        </pre>
            <p class="am-text-center">
                <a href="login.html" class="am-btn am-btn-primary">回到首页</a>
            </p>

            <p class="am-text-center">
                <span id="num">5</span>秒后自动跳转到首页<br/>
            </p>
        </div>
    </div>
</div>
</body>
</html>