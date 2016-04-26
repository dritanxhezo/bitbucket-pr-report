<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Demo</title>
</head>
<body>
Hello from the demo.jsp

<table>
<tr>
    <th>Id</th>
    <th>Author</th>
    <th>Approved By</th>
</tr>
<c:forEach var="pr" items="${pullRequests}">
    <tr>
        <td>${pr.id}</td>
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