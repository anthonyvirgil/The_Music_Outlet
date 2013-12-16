<%@include file="includes/header.jsp"%>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- orderConfirmation.jsp -->

<div class="block">
<h2>Confirmation</h2>
	<div class="inBlock">
	<!-- display client info -->
	<h3>Client Information</h3>
	<p>
		First Name:
		<c:out value="${client.firstName}" />
		<br /> Last Name:
		<c:out value="${client.lastName}" />
		<br /> E-Mail Address:
		<c:out value="${client.email}" />
		<br /> <br /> Credit Card Holder:
		<c:out value="${creditCard.cardHolder}" />
		<br /> Credit Card Number: XXXX-XXXX-XXXX-
		<c:out value="${creditCard.lastFourDigits}" />
		<br /> Expiration Date:
		<c:out value="${creditCard.expirationMonth}" />
		/
		<c:out value="${creditCard.expirationYear}" />
		<br />
	</p>
	<br />
	<h3>Shopping Items</h3>
	<!-- display the list of item in the cart -->
	<div id="list_shop">
		<table cellspacing="0">
			<tr>
				<th>Type</th>
				<th>Artist</th>
				<th>Genre</th>
				<th>Title</th>
				<th>Price</th>
			</tr>
			<c:if test="${cart.count==0}">
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
	</div>
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
	<!-- button -->
	<div class="buttonGroup">
		<form action="cart#content" method="get">
			<input type="submit" value="Cancel" />
		</form>
		<form action="invoice#content" method="post">
			<input type="submit" value="Confirm" />
		</form>
	</div>
</div></div>

<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/quizRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>