<%@include file="includes/header.jsp"%>

<!-- Natacha Gabbamonte, Dorian Mein -->
<!-- 0932340 -->
<!-- cart.jsp -->

<div class="block" id="cart">
	<h2>Your Cart</h2>
	<div class="inBlock">
		<h3>Shopping Items</h3>
		<!-- list the item in your cart -->
		<div id="list_shop">
			<table cellspacing="0">
				<tr>
					<th>Type</th>
					<th>Artist</th>
					<th>Genre</th>
					<th>Title</th>
					<th>Price</th>
					<th>Actions</th>
				</tr>
				<!-- test if you have item -->
				<c:if test="${cart.count==0}">
					<tr class="tr1">
						<td colspan="6">
							<h4>There are no items in your shopping cart</h4>
						</td>
					</tr>
				</c:if>
				<!-- display all the album -->
				<c:forEach var="cartAlbum" items="${cart.albums}"
					varStatus="loopCounter">
					<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
						<td>Album</td>
						<td><c:out value="${cartAlbum.artist}" /></td>
						<td><c:out value="${cartAlbum.musicCategory}" /></td>
						<td><c:out value="${cartAlbum.albumTitle}" /></td>
						<td><c:out value="${cartAlbum.currentPriceString}" /></td>
						<td class="invButtons"><a
							href="<c:url value="displayAlbum">
							<c:param name="albumId" value="${cartAlbum.albumId }"/>
						</c:url>#content"><img
								src="${path}/images/details_icon.png" /></a>

							<form action="<c:url value='removeFromCart'></c:url>#content"
								method="post">
								<input type="image" src="${path}/images/remove_icon.png" /> <input
									type="hidden" name="albumId"
									value="<c:out value='${cartAlbum.albumId}'></c:out>" />
							</form></td>
					</tr>
				</c:forEach>
				<!-- display all the track -->
				<c:forEach var="cartTrack" items="${cart.tracks}"
					varStatus="loopCounter">
					<c:choose>
						<c:when test="${cart.isAlbumsEven}">
							<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
						</c:when>
						<c:otherwise>
							<tr class="${loopCounter.count%2==0?'tr1':'tr2'}">
						</c:otherwise>
					</c:choose>
					<td>Track</td>
					<td><c:out value="${cartTrack.artist}" /></td>
					<td><c:out value="${cartTrack.musicCategory}" /></td>
					<td><c:out value="${cartTrack.trackTitle}" /></td>
					<td><c:out value="${cartTrack.currentPriceString}" /></td>
					<td class="invButtons"><a
						href="<c:url value="displayTrack">
							<c:param name="trackId" value="${cartTrack.inventoryId }"/></c:url>"><img
							src="${path}/images/details_icon.png" /></a>
						<form action="<c:url value='removeFromCart'></c:url>#content"
							method="post">
							<input type="image" src="${path}/images/remove_icon.png" /> <input
								type="hidden" name="trackId"
								value="<c:out value='${cartTrack.inventoryId}'></c:out>" />
						</form></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- display the total of the cart with taxes -->
		<div id="total">
			Subtotal:
			<fmt:formatNumber type="currency" value="${cart.subTotal}" />
			<br /> GST:
			<fmt:formatNumber type="currency" value="${cart.GSTTaxes}" />
			<br /> HST:
			<fmt:formatNumber type="currency" value="${cart.HSTTaxes}" />
			<br /> PST:
			<fmt:formatNumber type="currency" value="${cart.PSTTaxes}" />
			<br /> Total: <b><fmt:formatNumber type="currency"
					value="${cart.totalTaxes}" /></b>
		</div>
		<!-- button -->
		<div class="buttonGroup">
			<form action="index" method="get">
				<input type="submit" value="Continue shopping" />
			</form>
			<c:if test="${cart.count > 0}">
				<c:choose>
					<c:when test="${empty client }">
						<form action='userLogin#content' method='get'>
							<input type="submit" value="Check out" />
						</form>
					</c:when>
					<c:otherwise>
						<form action='verifyCart#content' method='post'>
							<input type="submit" value="Check out" />
						</form>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>

<%@include file="includes/suggestedAlbums.jsp"%>
<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<!-- cart to the right-->
<div class="block">
	<h2>Your Cart</h2>
	<div class="inBlock">
		<p>
			Number of items: ${cart.count}<br /> Total with taxes: <b><fmt:formatNumber
					type="currency" value="${cart.totalTaxes}" /></b>
		</p>
		<div class="buttonGroup">
			<form action="index" method="get">
				<input type="submit" value="Continue shopping" />
			</form>
			<c:if test="${cart.count > 0}">
				<c:choose>
					<c:when test="${empty client }">
						<form action='userLogin#content' method='get'>
							<input type="submit" value="Check out" />
						</form>
					</c:when>
					<c:otherwise>
						<form action='finalization#content' method='post'>
							<input type="submit" value="Check out" />
						</form>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/quizRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>