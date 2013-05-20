
<%@ page import="security.Log" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'log.label', default: 'Log')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
    <div class="body">
      <div id="list-log" class="content scaffold-list" role="main">
        <h1><g:message code="default.list.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
          <div class="message" role="status">${flash.message}</div>
        </g:if>

        <div class="list">
          <table>
            <thead>
              <tr>
                <g:sortableColumn property="log" title="${message(code: 'log.log.label', default: 'Log')}" />
                <g:sortableColumn property="email" title="${message(code: 'log.controller.label', default: 'Controller')}" />
                <g:sortableColumn property="name" title="${message(code: 'log.action.label', default: 'Action')}" />
                <g:sortableColumn property="name" title="${message(code: 'log.username.label', default: 'Username')}" />
              </tr>
            </thead>
            <tbody>
            <g:each in="${logInstanceList}" status="i" var="logInstance">
              <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td><g:link action="show" id="${logInstance.id}">${fieldValue(bean: logInstance, field: "id")}</g:link></td>
                <td>${fieldValue(bean: logInstance, field: "controller")}</td>
                <td>${fieldValue(bean: logInstance, field: "action")}</td>
                <td>${fieldValue(bean: logInstance, field: "username")}</td>
              </tr>
            </g:each>
            </tbody>
          </table>
        </div>
        <div class="pagination">
          <g:paginate total="${logInstanceTotal}" />
        </div>
      </div>
    </div>
	</body>
</html>
