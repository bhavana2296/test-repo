<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp" %>
<%
        String jobCronPattern= ParamUtil.getString(request, "jobCronPattern");
        %>

<portlet:renderURL var="addEntryURL">
    <portlet:param name="mvcPath" value="/edit_entry.jsp"></portlet:param>
</portlet:renderURL>



<%-- <aui:button-row>
    <aui:button action="<%= addEntryURL %>"  value="Add Delay"></aui:button>
</aui:button-row>
 --%>
<portlet:actionURL name="addEntry" var="addEntryUrl">
</portlet:actionURL>
 
<form action="${addEntryUrl}" method="POST">
    Parameter : <input type="text" name="jobCronPattern">
    <input type="submit" value="Submit">
</form>
