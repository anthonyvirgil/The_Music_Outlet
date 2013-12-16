<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var style = '<link rel="stylesheet" type="text/css" href="regStyle.css"/>';
	var validateFormScript = '<script type="text/javascript" src="validateAlbumFormScript.js"/>';
	$(document).ready(function() {
		$("head").append(style);
		$("head").append(validateFormScript);
	});
</script>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- addAlbum.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test='${client.status }'>
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Add Inventory</h2>
				<div class="inBlock">
					<h4>Add Album</h4>
					<div id="addAlbumDiv">
					<!-- form to add an album -->
						<form id="form" method="POST" action="submitAlbum"
							enctype="multipart/form-data" class="formClass">
							<label>Title</label> <input id="albumTitle" name="albumTitle"
								size="30" maxLength="200" type="text" class="textFields"
								value="${album.albumTitle }" /> <label>Artist</label> <input
								id="artist" name="artist" size="30" maxLength="200" type="text"
								value="${album.artist }" class="textFields" /> <label>Album
								Cover</label> <input id="albumCover" name="albumCover" size="30"
								maxLength="200" type="file" value="${album.albumCover }" /> <label>Genre</label>
							<input id="genre" name="genre" size="30" maxLength="50"
								class="textFields" type="text" value="${album.musicCategory }" />
							<label>Release Date </label><input id="releaseDate"
								name="releaseDate" placeHolder="YYYY-MM-DD" size="30"
								type="text" class="textFields" value="${album.releaseDate }" />
							<label>Record Label</label> <input id="recordLabel"
								name="recordLabel" size="30" maxLength="150" type="text"
								value="${album.recordLabel }" class="textFields" /> <label>Cost
								Price ($)</label> <input id="costPrice" name="costPrice"
								placeHolder="12.99" size="30" type="text" class="textFields"
								value="<fmt:formatNumber type="number" value="${album.costPrice }" minFractionDigits="2" />" />
							<label>List Price ($)</label> <input id="listPrice"
								class="textFields" name="listPrice" placeHolder="12.99"
								size="30" type="text"
								value="<fmt:formatNumber type="number" value="${album.listPrice }" minFractionDigits="2" />" />
							<label>Sale Price ($)</label> <input id="salePrice"
								class="textFields" name="salePrice" placeHolder="12.99"
								size="30" type="text"
								value="<fmt:formatNumber type="number" value="${album.salePrice }" minFractionDigits="2" />" />
							<label>Removal Status</label> <select id="removalStatus"
								name="removalStatus">
								<option value="0"
									<c:if test="${ !album.removalStatus }">selected</c:if>>Online</option>
								<option value="1"
									<c:if test="${ album.removalStatus }">selected</c:if>>Offline</option>
							</select>
							<div class="buttonGroup">
								<input type="reset" value="Clear" id="clearButton" /> <input
									type="submit" value="Submit" id="submitButton" />
							</div>
						</form>
					</div>
					<div class="end">&nbsp;</div>
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