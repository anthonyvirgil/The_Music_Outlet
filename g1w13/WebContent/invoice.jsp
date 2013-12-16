<%@include file="includes/header.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- invoice.jsp -->


<div class="block">
	<h2>Invoice</h2>
	<div class="inBlock">
		<h3>Order Invoice</h3>
		<!-- display info about the client -->
		<div id="invoice_Info">
			<h4>Invoice Information</h4>
			Client Name:
			<c:out value="${client.firstName }" />
			<c:out value="${client.lastName }" />
			<br> Client Email:
			<c:out value="${client.email }" />
			<br> Invoice Number:
			<c:out value="${invoice.saleId }" />
			<br> Invoice Date:
			<fmt:formatDate type="date" value="${invoice.saleDate }" />
		</div>
		<br>
		<!-- display all the item bought -->
		<div id="list_invoice">
			<table cellspacing="0">
				<tr>
					<th>Type</th>
					<th>Artist</th>
					<th>Genre</th>
					<th>Title</th>
					<th>Price</th>
				</tr>
				<c:if test="${oldCart.count == 0}">
					<tr class="tr1">
						<td colspan="6">
							<h4>There are no items in your shopping cart</h4>
						</td>
					</tr>
				</c:if>
				<c:forEach var="cartAlbum" items="${oldCart.albums}"
					varStatus="loopCounter">
					<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
						<td>Album</td>
						<td><c:out value="${cartAlbum.artist}" /></td>
						<td><c:out value="${cartAlbum.musicCategory}" /></td>
						<td><c:out value="${cartAlbum.albumTitle}" /></td>
						<td><fmt:formatNumber type="currency"
								value="${cartAlbum.currentPrice}" /></td>
					</tr>
				</c:forEach>
				<c:forEach var="cartTrack" items="${oldCart.tracks}"
					varStatus="loopCounter">
					<c:choose>
						<c:when test="${oldCart.isAlbumsEven}">
							<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
						</c:when>
						<c:otherwise>
							<tr class="${loopCounter.count%2==0?'tr1':'tr2'}">
						</c:otherwise>
					</c:choose>
					<td>Track</td>
					<td><c:out value="${cartTrack.artist}" /></td>
					<td><c:out value="${cartTrack.musicCategory}" /></td>
					<td><c:out value="${cartTrack.trackTitle}" /></td>
					<td><fmt:formatNumber type="currency"
							value="${cartTrack.currentPrice}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br />
		<!-- display the total of the purchase -->
		<div id="total">
			Total off taxes:
			<fmt:formatNumber type="currency" value="${oldCart.subTotal}" />
			<br /> GST:
			<fmt:formatNumber type="currency" value="${oldCart.GSTTaxes}" />
			<br />HST:
			<fmt:formatNumber type="currency" value="${oldCart.HSTTaxes}" />
			<br /> PST:
			<fmt:formatNumber type="currency" value="${oldCart.PSTTaxes}" />
			<br />Total with taxes: <b><fmt:formatNumber type="currency"
					value="${oldCart.totalTaxes}" /></b>
		</div>
		<!-- button -->
		<div class="buttonGroup">
			<form action="printInvoice" method="get">
				<input type="submit" value="Print" />
			</form>
			<form action="purchasedTracks#content" method="get">
				<input type="submit" value="Download" />
			</form>
			<form action="index" method="get">
				<input type="submit" value="Main Page" />
			</form>
		</div>
		<div class="end">&nbsp;</div>
	</div>
</div>

<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/quizRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>