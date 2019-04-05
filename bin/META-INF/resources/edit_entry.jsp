<%@ include file="init.jsp" %>
<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/view.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="addEntry" var="addEntryURL"></portlet:actionURL>

<aui:form action="<%= addEntryURL %>" name="<portlet:namespace />fm">
        <aui:fieldset>
            <aui:input name="jobCronPattern"></aui:input>
            
        </aui:fieldset>

        <aui:button-row>
            <aui:button type="Start"></aui:button>
            <aui:button type="cancel" onClick="<%= viewURL.toString() %>"></aui:button>
        </aui:button-row>
</aui:form>
