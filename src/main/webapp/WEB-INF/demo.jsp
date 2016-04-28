<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Demo</title>
<link href="app.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h1>Pull Requests Overview</h1>

<table>
<tr>
    <th>Id</th>
    <th>Description</th>
    <th>State</th>
    <th>Created On</th>
    <th>Author</th>
    <th>Approved By</th>
</tr>
<c:forEach var="pr" items="${pullRequests}">
    <tr>
        <td>${pr.id}</td>
        <td>${pr.description}</td>
        <td>${pr.state}</td>
        <td>${pr.createdOn}</td>
        <td>${pr.author}</td>
        <td>
            <c:forEach var="reviewer" items="${pr.reviewers}">
                ${reviewer}
            </c:forEach>
        </td>
    </tr>
</c:forEach>
</table>

</body>
</html>