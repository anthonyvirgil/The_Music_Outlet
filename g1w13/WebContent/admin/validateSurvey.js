/*
 * author: Natacha Gabbamonte 0932340 validateSurvey.js
 */

//GLOBAL
var mandatoryFields;
var numOfAnswers;
var defaultAnswersStr;
var newSurveyAnswersElement;
var MAX_ANSWERS = 6;
var MIN_ANSWERS = 2;

var lessButton;
var moreButton;

// Initialization
function init() {
	// Sets the default number of answers
	numOfAnswers = 4;

	// Find the span for the new survey answers element
	newSurveyAnswersElement = document.getElementById("newSurveyAnswers");
	lessButton = document.getElementById("lessButton");
	moreButton = document.getElementById("moreButton");

	setMandatoryFields();
}

/**
 * Sets the mandatory fields.
 */
function setMandatoryFields() {
	mandatoryFields = [];
	// Gets possible answers
	mandatoryFields = getByClass("quizOpt");

	// Survey name
	mandatoryFields.push(document.getElementById("surveyName"));
	// Survey question
	mandatoryFields.push(document.getElementById("question"));
}

/**
 * Gets the html for the answers with the current number of answers wanted to be
 * displayed.
 */
function getAnswersHtml() {
	var str = "";
	var previousValues = [];
	var pvCtr = 0;
	var currentAnswers = getByClass("quizOpt");

	for (var ctr in currentAnswers)
		if (currentAnswers[ctr].value){
			previousValues[pvCtr]=currentAnswers[ctr].value;
			pvCtr++;
		}

	for ( var i = 1; i <= numOfAnswers; i++)
		if (i <= previousValues.length)
			str += '&nbsp;&nbsp;&nbsp;&nbsp;Answer ' + i
					+ ': <input type="text" class="quizOpt" name="opt' + i
					+ '" id="opt' + i + '" value="' + previousValues[i - 1]
					+ '"><br />';
		else
			str += '&nbsp;&nbsp;&nbsp;&nbsp;Answer ' + i
					+ ': <input type="text" class="quizOpt" name="opt' + i
					+ '" id="opt' + i + '"><br />';

	return str;
}

/**
 * Changes an element so it is no longer displayed.
 * 
 * @param element
 *            the element
 */
function disableElement(element) {
	element.style.display = "none";
}

/**
 * Changes an element so it is displayed.
 * 
 * @param element
 *            the element
 */
function enableElement(element) {
	element.style.display = "inline";
}

/**
 * Adds more answers if it can.
 */
function moreAnswers() {
	numOfAnswers++;
	if (numOfAnswers >= MAX_ANSWERS) {
		numOfAnswers = MAX_ANSWERS;
		disableElement(moreButton);
	} else
		enableElement(moreButton);

	enableElement(lessButton);
	newSurveyAnswersElement.innerHTML = getAnswersHtml();
	setMandatoryFields();
	setJQuery();
}
/**
 * Removes answers if it can.
 */
function lessAnswers() {
	numOfAnswers--;
	if (numOfAnswers <= MIN_ANSWERS) {
		numOfAnswers = MIN_ANSWERS;
		disableElement(lessButton);
	} else
		enableElement(lessButton);

	enableElement(moreButton);
	newSurveyAnswersElement.innerHTML = getAnswersHtml();
	setMandatoryFields();
	setJQuery();
}

function setJQuery() {
	$("input[type=\"text\"]").focus(function() {
		$(this).css("background-color", "#eeeeee");
	});
	$("input[type=\"text\"]").blur(function() {
		$(this).css("background-color", "#ffffff");
	});
}

/*
 * Checks to see that whether the form is submitting a NEW survey. If it is, it
 * checks each field for empty strings.
 */
function validateForm(element) {
	var form = element.form;
	var survey = form.elements['survey'];
	var typeOfSurvey = "";
	if (survey.value) {
		typeOfSurvey = survey.value;
	} else {
		for ( var ctr in survey)
			if (survey[ctr].checked) {
				typeOfSurvey = survey[ctr].value;
			}
	}
	var valid = true;
	if (typeOfSurvey == "")
		valid = false;
	else if (typeOfSurvey == "new")
		for ( var ctr in mandatoryFields)
			if (!mandatoryFields[ctr].value) {
				mandatoryFields[ctr].style.backgroundColor = "#f33";
				valid = false;
			}
	return valid;
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
window.onload = init;