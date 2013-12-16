<%@include file="includes/header.jsp"%>
<!-- Natacha Gabbamonte -->
<!-- 0932340 -->
<!-- index.jsp -->

<div class="block">
	<h2>On Sale</h2>
	<div class="inBlock">
			<!-- test albums in sales -->
		<c:choose>
			<c:when test="${salesAlbums == null || salesAlbumsSize < 1}">
				<p>There are no albums on sale!</p>
			</c:when>
			<c:otherwise>
				<div id="sale">
					<!-- display album in sale -->
					<c:forEach var="album" items="${salesAlbums}">
						<div class="sug_alb">
							<h5>
								<c:out value="${album.artist}"></c:out>
							</h5>
							<a
								href="<c:url value="displayAlbum">
							<c:param name="albumId" value="${album.albumId }"/>
						</c:url>#content">
								<img align="left"
								src="${path}/images/covers/<c:out value="${album.albumCover}"></c:out>"
								alt="track picture" title="track picture" />
							</a>
							<c:out value="${album.albumTitle}"></c:out>
							<br />&nbsp;&nbsp;
							<ul class="listPrice">
								<fmt:formatNumber type="currency" value="${album.listPrice}"></fmt:formatNumber>
							</ul>
							<br />&nbsp;&nbsp;
							<ul class="salePrice">
								<fmt:formatNumber type="currency" value="${album.salePrice}"></fmt:formatNumber>
							</ul>
							<div class="sug_ico">
								<a
									href="<c:url value="displayAlbum">
							<c:param name="albumId" value="${album.albumId }"/>
						</c:url>#content">
									<img src="${path}/images/details_icon.png" />
								</a>
								<form action="<c:url value='addToCart'></c:url>" method="post">
									<input type="image" src="${path}/images/add_to_cart_icon.png" />
									<input type="hidden" name="albumId"
										value="<c:out value='${album.albumId}'></c:out>" />
								</form>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="end">&nbsp;</div>
	</div>
</div>

<%@include file="includes/suggestedAlbums.jsp"%>
<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/quizRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>