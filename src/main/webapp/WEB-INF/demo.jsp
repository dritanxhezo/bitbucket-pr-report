<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Demo</title>
</head>
<body>
Hello from the demo.jsp

Page ${repositories.page}
Size ${repositories.size}
Page Len ${repositories.pageLen}
Next ${repositories.next}

<table>
<tr>
    <th>Name</th>
    <th>Full name</th>
    <th>Actions</th>
</tr>
<c:forEach var="repo" items="${repositories.values}">
    <tr>
        <td>${repo.name}</td>
        <td>${repo.fullName}</td>
        <td><a href="pull-requests?repo=${repo.fullName}&link=${repo.links.pullRequests.href}">View Pull Requests</a></td>
    </tr>
</c:forEach>
</table>

</body>
</html>