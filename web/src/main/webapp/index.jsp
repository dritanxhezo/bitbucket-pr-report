<html>
<head>
<title>Welcome</title>
<link href="app.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h1>Welcome</h1>
<%
String url = "https://bitbucket.org/site/oauth2/authorize?client_id=" + net.ngeor.bprr.Settings.getInstance().getClientId() + "&response_type=code";
%>
<a href="<%= url %>">
    Authorize me
</a>

</body>
</html>
