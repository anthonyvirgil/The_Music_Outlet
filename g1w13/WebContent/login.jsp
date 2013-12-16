<%@include file="includes/header.jsp"%>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- login.jsp -->

<div class="block">
	<h2>Login</h2>
	<div class="inBlock" id="loginDiv">
		Login to continue. Not a member? <a href="register#content">Register</a>.
		<div class="buttonGroup">
			<!-- form to login -->
			<form action="userLogin" method="post">
				<input type="text" placeholder="Email" name="username2" size="35" /><br />
				<input type="password" placeholder="Password" name="password2"
					size="35" /> </br> <input type="submit" value="Login" />
			</form>
			<form action="<c:url value='register#content'></c:url>" method="get">
				<input type="submit" value="Register" />
			</form>
		</div>
		<c:if test="${!empty errorMessage }">
			<h4>
				<c:out value="${errorMessage}" />
			</h4>
		</c:if>
	</div>
</div>

<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>