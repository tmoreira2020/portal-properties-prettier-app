<%--

    Copyright © 2015 Thiago Moreira (tmoreira2020@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ include file="/html/init.jsp" %>

<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/8.7/styles/vs.min.css">
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/8.7/highlight.min.js"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.7.1/clipboard.min.js"></script>

<portlet:actionURL var="prettifyURL" name="prettify" />

<div class="alert alert-info"><liferay-ui:message key="help-message" /><span class="badge badge-warning">${portletPreferencesValues.counter[0]}</span></div>

<aui:form action="${prettifyURL}" method="POST" name="fm" enctype="multipart/form-data" >

	<aui:fieldset>
		<aui:select name="liferayVersion" >
			<aui:option value="6.2.5-ga6">6.2.5-ga6</aui:option>
			<aui:option value="6.2.4-ga5">6.2.4-ga5</aui:option>
			<aui:option value="6.2.3-ga4">6.2.3-ga4</aui:option>
			<aui:option value="6.2.2-ga3">6.2.2-ga3</aui:option>
			<aui:option value="6.2.1-ga2">6.2.1-ga2</aui:option>
			<aui:option value="6.2.0-ga1">6.2.0-ga1</aui:option>
			<aui:option value="6.1.2-ga3">6.1.2-ga3</aui:option>
			<aui:option value="6.1.1-ga2">6.1.1-ga2</aui:option>
			<aui:option value="6.1.0-ga1">6.1.0-ga1</aui:option>
		</aui:select>
		<aui:input name="printDefaultValue" type="checkbox" />
		<aui:input name="portalPropertiesFile" type="file" >
			<aui:validator name="required" />
		</aui:input>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="prettify" />
		<aui:button href="${param.redirect}" type="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="${not empty(requestScope.portalPrettyProperties)}">
	<div class="btn btn-warning pull-right" id="copyButton" title="Copied!" data-clipboard-target="#portalPrettyProperties">
		<liferay-ui:message key="copy" />
	</div>
	<pre><code class="makefile" id="portalPrettyProperties">${requestScope.portalPrettyProperties}</code></pre>
</c:if>