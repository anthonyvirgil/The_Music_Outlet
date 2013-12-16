<%@include file="includes/header.jsp"%>

<!-- Anthony-Virgil Bermejo, Natacha Gabbamonte-->
<!-- displayAlbum.jsp -->

<div class="block">
	<h2>Album Information</h2>
	<div class="inBlock">
		<!-- display information about the album -->
		<div id="info">
			<img src="${path}/images/covers/${album.albumCover}"
				alt="Album cover" title="Album Cover" />
			<h4>
				<c:out value="${album.albumTitle}" />
			</h4>
			<ul>
				<c:if test="${album.salePrice > 0 }">
					<li class="saleText">On sale!</li>
				</c:if>
				<li>Artist: <c:out value="${album.artist}" /></li>
				<li>Price: <c:choose>
						<c:when test="${album.salePrice >0}">
							<span class="listPrice"><fmt:formatNumber type="currency"
									value="${album.listPrice}" /></span>
							<span class="salePrice"><fmt:formatNumber type="currency"
									value="${album.salePrice}" /></span>
						</c:when>
						<c:otherwise>
							<fmt:formatNumber type="currency" value="${album.currentPrice}" />
						</c:otherwise>
					</c:choose>
				</li>
				<li>Genre: <c:out value="${album.musicCategory}" /></li>
				<li>Number of Tracks: <c:out value="${album.numOfTracks}" /></li>
				<li>Record Label: <c:out value="${album.recordLabel}" /></li>
				<li>Release Date: <c:out value="${album.releaseDate}" /></li>
				<c:if test="${!empty trackReviews }">
					<li><a href="#track_reviews">See Track Reviews</a></li>
				</c:if>
			</ul>
			<br>
			<c:choose>
				<c:when test="${album.removalStatus}">
				This album is no longer sold. You can still buy it's songs if they are sold individually.
			</c:when>
				<c:otherwise>
					<div class="buttonGroup">
						<form action="<c:url value='addToCart'></c:url>" method="post">
							<input type="submit" value="Add to Cart" /> <input type="hidden"
								name="albumId" value='${album.albumId}' />
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<!-- display the list of tracks sold with the album -->
		<div id="list_track">
			<h3>Tracks Sold With Album</h3>
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
						<td>${albumTrack.selectionNum}</td>
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
		<!-- display the list of tracks in the album sold individually -->
		<div id="list_track">
			<h3>Tracks From Album Sold Individually</h3>
			<table cellspacing="0">
				<tr>
					<th>#</th>
					<th>Title</th>
					<th>Time</th>
					<th>Rating</th>
					<th>Price</th>
					<th>Actions</th>
				</tr>
				<c:choose>
					<c:when test="${empty otherAlbumTracks}">
						<tr class="tr1">
							<td colspan="6"><i>No other tracks available from album</i></td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="albumTrack" items="${otherAlbumTracks}"
							varStatus="loopCounter">
							<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
								<td>${albumTrack.selectionNum}</td>
								<td style="width: 250px;"><c:out
										value="${albumTrack.trackTitle}" /></td>
								<td><c:out value="${albumTrack.playLength}" /></td>
								<td style="width: 85px;"><c:choose>
										<c:when
											test="${singleTrackRatingImgList[loopCounter.count - 1] == 'Not Yet Rated'}">Not Yet Rated</c:when>
										<c:otherwise>
											<img
												src="${path}/${singleTrackRatingImgList[loopCounter.count - 1]}" />
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
										src="${path}/images/details_icon.png" /></a>
									<form action="<c:url value='addToCart'></c:url>" method="post">
										<input type="image" src="${path}/images/add_to_cart_icon.png" />
										<input type="hidden" name="trackId"
											value="<c:out value='${albumTrack.inventoryId}'></c:out>" />
									</form></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<!-- display the list of review for the track in the album -->
		<div id="track_reviews">
			<br /> <br />
			<h3>Track Reviews</h3>
			<c:if test="${empty trackReviews }">
				<i>No tracks have been reviewed yet</i>
			</c:if>
			<c:forEach var="trackReview" items="${trackReviews }"
				varStatus="status">
				<c:if test="${!empty trackReview.reviews }">
					<div id="trackReviewBlock">
						<h4>
							<c:out value="${trackReview.track.trackTitle }" />
						</h4>
						<c:forEach var="aReview" items="${trackReview.reviews}"
							varStatus="loopCounter">
							<h5>
								<c:out value="${aReview.reviewTitle}" />
								<c:choose>
									<c:when test="${aReview.rating == 0 }">
										<span id="noRatingText">Not Yet Rated</span>
									</c:when>
									<c:otherwise>
										<img id="ratingImg"
											src="${path}/${trackReview.reviewImgPathList[loopCounter.count - 1]}" />
									</c:otherwise>
								</c:choose>
							</h5>
							<p>
								<c:out value="${aReview.reviewText}" />
								<br /> <i>- <c:out value="${aReview.clientName}" /></i>
							</p>
						</c:forEach>

					</div>
				</c:if>
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