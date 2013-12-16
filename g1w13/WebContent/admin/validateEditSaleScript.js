// Anthony-Virgil Bermejo
// 0831360
// validateEditSaleScript.js

function init() {

	salePriceInput = document.getElementById("salePrice");

	form = document.forms["form"];
	addEvent(form, "submit", validateForm, false);
	addEvent(salePriceInput, "blur", validatePrices, false);
}

/*
 * Check if the mandatory fields are filled and returns a boolean value
 */
function validateEmptyFields() {
	var valid = true;

	if (!salePriceInput.value) {
		salePriceInput.style.backgroundColor = "#f33";
		valid = false;
	}

	return valid;
}

/*
 * Checks if the price fields are in the correct format
 */
function validatePrices() {
	var valid;
	var currencyExp = /^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\.[0-9]{2}$/;

	if (salePriceInput.value) {
		if (!currencyExp.test(salePriceInput.value)) {
			salePriceInput.style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	return valid;
}

/*
 * Calls all the functions associated with the validation of the form, and then
 * submits it whether it is validated or not.
 */
function validateForm(e) {
	var evt = e || window.event;
	if (!validateEmptyFields())
		evt.preventDefault();
	if (!validatePrices())
		evt.preventDefault();

	removeEvent(form, "submit", validateForm, false);
	addEvent(form, "submit", validateForm, false);
	evt.submit();
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

window.onload = init;