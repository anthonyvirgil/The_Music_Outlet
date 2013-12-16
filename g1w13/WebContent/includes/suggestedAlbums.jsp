<!-- Natacha Gabbamonte -->
<!-- 0932340 -->
<!-- suggestedAlbums.jsp -->

<div class="block">
	<h2>Suggested Albums</h2>
	<div class="inBlock">
		<!-- test if they are suggested album -->
		<c:choose>
			<c:when test="${suggestedAlbums==null || suggestedAlbumsSize < 1}">
				<p>We have no suggestions for you!</p>
			</c:when>
			<c:otherwise>
				<!-- display 3 suggested album -->
				<div id="sug">
					<c:forEach var="suggestedAlbum" items="${suggestedAlbums}"
						varStatus="loopCounter">
						<div class="sug_alb">
							<h5>
								<c:out value="${suggestedAlbum.artist}"></c:out>
							</h5>
							<a
								href="<c:url value="displayAlbum">
							<c:param name="albumId" value="${suggestedAlbum.albumId }"/>
						</c:url>#content">
								<img align="left"
								src="${path}/images/covers/<c:out value="${suggestedAlbum.albumCover}"></c:out>"
								alt="track picture" title="track picture" />
							</a>
							<c:out value="${suggestedAlbum.albumTitle}"></c:out>

							<br /> &nbsp;&nbsp;
							<c:choose>
								<c:when test="${suggestedAlbum.salePrice >0}">
									<span class="listPrice"><fmt:formatNumber
											type="currency" value="${suggestedAlbum.listPrice}" /></span>
									<br />
									<span class="salePrice"><fmt:formatNumber
											type="currency" value="${suggestedAlbum.salePrice}" /></span>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber type="currency"
										value="${suggestedAlbum.currentPrice}" />
								</c:otherwise>
							</c:choose>
							<div class="sug_ico">
								<a
									href="<c:url value="displayAlbum">
							<c:param name="albumId" value="${suggestedAlbum.albumId }"/>
						</c:url>#content">
									<img src="${path}/images/details_icon.png" />
								</a>
								<form action="<c:url value='addToCart'></c:url>#content"
									method="post">
									<input type="image" src="${path}/images/add_to_cart_icon.png" />
									<input type="hidden" name="albumId"
										value="<c:out value='${suggestedAlbum.albumId}'></c:out>" />
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