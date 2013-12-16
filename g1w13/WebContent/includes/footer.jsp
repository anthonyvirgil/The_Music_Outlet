<!-- Natacha Gabbamonte -->
<!-- 0932340 -->
<!-- footer.jsp -->

<!-- FOOTER -->
<div id="footer">
	<div id="footerContainer">
		<div id="footerLogo">
			<a href='<c:url value="index"></c:url>'><img
				src="${path}/images/footerLogo.png"
				alt="The MUSIC Outlet footer logo" /></a>
		</div>
		<div id="footerContent">
			<!--list the menu -->
			<div id="categoriesBlock" class="block">
				<h2>Categories</h2>

				<br /> <a
					href='<c:url value="search">
							<c:param name="searchType" value="Genre"/>
							<c:param name="searchValue" value="Pop"/>
						</c:url>#content'>Pop</a>
				<br /> <a
					href='<c:url value="search">
							<c:param name="searchType" value="Genre"/>
							<c:param name="searchValue" value="Rock"/>
						</c:url>#content'>Rock</a>
				<br /> <a
					href='<c:url value="search">
							<c:param name="searchType" value="Genre"/>
							<c:param name="searchValue" value="Metal"/>
						</c:url>#content'>Metal</a>
				<br /> <a
					href='<c:url value="search">
							<c:param name="searchType" value="Genre"/>
							<c:param name="searchValue" value="Electronic"/>
						</c:url>#content'>Electronic</a>
				<br /> <a
					href='<c:url value="search">
							<c:param name="searchType" value="Genre"/>
							<c:param name="searchValue" value="Reggae"/>
						</c:url>#content'>Reggae</a>
				<br /> <a
					href='<c:url value="search">
							<c:param name="searchType" value="Genre"/>
							<c:param name="searchValue" value="Hip-Hop"/>
						</c:url>#content'>Hip-Hop</a>

			</div>
			<div class="block">
				<h2>User</h2>
				<!--info if the client is log -->
				<c:if test="${client == null}">
					<br />
					<a href='<c:url value="userLogin#content"></c:url>'>Login</a>
					<br />
					<a href='<c:url value="register#content"></c:url>'>Register</a>
				</c:if>
				<br /> <a href='<c:url value="cart#content"></c:url>'>Shopping
					Cart</a>
				<c:if test="${client != null}">
					<br />
					<a href='<c:url value="profile#content"></c:url>'>Profile</a>
					<br />
					<a href='<c:url value="purchasedTracks#content"></c:url>'>Downloads</a>
				</c:if>
				<br /> <a href='<c:url value="search#content"></c:url>'>Search</a>
			</div>
			<div class="block">
				<h2>Help</h2>
				<br /> <a href='<c:url value="help#content"></c:url>'>Main Help</a>
				<br /> <a href='<c:url value="termsOfUse#content"></c:url>'>Terms
					of Use</a> <br /> <a href='<c:url value="faq#content"></c:url>'>FAQ</a>
			</div>
		</div>
	</div>
	<!--copyright -->
	<div id="footerBottom">Copyright &copy; 2013 Dawson College. All
		Rights Reserved.</div>
</div>
</body>
</html>