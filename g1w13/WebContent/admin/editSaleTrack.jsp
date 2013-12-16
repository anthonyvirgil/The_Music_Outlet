<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var validateFormScript = '<script type="text/javascript" src="validateEditSaleScript.js"/>';
	$(document).ready(function() {
		$("head").append(validateFormScript);
	});
</script>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- editSaleTrack.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Edit Sales for Track</h2>
				<!-- Display track information -->
				<div class="inBlock" id="edit">
					<h4>Track Information</h4>
					<div id="editSaleAlbumDiv">
						<img src="${path}/images/covers/${album.albumCover }"
							alt="Album cover" /> Title :
						<c:out value="${track.trackTitle }" />
						<br /> Album :
						<c:out value="${ album.albumTitle }" />
						<br /> Artist :
						<c:out value="${track.artist }" />
						<br /> Songwriter(s) :
						<c:out value="${track.songWriter }" />
						<br /> Length :
						<c:out value="${track.playLength }" />
						<br /> Genre:
						<c:out value="${track.musicCategory }" />
						<br /> Cost Price : $
						<fmt:formatNumber type="number" value="${track.costPrice }"
							minFractionDigits="2" />
						<br /> List Price : $
						<fmt:formatNumber type="number" value="${track.listPrice }"
							minFractionDigits="2" />
						<br />
						<!-- Form for editing only the sale price -->
						<form id="form" method="POST" action="updateSaleTrack">
							<input id="trackId" name="trackId" type="hidden"
								value="${track.inventoryId }" /> Sale Price : $ <input
								id="salePrice" name="salePrice" type="text"
								value="<fmt:formatNumber type="number" value="${track.salePrice }" minFractionDigits="2" />" />
							<input type="submit" value="Submit" />
						</form>
						<!-- test on the type of sale for the track to display the good status -->
						<c:if test="${track.typeOfSale ==1}">Type : Sold with Album<br />
						</c:if>
						<c:if test="${track.typeOfSale ==2}">Type : Both<br />
						</c:if>
						<c:if test="${track.typeOfSale ==0}">Type : Sold Individually<br />
						</c:if>
						<!-- test on the track status to display the good status -->
						<c:if test="${!track.removalStatus}">Status : Online<br />
						</c:if>
						<c:if test="${track.removalStatus}">Status : Offline<br />
						</c:if>
					</div>
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
