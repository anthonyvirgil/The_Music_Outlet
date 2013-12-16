/*
 * author: Natacha Gabbamonte 0932340 validateQuizScript.js
 */
// GLOBAL
var mandatorySelectedRadio;

/*
 * Initialization function
 */
function quizInit() {
	mandatorySelectedRadio = getByClass("quizOpt");

	var quizForm = document.forms["quizForm"];

	quizForm.onsubmit = function() {
		var valid = false;
		for ( var i = 0; i < mandatorySelectedRadio.length; i++)
			if (mandatorySelectedRadio[i].checked) {
				valid = true;
				break;
			}
		return valid;
	};
}

/*
 * Gets all the elements of a particular class.
 */
function getByClass(className, parent) {
	parent || (parent = document);
	var descendants = parent.getElementsByTagName('*'), i = -1, e, result = [];
	while (e = descendants[++i]) {
		((' ' + (e['class'] || e.className) + ' ').indexOf(' ' + className
				+ ' ') > -1)
				&& result.push(e);
	}
	return result;
}

window.onload = quizInit;