<html>
<body>
<h2>Hello World!</h2>
<%
String url = "https://bitbucket.org/site/oauth2/authorize?client_id=" + net.ngeor.bprr.Settings.getInstance().getClientId() + "&response_type=code";
%>
<a href="<%= url %>">
    Authorize me
</a>

<p>Code:
<%= session.getAttribute("code") %>
</p>
</body>
</html>
