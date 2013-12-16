<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var validateFormScript = '<script type="text/javascript" src="validateAlbumFormScript.js"/>';
	$(document).ready(function() {
		$("head").append(validateFormScript);
	});
</script>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- editAlbum.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test='${client.status }'>
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Edit Album</h2>
				<div class="inBlock" id="edit">
					<h3>Album Information</h3>
					<div id="editAlbumDiv">
						<!-- form to edit an album -->
						<form id="form" method="POST" action="updateAlbum"
							enctype="multipart/form-data">
							<img src="${path}/images/covers/${album.albumCover }"
								alt="Album cover" /> <input id="albumId" name="albumId"
								type="hidden" value="${album.albumId }" />Album Title <input
								id="albumTitle" name="albumTitle" size="30" maxLength="200"
								type="text" value="${album.albumTitle }" /><br /> Artist <input
								id="artist" name="artist" size="30" maxLength="200" type="text"
								value="${album.artist }" /><br /> Album Cover <input
								id="albumCover" name="albumCover" size="30" maxLength="200"
								type="file" value="${album.albumCover }" /><br /> Genre <input
								id="genre" name="genre" size="30" maxLength="50" type="text"
								value="${album.musicCategory }" /><br /> Release Date <input
								id="releaseDate" name="releaseDate" placeHolder="YYYY-MM-DD"
								size="30" type="text" value="${album.releaseDate }" /><br />
							Record Label <input id="recordLabel" name="recordLabel" size="30"
								maxLength="150" type="text" value="${album.recordLabel }" /><br />
							Cost Price ($) <input id="costPrice" name="costPrice"
								placeHolder="12.99" size="30" type="text"
								value="<fmt:formatNumber type="number" value="${album.costPrice }" minFractionDigits="2" />" /><br />
							List Price ($) <input id="listPrice" name="listPrice"
								placeHolder="12.99" size="30" type="text"
								value="<fmt:formatNumber type="number" value="${album.listPrice }" minFractionDigits="2" />" /><br />
							Sale Price ($) <input id="salePrice" name="salePrice"
								placeHolder="12.99" size="30" type="text"
								value="<fmt:formatNumber type="number" value="${album.salePrice }" minFractionDigits="2" />" /><br />
							Removal Status <select id="removalStatus" name="removalStatus">
								<option value="0"
									<c:if test="${ !album.removalStatus }">selected</c:if>>Online</option>
								<option value="1"
									<c:if test="${ album.removalStatus }">selected</c:if>>Offline</option>
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