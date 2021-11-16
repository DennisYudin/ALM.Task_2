<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<head>
    <title> List categories </title>
</head>

<body>

	<div id="wrapper">
	    <div id="header">
	        <h2> Categories</h2>
	    </div>
	</div>

	<div id="container">
	    <div id="content">

	        <table>
	            <tr>
	                <th>Name</th>
	            </tr>

	            <c:forEach var="tempCategory" items="${categories}">

	                <tr>
	                    <td> ${tempCategory} </td>
	                </tr>

	            </c:forEach>
	        </table>
	    </div>
	</div>
</body>
</html>
