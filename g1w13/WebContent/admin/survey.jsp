
<%@include file="./includes/header.jsp"%>

<script type="text/javascript">
	var validateFormScript = '<script type="text/javascript" src="./validateSurvey.js" />';
	$(document).ready(function() {
		$("head").append(validateFormScript);
	});
</script>

<!-- author: Natacha Gabbamonte 0932340 survey.jsp -->

<!-- CONTENT -->
<div id="contentManag">
	<div class="mblock" id="survey">
		<h2>Survey Manager</h2>
		<div class="inBlock">
			<h4>Current Survey</h4>
			<c:choose>
				<c:when test="${currentSurvey != null}">
				<!-- display the actual survey -->
					<p>
						Name:
						<c:out value="${currentSurvey.survey.surveyName}" />
						<br />
						<c:out value="${currentSurvey.survey.surveyQuestion}" />
						<br />
					</p>
					<ul>
						<c:forEach var="currentSurveyAnswer"
							items="${currentSurvey.answers}">
							<li><c:out value="${currentSurveyAnswer.answer}" />&nbsp;&nbsp;(<fmt:formatNumber
									type="percent" maxIntegerDigits="3" minFractionDigits="2"
									value='${currentSurveyAnswer.votes/currentSurvey.totalVotes == "NaN" ? 0.0 : currentSurveyAnswer.votes/currentSurvey.totalVotes }' />)
								&nbsp;&nbsp;&nbsp;&nbsp;Votes: <c:out
									value="${currentSurveyAnswer.votes}" /></li>
						</c:forEach>
						<p>
							Total Votes:
							<c:out value="${currentSurvey.totalVotes}" />
						</p>
					</ul>
				</c:when>
				<c:otherwise>
					<h3>No current survey!</h3>
					<br />
				</c:otherwise>
			</c:choose>
			<!-- display the other survey -->
			<h4>Change survey and/or add a new survey</h4>
			<form method="post" action="./survey" id="form">
				<c:choose>
					<c:when test="${currentSurvey != null }">
						<input type="hidden" name="currentSurveyId"
							value="${currentSurvey.survey.surveyId}" />
					</c:when>
					<c:otherwise>
						<input type="hidden" name="currentSurveyId" value="-1" />
					</c:otherwise>
				</c:choose>
				<c:if test="${otherSurveys != null}">
					<p>
						<c:forEach var="aSurvey" items="${otherSurveys}">
							<label><input type="radio" name="survey"
								value="${aSurvey.survey.surveyId}"> <c:out
									value="${aSurvey.survey.surveyName}" /></label>
							<a href="deleteSurvey?surveyId=${aSurvey.survey.surveyId}"> <img
								src="${path}/images/remove_icon.png" alt="delete" title="delete" /></a>
							<br />
							<c:out value="${aSurvey.survey.surveyQuestion}" />
							<br />
							<c:forEach var="aSurveyAnswer" items="${aSurvey.answers}">
					&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${aSurveyAnswer.answer }" />&nbsp;&nbsp;(<fmt:formatNumber
									type="percent" maxIntegerDigits="3" minFractionDigits="2"
									value='${aSurveyAnswer.votes/aSurvey.totalVotes == "NaN" ? 0.0 : aSurveyAnswer.votes/aSurvey.totalVotes }' />)
						<br />
								<br />
							</c:forEach>
						</c:forEach>
					</p>
				</c:if>
				<!-- form to add a survey -->
				<label><input type="radio" name="survey" value="new" /> Add
					a new survey</label> <br /> &nbsp;&nbsp;&nbsp;&nbsp;Survey name: <input
					type="text" name="surveyName" id="surveyName"><br />
				&nbsp;&nbsp;&nbsp;&nbsp;Question: <input type="text" name="question"
					id="question"><br /> <span id="newSurveyAnswers">
					&nbsp;&nbsp;&nbsp;&nbsp;Answer 1: <input type="text"
					class="quizOpt" name="opt1" id="opt1"><br />
					&nbsp;&nbsp;&nbsp;&nbsp;Answer 2: <input type="text"
					class="quizOpt" name="opt2" id="opt2"><br />
					&nbsp;&nbsp;&nbsp;&nbsp;Answer 3: <input type="text"
					class="quizOpt" name="opt3" id="opt3"><br />
					&nbsp;&nbsp;&nbsp;&nbsp;Answer 4: <input type="text"
					class="quizOpt" name="opt4" id="opt4"><br />
				</span> <input type="button" id="lessButton" onclick="lessAnswers()"
					value="Less Answers" /> <input type="button" id="moreButton"
					onclick="moreAnswers()" value="More Answers" /> <br />
				<br /> <input type="reset" value="Clear" /> <input type="submit"
					value="Submit" onclick="return validateForm(this)" />
			</form>
			<c:if test="${surveyErrorMessage != null}">
				<br />
				<span class="error"><c:out value="${surveyErrorMessage}" /></span>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>