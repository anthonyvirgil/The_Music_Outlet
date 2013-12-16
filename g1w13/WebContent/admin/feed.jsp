<%@include file="./includes/header.jsp"%>

<!-- Venelin Koulaxazov, Dorian Mein -->
<!-- 1032744 -->
<!-- feed.jsp -->


<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status }">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>News Feed Manager</h2>
				<div class="inBlock" id="feed">
					<!-- display the actual news feed -->
					<h4>Rss Feed currently in use</h4>
					<h5>
						<c:out value="${currentRss.name }" />
					</h5>
					<a href="<c:url value='${currentRss.url }'></c:url>"
						target="_blank"><c:out value="${currentRss.url }" /></a> <br>
					<br>
					<h4>Other News Feeds</h4>
					<!-- test if they are other news feeds -->
					<c:choose>
						<c:when test="${empty otherRssFeeds }">
							<p>
								<i>There are no other rss feeds in the database</i>
							</p>
						</c:when>
						<c:otherwise>
							<!-- list all the other news feeds -->
							<c:forEach var="rssFeed" items="${otherRssFeeds }">
								<h5>
									<c:out value="${rssFeed.name }" />
								</h5>
								<a href="<c:url value='${rssFeed.url }'></c:url>"
									target="_blank"><c:out value="${rssFeed.url }" /></a>
								<a
									href="<c:url value='updateRss'><c:param name='rssFeedId' value='${rssFeed.rssFeedId}'/></c:url>"><img
									src="${path}/images/checkmark_icon.png" /></a>
								<a
									href="<c:url value='removeRss'><c:param name='rssFeedId' value='${rssFeed.rssFeedId}'/></c:url>"><img
									src="${path}/images/remove_icon.png" /></a>
								<br>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					<br> <br>
					<!-- Form to add a new feed -->
					<h4>Add a new feed</h4>
					<br>
					<form method="POST" action="./addRss">
						<label>Name : </label><input type="text" name="name" id="name"
							value="${name }"><br> <label>Link : </label>&nbsp;&nbsp;<input
							type="text" name="link" value="${link }" id="link"><br>
						<input type="submit" value="Submit" /><br> <span><c:out
								value="${servletError }" /></span>
					</form>
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