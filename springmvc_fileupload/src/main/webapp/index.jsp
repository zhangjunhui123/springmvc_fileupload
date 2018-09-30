<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<html>
<head>
    <title>WELCOME</title>
</head>
<body>
<center><h2>还有10秒开始游戏，加油！特种兵</h2></center>

<a href="user/xxx">xxx</a>

<form action="user/testFileupload" method="post" enctype="multipart/form-data" >
    选择文件：<input type="file" name="upload">
    <input type="submit" value="上传">
</form>

<form action="user/testFileupload2" method="post" enctype="multipart/form-data">
    选择文件：<input type="file" name="upload" /><br/>
    <input type="submit" value="上传" />
</form>


<a href="user/testException">异常跳转</a>


<a href="user/testInterceptor">拦截器</a>
</body>
</html>
