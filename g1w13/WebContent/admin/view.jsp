<%@include file="./includes/header.jsp"%>

<!-- Natacha Gabbamonte -->
<!-- 0932340 -->
<!-- view.jsp -->


<c:set var="NUM_OF_RECORDS_PER_PAGE" value="10"></c:set>

<!-- CONTENT -->
<div id="contentView">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock" id="viewInv">
				<h2>Management Tools</h2>
				<div class="inBlock">
					<h3>Parameters</h3>
					<!-- form to select an element in the inventory -->
					<form action="<c:url value='viewInv'></c:url>" method="get">
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
					<h3>Results</h3>
					<p>We found a total of ${searchAlbumsTotal + searchTracksTotal}
						results matching your parameters.</p>
					<a name="albums"></a>
					<!-- display album -->
					<c:if test="${searchAlbums!=null && searchAlbumsTotal >0}">
						<h4>Albums</h4>
						<div id="list_view">
							<table cellspacing="0">
								<tr>
									<th>ID</th>
									<th>Cover</th>
									<th>Title</th>
									<th>Artist</th>
									<th>Release Date</th>
									<th>Record Label</th>
									<th>Genre</th>
									<th>Cost Price</th>
									<th>List Price</th>
									<th>Sale Price</th>
									<th>Removal Status</th>
									<th>Actions</th>
								</tr>
								<c:forEach var="searchAlbum" items="${searchAlbums}"
									varStatus="loopCounter">
									<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
										<td><c:out value="${searchAlbum.albumId}"></c:out></td>
										<td><img class="albumCover"
											src="${path}/images/covers/<c:out value='${searchAlbum.albumCover}'></c:out>"
											alt="<c:out value='${searchAlbum.albumTitle}'></c:out> cover" /></td>
										<td><c:out value="${searchAlbum.albumTitle}"></c:out></td>
										<td><c:out value="${searchAlbum.artist}"></c:out></td>
										<td><c:out value="${searchAlbum.releaseDate}"></c:out></td>
										<td><c:out value="${searchAlbum.recordLabel}"></c:out></td>
										<td><c:out value="${searchAlbum.musicCategory}"></c:out></td>
										<td><fmt:formatNumber type="currency"
												value="${searchAlbum.costPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchAlbum.listPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchAlbum.salePrice}"></fmt:formatNumber></td>
										<td><c:out value="${searchAlbum.removalStatus}"></c:out></td>
										<td class="invButtons"><a
											href="<c:url value="editAlbum">
										<c:param name="albumId" value="${searchAlbum.albumId }"/>
										</c:url>">
												<img src="${path}/images/edit_icon.png" />
										</a>
											<form action="<c:url value='changeRemovalStatus'></c:url>"
												method="post">
												<input type="image"
													src='${path}${searchAlbum.removalStatus==true? "/images/checkmark_icon.png" : "/images/remove_icon.png"}' />
												<input type="hidden" name="type" value="album" /> <input
													type="hidden" name="id"
													value="<c:out value='${searchAlbum.albumId}'></c:out>" />
												<input type="hidden" name="removalStatus"
													value="${!searchAlbum.removalStatus}" />
											</form></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<!-- for navigate beetwen the page -->
						<div class="right">Viewing records
							${albumsPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
							&nbsp;-&nbsp;
							${(albumsPage*NUM_OF_RECORDS_PER_PAGE)>searchAlbumsTotal?
							searchAlbumsTotal : albumsPage*NUM_OF_RECORDS_PER_PAGE}
							&nbsp;of&nbsp; ${searchAlbumsTotal} &nbsp;records</div>
						<c:if test="${albumsPage > 1}">
							<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=1#albums"></c:url>">&lt;&lt;</a>
					&nbsp;
						<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${albumsPage-1}#albums"></c:url>">&lt;</a>
						</c:if>
					&nbsp;<b>${albumsPage}</b>&nbsp;
					<c:if test="${albumsPage < maxAlbumsPage}">
							<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${albumsPage+1}#albums"></c:url>">&gt;</a>
					&nbsp;
						<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage}&albumsPage=${maxAlbumsPage}#albums"></c:url>">&gt;&gt;</a>
						</c:if>
					</c:if>
					<!-- display track -->
					<c:if test="${searchTracks!=null && searchTracksTotal >0}">
						<a name="tracks"></a>
						<h4>Tracks</h4>
						<div id="list_view">
							<table cellspacing="0">
								<tr>
									<th>ID</th>
									<th>Title</th>
									<th>Album ID</th>
									<th>Artist</th>
									<th>SongWriter</th>
									<th>Length</th>
									<th>Genre</th>
									<th>Cost Price</th>
									<th>List Price</th>
									<th>Sale Price</th>
									<th>Type</th>
									<th>Removal Status</th>
									<th>Actions</th>
								</tr>
								<c:forEach var="searchTrack" items="${searchTracks}"
									varStatus="loopCounter">
									<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
										<td><c:out value="${searchTrack.inventoryId}"></c:out></td>
										<td><c:out value="${searchTrack.trackTitle}"></c:out></td>
										<td><c:out value="${searchTrack.albumId}"></c:out></td>
										<td><c:out value="${searchTrack.artist}"></c:out></td>
										<td><c:out value="${searchTrack.songWriter}"></c:out></td>
										<td><c:out value="${searchTrack.playLength}"></c:out></td>
										<td><c:out value="${searchTrack.musicCategory}"></c:out></td>
										<td><fmt:formatNumber type="currency"
												value="${searchTrack.costPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchTrack.listPrice}"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="currency"
												value="${searchTrack.salePrice}"></fmt:formatNumber></td>
										<td><c:out value="${searchTrack.typeOfSale}"></c:out></td>
										<td><c:out value="${searchTrack.removalStatus}"></c:out></td>
										<td class="invButtons"><a
											href="<c:url value="editTrack">
											<c:param name="trackId" value="${searchTrack.inventoryId }"/></c:url>">
												<img src="${path}/images/edit_icon.png" />
										</a>
											<form action="<c:url value='changeRemovalStatus'></c:url>"
												method="post">
												<input type="image"
													src='${path}${searchTrack.removalStatus==true? "/images/checkmark_icon.png" : "/images/remove_icon.png"}' />
												<input type="hidden" name="type" value="track" /> <input
													type="hidden" name="id"
													value="<c:out value='${searchTrack.inventoryId}'></c:out>" />
												<input type="hidden" name="removalStatus"
													value="${!searchTrack.removalStatus}" />
											</form></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<!-- for navigate beetwen the page-->
						<div class="right">Viewing records
							${tracksPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
							&nbsp;-&nbsp;
							${(tracksPage*NUM_OF_RECORDS_PER_PAGE)>searchTracksTotal?
							searchTracksTotal : tracksPage*NUM_OF_RECORDS_PER_PAGE}
							&nbsp;of&nbsp; ${searchTracksTotal} &nbsp;records</div>
						<c:if test="${tracksPage > 1}">
							<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=1&albumsPage=${albumsPage}#tracks"></c:url>">&lt;&lt;</a>
					&nbsp;
						<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage-1}&albumsPage=${albumsPage}#tracks"></c:url>">&lt;</a>
						</c:if>
					&nbsp;<b>${tracksPage}</b>&nbsp;
					<c:if test="${tracksPage < maxTracksPage}">
							<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${tracksPage+1}&albumsPage=${albumsPage}#tracks"></c:url>">&gt;</a>
					&nbsp;
						<a
								href="<c:url
							value="viewInv?newQuery=false&searchType=${searchType}&searchValue=${searchValue}&tracksPage=${maxTracksPage}&albumsPage=${albumsPage}#tracks"></c:url>">&gt;&gt;</a>
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