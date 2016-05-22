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

    <label for="updatedOnFrom">Updated On From</label>
    <input id="updatedOnFrom" name="updatedOnFrom" value="${view.updatedOnFrom}" />

    <label for="updatedOnUntil">Updated On Until</label>
    <input id="updatedOnUntil" name="updatedOnUntil" value="${view.updatedOnUntil}" />

    <input type="submit" />
</form>

<table>
<tr>
    <th>Id</th>
    <th>Author</th>
    <th>Author Team</th>
    <c:forEach var="number" begin="1" end="${view.pullRequests.maxReviewerCount}">
        <th>Approved By ${number}</th>
        <th>Approved By Team ${number}</th>
    </c:forEach>
</tr>
<c:forEach var="pr" items="${view.pullRequests.toArray()}">
    <tr>
        <td>${pr.id}</td>
        <td>${pr.author}</td>
        <td>${pr.authorTeam}</td>
        <c:forEach var="number" begin="0" end="${view.pullRequests.maxReviewerCount - 1}">
            <td>
                ${pr.reviewers[number]}
            </td>
            <td>
                ${pr.reviewerTeams[number]}
            </td>
        </c:forEach>
    </tr>
</c:forEach>
</table>

<h2>Pull Requests Per Team</h2>
<table>
    <tr>
        <th>Team</th>
        <th>Pull Requests Created</th>
        <th>Pull Requests Reviewed</th>
    </tr>

    <c:forEach var="stat" items="${view.statistics}">

        <tr>
            <td>${stat.name}</td>
            <td>${stat.created}</td>
            <td>${stat.reviewed}</td>
        </tr>

    </c:forEach>
</table>

</body>
</html>