<%@include file="./includes/header.jsp"%>
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
<!-- editClient.jsp -->

<!-- CONTENT -->

<div id="contentManag">
	<c:choose>
		<c:when test="${client.status }">
		<!-- test if it is an Admin -->
			<div class="mblock">
				<h2>Client Management</h2>
				<div class="inBlock">
					<!-- form to search a client -->
					<div id="searchDiv">
						<form method="POST" action="getClient#content" id="getClientForm"
							class="formClass">
							<label>Enter a client's email</label><input class="textFields"
								type="text" name="clientEmail" id="clientEmail" size="30"
								maxlength="200" value="${email }" /> <br>
							<div class="buttonGroup">
								<input type="submit" value="Search client" /> <span> <c:out
										value="${invalidEmail }" />
								</span>
							</div>
						</form>
					</div>
					<!-- form to edit client information -->
					<h3 id="infoHeader">
						<c:out value="${ clientMessage }" />
					</h3>
					<input type="hidden" id="hiddenField" value="${clientMessage }" />
					<div id="editProfileDiv">
						<form method="POST" action="updateClient" id="form"
							class="formClass">
							<h4>Personal Information</h4>
							<label>Title <span class="mandatoryFields">*</span></label> <select
								name="title" id="title">
								<option value="Mr."
									<c:if test="${existingClient.title == 'Mr.' }">selected</c:if>>Mr.
								</option>
								<option value="Mrs."
									<c:if test="${existingClient.title == 'Mrs.' }">selected</c:if>>Mrs.</option>
							</select> <label>First name <span class="mandatoryFields">*</span></label>
							<input type="text" name="firstName" size="30" class="textFields"
								maxlength="100" id="firstName"
								value="${existingClient.firstName }" /> <label>Last
								name <span class="mandatoryFields">*</span>
							</label> <input type="text" name="lastName" size="30" maxlength="100"
								class="textFields" id="lastName"
								value="${existingClient.lastName }" />

							<h4>Contact Information</h4>
							<label>Company name</label> <input type="text" name="companyName"
								size="30" class="textFields" maxlength="100" id="companyName"
								value="${existingClient.companyName }" /><label>Address
								1 <span class="mandatoryFields">*</span>
							</label> <input type="text" name="address1" size="30" maxlength="300"
								class="textFields" id="address1"
								value="${existingClient.address1 }" /> <label>Address 2</label>
							<input type="text" name="address2" size="30" maxlength="300"
								class="textFields" id="address2"
								value="${existingClient.address2 }" /> <label>City <span
								class="mandatoryFields">*</span></label> <input type="text" name="city"
								size="30" maxlength="100" class="textFields" id="city"
								value="${existingClient.city }" /><label>Province <span
								class="mandatoryFields">*</span>
							</label> <select name="province" id="province">
								<option value="AB"
									<c:if test="${existingClient.province == 'AB' }">selected</c:if>>AB
								</option>
								<option value="BC"
									<c:if test="${existingClient.province == 'BC' }">selected</c:if>>BC</option>
								<option value="MB"
									<c:if test="${existingClient.province == 'MB' }">selected</c:if>>MB</option>
								<option value="NB"
									<c:if test="${existingClient.province == 'NB' }">selected</c:if>>NB</option>
								<option value="NL"
									<c:if test="${existingClient.province == 'NL' }">selected</c:if>>NL</option>
								<option value="NT"
									<c:if test="${existingClient.province == 'NT' }">selected</c:if>>NT</option>
								<option value="NS"
									<c:if test="${existingClient.province == 'NS' }">selected</c:if>>NS</option>
								<option value="NU"
									<c:if test="${existingClient.province == 'NU' }">selected</c:if>>NU</option>
								<option value="ON"
									<c:if test="${existingClient.province == 'ON' }">selected</c:if>>ON</option>
								<option value="PE"
									<c:if test="${existingClient.province == 'PE' }">selected</c:if>>PE</option>
								<option value="QC"
									<c:if test="${existingClient.province == 'QC' }">selected</c:if>>QC</option>
								<option value="SK"
									<c:if test="${existingClient.province == 'SK' }">selected</c:if>>SK</option>
								<option value="YT"
									<c:if test="${existingClient.province == 'YT' }">selected</c:if>>YT</option>
							</select><label>Country</label> <input type="text" name="country"
								value="Canada" readonly="readonly" size="30" class="textFields" /><label>Postal
								code <span class="mandatoryFields">*</span>
							</label> <input type="text" name="postalCode" size="30" maxlength="6"
								placeholder="Ex. H1H1H1" class="textFields" id="postalCode"
								value="${existingClient.postalCode }" /> <label>Home
								phone <span class="mandatoryFields">*</span>
							</label> <input type="text" name="homePhone" size="30" maxlength="13"
								placeHolder="Ex. (514)123-4567" class="textFields"
								id="homePhone" value="${existingClient.homePhone }" /> <label>Cell
								phone</label> <input type="text" size="30" name="cellPhone"
								maxlength="13" class="textFields" id="cellPhone"
								value="${existingClient.cellPhone }" /> <span
								id="cellPhoneError"></span>

							<h4>Login Information</h4>

							<label>Email <span class="mandatoryFields">*</span></label> <input
								type="text" name="email" class="textFields" id="email" size="30"
								maxlength="200" value="${existingClient.email }"
								readonly="readonly" /><label>Password <span
								class="mandatoryFields">*</span>
							</label> <input type="text" name="password1" class="textFields"
								id="password1" size="30" maxlength="50"
								value="${existingClient.password }" /> <label>Admin
								status<span class="mandatoryFields">*</span>
							</label> <select name="status" id="status">
								<option value="true"
									<c:if test="${!existingClient.status }">selected</c:if>>True
								</option>
								<option value="false"
									<c:if test="${!existingClient.status }">selected</c:if>>False
								</option>

							</select> <label>Last search</label> <input type="text" id="lastSearch"
								name="lastSearch" class="textFields" size="30"
								readonly="readonly" value="${existingClient.genreLastSearch }" />
							<p id="infoParagraph">* Mandatory fields</p>
							<span id="servletError"> <c:out value="${servletError }" /></span>
							<div class="buttonGroup">
								<input id="clearButton" type="reset" value="Clear" /> <input
									id="submitButton" type="submit" value="Edit profile" />
							</div>
						</form>
					</div>
					<div class="end">&nbsp;</div>
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