// Anthony-Virgil Bermejo
// 0831360
// validateReportFormScript.js

function init() {
	// array to hold all form objects
	reportFormArray = new Array();

	reportFormArray[0] = document.getElementById("startDate");
	reportFormArray[1] = document.getElementById("endDate");
	reportFormArray[2] = document.getElementById("searchInput");
	reportFormArray[3] = document.getElementById("reportType");

	form = document.forms["form"];
	addEvent(form, "submit", validateForm, false);
	addEvent(form, "reset", removeErrors, false);
	addEvent(reportFormArray[0], "blur", validateDates, false);
	addEvent(reportFormArray[1], "blur", validateDates, false);
	addEvent(reportFormArray[3], "change", changeSearchInput, false);
}

/*
 * Dynamically change search box functionality according to selection of report
 * type
 */
function changeSearchInput() {
	var reportType = reportFormArray[3].value;
	reportFormArray[2].disabled = false;
	reportFormArray[2].value = "";

	switch (reportType) {
	case "none":
		reportFormArray[2].placeholder = "Search value";
		break;
	case "total_sales":
		reportFormArray[2].placeholder = "Album or Track name";
		break;
	case "sales_client":
		reportFormArray[2].placeholder = "Client Email";
		break;
	case "sales_artist":
		reportFormArray[2].placeholder = "Artist name";
		break;
	case "sales_track":
		reportFormArray[2].placeholder = "Track name";
		break;
	case "sales_album":
		reportFormArray[2].placeholder = "Album name";
		break;
	case "top_clients":
		reportFormArray[2].placeholder = "Client Email";
		break;
	case "top_sellers":
		reportFormArray[2].placeholder = "Album or Track name";
		break;
	case "zero_tracks":
		reportFormArray[2].placeholder = "Album or Track name";
		break;
	case "zero_clients":
		reportFormArray[2].placeholder = "Client Email";
		break;
	default:
		reportFormArray[2].placeholder = "Search value";
		break;
	}
}

/*
 * Checks if the price fields are in the correct format
 */
function validateDates() {
	var valid = true;
	var dateExp = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;

	if (reportFormArray[0].value) {
		if (!dateExp.test(reportFormArray[0].value)) {
			reportFormArray[0].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	}

	if (reportFormArray[1].value) {
		if (!dateExp.test(reportFormArray[1].value)) {
			reportFormArray[1].style.backgroundColor = "#f33";
			valid = false;
		} else
			valid = true;
	}

	return valid;
}

/*
 * Calls all the functions associated with the validation of the form, and then
 * submits it whether it is validated or not.
 */
function validateForm(e) {
	var evt = e || window.event;

	if (reportFormArray[1].value && !reportFormArray[0].value) {
		reportFormArray[0].style.backgroundColor = "#f33";
		evt.preventDefault();
	}

	if (!validateDates())
		evt.preventDefault();

	removeEvent(form, "submit", validateForm, false);
	addEvent(form, "submit", validateForm, false);
	evt.submit();
}

// removes the errors from the fields
function removeErrors() {

	reportFormArray[0].value = "";
	reportFormArray[0].style.backgroundColor = "white";
	reportFormArray[1].value = "";
	reportFormArray[1].style.backgroundColor = "white";
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