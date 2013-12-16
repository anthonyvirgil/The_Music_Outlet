function init() {
	ad = document.getElementById("ad");
	hiddenField = document.getElementById("hiddenField");
	form = document.getElementById("selectAdTypeForm");
	bottom = document.getElementById("bottom");
	right = document.getElementById("right");

	if (!hiddenField.value)
		ad.style.visibility = "hidden";
	else
		ad.style.visibility = "visible";

	addEvent(form, "submit", validateSelection, false);
}

/*
 * Verifies if one of the radio buttons is selected
 */
function validateSelection(e) {
	var evt = e || window.event;

	if (!bottom.checked && !right.checked) {
		alert("You must select one option");
		evt.preventDefault();
	} else {
		if (bottom.checked)
			hiddenField.value = "bottom";
		else
			hiddenField.value = "right";
		enableDiv();
	}
}
/*
 * Enables the visibility of the adDiv
 */
function enableDiv() {
	ad.style.visibility = "visible";
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

window.onload = init;