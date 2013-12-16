// Anthony-Virgil Bermejo
// 0831360
// validateTrackFormScript.js

function init() {
	// array to hold all form objects
	trackFormArray = new Array();
	trackFormArray[0] = document.getElementById("trackTitle");
	trackFormArray[1] = document.getElementById("albumTitle");
	trackFormArray[2] = document.getElementById("artist");
	trackFormArray[3] = document.getElementById("songWriter");
	trackFormArray[4] = document.getElementById("songLength");
	trackFormArray[5] = document.getElementById("genre");
	trackFormArray[6] = document.getElementById("costPrice");
	trackFormArray[7] = document.getElementById("listPrice");
	trackFormArray[8] = document.getElementById("salePrice");
	trackFormArray[9] = document.getElementById("typeOfSale");
	trackFormArray[10] = document.getElementById("removalStatus");

	form = document.forms["form"];
	addEvent(form, "submit", validateForm, false);
	addEvent(form, "reset", removeErrors, false);
	addEvent(trackFormArray[4], "blur", validateSongLength, false);
	addEvent(trackFormArray[6], "blur", validatePrices, false);
	addEvent(trackFormArray[7], "blur", validatePrices, false);
	addEvent(trackFormArray[8], "blur", validatePrices, false);

}

/*
 * Check if the mandatory fields are filled and returns a boolean value
 */
function validateEmptyFields() {
	var valid = true;
	for ( var ctr in trackFormArray)
		if (!trackFormArray[ctr].value) {
			trackFormArray[ctr].style.backgroundColor = "#f33";
			valid = false;
		}

	return valid;
}

/*
 * Checks if the song length is in the correct format
 */
function validateSongLength() {
	var valid;
	var songLengthExp = /^([0-5][0-9]|0[0-9]|[0-9]):[0-5][0-9]$/;

	if (trackFormArray[4].value) {
		if (!songLengthExp.test(trackFormArray[4].value)) {
			trackFormArray[4].style.backgroundColor = "#f33";
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

	if (trackFormArray[6].value) {
		if (!currencyExp.test(trackFormArray[6].value)) {
			trackFormArray[6].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	if (trackFormArray[7].value) {
		if (!currencyExp.test(trackFormArray[7].value)) {
			trackFormArray[7].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	} else
		valid = false;

	if (trackFormArray[8].value) {
		if (!currencyExp.test(trackFormArray[8].value)) {
			trackFormArray[8].style.backgroundColor = "#f33";
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
	if (!validateSongLength())
		evt.preventDefault();
	if (!validatePrices())
		evt.preventDefault();

	removeEvent(form, "submit", validateForm, false);
	addEvent(form, "submit", validateForm, false);
	evt.submit();
}

// removes the errors from the fields
function removeErrors() {
	for ( var ctr in trackFormArray) {
		trackFormArray[ctr].style.backgroundColor = "white";
		trackFormArray[ctr].value = "";
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