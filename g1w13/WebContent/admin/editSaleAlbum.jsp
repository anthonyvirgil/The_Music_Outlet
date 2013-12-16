<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var validateFormScript = '<script type="text/javascript" src="validateEditSaleScript.js"/>';
	$(document).ready(function() {
		$("head").append(validateFormScript);
	});
</script>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- editSaleAlbum.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Edit Sales for Album</h2>
				<!-- Display album information -->
				<div class="inBlock" id="edit">
					<h4>Album Information</h4>
					<div id="editSaleAlbumDiv">
						<img src="${path}/images/covers/${album.albumCover }"
							alt="Album cover" /> Title :
						<c:out value="${album.albumTitle }" />
						<br /> Artist :
						<c:out value="${album.artist}" />
						<br /> Release Date :
						<c:out value="${album.releaseDate}" />
						<br /> Label Record :
						<c:out value="${album.recordLabel}" />
						<br /> Genre :
						<c:out value="${album.musicCategory}" />
						<br /> Cost Price : $
						<fmt:formatNumber type="number" value="${album.costPrice }"
							minFractionDigits="2" />
						<br /> List Price : $
						<fmt:formatNumber type="number" value="${album.listPrice }"
							minFractionDigits="2" />
						<br />
						<!-- Form for editing only the sale price -->
						<form id="form" method="POST" action="updateSaleAlbum">
							<input id="albumId" name="albumId" type="hidden"
								value="${album.albumId }" /> Sale Price : $ <input
								id="salePrice" name="salePrice" type="text"
								value="<fmt:formatNumber type="number" value="${album.salePrice }" minFractionDigits="2" />" />
							<input type="submit" value="Submit" />
						</form>
						<!-- test on the album status to display the good status -->
						<c:if test="${!album.removalStatus}">Status : Online<br />
						</c:if>
						<c:if test="${album.removalStatus}">Status : Offline<br />
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