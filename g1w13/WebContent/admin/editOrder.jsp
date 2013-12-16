<%@include file="./includes/header.jsp"%>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- editOrder.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status}">
			<div class="mblock">
				<h2>Order Management</h2>
				<div class="inBlock">
					<div id="list_order">
						<div id="invoice_Info">
							<h4>Invoice Information</h4>
							Client Name:
							<c:out value="${invoiceClient.firstName }" />
							<c:out value="${invoiceClient.lastName }" />
							<br> Client Email:
							<c:out value="${invoiceClient.email }" />
							<br> Invoice Number:
							<c:out value="${invoice.saleId }" />
							<br> Invoice Date:
							<fmt:formatDate type="date" value="${invoice.saleDate }" />
						</div>
						<br>
						<h4>List of Items</h4>
						<table cellspacing="0">
							<tr>
								<th>Type</th>
								<th>Artist</th>
								<th>Genre</th>
								<th>Title</th>
								<th>Price</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
							<c:if test="${invoiceAlbums == null && invoiceTracks == null}">
								<tr class="tr1">
									<td colspan="6">
										<h4>There are no items in the invoice</h4>
									</td>
								</tr>
							</c:if>
							<c:forEach var="invoiceAlbum" items="${invoiceAlbums}"
								varStatus="loopCounter">
								<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
									<td>Album</td>
									<td><c:out value="${invoiceAlbum.artist}" /></td>
									<td><c:out value="${invoiceAlbum.genre}" /></td>
									<td><c:out value="${invoiceAlbum.albumTitle}" /></td>
									<td><fmt:formatNumber type="currency"
											value="${invoiceAlbum.salePrice}" /></td>
									<td><c:out
											value="${invoiceAlbum.removalStatus ? 'Offline' : 'Online' }" /></td>
									<td><a
										href="<c:url value="removeDetail"><c:param name="detailId" value="${invoiceAlbum.detailId}"/><c:param name="saleId" value="${invoice.saleId }"/><c:param name="status" value="${invoiceAlbum.removalStatus}"/></c:url>"><img
											src="${path}/images/${ invoiceAlbum.removalStatus? 'checkmark_icon.png' : 'remove_icon.png' }" /></a></td>
								</tr>
								<c:set var="albumCount" value="${loopCounter.count}" />
							</c:forEach>
							<c:forEach var="invoiceTrack" items="${invoiceTracks}"
								varStatus="loopCounter">
								<c:choose>
									<c:when test="${albumCount%2 == 0}">
										<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
									</c:when>
									<c:otherwise>
										<tr class="${loopCounter.count%2==0?'tr1':'tr2'}">
									</c:otherwise>
								</c:choose>
								<td>Track</td>
								<td><c:out value="${invoiceTrack.artist}" /></td>
								<td><c:out value="${invoiceTrack.genre}" /></td>
								<td><c:out value="${invoiceTrack.trackTitle}" /></td>
								<td><fmt:formatNumber type="currency"
										value="${invoiceTrack.salePrice}" /></td>
								<td><c:out
										value="${invoiceTrack.removalStatus ? 'Offline' : 'Online' }" /></td>
								<td><a
									href="<c:url value="removeDetail"><c:param name="detailId" value="${invoiceTrack.detailId}"/><c:param name="saleId" value="${invoice.saleId }"/><c:param name="status" value="${invoiceTrack.removalStatus}"/></c:url>"><img
										src="${path}/images/${ invoiceTrack.removalStatus ? 'checkmark_icon.png' : 'remove_icon.png' }" /></a></td>
								</tr>
							</c:forEach>
						</table>
						<div class="buttonGroup">
							<c:choose>
								<c:when test="${!invoice.removalStatus }">
									<form action="removeInvoice" method="get">
										<input type="hidden" name="saleId" value="${invoice.saleId }" />
										<input type="hidden" name="status"
											value="${invoice.removalStatus }" /> <input type="submit"
											value="Remove entire order" />
									</form>
								</c:when>
								<c:otherwise>
									<form action="removeInvoice" method="get">
										<input type="hidden" name="saleId" value="${invoice.saleId }" />
										<input type="hidden" name="status"
											value="${invoice.removalStatus }" /> <input type="submit"
											value="Re-add entire order" />
									</form>
								</c:otherwise>
							</c:choose>
							<form action="orders" method="get">
								<input type="submit" value="Go back" />
							</form>
						</div>
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