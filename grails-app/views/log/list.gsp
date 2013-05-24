
<%@ page import="security.Log" %>
<%@ page import="security.User" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'log.label', default: 'Log')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require modules="blockUI" />
    <g:javascript>
     $(function() {
       $(".step, .nextLink, .prevLink").live("click", function(event){ 
        event.preventDefault();
        $.get(event.target.href, function(data, textStatus, request){
            $("#listLogContent").html(data);
          }
        );
       });
      });
    </g:javascript>
	</head>
	<body>
    <div class="body">
      <div id="list-log" class="content scaffold-list" role="main">
        <h1><g:message code="default.list.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
          <div class="message" role="status">${flash.message}</div>
        </g:if>

        <g:formRemote name="searchForm" url="[controller:'log', action: 'listContent']" update="listLogContent">
          <g:select name="actionLogSearch"
              noSelection="${['':'Select One...']}"
              from="${logInstance.constraints.action.inList}">
          </g:select>

          <g:select name="userId"
              noSelection="${['':'Select One...']}"
              from='${User.list()}'
              optionKey="id" optionValue="username">
          </g:select>

          <input type="submit" name="doit" value="${message(code:'studySearchResult.list.action.search')}" />
        </g:formRemote>

        <div id="listLogContent">
          <g:render template="listContent" />
        </div>
      </div>
    </div>
	</body>
</html>
