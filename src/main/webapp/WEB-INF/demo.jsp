<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>Demo</title>
<link href="app.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h1>Pull Requests Overview</h1>

<form method="POST" action="${view.formUrl}">
    <label for="repo">Repository</label>
    <input id="repo" name="repo" value="${view.repo}" />

    <label for="updatedOn">Updated On</label>
    <input id="updatedOn" name="updatedOn" value="${view.updatedOn}" />

    <input type="submit" />
</form>

<table>
<tr>
    <th>Id</th>
    <th>Description</th>
    <th>State</th>
    <th>Created On</th>
    <th>Updated On</th>
    <th>Author</th>
    <c:forEach var="number" begin="1" end="${view.maxReviewerCount}">
        <th>Approved By ${number}</th>
    </c:forEach>
</tr>
<c:forEach var="pr" items="${view.pullRequests}">
    <tr>
        <td>${pr.id}</td>
        <td>${fn:escapeXml(pr.description)}</td>
        <td>${pr.state}</td>
        <td>${pr.createdOn}</td>
        <td>${pr.updatedOn}</td>
        <td>${pr.author}</td>
        <c:forEach var="reviewer" items="${pr.reviewers}">
            <td>
                ${reviewer}
            </td>
        </c:forEach>
    </tr>
</c:forEach>
</table>

</body>
</html>