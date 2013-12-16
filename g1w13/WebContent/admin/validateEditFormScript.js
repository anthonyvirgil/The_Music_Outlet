// Venelin Koulaxazov
// 1032744
// validateEditFormScript.js
var form;
var mandatoryFieldsArray;
function init() {

	// cache objects

	form = document.getElementById("form");

	// getClientForm
	getClientForm = document.forms["getClientForm"];
	clientEmail = document.getElementById("clientEmail");
	addEvent(getClientForm, "submit", validateClientForm, false);
	hiddenField = document.getElementById("hiddenField");

	enableEditing();

	editProfileDiv = document.getElementById("editProfileDiv");

	mandatoryFieldsArray = new Array();
	mandatoryFieldsArray[0] = document.getElementById("firstName");
	mandatoryFieldsArray[1] = document.getElementById("lastName");
	mandatoryFieldsArray[2] = document.getElementById("address1");
	mandatoryFieldsArray[3] = document.getElementById("city");
	mandatoryFieldsArray[4] = document.getElementById("postalCode");
	mandatoryFieldsArray[5] = document.getElementById("homePhone");
	mandatoryFieldsArray[6] = document.getElementById("email");
	mandatoryFieldsArray[7] = document.getElementById("password1");
	cellPhoneTextBox = document.getElementById("cellPhone");

	addEvent(form, "submit", validateForm, false);
	addEvent(form, "reset", removeErrors, false);
	addEvent(mandatoryFieldsArray[4], "blur", validatePostalCode, false);
	addEvent(mandatoryFieldsArray[5], "blur", checkPhoneNumber, false);
	addEvent(cellPhoneTextBox, "blur", checkPhoneNumber, false);
}

/*
 * Enables the editProfileDiv so that the manager can edit the client's
 * information
 */

function enableEditing() {
	if (!hiddenField.value
			|| hiddenField.value === "There is no client with this email")
		editProfileDiv.style.visibility = "hidden";
	else
		editProfileDiv.style.visibility = "visible";
}
/*
 * Adds an event to the specific object and calls the function related to that
 * event
 */
function addEvent(obj, type, functionName, cap) {
	if (obj.attachEvent)
		obj.attachEvent("on" + type, functionName);
	else if (obj.addEventListener)
		obj.addEventListener(type, functionName, cap);
	else
		alert("Incompatible browser.");
}

/*
 * Removes the event associated with the object
 */
function removeEvent(obj, type, fName, cap) {
	if (obj.detachEvent)
		obj.detachEvent("on" + type, fName, cap);
	else if (obj.removeEventListener)
		obj.removeEventListener(type, fName, cap);
	else
		alert("Incompatible browser");
}

// removes the errors from the fields
function removeErrors() {
	for ( var ctr in mandatoryFieldsArray)
		mandatoryFieldsArray[ctr].style.backgroundColor = "white";

	cellPhoneTextBox.style.backgroundColor = "white";
}

/*
 * Calls all the functions associated with the validation of the form, and then
 * submits it whether it is validated or not.
 */
function validateClientForm(e) {
	clientEmail.style.backgroundColor = "white";

	var evt = e || window.event;

	if (!clientEmail.value) {
		clientEmail.style.backgroundColor = "#f33";
		evt.preventDefault();
	}
}
/*
 * Calls all the functions associated with the validation of the form, and then
 * submits it whether it is validated or not.
 */
function validateForm(e) {
	var evt = e || window.event;

	if (!validateEmptyFields())
		evt.preventDefault();
	if (!validatePostalCode())
		evt.preventDefault();
	if (!checkPhoneNumber())
		evt.preventDefault();
	if (!validateEmail())
		evt.preventDefault();

	removeEvent(form, "submit", validateForm, false);
	addEvent(form, "submit", validateForm, false);
	evt.submit();
}

/*
 * Check if the mandatory fields are filled and returns a boolean value
 */
function validateEmptyFields() {
	var valid = true;
	for ( var ctr in mandatoryFieldsArray)
		if (!mandatoryFieldsArray[ctr].value) {
			mandatoryFieldsArray[ctr].style.backgroundColor = "#f33";
			valid = false;
		}

	return valid;
}

// validates the postal code using a regular expression
function validatePostalCode() {
	var valid;
	var postalCodeExp = /^(\D\d\D)(\d\D\d)$/;

	if (mandatoryFieldsArray[4].value) {
		if (!postalCodeExp.test(mandatoryFieldsArray[4].value)) {
			mandatoryFieldsArray[4].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	return valid;
}

// checks whether the event is undefined and then calls the validatePhoneNumber
// method for the according field
function checkPhoneNumber(e) {
	if (!e) {
		if (validatePhoneNumber(mandatoryFieldsArray[5])
				&& validatePhoneNumber(cellPhoneTextBox))
			return true;
		else
			return false;
	}

	var evt = e || window.event;
	var evtSource = evt.srcElement || evt.target;
	var valid;

	if (validatePhoneNumber(evtSource))
		valid = true;
	else
		valid = false;

	return valid;
}

// validates a phone number using a regular expression
function validatePhoneNumber(field) {
	var valid;
	var phoneNumExp = /^(\(?\d{3}\)?)(\d{3})[ \-]?(\d{4})$/;
	var error;

	if (field.id === "homePhone")
		error = mandatoryFieldsArray[5];
	else
		error = cellPhoneTextBox;

	if (field.value) {
		if (!phoneNumExp.test(field.value)) {
			error.style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else {
		valid = true;
		error.innerHTML = "";
	}

	return valid;
}

// validates an email with a regular expression
function validateEmail() {
	var valid;
	var emailExp = /^[_\w\-]+(\.[_\w\-]+)*@[\w\-]+(\.[\w\-]+)*(\.[\D]{2,4})$/;

	if (mandatoryFieldsArray[6].value) {
		if (!emailExp.test(mandatoryFieldsArray[6].value)) {
			mandatoryFieldsArray[6].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	return valid;
}

window.onload = init;