<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var style = '<link rel="stylesheet" type="text/css" href="regStyle.css"/>';
	var validateFormScript = '<script type="text/javascript" src="./validateTrackFormScript.js"/>';
	$(document).ready(function() {
		$("head").append(style);
		$("head").append(validateFormScript);
	});
</script>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- addTrack.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test='${client.status }'>
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Add Inventory</h2>
				<div class="inBlock">
					<h4>Add Track</h4>
					<div id="addTrackDiv">
						<!-- test if the album exist -->
						<c:if test="${!empty albumError }">
							<h3>
								<c:out value="${albumError }" />
								Click <a href="<c:url value="./addAlbum.jsp"/>">here</a>
							</h3>
						</c:if>
						<!-- form to add an track -->
						<form id="form" method="POST" action="submitTrack"
							class="formClass">
							<label>Title </label><input id="trackTitle" name="trackTitle"
								size="30" maxLength="150" type="text" class="textFields"
								value="${track.trackTitle }" /> <label>Album</label> <input
								id="albumTitle" name="albumTitle" size="30" maxLength="200"
								type="text" value="${ albumName }" class="textFields" /> <label>Artist</label>
							<input id="artist" name="artist" size="30" maxLength="150"
								class="textFields" type="text" value="${track.artist }" /> <label>Songwriter(s)</label>
							<input id="songWriter" name="songWriter" size="30"
								class="textFields" maxLength="200" type="text"
								value="${track.songWriter }" /> <label>Length</label> <input
								id="songLength" size="30" class="textFields" maxLength="10"
								name="songLength" placeHolder="Ex. 59:59" type="text"
								value="${track.playLength }" /> <label>Genre</label> <input
								id="genre" name="genre" size="30" maxLength="45"
								class="textFields" type="text" value="${track.musicCategory }" />
							<label>Cost Price ($) </label><input id="costPrice"
								name="costPrice" placeHolder="Ex. 12.99" size="30" type="text"
								class="textFields"
								value="<fmt:formatNumber type="number" value="${track.costPrice }" minFractionDigits="2" />" />
							<label>List Price ($)</label> <input id="listPrice"
								class="textFields" name="listPrice" placeHolder="Ex. 12.99"
								size="30" type="text"
								value="<fmt:formatNumber type="number" value="${track.listPrice }" minFractionDigits="2" />" />
							<label>Sale Price ($)</label> <input id="salePrice"
								class="textFields" name="salePrice" placeHolder="Ex. 12.99"
								size="30" type="text"
								value="<fmt:formatNumber type="number" value="${track.salePrice }" minFractionDigits="2" />" />
							<label>Type of Sale</label> <select id="typeOfSale"
								name="typeOfSale">
								<option value="0"
									<c:if test="${ track.typeOfSale == 0 }">selected</c:if>>Single
									Track</option>
								<option value="1"
									<c:if test="${ track.typeOfSale == 1 }">selected</c:if>>Only
									Album</option>
								<option value="2"
									<c:if test="${ track.typeOfSale == 2 }">selected</c:if>>Both</option>
							</select> <label> Removal Status</label> <select id="removalStatus"
								name="removalStatus">
								<option value="0"
									<c:if test="${ !track.removalStatus }">selected</c:if>>Online</option>
								<option value="1"
									<c:if test="${ track.removalStatus }">selected</c:if>>Offline</option>
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