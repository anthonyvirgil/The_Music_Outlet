<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var validateFormScript = '<script type="text/javascript" src="validateTrackFormScript.js"/>';
	$(document).ready(function() {
		$("head").append(validateFormScript);
	});
</script>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- editTrack.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test='${client.status }'>
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Edit Track</h2>
				<div class="inBlock" id="edit">
					<h3>Track Information</h3>
					<div id="editAlbumDiv">
					<!-- test if the album exist -->
						<c:if test="${!empty albumError }">
							<h3>
								${albumError } Click <a href="<c:url value="./addAlbum.jsp"/>">here</a>
							</h3>
						</c:if>
						<!-- form to edit a track -->
						<form id="form" method="POST" action="updateTrack">
							<img src="${path}/images/covers/${album.albumCover }"
								alt="Album cover" /><input id="trackId" name="trackId"
								type="hidden" value="${track.inventoryId }" /> Title <input
								id="trackTitle" name="trackTitle" size="30" maxLength="150"
								type="text" value="${track.trackTitle }" /><br /> Album <input
								id="albumTitle" name="albumTitle" size="30" maxLength="200"
								type="text" value="${ album.albumTitle }" /><br /> Artist <input
								id="artist" name="artist" size="30" maxLength="150" type="text"
								value="${track.artist }" /><br /> Songwriter(s) <input
								id="songWriter" name="songWriter" size="30" maxLength="200"
								type="text" value="${track.songWriter }" /><br /> Length <input
								id="songLength" size="30" maxLength="10" name="songLength"
								placeHolder="Ex. 59:59" type="text" value="${track.playLength }" /><br />
							Genre <input id="genre" name="genre" size="30" maxLength="45"
								type="text" value="${track.musicCategory }" /><br /> Cost
							Price ($) <input id="costPrice" name="costPrice"
								placeHolder="Ex. 12.99" size="30" type="text"
								value="<fmt:formatNumber type="number" value="${track.costPrice }" minFractionDigits="2" />" /><br />
							List Price ($) <input id="listPrice" name="listPrice"
								placeHolder="Ex. 12.99" size="30" type="text"
								value="<fmt:formatNumber type="number" value="${track.listPrice }" minFractionDigits="2" />" /><br />
							Sale Price ($) <input id="salePrice" name="salePrice"
								placeHolder="Ex. 12.99" size="30" type="text"
								value="<fmt:formatNumber type="number" value="${track.salePrice }" minFractionDigits="2" />" /><br />
							Type of Sale <select id="typeOfSale" name="typeOfSale">
								<option value="0"
									<c:if test="${ track.typeOfSale == 0 }">selected</c:if>>Single
									Track</option>
								<option value="1"
									<c:if test="${ track.typeOfSale == 1 }">selected</c:if>>Only
									Album</option>
								<option value="2"
									<c:if test="${ track.typeOfSale == 2 }">selected</c:if>>Both</option>
							</select><br /> Removal Status <select id="removalStatus"
								name="removalStatus">
								<option value="0"
									<c:if test="${ !track.removalStatus }">selected</c:if>>Online</option>
								<option value="1"
									<c:if test="${ track.removalStatus }">selected</c:if>>Offline</option>
							</select><br /> <input type="reset" value="Clear" /> <input
								type="submit" value="Submit" />
						</form>
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