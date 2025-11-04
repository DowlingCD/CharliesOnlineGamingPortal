<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Register</title></head>
<body>
<h2>Register</h2>
<form method="post" action="register">
    <label>Gamer Tag: <input name="gamerTag" required></label><br>
    <label>Password: <input type="password" name="password" required></label><br>
    <label>Confirm Password: <input type="password" name="confirmPassword" required></label><br>
    <button type="submit">Create Account</button>
</form>
<p style="color:red;">${error}</p>
<p><a href="login.jsp">Back to login</a></p>
</body>
</html>
