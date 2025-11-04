<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Online Gaming Portal</h2>
<form method="post" action="login">
    <label>Gamer Tag: <input name="gamerTag"></label><br>
    <label>Password: <input type="password" name="password"></label><br>
    <button type="submit">Login</button>
</form>
<p><a href="register.jsp">Create an account</a></p>
<p style="color:red;">${error}</p>
</body>
</html>
