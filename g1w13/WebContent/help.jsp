<%@ include file="includes/header.jsp"%>

<!-- Anthony-Virgil Bermejo -->
<!-- 0831360 -->
<!-- help.jsp -->

<div class="block">
<h2>Help</h2>
	<div class="inBlock">
	<!-- help page -->
	<h3>Topics</h3>
	<ul>
		<li><a href="#registration">Registration Page</a></li>
		<li><a href="#profile">Profile Page</a></li>
		<li><a href="#searchResults">Search Results Page</a></li>
		<li><a href="#trackAlbum">Track and Album Page</a></li>
		<li><a href="#reviewEntry">Review Entry Page</a></li>
		<li><a href="#shoppingCart">Shopping Cart Page</a></li>
		<li><a href="#finalization">Finalization Page</a></li>
		<li><a href="#orderConfirmation">Order Confirmation Page</a></li>
		<li><a href="#invoice">Invoice Page</a></li>
		<li><a href="#download">Download Page</a></li>
	</ul>
	<br id='registration' /> <br />
	<h3>Registration Page</h3>
	<p>On the registration page, a new user must enter the following
		information to register</p>
	<ul class="bulletList">
		<li>View your profile information</li>
		<li>Edit your profile information</li>
	</ul>

	<br id='profile' /> <br />
	<h3>Profile Page</h3>
	<p>As a registered user, you are able to</p>
	<ul class="bulletList">
		<li>View your profile information</li>
		<li>Edit your profile information</li>
	</ul>

	<br id='searchResults' /> <br />
	<h3>Search Results Page</h3>
	<p>The Search Results page displays all items in the inventory that
		match the search criteria from the search bar.</p>
	<br>
	<p>A user will be able to search by</p>
	<ul class="bulletList">
		<li>Track name</li>
		<li>Album name</li>
		<li>Artist</li>
		<li>Genre</li>
	</ul>
	<br>
	<p>
		The default search type is <b>Track name</b>
	</p>

	<br id='trackAlbum' /> <br />
	<h3>Track and Album Page</h3>
	<p>Displays all information for a specific track or album that was
		chosen. From this page, a user is able to add this item to their cart
		and add/edit a review. Other tracks from the same album is also
		displayed.</p>

	<br id='reviewEntry' /> <br />
	<h3>Review Entry Page</h3>
	<p>A registered user can add a new review or edit a review that
		have already submitted. The user must also give a numeric rating for
		the item they are reviewing.</p>

	<br id='shoppingCart' /> <br />
	<h3>Shopping Cart Page</h3>
	<p>
		Displays all items currently in a user's shopping cart along with
		their prices. An item can be removed from the cart if they wish not to
		purchase it. Once done shopping, the user will go to the <b>Finalization
			Page</b> for payment.
	</p>

	<br id='finalization' /> <br />
	<h3>Finalization Page</h3>
	<p>
		Registered users must enter their credit card information to pay for
		their purchases. Once valid information has been entered, they will go
		to the <b>Order Confirmation</b> page to finalize their order.
	</p>
	<br>
	<p>
		Note: A user must be <b>logged in</b> to continue to this page.
	</p>

	<br id='orderConfirmation' /> <br />
	<h3>Order Confirmation Page</h3>
	<p>A list of all the items that will be purchased as well as their
		credit card information is displayed. Once the client confirms all
		information is correct, they will complete the transcation.</p>

	<br id='invoice' /> <br />
	<h3>Invoice Page</h3>
	<p>An e-mail is sent automatically to the client with the invoice
		of their purchase.</p>
	<br>
	<p>The clients are then able to</p>
	<ul class="bulletList">
		<li><b>Print</b> their invoice</li>
		<li><b>Download</b> the items they have just purchased</li>
		<li>Continue shopping</li>
	</ul>

	<br id='download' /> <br />
	<h3>Download Page</h3>
	<p>All of a registered user's purchases will be displayed on this
		page. You are able to download any music that you have already
		purchased.</p>


	<br />
</div></div>

<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/quizRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>