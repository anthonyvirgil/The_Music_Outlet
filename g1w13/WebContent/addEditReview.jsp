<%@include file="includes/header.jsp"%>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- addEditReview.jsp -->

<div class="block">
	<h2>Add/Edit Review</h2>
	<div class="inBlock">
		<!-- display info about the track -->
		<div id="info">
			<img src="${path}/images/covers/${track.albumCover}"
				alt="track picture" title="track picture" />
			<h4>
				<c:out value="${track.trackTitle}" />
			</h4>
			<ul>
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
					</c:choose></li>
				<li>Length: <c:out value="${track.playLength}" /></li>
				<li>Songwriter: <c:out value="${track.songWriter}" /></li>
				<li>Genre: <c:out value="${track.musicCategory}" /></li>
				<li>Rating: <c:choose>
						<c:when test="${trackRating > 0.0 }">
							<fmt:formatNumber type="number" value="${trackRating}"
								maxFractionDigits="1" />/5</c:when>
						<c:otherwise>
					Not Yet Rated
					</c:otherwise>
					</c:choose>
				</li>
			</ul>
			<br />
			<!-- button for navigate beetwen track/album/cart-->
			<div class="buttonGroup">
				<form action="<c:url value='displayTrack'></c:url>#content"
					method="get">
					<input type="submit" value="Return to Track" /> <input
						type="hidden" name="trackId" value="${track.inventoryId}" />
				</form>

				<form action="<c:url value='displayAlbum'></c:url>#content"
					method="get">
					<input type="submit" value="View Album" /> <input type="hidden"
						name="albumId" value="${track.albumId}" />
				</form>

				<form action="<c:url value='addToCart'></c:url>#content"
					method="post">
					<input type="submit" value="Add to Cart" /> <input type="hidden"
						name="trackId" value="${track.inventoryId}" />
				</form>
			</div>
		</div>
		<!-- form to add/edit your review -->
		<h3>Your Review</h3>
		<div id="review">
			<form method="POST" action="submitReview#content" id="form">
				<input type="hidden" name="trackId" value="${track.inventoryId}">
				<c:if test="${!empty servletError }">
					<h4>
						<c:out value='${servletError}' />
					</h4>
				</c:if>
				Title <input name="title" id="title" type="text"
					value="<c:out value='${review.reviewTitle}'/>" /><span
					id="titleError"><c:out value='${titleError}' /></span> <br />
				Rating <select name="rating">
					<option value="0"
						<c:if test="${ review.rating == 0 }">selected</c:if>>No
						rating</option>
					<option value="1"
						<c:if test="${ review.rating == 1 }">selected</c:if>>1</option>
					<option value="2"
						<c:if test="${ review.rating == 2 }">selected</c:if>>2</option>
					<option value="3"
						<c:if test="${ review.rating == 3 }">selected</c:if>>3</option>
					<option value="4"
						<c:if test="${ review.rating == 4 }">selected</c:if>>4</option>
					<option value="5"
						<c:if test="${ review.rating == 5 }">selected</c:if>>5</option>
				</select><br /> Comments <span id="commentsError" class="error"></span><br />
				<textarea name="comments" id="comments" rows="8" cols="61">
					<c:out value='${review.reviewText }' />
			</textarea>
				<c:if test="${!empty review && !review.approvalStatus }">
					<br />
					<br /> Your review is still being approved
			</c:if>
				<br /> <br />
				<input type="reset" value="Clear" /> <input type="submit"
					value="Submit" />
			</form>
		</div>
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