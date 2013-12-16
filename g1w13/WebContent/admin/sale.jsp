<%@include file="./includes/header.jsp"%>

<!-- Natacha Gabbamonte, Dorian Mein -->
<!-- 0932340 -->
<!-- sale.jsp (Admin) -->

<c:set var="NUM_OF_RECORDS_PER_PAGE" value="10"></c:set>

<!-- CONTENT -->
<div id="contentView">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Management Tools</h2>
				<div class="inBlock">
					<h3>Parameters</h3>
					<!-- form to choose the parameters to the search -->
					<form action="<c:url value='sale'></c:url>" method="get">
						<input type="text" size="39" placeholder="Search value"
							name="searchValue" value="${searchValue}" /> <input type="reset"
							value="Clear" /> <input type="submit" value="Search" /><br />
						<label><input type="radio" name="searchType"
							value="Artist" ${searchType.equals("Artist")?"checked":""}>&nbsp;Artist</label>&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="searchType" value="Track"
							${searchType.equals("Track")?"checked":""}>&nbsp;Track</label>&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="searchType" value="Album"
							${searchType.equals("Album")?"checked":""}>&nbsp;Album</label>&nbsp;&nbsp;&nbsp;
						<label><input type="radio" name="searchType" value="Genre"
							${searchType.equals("Genre")?"checked":""}>&nbsp;Genre</label>
					</form>
					<!-- display the result of the search -->
					<h3>Results</h3>
					<p>We found a total of ${searchAlbumsTotal + searchTracksTotal}
						results matching your parameters.</p>
					<a name="albums"></a>
					<!-- if we have one or more album -->
					<c:if test="${searchAlbums!=null && searchAlbumsTotal >0}">
						<h4>Albums</h4>
						<div id="list_view">
							<table cellspacing="0">
								<tr>
									<th>ID</th>
									<th>Title</th>
									<th>Artist</th>
									<th>Cost Price</th>
									<th>List Price</th>
									<th>Sale Price</th>
									<th>Edit</th>
								</tr>
								<!-- display each album found on a line of the table -->
								<c:forEach var="searchAlbum" items="${searchAlbums}"
									varStatus="loopCounter">
									<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
										<td><c:out value="${searchAlbum.albumId}"></c:out></td>
										<td><c:out value="${searchAlbum.albumTitle}"></c:out></td>
										<td><c:out value="${searchAlbum.artist}"></c:out></td>
										<td><fmt:formatNumber type="currency"
												value="${searchAlbum.costPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchAlbum.listPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchAlbum.salePrice}"></fmt:formatNumber></td>
										<td class="invButtons"><a
											href="<c:url value="editSaleAlbum">
										<c:param name="albumId" value="${searchAlbum.albumId }"/>
										</c:url>">
												<img src="${path}/images/edit_icon.png" />
										</a></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<!-- Test for navigate between the page -->
						<div class="right">Viewing records
							${albumsPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
							&nbsp;-&nbsp;
							${(albumsPage*NUM_OF_RECORDS_PER_PAGE)>searchAlbumsTotal?
							searchAlbumsTotal : albumsPage*NUM_OF_RECORDS_PER_PAGE}
							&nbsp;of&nbsp; ${searchAlbumsTotal} &nbsp;records</div>
						<c:if test="${albumsPage > 1}">
							<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=1#albums"></c:url>">&lt;&lt;</a>
					&nbsp;
						<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${albumsPage-1}#albums"></c:url>">&lt;</a>
						</c:if>
					&nbsp;<b>${albumsPage}</b>&nbsp;
					<c:if test="${albumsPage < maxAlbumsPage}">
							<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${albumsPage+1}#albums"></c:url>">&gt;</a>
					&nbsp;
						<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${maxAlbumsPage}#albums"></c:url>">&gt;&gt;</a>
						</c:if>
					</c:if>
					<!-- if we have one or more track -->
					<c:if test="${searchTracks!=null && searchTracksTotal >0}">
						<a name="tracks"></a>
						<h4>Tracks</h4>
						<div id="list_view">
							<table cellspacing="0">
								<tr>
									<th>ID</th>
									<th>Title</th>
									<th>Artist</th>
									<th>Cost Price</th>
									<th>List Price</th>
									<th>Sale Price</th>
									<th>Edit</th>
								</tr>
								<!-- display each track found on a line of the table -->
								<c:forEach var="searchTrack" items="${searchTracks}"
									varStatus="loopCounter">
									<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
										<td><c:out value="${searchTrack.inventoryId}"></c:out></td>
										<td><c:out value="${searchTrack.trackTitle}"></c:out></td>
										<td><c:out value="${searchTrack.artist}"></c:out></td>
										<td><fmt:formatNumber type="currency"
												value="${searchTrack.costPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchTrack.listPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchTrack.salePrice}"></fmt:formatNumber></td>
										<td class="invButtons"><a
											href="<c:url value="editSaleTrack">
											<c:param name="trackId" value="${searchTrack.inventoryId }"/></c:url>">
												<img src="${path}/images/edit_icon.png" />
										</a></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<!-- Test for navigate between the page -->
						<div class="right">Viewing records
							${tracksPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
							&nbsp;-&nbsp;
							${(tracksPage*NUM_OF_RECORDS_PER_PAGE)>searchTracksTotal?
							searchTracksTotal : tracksPage*NUM_OF_RECORDS_PER_PAGE}
							&nbsp;of&nbsp; ${searchTracksTotal} &nbsp;records</div>
						<c:if test="${tracksPage > 1}">
							<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=1&albumsPage=${albumsPage}#tracks"></c:url>">&lt;&lt;</a>
					&nbsp;
						<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage-1}&albumsPage=${albumsPage}#tracks"></c:url>">&lt;</a>
						</c:if>
					&nbsp;<b>${tracksPage}</b>&nbsp;
					<c:if test="${tracksPage < maxTracksPage}">
							<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage+1}&albumsPage=${albumsPage}#tracks"></c:url>">&gt;</a>
					&nbsp;
						<a
								href="<c:url
							value="sale?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${maxTracksPage}&albumsPage=${albumsPage}#tracks"></c:url>">&gt;&gt;</a>
						</c:if>
					</c:if>
				</div>
			</div>
		</c:when>
		<c:otherwise>
		<!-- include for non Admin -->
			<%@include file="./includes/nopermissions.jsp"%>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>