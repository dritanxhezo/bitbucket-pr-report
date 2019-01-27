<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Welcome</title>
<link href="<%= request.getContextPath() %>/app.css" type="text/css" rel="stylesheet" />
</head>
<body>
  <%@ include file="includes/menu.jsp" %>

  <form method="POST" action="<%= request.getContextPath() %>/login">
    <label>Repository owner</label>
    <input name="owner" autocomplete>
    <label>Username</label>
    <input name="username" autocomplete>
    <label>Password</label>
    <input name="password" type="password" autocomplete>
    <button type="submit">Submit</button>
  </form>

</body>
</html>
