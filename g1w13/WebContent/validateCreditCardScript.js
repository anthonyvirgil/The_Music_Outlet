// Venenlin Koulaxazov
// 1032744
// validateCreditCardScript.js

function init() {

	// cache objects

	// images

	visa = document.getElementById("visa");
	masterCard = document.getElementById("masterCard");

	// text fields

	mandatoryFieldsArray = new Array();
	mandatoryFieldsArray[0] = document.getElementById("creditCardName");
	mandatoryFieldsArray[1] = document.getElementById("creditCardNumber");
	mandatoryFieldsArray[2] = document.getElementById("cardSecurityCode");

	monthDropDown = document.getElementById("month");
	yearDropDown = document.getElementById("year");

	form = document.getElementById("form");
	addEvent(form, "submit", validateForm, false);
	addEvent(mandatoryFieldsArray[1], "blur", validateCreditCard, false);
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

/*
 * Calls all the functions associated with the validation of the form, and then
 * submits it whether it is validated or not.
 */
function validateForm(e) {
	var evt = e || window.event;

	if (!validateEmptyFields())
		evt.preventDefault();
	if (!validateDropDowns())
		evt.preventDefault();
	if (!validateCreditCard())
		evt.preventDefault();
	if (!validateCsc())
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

/*
 * Check if the drop down list do not have the default values selected
 */
function validateDropDowns() {
	var valid = true;

	if (monthDropDown.value === "MM" || yearDropDown.value === "YY") {
		if (monthDropDown.value === "MM")
			monthDropDown.style.backgroundColor = "#f33";
		if (yearDropDown.value === "YY")
			yearDropDown.style.backgroundColor = "#f33";
		valid = false;
	}

	return valid;
}

/*
 * Validates if the credit card number in the text box is either a valid visa or
 * mastercard number
 */
function validateCreditCard(e) {
	var valid;
	var visaRegExp = /^4\d{15}$/;
	var masterCardRegExp = /^5[1-5]\d{14}$/;
	if (mandatoryFieldsArray[1].value) {
		if (visaRegExp.test(mandatoryFieldsArray[1].value)) {
			visa.style.opacity = "1";
			masterCard.style.opacity = "0.4";
		} else if (masterCardRegExp.test(mandatoryFieldsArray[1].value)) {
			masterCard.style.opacity = "1";
			visa.style.opacity = "0.4";
		} else {
			masterCard.style.opacity = "1";
			visa.style.opacity = "1";
			mandatoryFieldsArray[1].style.backgroundColor = "#f33";
			return false;
		}

		array = mandatoryFieldsArray[1].value.split("");

		for ( var i = 0; i < array.length; i += 2)
			array[i] = parseInt(array[i]) * 2;

		var totalSum = 0;
		for ( var i = 0; i < array.length; i++) {
			var digit = parseInt(array[i]);
			if (digit >= 10)
				totalSum += (digit - 9);
			else
				totalSum += digit;
		}

		if ((totalSum % 10) === 0)
			valid = true;
		else
			valid = false;
	} else
		valid = true;

	return valid;
}

// validates if the card security code is all digits
function validateCsc() {
	var valid;
	var cscRegExp = /^\d{3}$/;

	if (cscRegExp.test(mandatoryFieldsArray[2].value))
		valid = true;
	else {
		mandatoryFieldsArray[2].style.backgroundColor = "#f33";
		valid = false;
	}
	return valid;
}

window.onload = init;