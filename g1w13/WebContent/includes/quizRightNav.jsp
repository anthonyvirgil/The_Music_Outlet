<script type="text/javascript">
	var quizScript = '<script type="text/javascript" src="validateQuizScript.js"/>';
	$(document).ready(function() {
		$("head").append(quizScript);
	});
</script>

<!-- Natacha Gabbamonte -->
<!-- 0932340 -->
<!-- quizRightNav.jsp -->


<a name="quizContent"></a>
<div id="quiz" class="block">
	<h2 class="navH2">Survey</h2>
	<div class="inBlock">
		<!-- test if a survey exist -->
		<c:choose>
			<c:when test="${currentSurvey == null}">
				<h3>
					Survey not loaded. Go to <a href="index">index page</a>.
				</h3>
			</c:when>
			<c:when test="${voted == null }">
			<!-- display the survey if you are not already vote -->
				<form method="post" action="vote#quizContent" id="quizForm">
					<p>
						<c:out value="${currentSurvey.survey.surveyQuestion}" />
						<br />
						<c:forEach var="surveyAnswer" items="${currentSurvey.answers}">
							<label> <input type="radio" name="quiz" class="quizOpt"
								id="quiz${surveyAnswer.surveyAnswerId}"
								value="${surveyAnswer.surveyAnswerId}"> &nbsp;<c:out
									value="${surveyAnswer.answer}" />
							</label>
							<br />
						</c:forEach>
					</p>
					<input type="submit" value="Vote" />
				</form>
			</c:when>
			<c:otherwise>
			<!-- display the result of the survey -->
				<p>
					<c:out value="${currentSurvey.survey.surveyQuestion}" />
					<br />
					<c:forEach var="surveyAnswer" items="${currentSurvey.answers}">
					${voted == surveyAnswer.surveyAnswerId ? "<b>" : ""}
					<c:out value="${surveyAnswer.answer}" />
					${voted == surveyAnswer.surveyAnswerId ? "</b>" : ""}
					&nbsp;&nbsp;(<fmt:formatNumber type="percent" maxIntegerDigits="3"
							minFractionDigits="2"
							value='${surveyAnswer.votes/currentSurvey.totalVotes == "NaN" ? 0.0 : surveyAnswer.votes/currentSurvey.totalVotes }' />)
					<br />
					</c:forEach>
				</p>
				<span id="quizTotalVotes">Total votes:
					${currentSurvey.totalVotes}</span>
				<form method="post" action="vote#quizContent" id="quizResultForm">
					<input type="submit" value="Refresh" />
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</div>