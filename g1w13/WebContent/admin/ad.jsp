<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var enableAdDiv = '<script type="text/javascript" src="enableAdDiv.js"/>';
	$(document).ready(function() {
		$("head").append(enableAdDiv);
	});
</script>

<!-- Venelin Koulaxazov, Dorian Mein -->
<!-- 1032744 -->
<!-- ad.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<div class="mblock">
		<h2>Ad Manager</h2>
		<div class="inBlock">
			<c:choose>
				<c:when test="${client.status }">
				<!-- test if it is an Admin -->
					<div id="adTypeDiv">
						<!-- form for choose the type of ad -->
						<form method="POST" action="populateAds" id="selectAdTypeForm">
							<label for="bottom">View Bottom Ads</label> <input type="radio"
								name="typeOfAd" value="bottom"
								<c:if test="${type == 'bottom' }">checked</c:if> id="bottom" />
							<br> <label for="right">View Right Ads</label> <input
								type="radio" name="typeOfAd" value="right"
								<c:if test="${type == 'right' }">checked</c:if> id="right" /> <br>
							<br> <input type="submit" value="View Ads" />
						</form>
					</div>
					<div id="ad">
						<input type="hidden" name="hiddenField" id="hiddenField"
							value="${type }" />
						<!-- display the banner ad -->
						<h4>Banner ad currently in use</h4>
						<a href="<c:url value='${currentBanner.url }'></c:url>"
							target="_blank"><c:out value="${currentBanner.url }" /></a> <br>
						<img src="${path}/images/ads/${currentBanner.imageSource }"
							width="350px" height="auto" /> <br> <br>
						<h4>Other banner ads</h4>
						<!-- test for the list of other banners-->
						<c:choose>
							<c:when test="${empty otherBannerAds }">
								<p>
									<i>There are no other banner ads in the database</i>
								</p>
							</c:when>
							<c:otherwise>
								<table cellspacing="0">
									<tr>
										<th>URL</th>
										<th>Image</th>
										<th>Make current banner ad</th>
										<th>Remove banner ad</th>
									</tr>
									<!-- display each banner in the database -->
									<c:forEach var="bannerAd" items="${otherBannerAds }"
										varStatus="loopCounter">
										<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
											<td><a href="<c:url value='${bannerAd.url }'></c:url>"
												target="_blank"><c:out value="${bannerAd.url }" /></a></td>
											<td><img id="td_banner"
												src="${path}/images/ads/${bannerAd.imageSource }" /></td>
											<td><a
												href="<c:url value='updateBannerAd'><c:param name='bannerAdId' value='${bannerAd.bannerId}'/><c:param name="typeOfTheAd" value='${type }'/></c:url>"><img
													src="${path}/images/checkmark_icon.png" /></a></td>
											<td><a
												href="<c:url value='removeBannerAd'><c:param name='bannerAdId' value='${bannerAd.bannerId}'/><c:param name="typeOfTheAd" value='${type }'/></c:url>"><img
													src="${path}/images/remove_icon.png" /></a></td>
										</tr>
									</c:forEach>
								</table>
							</c:otherwise>
						</c:choose>
						<br> <br>
						<!-- add a new banner -->
						<h4>Add a new banner ad</h4>
						<br>
						<form method="POST" action="./addBannerAd"
							enctype="multipart/form-data">
							<label for="newBottom">Bottom Ad</label> <input type="radio"
								name="newAdType" value="bottom"
								<c:if test="${type == 'bottom' }">checked</c:if> id="newBottom" />
							<br> <label for="newRight">Right Ad</label> <input
								type="radio" name="newAdType" value="right"
								<c:if test="${type == 'right' }">checked</c:if> id="newRight" />
							<br> <label>Link : </label><input type="text" name="link"
								id="link"><br> <label>Image: </label><input
								type="file" name="imageSource" value="${imageSource }"
								id="imageSource"><br> <input type="submit"
								value="Submit" /><br> <span><c:out
									value="${servletError }" /></span>
						</form>
					</div>
				</c:when>
				<c:otherwise>
				<!-- include for non Admin -->
					<%@include file="./includes/nopermissions.jsp"%>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
</body>
</html>