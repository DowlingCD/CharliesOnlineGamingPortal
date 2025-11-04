<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="repo.PlayersRepo" %>
<%
    Integer playerId = (Integer) session.getAttribute("playerId");
    String tag = (String) session.getAttribute("gamerTag");
    if (playerId == null || tag == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    int balance = 0;
    try { balance = new PlayersRepo().getCredits(playerId); } catch (Exception ignore) {}
%>
<html>
<head><title>Dashboard</title></head>
<body>
<h2>Welcome, <%= tag %>!</h2>
<p>Current Balance: <strong><%= balance %></strong> credits</p>

<h3>Earn Credits</h3>
<form method="post" action="credit">
    <input type="hidden" name="action" value="earn">
    <label>Amount: <input name="amount" type="number" min="1" required></label>
    <button type="submit">Add</button>
</form>

<h3>Spend Credits</h3>
<form method="post" action="credit">
    <input type="hidden" name="action" value="spend">
    <label>Amount: <input name="amount" type="number" min="1" required></label>
    <button type="submit">Spend</button>
</form>

<p><a href="logout">Logout</a></p>
</body>
</html>
