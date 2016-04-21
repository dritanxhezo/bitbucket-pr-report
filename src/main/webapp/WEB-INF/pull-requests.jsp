<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Pull Requests</title>
</head>
<body>

Page ${pullRequests.page}
Size ${pullRequests.size}
Page Len ${pullRequests.pageLen}
Next ${pullRequests.next}

<table>
<tr>
    <th>Id</th>
    <th>Description</th>
    <th>Author</th>
    <th>Actions</th>
</tr>
<c:forEach var="pullRequest" items="${pullRequests.values}">
    <tr>
        <td>${pullRequest.id}</td>
        <td>${pullRequest.description}</td>
        <td>${pullRequest.author.username} ${pullRequest.author.displayName}</td>
        <td></td>
    </tr>
</c:forEach>
</table>

</body>
</html>