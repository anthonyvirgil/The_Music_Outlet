<%@include file="./includes/header.jsp"%>
<script type="text/javascript">
	var validateFormScript = '<script type="text/javascript" src="./validateReportFormScript.js"/>';
	$(document).ready(function() {
		$("head").append(validateFormScript);
	});
</script>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- report.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Management Tools</h2>
				<div class="inBlock" id="report">
					<!-- form to select the report -->
					<form id="form" action="./reports" method="get">
						<select id="reportType" name="reportType">
							<option value="none"
								<c:if test="${ reportType == 'none' }">selected</c:if>>Choose
								a Report</option>
							<option value="total_sales"
								<c:if test="${ reportType == 'total_sales' }">selected</c:if>>Total
								Sales</option>
							<option value="sales_client"
								<c:if test="${ reportType == 'sales_client' }">selected</c:if>>Sales
								by Client</option>
							<option value="sales_artist"
								<c:if test="${ reportType == 'sales_artist' }">selected</c:if>>Sales
								by Artist</option>
							<option value="sales_track"
								<c:if test="${ reportType == 'sales_track' }">selected</c:if>>Sales
								by Track</option>
							<option value="sales_album"
								<c:if test="${ reportType == 'sales_album' }">selected</c:if>>Sales
								by Album</option>
							<option value="top_clients"
								<c:if test="${ reportType == 'top_clients' }">selected</c:if>>Top
								Clients</option>
							<option value="top_sellers"
								<c:if test="${ reportType == 'top_sellers' }">selected</c:if>>Top
								Sellers</option>
							<option value="zero_tracks"
								<c:if test="${ reportType == 'zero_tracks' }">selected</c:if>>Zero
								Tracks</option>
							<option value="zero_clients"
								<c:if test="${ reportType == 'zero_clients' }">selected</c:if>>Zero
								Clients</option>
						</select> <br /> <input id="searchInput" type="text" size="39"
							placeholder="Search value" name="searchValue"
							value="<c:out value="${searchValue }"/>" /> <input type="reset"
							value="Clear" /> <input type="submit" value="Search" /><br />
						<input type="checkbox" name="detailed"
							<c:if test="${detailed }">checked</c:if> /> Detailed Report <br />
						Start Date : <input id="startDate" type="text" name="startDate"
							placeHolder="YYYY-MM-DD" value="${startDate }"><br />
						End Date : <input id="endDate" type="text" name="endDate"
							placeHolder="YYYY-MM-DD" value=${endDate }>
					</form>
					<br />
					<!-- Resum report -->
					<div id="totalsReport">
						<h4>Financial Report</h4>
						<p>
							<b>Total Sales:</b>
							<fmt:formatNumber type="currency" value="${totalSales }" />
						</p>
						<p>
							<b>Total Cost:</b>
							<fmt:formatNumber type="currency" value="${totalCost }" />
						</p>
						<p>
							<b>Total Profit:</b>
							<fmt:formatNumber type="currency" value="${totalProfit }" />
						</p>
					</div>
					<br>
					<c:choose>
						<c:when test="${ reportType == 'total_sales' }">
						<!-- report for total sales -->
							<h4>Total Sales Report - ${detailed ? 'Detailed' : 'Summary'
								}</h4>
							<div class="list_report">
								<c:choose>
									<c:when test="${ totalSummary == 0 && empty reportList }">
										<h3>No results found for '${searchValue}'</h3>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${ !detailed }">
												<p>
													<b>Total Sales:</b>
													<fmt:formatNumber type="currency" value="${totalSummary }" />
												</p>
											</c:when>
											<c:otherwise>
												<table cellspacing="0">
													<tr>
														<th>Client</th>
														<th>Type</th>
														<th>Artist</th>
														<th>Title</th>
														<th>Date</th>
														<th>Sale Price</th>
													</tr>
													<c:forEach var="report" items="${reportList}"
														varStatus="loopCounter">
														<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
															<c:choose>
																<c:when test="${!empty report.albumTitle }">
																	<td><c:out value="${ report.clientEmail }" /></td>
																	<td>Album</td>
																	<td><c:out value="${ report.albumArtist }" /></td>
																	<td><c:out value="${ report.albumTitle }" /></td>
																	<td><fmt:formatDate type="date"
																			value="${report.saleDate }" /></td>
																	<td><fmt:formatNumber type="currency"
																			value="${report.salePrice }" /></td>
																</c:when>
																<c:otherwise>
																	<td><c:out value="${ report.clientEmail }" /></td>
																	<td>Track</td>
																	<td><c:out value="${ report.trackArtist }" /></td>
																	<td><c:out value="${ report.trackTitle }" /></td>
																	<td><fmt:formatDate type="date"
																			value="${report.saleDate }" /></td>
																	<td><fmt:formatNumber type="currency"
																			value="${report.salePrice }" /></td>
																</c:otherwise>
															</c:choose>
														</tr>
													</c:forEach>
												</table>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:when test="${ reportType == 'sales_client' }">
						<!-- report for sales client -->
							<h4>Sales by Client Report - ${detailed ? 'Detailed' :
								'Summary' }</h4>
							<div class="list_report">
								<c:choose>
									<c:when test="${ totalSummary == 0 && empty reportList }">
										<h3>No results found for '${searchValue}'</h3>
									</c:when>
									<c:otherwise>
										<c:if test="${!empty searchValue }">
											<p>
												<b>Results for</b> '${searchValue }'
											</p>
											<br>
										</c:if>
										<c:choose>
											<c:when test="${ !detailed }">
												<p>
													<b>Total Sales:</b>
													<fmt:formatNumber type="currency" value="${totalSummary }" />
												</p>
											</c:when>
											<c:otherwise>
												<table cellspacing="0">
													<tr>
														<th>Client</th>
														<th>Type</th>
														<th>Artist</th>
														<th>Title</th>
														<th>Date</th>
														<th>Sale Price</th>
													</tr>
													<c:forEach var="report" items="${reportList}"
														varStatus="loopCounter">
														<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
															<c:choose>
																<c:when test="${!empty report.albumTitle }">
																	<td><c:out value="${ report.clientEmail }" /></td>
																	<td>Album</td>
																	<td><c:out value="${ report.albumArtist }" /></td>
																	<td><c:out value="${ report.albumTitle }" /></td>
																	<td><fmt:formatDate type="date"
																			value="${report.saleDate }" /></td>
																	<td><fmt:formatNumber type="currency"
																			value="${report.salePrice }" /></td>
																</c:when>
																<c:otherwise>
																	<td><c:out value="${ report.clientEmail }" /></td>
																	<td>Track</td>
																	<td><c:out value="${ report.trackArtist }" /></td>
																	<td><c:out value="${ report.trackTitle }" /></td>
																	<td><fmt:formatDate type="date"
																			value="${report.saleDate }" /></td>
																	<td><fmt:formatNumber type="currency"
																			value="${report.salePrice }" /></td>
																</c:otherwise>
															</c:choose>
														</tr>
													</c:forEach>
												</table>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:when test="${ reportType == 'sales_artist' }">
						<!-- report for sales artist -->
							<h4>Sales by Artist Report - ${detailed ? 'Detailed' :
								'Summary' }</h4>
							<div class="list_report">
								<c:choose>
									<c:when test="${ totalSummary == 0 && empty reportList }">
										<h3>No results found for '${searchValue}'</h3>
									</c:when>
									<c:otherwise>
										<c:if test="${!empty searchValue }">
											<p>
												<b>Results for</b> '${searchValue }'
											</p>
											<br>
										</c:if>
										<c:choose>
											<c:when test="${ !detailed }">
												<p>
													<b>Total Sales:</b>
													<fmt:formatNumber type="currency" value="${totalSummary }" />
												</p>
											</c:when>
											<c:otherwise>
												<table cellspacing="0">
													<tr>
														<th>Client</th>
														<th>Type</th>
														<th>Artist</th>
														<th>Title</th>
														<th>Date</th>
														<th>Sale Price</th>
													</tr>
													<c:forEach var="report" items="${reportList}"
														varStatus="loopCounter">
														<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
															<c:choose>
																<c:when test="${!empty report.albumTitle }">
																	<td><c:out value="${ report.clientEmail }" /></td>
																	<td>Album</td>
																	<td><c:out value="${ report.albumArtist }" /></td>
																	<td><c:out value="${ report.albumTitle }" /></td>
																	<td><fmt:formatDate type="date"
																			value="${report.saleDate }" /></td>
																	<td><fmt:formatNumber type="currency"
																			value="${report.salePrice }" /></td>
																</c:when>
																<c:otherwise>
																	<td><c:out value="${ report.clientEmail }" /></td>
																	<td>Track</td>
																	<td><c:out value="${ report.trackArtist }" /></td>
																	<td><c:out value="${ report.trackTitle }" /></td>
																	<td><fmt:formatDate type="date"
																			value="${report.saleDate }" /></td>
																	<td><fmt:formatNumber type="currency"
																			value="${report.salePrice }" /></td>
																</c:otherwise>
															</c:choose>
														</tr>
													</c:forEach>
												</table>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:when test="${ reportType == 'sales_track' }">
						<!-- report for sales track -->
							<h4>Sales by Track Report - ${detailed ? 'Detailed' :
								'Summary' }</h4>
							<div class="list_report">
								<c:choose>
									<c:when
										test="${ totalSummary == 0 && totalDownloads == 0 && empty reportList }">
										<h3>No results found for '${searchValue}'</h3>
									</c:when>
									<c:otherwise>
										<c:if test="${!empty searchValue }">
											<p>
												<b>Results for</b> '${searchValue }'
											</p>
											<br>
										</c:if>
										<c:choose>
											<c:when test="${ !detailed }">
												<p>
													<b>Total Sales:</b>
													<fmt:formatNumber type="currency" value="${totalSummary }" />
												</p>
												<br>
												<p>
													<b>Total Downloads:</b>
													<fmt:formatNumber type="number" value="${totalDownloads }" />
												</p>
											</c:when>
											<c:otherwise>
												<table cellspacing="0">
													<tr>
														<th>Client</th>
														<th>Type</th>
														<th>Artist</th>
														<th>Title</th>
														<th>Date</th>
														<th>Sale Price</th>
													</tr>
													<c:forEach var="report" items="${reportList}"
														varStatus="loopCounter">
														<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
															<td><c:out value="${ report.clientEmail }" /></td>
															<td>Track</td>
															<td><c:out value="${ report.artist }" /></td>
															<td><c:out value="${ report.itemTitle }" /></td>
															<td><fmt:formatDate type="date"
																	value="${report.invoiceSaleDate }" /></td>
															<td><fmt:formatNumber type="currency"
																	value="${report.salePrice }" /></td>
														</tr>
													</c:forEach>
												</table>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:when test="${ reportType == 'sales_album' }">
						<!-- report for sales album -->
							<h4>Sales by Album Report - ${detailed ? 'Detailed' :
								'Summary' }</h4>
							<div class="list_report">
								<c:choose>
									<c:when
										test="${ totalSummary == 0 && totalDownloads == 0 && empty reportList }">
										<h3>No results found for '${searchValue}'</h3>
									</c:when>
									<c:otherwise>
										<c:if test="${!empty searchValue }">
											<p>
												<b>Results for</b> '${searchValue }'
											</p>
											<br>
										</c:if>
										<c:choose>
											<c:when test="${ !detailed }">
												<p>
													<b>Total Sales:</b>
													<fmt:formatNumber type="currency" value="${totalSummary }" />
												</p>
												<br>
												<p>
													<b>Total Downloads:</b>
													<fmt:formatNumber type="number" value="${totalDownloads }" />
												</p>
											</c:when>
											<c:otherwise>
												<table cellspacing="0">
													<tr>
														<th>Client</th>
														<th>Type</th>
														<th>Artist</th>
														<th>Title</th>
														<th>Date</th>
														<th>Sale Price</th>
													</tr>
													<c:forEach var="report" items="${reportList}"
														varStatus="loopCounter">
														<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
															<td><c:out value="${ report.clientEmail }" /></td>
															<td>Album</td>
															<td><c:out value="${ report.artist }" /></td>
															<td><c:out value="${ report.itemTitle }" /></td>
															<td><fmt:formatDate type="date"
																	value="${report.invoiceSaleDate }" /></td>
															<td><fmt:formatNumber type="currency"
																	value="${report.salePrice }" /></td>
														</tr>
													</c:forEach>
												</table>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:when test="${ reportType == 'top_sellers' }">
						<!-- report for top sellers -->
							<h4>Top Sellers Report</h4>
							<div class="list_report">
								<c:choose>
									<c:when test="${ empty reportList }">
										<h3>No results found for '${searchValue}'</h3>
									</c:when>
									<c:otherwise>
										<c:if test="${!empty searchValue }">
											<p>
												<b>Results for</b> '${searchValue }'
											</p>
											<br>
										</c:if>
										<table cellspacing="0">
											<tr>
												<th>Type</th>
												<th>Artist</th>
												<th>Title</th>
												<th>Total Sales</th>
											</tr>
											<c:forEach var="report" items="${reportList}"
												varStatus="loopCounter">
												<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
													<c:choose>
														<c:when test="${!empty report.albumTitle }">
															<td>Album</td>
															<td><c:out value="${ report.albumArtist }" /></td>
															<td><c:out value="${ report.albumTitle }" /></td>
															<td><fmt:formatNumber type="currency"
																	value="${report.totalSales }" /></td>
														</c:when>
														<c:otherwise>
															<td>Track</td>
															<td><c:out value="${ report.trackArtist }" /></td>
															<td><c:out value="${ report.trackTitle }" /></td>
															<td><fmt:formatNumber type="currency"
																	value="${report.totalSales }" /></td>
														</c:otherwise>
													</c:choose>
												</tr>
											</c:forEach>
										</table>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:when test="${ reportType == 'top_clients' }">
						<!-- report for top clients -->
							<h4>Top Clients Report</h4>
							<div class="list_report">
								<c:choose>
									<c:when test="${ empty reportList }">
										<h3>No results found for '${searchValue}'</h3>
									</c:when>
									<c:otherwise>
										<c:if test="${!empty searchValue }">
											<p>
												<b>Results for</b> '${searchValue }'
											</p>
											<br>
										</c:if>
										<table cellspacing="0">
											<tr>
												<th>Client Id</th>
												<th>First Name</th>
												<th>Last Name</th>
												<th>Email</th>
												<th>Total Purchases</th>
											</tr>
											<c:forEach var="report" items="${reportList}"
												varStatus="loopCounter">
												<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
													<td><fmt:formatNumber type="number"
															value="${ report.clientId }" /></td>
													<td><c:out value="${ report.firstName }" /></td>
													<td><c:out value="${ report.lastName }" /></td>
													<td><c:out value="${ report.email }" /></td>
													<td><fmt:formatNumber type="currency"
															value="${report.totalSales }" /></td>
												</tr>
											</c:forEach>
										</table>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:when test="${ reportType == 'zero_tracks' }">
						<!-- report for zero tracks -->
							<h4>Zero Tracks Report</h4>
							<div class="list_report">
								<table cellspacing="0">
									<tr>
										<th>Track Id</th>
										<th>Artist</th>
										<th>Title</th>
										<th>Genre</th>
									</tr>
									<c:forEach var="report" items="${reportList}"
										varStatus="loopCounter">
										<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
											<td><fmt:formatNumber type="number"
													value="${ report.inventoryId }" /></td>
											<td><c:out value="${ report.artist }" /></td>
											<td><c:out value="${ report.trackTitle }" /></td>
											<td><c:out value="${ report.musicCategory }" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:when>
						<c:when test="${ reportType == 'zero_clients' }">
						<!-- report for zero clients -->
							<h4>Zero Clients Report</h4>
							<div class="list_report">
								<table cellspacing="0">
									<tr>
										<th>Client Id</th>
										<th>First Name</th>
										<th>Last Name</th>
										<th>Email</th>
										<th>Total Purchases</th>
									</tr>
									<c:forEach var="report" items="${reportList}"
										varStatus="loopCounter">
										<tr class="${loopCounter.count%2==0?'tr2':'tr1'}">
											<td><fmt:formatNumber type="number"
													value="${ report.clientId }" /></td>
											<td><c:out value="${ report.firstName }" /></td>
											<td><c:out value="${ report.lastName }" /></td>
											<td><c:out value="${ report.email }" /></td>
											<td><fmt:formatNumber type="currency"
													value="${report.totalSales }" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:when>
					</c:choose>
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