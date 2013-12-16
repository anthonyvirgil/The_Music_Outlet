<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>The MUSIC Outlet</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=2.0, user-scalable=yes" />
<link rel="SHORTCUT ICON" href="images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="style.css" />

<link rel="stylesheet" type="text/css" href="slider/slider.css" />
<link href="rssFeed/jquery.zrssfeed.css" rel="stylesheet"
	type="text/css" />
<link
	media="handheld, only screen and (max-width: 480px), only screen and (max-device-width: 480px)"
	href="mobile.css" type="text/css" rel="stylesheet" />
<link rel="apple-touch-icon" href="images/footerLogo.png" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="rssFeed/jquery.zrssfeed.js" type="text/javascript"></script>
<script src="slider/slides.min.jquery.js"></script>

<script>
	$(function() {
		$('#slides').slides({
			preload : false,
			preloadImage : 'images/loading.gif',
			generatePagination : true,
			play : 5000,
			pause : 2500,
			hoverPause : true
		});
	});
</script>
</head>

<!-- Natacha Gabbamonte,Anthony-Virgil Bermejo, Venelin Koulaxazov -->
<!-- 0932340,0831360, 1032744 -->
<!-- header.jsp -->

<body>
	<input type="hidden" id="rss" value="${rss}" />
	<!-- menu on the top-->
	<div id="topNav">
		<div id="topNavLogo">
			<a href='<c:url value="index"></c:url>'> <img
				src="${path }/images/toplogo.png" />
			</a>
		</div>
		<div id="topNavSearchBar">
			<form action="<c:url value='search#content'></c:url>" method="get">
				<a href="<c:url value='search#content'></c:url>"
					title="Click for advanced search">Search</a>:&nbsp; <input
					type="text" id="searchBarText" name="searchValue"
					placeholder="Track name" /><input id="topSearchLogo" type="image"
					alt="Submit" src="${path }/images/search_icon.png" />
			</form>
		</div>
		<!-- test if the client is login -->
		<c:choose>
			<c:when test="${client == null}">
				<div id="topNavLogin">
					<div class="notInMobile">
						<form action="login" method="post">
							<input type="text" placeholder="Email" name="username" /> <input
								type="password" placeholder="Password" name="password" /> <input
								type="submit" value="Login" /> <a
								href="<c:url value='register#content'></c:url>">Register</a>
						</form>
					</div>
					<div class="onlyInMobile">
						<a href="userLogin#content">Login</a>
					</div>
				</div>
				<div id="topNavCart">
					<a href="<c:url value='cart#content'></c:url>"> <img
						src="${path }/images/shopping_cart_icon_small.png" align="left"
						alt="Cart image" /> ${cart.count} Item(s)
					</a>
				</div>
			</c:when>
			<c:otherwise>
				<div id="topNavLogin">
					<div class="notInMobile">
						<a href="<c:url value='profile#content'></c:url>">Profile</a> | <a
							href="<c:url value='purchasedTracks#content'></c:url>">Downloads</a>
						| <a href="<c:url value='logout'></c:url>">Logout</a>
					</div>
					<div class="onlyInMobile">
						<a href="<c:url value='logout'></c:url>">Logout</a>
					</div>
				</div>
				<div id="topNavCart">
					<a href="<c:url value='cart#content'></c:url>"> <img
						src="${path }/images/shopping_cart_icon_small.png" align="left"
						alt="Cart image" /> ${cart.count} Item(s)
					</a>
				</div>
				<div id="topNavWelcome">
					<div class="notInMobile">
						Welcome,
						<c:out value="${client.firstName}" />
						&nbsp;&nbsp;
						<c:if test="${client.status}">
							|&nbsp;&nbsp;<a
								href="<c:url value='admin/index#content'></c:url>">Site
								Management</a>
						</c:if>
						&nbsp;&nbsp;
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- display message -->
	<c:if test="${!empty displayMessage }">
		<div id="leftOfLogoDiv">
			<c:out value="${displayMessage}" />
		</div>
	</c:if>

	<!-- HEADER -->
	<div id="header">
		<div id="logoDiv">
			<a href="<c:url value='index'></c:url>"> <img
				src="${path }/images/logo.png" alt="The MUSIC Outlet Homepage" /></a>
		</div>
	</div>

	<!-- SLIDER -->
	<div id="example">
		<c:choose>
			<c:when test="${fiveLatestAlbums != null }">
				<img src="${path }/images/new-ribbon.png" width="112" height="112"
					alt="New Ribbon" id="ribbon" />
				<div id="slides">
					<div class="slides_container">
						<c:forEach var="latestAlbum" items="${fiveLatestAlbums}">
							<div class="slide">
								<a
									href="<c:url value="displayAlbum">
								<c:param name="albumId" value='${latestAlbum.albumId}'/>
								</c:url>#content">
									<img
									src="${path }/images/covers/<c:out value='${latestAlbum.albumCover}' />"></img>
								</a>
								<div id="slideDiv">
									<h1 id="albumTitle">
										<c:out value="${latestAlbum.albumTitle}" />
										-
										<fmt:formatNumber type="currency"
											value="${latestAlbum.currentPrice}" />
									</h1>
									<div class="slider_info">
										<h2 id="albumArtist">
											<c:out value="${latestAlbum.artist}" />
										</h2>
										<h3>
											<c:out value="${latestAlbum.musicCategory}" />
										</h3>
										<h3>
											<c:out value="${latestAlbum.recordLabel}" />
										</h3>
									</div>
									<div class="slider_ico">
										<a
											href="<c:url value="displayAlbum">
											<c:param name="albumId" value='${latestAlbum.albumId}'/>
										</c:url>#content">
											<img src="${path }/images/details_icon.png" />
										</a>
										<form action="<c:url value='addToCart'></c:url>" method="post">
											<input type="image"
												src="${path }/images/add_to_cart_icon.png" /> <input
												type="hidden" name="albumId"
												value="<c:out value='${latestAlbum.albumId}'></c:out>" />
										</form>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<a href="#" class="prev"> <img
						src="${path }/images/arrow-prev.png" width="24" height="43"
						alt="Arrow Prev" /></a> <a href="#" class="next"> <img
						src="${path }/images/arrow-next.png" width="24" height="43"
						alt="Arrow Next" /></a>
				</div>
			</c:when>
			<c:otherwise>
				<h2>No Albums In Database</h2>
			</c:otherwise>
		</c:choose>

	</div>
	<!-- GENRE NAV BAR -->
	<a name="content"></a>
	<div id="genreNavBar">
		<a
			href='<c:url value="search">
					<c:param name="searchType" value="Genre"/>
					<c:param name="searchValue" value="Pop"/>
				</c:url>#content'>Pop</a>
		<a
			href='<c:url value="search">
					<c:param name="searchType" value="Genre"/>
					<c:param name="searchValue" value="Rock"/>
				</c:url>#content'>Rock</a>
		<a
			href='<c:url value="search">
					<c:param name="searchType" value="Genre"/>
					<c:param name="searchValue" value="Metal"/>
				</c:url>#content'>Metal</a>
		<a
			href='<c:url value="search">
					<c:param name="searchType" value="Genre"/>
					<c:param name="searchValue" value="Electronic"/>
				</c:url>#content'>Electronic</a>
		<a
			href='<c:url value="search">
					<c:param name="searchType" value="Genre"/>
					<c:param name="searchValue" value="Reggae"/>
				</c:url>#content'>Reggae</a>
		<a
			href='<c:url value="search">
					<c:param name="searchType" value="Genre"/>
					<c:param name="searchValue" value="Hip-Hop"/>
				</c:url>#content'>Hip-Hop</a>
	</div>
	<script type="text/javascript" src="script.js"></script>
	<!-- CONTENT -->
	<div id="contentWrap">
		<div id="mainContent">