<!-- 세션이 존재하지 않을 때 출력 -->

<%@ tag body-content="scriptless" pageEncoding="utf-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%
	HttpSession httpSession = request.getSession(false);
	if (httpSession == null || httpSession.getAttribute("authUser") == null) {
%>
<jsp:doBody />
<%
	}
%>