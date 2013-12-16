<%@include file="./includes/header.jsp"%>

<!-- Dorian Mein -->
<!-- index.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Management Tools</h2>
				<!-- List of all the management page-->
				<div class="inBlock" id="frontdoor">
					<ul>
						<li><a href='<c:url value="./viewInv"></c:url>'>Edit/View
								Inventory</a><br />&nbsp;
							<h3>Manage the inventory of tracks and albums</h3></li>
						<li><a href='<c:url value="./reports"></c:url>'>View
								Sales Reports</a><br />&nbsp;
							<h3>Check detailed or summarized sales repots</h3></li>
						<li><a href='<c:url value="./sale"></c:url>'>Set Sales</a><br />&nbsp;
							<h3>Change the sale prices of inventory</h3></li>
						<li><a href='<c:url value="./track"></c:url>'>Add Track
								to Inventory</a><br />&nbsp;
							<h3>Add new track to the inventory</h3></li>
						<li><a href='<c:url value="./album"></c:url>'>Add Album
								to Inventory</a><br />&nbsp;
							<h3>Add new album to the inventory</h3></li>
						<li><a href='<c:url value="./editClient"></c:url>'>Client
								Management</a><br />&nbsp;
							<h3>Manage client accounts</h3></li>
						<li><a href='<c:url value="./reviews"></c:url>'>Review
								Management</a><br />&nbsp;
							<h3>Approve or disapprove reviews</h3></li>
						<li><a href='<c:url value="./orders"></c:url>'>Order
								Management</a><br />&nbsp;
							<h3>Manage client orders</h3></li>
						<li><a href='<c:url value="./populateRss"></c:url>'>News
								Feed Management</a><br />&nbsp;
							<h3>Manage the news feed for client website</h3></li>
						<li><a href='<c:url value="./survey"></c:url>'>Survey
								Management</a><br />&nbsp;
							<h3>Manage the survey for client website</h3></li>
						<li><a href='<c:url value="./ad"></c:url>'>Banner Ad
								Management</a><br />&nbsp;
							<h3>Manage the banner ad for client website</h3></li>
					</ul>
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