<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>The MUSIC Outlet</title>
<link rel="SHORTCUT ICON" href="../images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="style.css" />
<link href="../rssFeed/jquery.zrssfeed.css" rel="stylesheet"
	type="text/css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="../rssFeed/jquery.zrssfeed.js" type="text/javascript"></script>
<script type="text/javascript" src="../script.js"></script>

</head>

<!-- Natacha Gabbamonte, Venelin Koulaxazov, Dorian Mein -->
<!-- 0932340, 1032744 -->
<!-- header.jsp (Admin) -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body>
	<!--NAV -->
	<!-- test if it is an Admin -->
	<c:if test="${client.status}">
		<div id="topNav">
			<div id="topNavLogo">
				<a href='<c:url value="index"></c:url>'> <img
					src="${path}/images/toplogo.png" /></a>
			</div>
			<!-- display admin's top menu -->
			<div id="topNavManag">
				Manager Tools for: ${client.firstName} --- <a
					href='<c:url value="./viewInv"></c:url>'>Inventory</a>&nbsp;|&nbsp;
				<a href='<c:url value="./sale"></c:url>'>Set Sales</a> &nbsp;|&nbsp;
				<a href='<c:url value="./editClient"></c:url>'>Clients</a>&nbsp;|&nbsp;
				<a href='<c:url value="./reviews"></c:url>'>Reviews</a>
				<c:if test="${numOfReviews > 0}">
				&nbsp;(${numOfReviews})
				</c:if>
				&nbsp;|&nbsp; <a href='<c:url value="./orders"></c:url>'>Orders</a>&nbsp;|&nbsp;
				<a href='<c:url value="./reports"></c:url>'>Reports</a>&nbsp;|&nbsp;
				<a href='<c:url value="./populateRss"></c:url>'>News Feed</a>&nbsp;|&nbsp;
				<a href='<c:url value="./survey"></c:url>'>Surveys</a>&nbsp;|&nbsp;
				<a href='<c:url value="./ad"></c:url>'>Banner Ads</a>
			</div>
			<div id="topNavDeco">
				<a href='<c:url value="../index"></c:url>'>Go back to client
					website</a> &nbsp;|&nbsp; <a href='<c:url value="../logout"></c:url>'>Logout</a>
			</div>
		</div>
	</c:if>
	<!-- HEADER -->
	<div id="header">
		<div id="logoDiv">
			<a href="index"><img src="${path}/images/logo.png"
				alt="The MUSIC Outlet Management Homepage" /></a>
		</div>
		<div id="leftOfLogoDiv">${ empty displayMessage ? "" :
			displayMessage}</div>
	</div>