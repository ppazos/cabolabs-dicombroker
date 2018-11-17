<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main" />
    <title><g:message code="aei.studySearchResult.list.title" /></title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><g:link action="list" class="home">Home</g:link></span>
    </div>
    <div class="body">

       <h1><g:message code="aei.studySearchResult.list.title" /></h1>

       <g:if test="${flash.message}">
         <div class="message"><g:message code="${flash.message}" /></div>
       </g:if>

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
		    <%-- modifico el codigo para ver solo el estudio seleccionado
            <g:each in="${studies}" status="i" var="study">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" style="${(study.id.toString()==params.id) ? 'font-weight: bold' : ''}">
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
                  <g:link action="studySeries2" id="${study.id}">Series</g:link>
                </td>
              </tr>
             </g:each>
			 --%>
			 <tr>
                <td>${selectedStudy.id}</td>
                <td>${selectedStudy.patientName}</td>
                <td>${selectedStudy.studyDescription}</td>
                <td>${selectedStudy.studyDate}</td>
                <td>${selectedStudy.modalitiesInStudy}</td>
                <td>${selectedStudy.studyId}</td>
                <td>${selectedStudy.seriesNumber}</td>
                <td>${selectedStudy.imagesNumber}</td>
                <td>${selectedStudy.source.description}</td>
                <td>
                  <g:link action="studySeries2" id="${selectedStudy.id}"><g:message code="studySeries2.action.series" /></g:link>
                </td>
              </tr>
           </tbody>
         </table>

         <table class="center_td">
           <thead>
             <tr>
               <th><g:message code="dicom.tag.80060" /></th>
               <th><g:message code="dicom.tag.8103e" /></th>
               <%-- UIDs study y series
               <th><g:message code="dicom.tag.20000d" /></th>
               <th><g:message code="dicom.tag.20000e" /></th>
               --%>
               <th><g:message code="dicom.tag.200011" /></th>
               <th><g:message code="dicom.tag.201209" /></th>
               <th><g:message code="studySearhcResult.label.actions" /></th>
             </tr>
           </thead>
           <tbody>
             <g:each in="${selectedStudy.series}" status="i" var="serie">
               <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                 <td>${serie.modality}</td>
                 <td>${serie.description}</td>
                 <%--
                 <td>${serie.studyUID}</td>
                 <td>${serie.serieUID}</td>
                 --%>
                 <td>${serie.number}</td>
                 <td>${serie.elements}</td>
                 <td>
                   <g:if test="${serie.modality=='SR'}">
                     <g:link action="seriesImages2"
                             id="${selectedStudy.id}"
                             params="[serieId: serie.id]"><g:message code="seriesImages2.action.SR" /></g:link>
                   </g:if>
                   <g:else>
                     <g:link action="seriesImages2"
                             id="${selectedStudy.id}"
                             params="[serieId: serie.id]"><g:message code="seriesImages2.action.IMG" /></g:link>
                   </g:else>
                 </td>
               </tr>
             </g:each>
           </tbody>
         </table>
       </div>
    </div>
  </body>
</html>
