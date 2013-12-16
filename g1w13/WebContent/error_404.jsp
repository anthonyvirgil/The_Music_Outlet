<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true"%>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- error_404.jsp -->


<html>
<head>
<link rel="SHORTCUT ICON" href="images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="style.css" />
<title>The MUSIC Outlet - Page Not Found</title>
</head>

<body>
	<!-- display the error 404 -->
	<div id="errorPageDiv">
		<div id="leftErrorPageDiv">
			<a href="<c:url value='index'></c:url>"> <img
				src="${path}/images/logo.png" alt="The MUSIC Outlet Homepage" /></a>
		</div>
		<div id="rightErrorPageDiv">
			<h2>404 Error - Page Not Found</h2>
			<p>
				Sorry, but the page you are looking for cannot be found. <br /> <br />The
				page might have been removed, had it's name changed, or is
				temporarily unavailable. If this problem persists, please notify the
				administration. <br /> <br /> <a
					href="<c:url value='index'></c:url>">Go Back</a>
			</p>
		</div>
	</div>
</body>
</html>