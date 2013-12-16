// Anthony-Virgil Bermejo
// 0831360
// validateAlbumFormScript.js

function init() {
	// array to hold all form objects
	albumFormArray = new Array();
	albumFormArray[0] = document.getElementById("albumTitle");
	albumFormArray[1] = document.getElementById("artist");
	albumFormArray[2] = document.getElementById("genre");
	albumFormArray[3] = document.getElementById("releaseDate");
	albumFormArray[4] = document.getElementById("recordLabel");
	albumFormArray[5] = document.getElementById("costPrice");
	albumFormArray[6] = document.getElementById("listPrice");
	albumFormArray[7] = document.getElementById("salePrice");
	albumFormArray[8] = document.getElementById("removalStatus");

	form = document.forms["form"];
	addEvent(form, "submit", validateForm, false);
	addEvent(form, "reset", removeErrors, false);
	addEvent(albumFormArray[3], "blur", validateDate, false);
	addEvent(albumFormArray[5], "blur", validatePrices, false);
	addEvent(albumFormArray[6], "blur", validatePrices, false);
	addEvent(albumFormArray[7], "blur", validatePrices, false);

}

/*
 * Check if the mandatory fields are filled and returns a boolean value
 */
function validateEmptyFields() {
	var valid = true;
	for ( var ctr in albumFormArray)
		if (!albumFormArray[ctr].value) {
			albumFormArray[ctr].style.backgroundColor = "#f33";
			valid = false;
		}

	return valid;
}

/*
 * Checks if the price fields are in the correct format
 */
function validateDate() {
	var valid;
	var dateExp = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;

	if (albumFormArray[3].value) {
		if (!dateExp.test(albumFormArray[3].value)) {
			albumFormArray[3].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	return valid;
}

/*
 * Checks if the price fields are in the correct format
 */
function validatePrices() {
	var valid;
	var currencyExp = /^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\.[0-9]{2}$/;

	if (albumFormArray[5].value) {
		if (!currencyExp.test(albumFormArray[5].value)) {
			albumFormArray[5].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	if (albumFormArray[6].value) {
		if (!currencyExp.test(albumFormArray[6].value)) {
			albumFormArray[6].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	if (albumFormArray[7].value) {
		if (!currencyExp.test(albumFormArray[7].value)) {
			albumFormArray[7].style.backgroundColor = "#f33";
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
	if (!validateDate())
		evt.preventDefault();

	removeEvent(form, "submit", validateForm, false);
	addEvent(form, "submit", validateForm, false);
	evt.submit();
}

// removes the errors from the fields
function removeErrors() {
	for ( var ctr in albumFormArray) {
		albumFormArray[ctr].style.backgroundColor = "white";
		albumFormArray[ctr].value = "";
	}
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