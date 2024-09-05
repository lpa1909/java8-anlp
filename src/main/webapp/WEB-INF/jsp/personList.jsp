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
    <br/><br/>

    <div>
        <table border="1">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <c:forEach item="person" var ="persons">
                <tr>
                    <td>${person.firstName}</td>
                    <td>${person.lastName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>