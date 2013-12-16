
<%@include file="./includes/header.jsp"%>
<c:set var="NUM_OF_RECORDS_PER_PAGE" value="10"></c:set>

<!-- Anthony-Virgil Bermejo, Dorian Mein -->
<!-- 0831360 -->
<!-- viewOrders.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Order Management</h2>
				<div class="inBlock" id="order">
					<h4>View Orders</h4>
					<!-- form to select an order-->
					<form action="<c:url value='orders'></c:url>" method="get">
						<input type="text" size="39" placeholder="Search value"
							name="searchValue" value="${searchValue }" /> <input
							type="reset" value="Clear" /> <input type="submit"
							value="Search" /><br /> <label><input type="radio"
							name="searchType" value="clientEmail"
							${searchType.equals("clientEmail")?"checked":""}>&nbsp;Client
							Email</label>&nbsp;&nbsp;&nbsp; <label><input type="radio"
							name="searchType" value="saleId"
							${searchType.equals("saleId")?"checked":""}>&nbsp;Order
							ID</label>
					</form>
					<h4>Results</h4>
					<p>We found a total of ${searchOrdersTotal } orders for your
						search</p>
					<!-- display the list of order -->
					<div id="list_order">
						<c:if test="${searchOrders!=null && searchOrdersTotal >0}">
							<table cellspacing="0">
								<tr>
									<th style="width: 50px;">Order Id</th>
									<th style="width: 220px;">Client Email</th>
									<th style="width: 100px;">Date</th>
									<th style="width: 150px;">Total Purchase</th>
									<th style="width: 50px;">Status</th>
									<th style="width: 50px;">Modify</th>
								</tr>
								<c:forEach var="searchOrder" items="${searchOrders}"
									varStatus="loopCounter">
									<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
										<td><c:out value="${searchOrder.saleId }" /></td>
										<td><c:out value="${searchOrder.email }" /></td>
										<td><fmt:formatDate type="date"
												value="${searchOrder.saleDate }" /></td>
										<td><fmt:formatNumber type="currency"
												value="${searchOrder.netValue}"></fmt:formatNumber></td>
										<td><c:out
												value="${searchOrder.removalStatus ? 'Offline' : 'Online' }" /></td>
										<td><a
											href="<c:url value="editOrder">
										<c:param name="saleId" value="${searchOrder.saleId }"/></c:url>">
												<img src="${path}/images/edit_icon.png" />
										</a></td>
									</tr>
								</c:forEach>

							</table>
							<!-- for navigate beetwen the page -->
							<div class="right">Viewing records
								${ordersPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
								&nbsp;-&nbsp;
								${(ordersPage*NUM_OF_RECORDS_PER_PAGE)>searchOrdersTotal?
								searchOrdersTotal : ordersPage*NUM_OF_RECORDS_PER_PAGE}
								&nbsp;of&nbsp; ${searchOrdersTotal} &nbsp;records</div>
							<c:if test="${ordersPage > 1}">
								<a
									href="<c:url value="orders"> <c:param name="newQuery" value="false"/><c:param name="searchType" value="${searchType}"/><c:param name="searchValue" value="${searchValue}"/><c:param name="ordersPage" value="1"/></c:url>">&lt;&lt;</a>
					&nbsp;
						<a
									href="<c:url
							value="orders"><c:param name="newQuery" value="false"/><c:param name="searchType" value="${searchType}"/><c:param name="searchValue" value="${searchValue}"/><c:param name="ordersPage" value="${ordersPage-1}"/></c:url>">&lt;</a>
							</c:if>
					&nbsp;<b>${ordersPage}</b>&nbsp;
					<c:if test="${ordersPage < maxOrdersPage}">
								<a
									href="<c:url
							value="orders"><c:param name="newQuery" value="false"/><c:param name="searchType" value="${searchType}"/><c:param name="searchValue" value="${searchValue}"/><c:param name="ordersPage" value="${ordersPage+1}"/></c:url>">&gt;</a>
							
					&nbsp;
						<a
									href="<c:url
							value="orders"><c:param name="newQuery" value="false"/><c:param name="searchType" value="${searchType}"/><c:param name="searchValue" value="${searchValue}"/><c:param name="ordersPage" value="${maxOrdersPage}"/></c:url>">&gt;&gt;</a>

							</c:if>
						</c:if>
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