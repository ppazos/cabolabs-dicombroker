<%@ page import="aei.AeRegistry" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'aeRegistry.label', default: 'aeRegistry')}" />
		<title><g:message code="default.edit.label" args="[entityName]" default="Edit" /></title>
    <r:require modules="jquery" />
	</head>
	<body>
    <div id="edit-aeRegistry" class="content scaffold-edit" role="main">
      <h1><g:message code="default.edit.label" args="[entityName]" default="Edit" /></h1>
      <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${ae}">
      <ul class="errors" role="alert">
        <g:eachError bean="${ae}" var="error">
        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
      </g:hasErrors>
      <g:form method="post">
        <g:hiddenField name="id" value="${ae?.id}" />
        <g:hiddenField name="version" value="${ae?.version}" />
        <g:render template="form"/>
        <div class="form-group">
          <g:actionSubmit class="save btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
          <%--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--%>
        </div>
      </g:form>
    </div>
	</body>
</html>
