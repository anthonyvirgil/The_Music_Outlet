<%@include file="./includes/header.jsp"%>

<!-- Natacha Gabbamonte -->
<!-- 0932340 -->
<!-- reviews.jsp -->

<c:set var="NUM_OF_RECORDS_PER_PAGE" value="3"></c:set>

<!-- CONTENT -->
<div id="contentManag">
	<c:choose>
		<c:when test="${client.status}">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Manage Reviews</h2>
				<div class="inBlock">
					<c:choose>
						<c:when test="${toBeApprovedReviews != null}">
							<!-- test if some review waiting for approval-->
							<h4>Waiting for approval (${numOfReviews})</h4>
							<c:forEach var="toBeApprovedReview"
								items="${toBeApprovedReviews}">
								<div class="review">
									<h4>Review track id:</h4>
									<span class="reviewTrackTitle">${toBeApprovedReview.inventoryId}</span>
									<br /> <br /> Date: ${toBeApprovedReview.reviewDate} <br />
									Rating:
									<c:out value="${toBeApprovedReview.rating}"></c:out>
									/ 5 <br /> Title:
									<c:out value="${toBeApprovedReview.reviewTitle}"></c:out>
									<br /> Comments:
									<div class="reviewComment">
										<c:out value="${toBeApprovedReview.reviewText}"></c:out>
									</div>
									Signature:
									<c:out value="${toBeApprovedReview.clientName}"></c:out>
									<div class="buttonGroup">
										<form action="changeApprovalReview#content" method="post">
											<input type="submit" value="Approve" /> <input type="hidden"
												name="reviewId"
												value="<c:out value="${toBeApprovedReview.reviewId}"></c:out>" />
											<input type="hidden" name="approval" value="true" />
										</form>
										<form action="deleteReview#content" method="post">
											<input type="submit" value="Delete" /> <input type="hidden"
												name="reviewId"
												value="<c:out value="${toBeApprovedReview.reviewId}"></c:out>" />
										</form>
									</div>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<h3>No reviews waiting to be approved</h3>
							<br />
						</c:otherwise>
					</c:choose>
					<!-- list the review already approval-->
					<h4>Browse reviews</h4>
					<a name="browse"></a>
					<div id="reviewSearch">
						<h3>Refine search:</h3>
						<form action="reviews#browse" method="get">
							<input type="text" placeholder="Search value" name="searchValue"
								value="${searchValue}" /> <input type="submit" name="search"
								value="Search" /> <input type="submit" name="search"
								value="Browse all" /> <br /> <label><input
								type="radio" name="searchType" value="trackId"
								${searchType.equals("trackId")?"checked":""} />&nbsp;Track ID</label>
							&nbsp;&nbsp;&nbsp;&nbsp; <label><input type="radio"
								name="searchType" value="clientName"
								${searchType.equals("clientName")?"checked":""} />&nbsp;Client
								name</label>
						</form>
						<br />
					</div>
					<!-- display the review-->
					<c:if test="${browseReviews != null && reviewsTotal > 0}">
						<c:forEach var="browseReview" items="${browseReviews}">
							<div class="review">
								<h4>Review track id:</h4>
								<span class="reviewTrackTitle">${browseReview.inventoryId}</span>
								<br /> <br /> Date: ${browseReview.reviewDate} <br /> Rating:
								<c:out value="${browseReview.rating}"></c:out>
								/ 5 <br /> Title:
								<c:out value="${browseReview.reviewTitle}"></c:out>
								<br /> Comments:
								<div class="reviewComment">
									<c:out value="${browseReview.reviewText}"></c:out>
								</div>
								Signature:
								<c:out value="${browseReview.clientName}"></c:out>
								<div class="buttonGroup">
									<form action="changeApprovalReview#content" method="post">
										<input type="submit" value="Disapprove" /> <input
											type="hidden" name="reviewId"
											value="<c:out value="${browseReview.reviewId}"></c:out>" />
										<input type="hidden" name="approval" value="false" />
									</form>
									<form action="deleteReview" method="post">
										<input type="submit" value="Delete" /> <input type="hidden"
											name="reviewId"
											value="<c:out value="${browseReview.reviewId}"></c:out>" />
									</form>
								</div>
							</div>
						</c:forEach>
						<!-- For navigate beetwen the page-->
						<div class="right">Viewing records
							${reviewsPage*NUM_OF_RECORDS_PER_PAGE-NUM_OF_RECORDS_PER_PAGE+1}
							&nbsp;-&nbsp;
							${(reviewsPage*NUM_OF_RECORDS_PER_PAGE)>reviewsTotal?
							reviewsTotal : reviewsPage*NUM_OF_RECORDS_PER_PAGE}
							&nbsp;of&nbsp; ${reviewsTotal} &nbsp;records</div>
						<c:if test="${reviewsPage > 1}">
							<a
								href="<c:url
						value="reviews?newQuery=false&search=${search}&searchType=${searchType}&searchValue=${searchValue}&reviewsPage=1#browse"></c:url>">&lt;&lt;</a>
				&nbsp;
					<a
								href="<c:url
						value="reviews?newQuery=false&search=${search}&searchType=${searchType}&searchValue=${searchValue}&reviewsPage=${reviewsPage-1}#browse"></c:url>">&lt;</a>
						</c:if>
				&nbsp;<b>${reviewsPage}</b>&nbsp;
				<c:if test="${reviewsPage < maxReviewsPage}">
							<a
								href="<c:url
						value="reviews?newQuery=false&search=${search}&searchType=${searchType}&searchValue=${searchValue}&reviewsPage=${reviewsPage+1}#browse"></c:url>">&gt;</a>
				&nbsp;
					<a
								href="<c:url
						value="reviews?newQuery=false&search=${search}&searchType=${searchType}&searchValue=${searchValue}&reviewsPage=${maxReviewsPage}#browse"></c:url>">&gt;&gt;</a>
						</c:if>
					</c:if>
					<!-- Error of search-->
					<c:choose>
						<c:when test="${searchReviewsError != null}">
							<span class="error">${searchReviewsError}</span>
						</c:when>
						<c:when test="${reviewsTotal < 1}">
							<h3>No results matching your parameters.</h3>
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
	</body>
	</html>