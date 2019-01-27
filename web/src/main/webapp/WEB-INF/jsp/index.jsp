<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Welcome</title>
<link href="<%= request.getContextPath() %>/app.css" type="text/css" rel="stylesheet" />
</head>
<body>
  <%@ include file="includes/menu.jsp" %>

  <c:if test="${repositories != null}">
  Repositories are here.
    <c:out value="${repositories.size()}" />
    <table>
    <c:forEach items="${repositories}" var="repository">
      <tr>
        <td>
          <c:out value="${repository.name}" />
        </td>
      </tr>
    </c:forEach>
    </table>
  </c:if>

</body>
</html>
