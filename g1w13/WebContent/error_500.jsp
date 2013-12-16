<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true"%>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- error_505.jsp -->

<html>
<head>
<link rel="SHORTCUT ICON" href="images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="style.css" />
<title>The MUSIC Outlet - Internal Server Error</title>
</head>

<body>
	<!-- display the error 500 -->
	<div id="errorPageDiv">
		<div id="leftErrorPageDiv">
			<a href="<c:url value='index'></c:url>"> <img
				src="${path}/images/logo.png" alt="The MUSIC Outlet Homepage" /></a>
		</div>
		<div id="rightErrorPageDiv">
			<h2>500 Error - Server Error</h2>
			<p>
				Oh no, it looks like something has gone wrong. Don't you worry, we
				already about the problem and our team is on the job to fixing it!
				Please try again later.<br /> <br /> <a
					href="<c:url value='index'></c:url>">Go Back</a>
			</p>
		</div>
	</div>
</body>
</html>