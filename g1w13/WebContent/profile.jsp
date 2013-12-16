<%@include file="includes/header.jsp"%>

<script type="text/javascript">
	var style = '<link rel="stylesheet" type="text/css" href="regStyle.css"/>';
	var validateFormScript = '<script type="text/javascript" src="validateEditFormScript.js"/>';
	$(document).ready(function() {
		$("head").append(style);
		$("head").append(validateFormScript);
	});
</script>

<!-- Venelin Koulaxazov -->
<!-- 1032744 -->
<!-- profile.jsp -->

<div class="block">
	<h2>Profile</h2>
	<div class="inBlock">
		<div id="editProfileDiv" class="buttonGroup">
			<!-- form for editing info -->
			<form method="POST" action="editProfile#content" id="form"
				class="formClass">
				<!-- display client info -->
				<h3>Personal Information</h3>
				<label>Title <span class="mandatoryFields">*</span></label> <select
					name="title" id="title">
					<option value="Mr."
						<c:if test="${client.title == 'Mr.' }">selected</c:if>>Mr.
					</option>
					<option value="Mrs."
						<c:if test="${client.title == 'Mrs.' }">selected</c:if>>Mrs.</option>
				</select> <label>First name <span class="mandatoryFields">*</span></label> <input
					type="text" name="firstName" size="30"
					class="registrationTextFields" maxlength="100" id="firstName"
					value="${client.lastName}" /><label>Last name <span
					class="mandatoryFields">*</span></label> <input type="text" name="lastName"
					size="30" maxlength="100" class="registrationTextFields"
					id="lastName" value="${client.firstName }" />

				<h3>Contact Information</h3>
				<label>Company name</label> <input type="text" name="companyName"
					size="30" class="registrationTextFields" maxlength="100"
					id="companyName" value="${client.companyName }" /><label>Address
					1 <span class="mandatoryFields">*</span>
				</label> <input type="text" name="address1" size="30" maxlength="300"
					class="registrationTextFields" id="address1"
					value="${client.address1 }" /> <label>Address 2</label> <input
					type="text" name="address2" size="30" maxlength="300"
					class="registrationTextFields" id="address2"
					value="${client.address2 }" /> <label>City <span
					class="mandatoryFields">*</span></label> <input type="text" name="city"
					size="30" maxlength="100" class="registrationTextFields" id="city"
					value="${client.city }" /><label>Province <span
					class="mandatoryFields">*</span></label> <select name="province"
					id="province">
					<option value="AB"
						<c:if test="${client.province == 'AB' }">selected</c:if>>AB
					</option>
					<option value="BC"
						<c:if test="${client.province == 'BC' }">selected</c:if>>BC</option>
					<option value="MB"
						<c:if test="${client.province == 'MB' }">selected</c:if>>MB</option>
					<option value="NB"
						<c:if test="${client.province == 'NB' }">selected</c:if>>NB</option>
					<option value="NL"
						<c:if test="${client.province == 'NL' }">selected</c:if>>NL</option>
					<option value="NT"
						<c:if test="${client.province == 'NT' }">selected</c:if>>NT</option>
					<option value="NS"
						<c:if test="${client.province == 'NS' }">selected</c:if>>NS</option>
					<option value="NU"
						<c:if test="${client.province == 'NU' }">selected</c:if>>NU</option>
					<option value="ON"
						<c:if test="${client.province == 'ON' }">selected</c:if>>ON</option>
					<option value="PE"
						<c:if test="${client.province == 'PE' }">selected</c:if>>PE</option>
					<option value="QC"
						<c:if test="${client.province == 'QC' }">selected</c:if>>QC</option>
					<option value="SK"
						<c:if test="${client.province == 'SK' }">selected</c:if>>SK</option>
					<option value="YT"
						<c:if test="${client.province == 'YT' }">selected</c:if>>YT</option>
				</select><label>Country</label> <input type="text" name="country"
					value="Canada" readonly="readonly" size="30"
					class="registrationTextFields" /><label>Postal code <span
					class="mandatoryFields">*</span></label> <input type="text"
					name="postalCode" size="25" maxlength="6" placeholder="Ex. H1H1H1"
					class="registrationTextFields" id="postalCode"
					value="${client.postalCode }" /> <label>Home phone <span
					class="mandatoryFields">*</span>
				</label> <input type="text" name="homePhone" size="25" maxlength="13"
					placeHolder="Ex. (514)123-4567" class="registrationTextFields"
					id="homePhone" value="${client.homePhone }" /> <label>Cell
					phone</label> <input type="text" size="30" name="cellPhone" maxlength="13"
					class="registrationTextFields" id="cellPhone"
					value="${client.cellPhone }" /> <span id="cellPhoneError"></span>

				<h3>Login Information</h3>

				<label>Email</label> <input type="text" name="email"
					class="registrationTextFields" id="email" size="30" maxlength="200"
					readonly="readonly" value="${client.email }" />
				<p id="infoParagraph">* Mandatory fields</p>
				<span id="servletError"> <c:out value="${servletError }" /></span>
				<div id="submitDiv">
					<input type="reset" id="clearButton" value="Clear" /><input
						id="submitButton" type="submit" value="Edit profile" />
				</div>
			</form>
		</div>
		<div class="end">&nbsp;</div>
	</div>
</div>
<%@include file="includes/contentAd.jsp"%>
<%@include file="includes/endOfContent.jsp"%>
<%@include file="includes/beginningRightNav.jsp"%>
<%@include file="includes/newsRightNav.jsp"%>
<%@include file="includes/adsRightNav.jsp"%>
<%@include file="includes/endRightNav.jsp"%>
<%@include file="includes/footer.jsp"%>