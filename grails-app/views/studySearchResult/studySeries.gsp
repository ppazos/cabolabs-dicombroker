<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="hctrauma" />
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
         <table class="center_td">
           <thead>
             <tr><!--  TODO: i18n ! -->
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
             <tr>
               <td>${sresult.id}</td>
               <td>${sresult.patientName}</td>
               <td>${sresult.studyDescription}</td>
               <td>${sresult.studyDate}</td>
               <td>${sresult.modalitiesInStudy}</td>
               <td>${sresult.studyId}</td>
               <td>${sresult.seriesNumber}</td>
               <td>${sresult.imagesNumber}</td>
               <td>${sresult.source.description}</td>
               <td>
                 <g:link action="studySeries" id="${sresult.id}">Series</g:link>
               </td>
             </tr>
           </tbody>
         </table>
           
         <table class="center_td">
           <thead>
             <tr><!--  TODO: i18n ! -->
               <th><g:message code="dicom.tag.80060" /></th>
               <th><g:message code="dicom.tag.8103e" /></th>
               <!--
               <th><g:message code="dicom.tag.20000d" /></th>
               <th><g:message code="dicom.tag.20000e" /></th>
               -->
               <th><g:message code="dicom.tag.200011" /></th>
               <th><g:message code="dicom.tag.201209" /></th>
               <th><g:message code="studySearhcResult.label.actions" /></th>
             </tr>
           </thead>
           <tbody>
             <g:each in="${results}" status="i" var="result">
               <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                 <td>${result['80060']}</td>
                 <td>${result['8103e']}</td>
                 <!--
                 <td>${result['20000d']}</td>
                 <td>${result['20000e']}</td>
                 -->
                 <td>${result['200011']}</td>
                 <td>${result['201209']}</td>
                 <td>
                   <g:if test="${result['80060']=='SR'}">
                     <g:link action="seriesImages" id="${sresult.id}"
                             params="[uid:         result['20000e'],
                                      sdesc:       result['8103e'],
                                      studyuid:    result['20000d'],
                                      snumber:     result['200011'],
                                      simagecount: result['201209'],
                                      modality:    result['80060']]">Informes</g:link>
                   </g:if>
                   <g:else>
                     <g:link action="seriesImages" id="${sresult.id}"
                             params="[uid:         result['20000e'],
                                      sdesc:       result['8103e'],
                                      studyuid:    result['20000d'],
                                      snumber:     result['200011'],
                                      simagecount: result['201209'],
                                      modality:    result['80060']]">Imagenes</g:link>
                   </g:else>
                 </td>
                 <%--
                 ${result}
                 {80052=SERIES, 80054=SERVERAE, 80056=ONLINE, 80060=CT, 
                 20000d=1.2.840.113619.2.30.1.1762295590.1623.978668949.886, 
                 20000e=1.2.840.113619.2.30.1.1762295590.1623.978668949.887,
                 200011=1, 201209=2}
                 {80052=SERIES, 80054=SERVERAE, 80056=ONLINE, 80060=CT, 
                 20000d=1.2.840.113619.2.30.1.1762295590.1623.978668949.886, 
                 20000e=1.2.840.113619.2.30.1.1762295590.1623.978668949.890, 
                 200011=2, 201209=109}
                 --%>
               
               </tr>
             </g:each>
           </tbody>
         </table>
       </div>
    </div>
  </body>
</html>