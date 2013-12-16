<%@include file="includes/header.jsp"%>

<script type="text/javascript">
	var style = '<link rel="stylesheet" type="text/css" href="finalStyle.css"/>';
	var validateCreditCardScript = '<script type="text/javascript" src="validateCreditCardScript.js"/>';
	$(document).ready(function() {
		$("head").append(style);
		$("head").append(validateCreditCardScript);
	});
</script>

<!-- Venelin Koulaxazov -->
<!-- 1032744 -->
<!-- finalization.jsp -->

<div class="block">
	<h2>Finalization</h2>
	<div class="inBlock">
		<h3>Credit Card Information</h3>
		<c:if test="${!empty errorMessage }">
			<h4>
				<c:out value="${errorMessage}" />
			</h4>
		</c:if>
		<!-- form for validate the transaction -->
		<form method="POST" action="finalization#content" class="formClass"
			id="form">
			<div id="finalizationDiv">
				<label>Card holder: <input type="text" name="creditCardName"
					size="30" class="requiredTextFields" maxLength="100"
					id="creditCardName" value="${creditCard.cardHolder }" /></label>
				<p class="tip">Enter name is as it appears on the credit card</p>
				<span class="error"></span>
				<!-- credit card number -->
				<div id="cardNum">
					<label>Card number: <input type="text"
						name="creditCardNumber" size="30" class="requiredTextFields"
						maxLength="16" id="creditCardNumber"
						value="${creditCard.creditCardNum }" /></label>
				</div>
				<!-- type of card-->
				<div id="cardDiv">
					<img id="visa" src="${path}/images/visa.jpg" /> <img
						id="masterCard" src="${path}/images/masterCard.jpg" />
				</div>
				<div id="bottomOfForm">
					<p class="tip">Ex. 4111111111111111</p>
					<span class="error"></span> <label>Expiration date:</label><select
						name="month" id="month">
						<option value="MM">MM</option>
						<option value="01"
							<c:if test="${creditCard.expirationMonth == 1}">selected</c:if>>01</option>
						<option value="02"
							<c:if test="${creditCard.expirationMonth == 2}">selected</c:if>>02</option>
						<option value="03"
							<c:if test="${creditCard.expirationMonth == 3}">selected</c:if>>03</option>
						<option value="04"
							<c:if test="${creditCard.expirationMonth == 4}">selected</c:if>>04</option>
						<option value="05"
							<c:if test="${creditCard.expirationMonth == 5}">selected</c:if>>05</option>
						<option value="06"
							<c:if test="${creditCard.expirationMonth == 6}">selected</c:if>>06</option>
						<option value="07"
							<c:if test="${creditCard.expirationMonth == 7}">selected</c:if>>07</option>
						<option value="08"
							<c:if test="${creditCard.expirationMonth == 8}">selected</c:if>>08</option>
						<option value="09"
							<c:if test="${creditCard.expirationMonth == 9}">selected</c:if>>09</option>
						<option value="10"
							<c:if test="${creditCard.expirationMonth == 10}">selected</c:if>>10</option>
						<option value="11"
							<c:if test="${creditCard.expirationMonth == 11}">selected</c:if>>11</option>
						<option value="12"
							<c:if test="${creditCard.expirationMonth == 12}">selected</c:if>>12</option>
					</select> &nbsp;<select name="year" id="year">
						<option value="YY">YY</option>
						<option value="13"
							<c:if test="${creditCard.expirationMonth == 13}">selected</c:if>>13</option>
						<option value="14"
							<c:if test="${creditCard.expirationMonth == 14}">selected</c:if>>14</option>
						<option value="15"
							<c:if test="${creditCard.expirationMonth == 15}">selected</c:if>>15</option>
						<option value="16"
							<c:if test="${creditCard.expirationMonth == 16}">selected</c:if>>16</option>
						<option value="17"
							<c:if test="${creditCard.expirationMonth == 17}">selected</c:if>>17</option>
						<option value="18"
							<c:if test="${creditCard.expirationMonth == 18}">selected</c:if>>18</option>
						<option value="19"
							<c:if test="${creditCard.expirationMonth == 19}">selected</c:if>>19</option>
						<option value="20"
							<c:if test="${creditCard.expirationMonth == 20}">selected</c:if>>20</option>
						<option value="21"
							<c:if test="${creditCard.expirationMonth == 21}">selected</c:if>>21</option>
						<option value="22"
							<c:if test="${creditCard.expirationMonth == 22}">selected</c:if>>22</option>
						<option value="23"
							<c:if test="${creditCard.expirationMonth == 23}">selected</c:if>>23</option>
					</select><span class="error"></span> <br /> <label>Card security
						code: <input type="text" name="cardSecurityCode" size="7"
						class="requiredTextFields" maxLength="3" id="cardSecurityCode"
						value="${creditCard.cardSecurityCode }" />
					</label> <span class="error"></span>

					<div class="buttonGroup">
						<input type="submit" value="Submit" /><a
							href="<c:url value='index'></c:url>"><input type="button"
							value="Cancel" /></a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>