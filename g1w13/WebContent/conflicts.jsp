<%@include file="includes/header.jsp"%>

<!-- Natacha Gabbamonte-->
<!-- 0932340 -->
<!-- conflicts.jsp -->

<div class="block">
<!-- test conflict in the cart -->
	<c:choose>
		<c:when test="${totalConflicts > 1}">
			<h2>There are conflicts in your cart</h2>
		</c:when>
		<c:otherwise>
			<h2>There is a conflict in your cart</h2>
		</c:otherwise>
	</c:choose>
	<div class="inBlock">
		<h3>Your cart</h3>
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
								src="${path}/images/details_icon.png" /></a></td>
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
							src="${path}/images/details_icon.png" /></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<c:choose>
			<c:when test="${totalConflicts > 1}">
				<h3>You already own these items</h3>
			</c:when>
			<c:otherwise>
				<h3>You already own this item</h3>
			</c:otherwise>
		</c:choose>
		<!-- list the element-->
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
				<c:if test="${cart.count==0}">
					<tr class="tr1">
						<td colspan="6">
							<h4>There are no items in your shopping cart</h4>
						</td>
					</tr>
				</c:if>
				<!-- display all the album -->
				<c:forEach var="cartAlbum" items="${albumConflicts}"
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
								src="${path}/images/details_icon.png" /></a></td>
					</tr>
				</c:forEach>
				<!-- display all the track -->
				<c:forEach var="cartTrack" items="${trackConflicts}"
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
							src="${path}/images/details_icon.png" /></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- buttons -->
		<c:choose>
			<c:when test="${cart.count == totalConflicts}">
				<div class="buttonGroup" id="conflictButton">
					<form action='removeConflicts#content' method='post'>
						<input type="submit" value="Remove and go back" />
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<div class="buttonGroup" id="conflictButton">
					<form action='removeConflicts#content' method='post'>
						<input type="submit" value="Remove and continue" />
					</form>
				</div>
			</c:otherwise>
		</c:choose>
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