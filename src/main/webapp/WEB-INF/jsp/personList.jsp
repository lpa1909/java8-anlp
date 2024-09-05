<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Person List</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/styles.css"/>
</head>
<body>
    <h1>Welcome</h1>
    <br/><hr/>

    <div>
        <table border="1">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <c:forEach item="${persons}" var ="person">
                <tr>
                    <td>${person.firstName}</td>
                    <td>${person.lastName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>