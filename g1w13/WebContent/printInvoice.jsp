<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- printInvoice.jsp -->

<title>The MUSIC Outlet</title>
<link rel="SHORTCUT ICON" href="images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="invoiceStyle.css" />
<div id="list_invoice">
	<div id="invoice_Info">
		<!-- display client info -->
		<h4>Invoice Information</h4>
		Client Name:
		<c:out value="${client.firstName }" />
		<c:out value="${client.lastName }" />
		<br> Client Email:
		<c:out value="${client.lastName }" />
		<br> Invoice Number:
		<c:out value="${invoice.saleId }" />
		<br> Invoice Date:
		<fmt:formatDate type="date" value="${invoice.saleDate }" />
	</div>
	<br>
	<!-- display the list of item bought -->
	<table cellspacing="0">
		<tr>
			<th>Type</th>
			<th>Artist</th>
			<th>Genre</th>
			<th>Title</th>
			<th>Price</th>
		</tr>
		<c:if test="${cart.count == 0}">
			<tr class="tr1">
				<td colspan="6">
					<h4>There are no items in your shopping cart</h4>
				</td>
			</tr>
		</c:if>
		<!-- display all the albums -->
		<c:forEach var="cartAlbum" items="${cart.albums}"
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
		<!-- display all the tracks -->
		<c:forEach var="cartTrack" items="${cart.tracks}"
			varStatus="loopCounter">
			<c:choose>
				<c:when test="${cart.isAlbumsEven}">
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
	<br />
	<!-- display the total of the purchase -->
	<div id="total">
		Total off taxes:
		<fmt:formatNumber type="currency" value="${cart.subTotal}" />
		<br /> GST:
		<fmt:formatNumber type="currency" value="${cart.GSTTaxes}" />
		<br />HST:
		<fmt:formatNumber type="currency" value="${cart.HSTTaxes}" />
		<br /> PST:
		<fmt:formatNumber type="currency" value="${cart.PSTTaxes}" />
		<br />Total with taxes: <b><fmt:formatNumber type="currency"
				value="${cart.totalTaxes}" /></b>
	</div>
</div>