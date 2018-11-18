<%=packageName%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main" />
    <title><g:message code="aei.studySearchResult.list.title" /></title>
  </head>
  <body>
    <div class="body">

      <h1><g:message code="aei.studySearchResult.list.title" /></h1>

      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>

      <g:form controller="studySearchResult" action="search" class="form-inline">
        <div class="form-group">
          <input type="text" name="patientId" class="form-control" placeholder="${message(code:'list.label.searchByPatientId')}"  value="${params.patientId}" />
        </div>
        <div class="form-group">
          <input type="text" name="patientName" class="form-control" placeholder="${message(code:'list.label.searchByPatientName')}"  value="${params.patientName}" />
        </div>
        <div class="form-group">
          <input type="text" name="patientLastname" class="form-control" placeholder="${message(code:'list.label.searchByPatientLastname')}"  value="${params.patientLastname}" />
        </div>
        <button type="submit" name="doit" class="btn btn-default">${message(code:'studySearchResult.list.action.search')}</button>

	      <!-- FIXME: aunque le pase nombre y apellido, no los considera en la query AeiService.sendDICOMQuery2...
	      <g:message code="list.label.searchByPatientName" />:
	      <input type="text" name="patientName" value="${params.patientName}" />
	      <g:message code="list.label.searchByPatientLastname" />:
	      <input type="text" name="patientLastname" value="${params.patientLastname}" />
	      -->
      </g:form>

      <br />

      <h2><g:message code="aei.studySearchResult.found.title" /></h2>

      <div class="list">
        <table class="table table-bordered table-hover">
          <thead>
            <tr>
              <th><g:message code="studySearhcResult.label.id" /></th>
              <th><g:message code="studySearhcResult.label.patientName" /></th>
              <th><g:message code="studySearhcResult.label.studyDescription" /></th>
              <th><g:message code="studySearhcResult.label.studyDate" /></th>
              <th><g:message code="studySearhcResult.label.modalities" /></th>
              <th><g:message code="studySearhcResult.label.studyId" /></th>
              <th><g:message code="studySearhcResult.label.series" /></th>
              <th><g:message code="studySearhcResult.label.images" /></th>
              <th><g:message code="studySearhcResult.label.source" /></th>
              <th><g:message code="studySearhcResult.label.actions" /></th>
            </tr>
          </thead>
          <tbody>
            <g:each in="${studies}" status="i" var="study">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>${study.id}</td>
                <td>${study.patientName}</td>
                <td>${study.studyDescription}</td>
                <td>${study.studyDate}</td>
                <td>${study.modalitiesInStudy}</td>
                <td>${study.studyId}</td>
                <td>${study.seriesNumber}</td>
                <td>${study.imagesNumber}</td>
                <td>${study.source.description}</td>
                <td>
                  <%-- Este cuando se selecciona una serie se ocultan las demas.
                       en el 2 se muestran todas las series y la seleccionaa queda
                       marcada destacada.
                  <g:link action="studySeries" id="${study.id}">Series</g:link>
                  |
                  --%>
                  <g:link action="studySeries2" id="${study.id}">Series</g:link>
                </td>
              </tr>
            </g:each>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>
