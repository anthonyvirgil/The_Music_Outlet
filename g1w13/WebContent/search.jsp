<%@include file="includes/header.jsp"%>

<!-- Natacha Gabbamonte -->
<!-- 0932340 -->
<!-- search.jsp -->

<c:set var="NUM_OF_RECORDS_PER_PAGE" value="10"></c:set>

<div class="block">
	<h2>Search</h2>
	<div class="inBlock">
		<h3>Parameters</h3>
		<!-- form for do a search -->
		<form id="searchform" action="<c:url value='search#content'></c:url>"
			method="get">
			<input type="text" size="39" placeholder="Search value"
				name="searchValue" value="${searchValue}" /> <input type="reset"
				value="Clear" /> <input type="submit" value="Search" /><br /> <label><input
				type="radio" name="searchType" value="Artist"
				${searchType.equals("Artist")? "checked" : ""}>&nbsp;Artist</label>&nbsp;&nbsp;&nbsp;
			<label><input type="radio" name="searchType" value="Track"
				${searchType.equals("Track")? "checked" : ""}>&nbsp;Track</label>&nbsp;&nbsp;&nbsp;
			<label><input type="radio" name="searchType" value="Album"
				${searchType.equals("Album")? "checked" : ""}>&nbsp;Album</label>&nbsp;&nbsp;&nbsp;
			<label><input type="radio" name="searchType" value="Genre"
				${searchType.equals("Genre")? "checked" : ""}>&nbsp;Genre</label>
		</form>
		<p>
			<span class="error">${searchError}</span>
		</p>
		<h3>Results</h3>
		<p>We found a total of ${searchAlbumsTotal + searchTracksTotal}
			results matching your parameters.</p>

		<a name="albums"></a>
		<c:if test="${searchAlbums!=null && searchAlbumsTotal >0}">
			<h4>Albums</h4>
			<!-- display the list of albums -->
			<div id="list_search">
				<table cellspacing="0">
					<tr>
						<th>Artist</th>
						<th>Genre</th>
						<th>Title</th>
						<th>Price</th>
						<th>Actions</th>
					</tr>
					<!-- display each album -->
					<c:forEach var="searchAlbum" items="${searchAlbums}"
						varStatus="loopCounter">
						<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
							<td style="width: 180px;"><c:out
									value="${searchAlbum.artist}"></c:out></td>
							<td><c:out value="${searchAlbum.musicCategory}"></c:out></td>
							<td style="width: 200px;"><c:out
									value="${searchAlbum.albumTitle}"></c:out></td>
							<td><c:choose>
									<c:when test="${searchAlbum.salePrice >0}">
										<span class="listPrice"><fmt:formatNumber
												type="currency" value="${searchAlbum.listPrice}" /></span>
										<span class="salePrice"><fmt:formatNumber
												type="currency" value="${searchAlbum.salePrice}" /></span>
									</c:when>
									<c:otherwise>
										<fmt:formatNumber type="currency"
											value="${searchAlbum.currentPrice}" />
									</c:otherwise>
								</c:choose></td>
							<td class="invButtons"><a
								href="<c:url value="displayAlbum">
						<c:param name="albumId" value="${searchAlbum.albumId }"/>
					</c:url>#content">
									<img src="${path}/images/details_icon.png" />
							</a>
								<form action="<c:url value='addToCart'></c:url>#content"
									method="post">
									<input type="image" src="${path}/images/add_to_cart_icon.png" />
									<input type="hidden" name="albumId"
										value="<c:out value='${searchAlbum.albumId}'></c:out>" />
								</form></td>
						</tr>
					</c:forEach>

				</table>
			</div>
			<!-- navigate beetwen the page -->
			<div class="right">Viewing records
				${albumsPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
				&nbsp;-&nbsp;
				${(albumsPage*NUM_OF_RECORDS_PER_PAGE)>searchAlbumsTotal?
				searchAlbumsTotal : albumsPage*NUM_OF_RECORDS_PER_PAGE}
				&nbsp;of&nbsp; ${searchAlbumsTotal} &nbsp;records</div>
			<c:if test="${albumsPage > 1}">
				<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=1#albums"></c:url>">&lt;&lt;</a>
		&nbsp;
			<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${albumsPage-1}#albums"></c:url>">&lt;</a>
			</c:if>
		&nbsp;<b>${albumsPage}</b>&nbsp;
		<c:if test="${albumsPage < maxAlbumsPage}">
				<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${albumsPage+1}#albums"></c:url>">&gt;</a>
		&nbsp;
			<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${maxAlbumsPage}#albums"></c:url>">&gt;&gt;</a>
			</c:if>
		</c:if>
		<c:if test="${searchTracks!=null && searchTracksTotal >0}">
			<a name="tracks"></a>
			<h4>Tracks</h4>
			<!-- display the list of tracks -->
			<div id="list_search">
				<table cellspacing="0">
					<tr>
						<th>Artist</th>
						<th>Genre</th>
						<th>Title</th>
						<th class="notInMobile">Length</th>
						<th>Price</th>
						<th>Actions</th>
					</tr>
					<!-- display each the track -->
					<c:forEach var="searchTrack" items="${searchTracks}"
						varStatus="loopCounter">
						<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
							<td style="width: 130px;"><c:out
									value="${searchTrack.artist}"></c:out></td>
							<td><c:out value="${searchTrack.musicCategory}"></c:out></td>
							<td style="width: 250px;"><c:out
									value="${searchTrack.trackTitle}"></c:out></td>
							<td class="notInMobile"><c:out
									value="${searchTrack.playLength}"></c:out></td>
							<td><c:choose>
									<c:when test="${searchTrack.salePrice >0}">
										<span class="listPrice"><fmt:formatNumber
												type="currency" value="${searchTrack.listPrice}" /></span>
										<span class="salePrice"><fmt:formatNumber
												type="currency" value="${searchTrack.salePrice}" /></span>
									</c:when>
									<c:otherwise>
										<fmt:formatNumber type="currency"
											value="${searchTrack.currentPrice}" />
									</c:otherwise>
								</c:choose></td>
							<td class="invButtons"><a
								href="<c:url value="displayTrack">
									<c:param name="trackId" value="${searchTrack.inventoryId }"/></c:url>#content">
									<img src="${path}/images/details_icon.png" />
							</a> <c:if test="${searchTrack.typeOfSale != 1}">
									<form action="<c:url value='addToCart'></c:url>#content"
										method="post">
										<input type="image" src="${path}/images/add_to_cart_icon.png" />
										<input type="hidden" name="trackId"
											value="<c:out value='${searchTrack.inventoryId}'></c:out>" />
									</form>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!--navigate beetwen the page -->
			<div class="right">Viewing records
				${tracksPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
				&nbsp;-&nbsp;
				${(tracksPage*NUM_OF_RECORDS_PER_PAGE)>searchTracksTotal?
				searchTracksTotal : tracksPage*NUM_OF_RECORDS_PER_PAGE}
				&nbsp;of&nbsp; ${searchTracksTotal} &nbsp;records</div>
			<c:if test="${tracksPage > 1}">
				<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=1&albumsPage=${albumsPage}#tracks"></c:url>">&lt;&lt;</a>
		&nbsp;
			<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage-1}&albumsPage=${albumsPage}#tracks"></c:url>">&lt;</a>
			</c:if>
		&nbsp;<b>${tracksPage}</b>&nbsp;
		<c:if test="${tracksPage < maxTracksPage}">
				<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage+1}&albumsPage=${albumsPage}#tracks"></c:url>">&gt;</a>
		&nbsp;
			<a
					href="<c:url
				value="search?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${maxTracksPage}&albumsPage=${albumsPage}#tracks"></c:url>">&gt;&gt;</a>
			</c:if>
		</c:if>
	</div>
</div>
<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/quizRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>