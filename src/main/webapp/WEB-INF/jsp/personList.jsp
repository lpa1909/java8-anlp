<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Person List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
    <h1>Person List</h1>

    <br/><br/>
    <div>
        <table border="1">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <c:forEach  var ="p" item="${persons}">
                <tr>
                    <td>${p.firstName}</td>
                    <td>${p.lastName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>

</html>