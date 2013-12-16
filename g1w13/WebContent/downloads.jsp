<%@include file="includes/header.jsp"%>

<!-- Venelin Koulaxazov -->
<!-- 1032744 -->
<!-- downloads.jsp -->

<div class="block">
	<h2>Download</h2>
	<div class="inBlock">
		<h3>Purchased Tracks</h3>
		<!-- display the list of items bought -->
		<div id="list_shop">
			<table cellspacing="0">
				<tr>
					<th>Title</th>
					<th>Artist</th>
					<th>Genre</th>
					<th class="notInMobile">Time</th>
					<th>Download</th>
				</tr>
				<c:choose>
					<c:when test="${empty purchasedTracks}">
						<tr class="tr1">
							<td colspan="5"><i>You have not purchased any tracks</i></td>
						</tr>
					</c:when>
					<c:otherwise>
						<!-- display all the element with info -->
						<c:forEach var="purchasedTrack" items="${purchasedTracks }"
							varStatus="loopCounter">
							<tr class="${loopCounter.count%2==0?'tr2':'tr1' }">
								<td><c:out value="${purchasedTrack.trackTitle }" /></td>
								<td><c:out value="${purchasedTrack.artist }" /></td>
								<td><c:out value="${purchasedTrack.musicCategory }" /></td>
								<td class="notInMobile"><c:out
										value="${purchasedTrack.playLength }" /></td>
								<td><a
									href="<c:url value='displayTrack'><c:param name='trackId' value='${purchasedTrack.inventoryId}'/></c:url>#content"><img
										src="${path}/images/details_icon.png" /></a><a
									href="<c:url value='downloads'><c:param name='trackId' value='${purchasedTrack.inventoryId}'/></c:url>"><img
										src="${path}/images/download_icon.png" /></a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
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