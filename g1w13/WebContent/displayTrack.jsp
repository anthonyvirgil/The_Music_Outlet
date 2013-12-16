<%@include file="includes/header.jsp"%>

<!-- Anthony-Virgil Bermejo, Natacha Gabbamonte-->
<!-- displayTrack.jsp -->

<div class="block">
	<!-- display the track info -->
	<h2>Track Information</h2>
	<div class="inBlock">
		<div id="info">
			<img src="${path}/images/covers/${track.albumCover}"
				alt="Album cover" title="track picture" />
			<h4>
				<c:out value="${track.trackTitle}" />
			</h4>
			<ul>
				<c:if test="${track.salePrice > 0 }">
					<li class="saleText">On sale!</li>
				</c:if>
				<li>Artist: <c:out value="${track.artist}" /></li>
				<li>Album: <c:out value="${album.albumTitle}" /></li>
				<li>Price: <c:choose>
						<c:when test="${track.salePrice > 0.0}">
							<span class="listPrice"><fmt:formatNumber type="currency"
									value="${track.listPrice}" /></span>
							<span class="salePrice"><fmt:formatNumber type="currency"
									value="${track.salePrice}" /></span>
						</c:when>
						<c:otherwise>
							<fmt:formatNumber type="currency" value="${track.currentPrice}" />
						</c:otherwise>
					</c:choose>
				</li>
				<li>Length: <c:out value="${track.playLength}" /></li>
				<li>Songwriter(s): <c:out value="${track.songWriter}" /></li>
				<li>Genre: <c:out value="${track.musicCategory}" /></li>
				<li>Rating: <c:choose>
						<c:when test="${rating > 0.0 }">
							<fmt:formatNumber type="number" value="${rating}"
								maxFractionDigits="1" />/5</c:when>
						<c:otherwise>
					Not Yet Rated
					</c:otherwise>
					</c:choose>
				<li><a href="#review">See Reviews</a></li>
			</ul>

			<div id="trackBottomText">
				<c:choose>
					<c:when test="${track.removalStatus==true }">
						<br />This track is no longer being sold.</c:when>
					<c:when test="${track.typeOfSale == 0}">
						<br />This track is only sold individually.</c:when>
					<c:when test="${track.typeOfSale == 1}">
						<br />This track is only sold as part of an album.</c:when>
				</c:choose>
			</div>
			<!-- buttons -->
			<div class="buttonGroup">
				<form action="<c:url value='displayAlbum'></c:url>#content"
					method="get">
					<input type="submit" value="View Album" /> <input type="hidden"
						name="albumId" value="${track.albumId}" />
				</form>
				<c:if test="${track.typeOfSale != 1 && track.removalStatus != true}">
					<form action="<c:url value='addToCart'></c:url>#content"
						method="post">
						<input type="submit" value="Add to Cart" /> <input type="hidden"
							name="trackId" value='${track.inventoryId}' />
					</form>
				</c:if>

				<form action="<c:url value='addEditReview'></c:url>#content"
					method="get">
					<input type="submit" value="Add/Edit Review" /> <input
						type="hidden" name="trackId" value="${track.inventoryId}" />
				</form>
			</div>
		</div>
		<!-- display the list of other tracks in the same album -->
		<h3>Other Tracks From Album</h3>
		<div id="list_track">
			<table cellspacing="0">
				<tr>
					<th>#</th>
					<th>Title</th>
					<th>Time</th>
					<th>Rating</th>
					<th>Price</th>
					<th>Actions</th>
				</tr>
				<c:if test="${empty albumTracks}">
					<tr class="tr1">
						<td colspan="6"><i>No other tracks available from album</i></td>
					</tr>
				</c:if>
				<c:forEach var="albumTrack" items="${albumTracks}"
					varStatus="loopCounter">
					<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
						<td><c:out value="${albumTrack.selectionNum}" /></td>
						<td style="width: 250px;"><c:out
								value="${albumTrack.trackTitle}" /></td>
						<td><c:out value="${albumTrack.playLength}" /></td>
						<td style="width: 85px;"><c:choose>
								<c:when
									test="${ratingImgPathList[loopCounter.count - 1] == 'Not Yet Rated'}">Not Yet Rated</c:when>
								<c:otherwise>
									<img src="${path}/${ratingImgPathList[loopCounter.count - 1]}" />
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${albumTrack.salePrice >0}">
									<span class="listPrice"><fmt:formatNumber
											type="currency" value="${albumTrack.listPrice}" /></span>
									<span class="salePrice"><fmt:formatNumber
											type="currency" value="${albumTrack.salePrice}" /></span>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="currency"
										value="${albumTrack.currentPrice}" />
								</c:otherwise>
							</c:choose></td>
						<td class="invButtons"><a
							href="<c:url value='displayTrack'><c:param name='trackId' value='${albumTrack.inventoryId}'/></c:url>#content"><img
								src="${path}/images/details_icon.png" /></a> <c:if
								test="${albumTrack.typeOfSale != 1}">
								<form action="<c:url value='addToCart'></c:url>" method="post">
									<input type="image" src="${path}/images/add_to_cart_icon.png" />
									<input type="hidden" name="trackId"
										value="<c:out value='${albumTrack.inventoryId}'></c:out>" />
								</form>
							</c:if></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- display the list of review for this track -->
		<div id="review">
			<br /> <br />
			<h3>Reviews</h3>
			<c:if test="${empty trackReviews}">
				<i>This track has not yet been reviewed</i>
			</c:if>
			<c:forEach var="aReview" items="${trackReviews.reviews}"
				varStatus="loopCounter">
				<h5>
					<c:out value="${aReview.reviewTitle}" />
					<c:choose>
						<c:when test="${aReview.rating == 0.0 }">
							<span id="noRatingText">Not Yet Rated</span>
						</c:when>
						<c:otherwise>
							<img id="ratingImg"
								src="${path}/${trackReviews.reviewImgPathList[loopCounter.count - 1]}" />
						</c:otherwise>
					</c:choose>
				</h5>
				<p>
					<c:out value="${aReview.reviewText}" />
					<br /> <i>- <c:out value="${aReview.clientName}" /></i>
				</p>
			</c:forEach>
		</div>
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