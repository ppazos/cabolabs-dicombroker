<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="hctrauma" />
    <title><g:message code="aei.studySearchResult.list.title" /></title>
    <g:javascript>
      Event.observe(window, 'load', function () {
      
        $$('a.wado_url').each( function(item) {
        
          //alert(item.href);
        
          item.observe('click', function(event) {
          
             $('show_object').src = item.href;
             
             return false;
          });
        });
      });
    </g:javascript>
    <style>
      #show_object {
        height: 400px;
        width: 858px;
        overflow: auto;
        border: 1px solid #000;
        margin-top: 10px;
        background-color: #fff;
      }
      .objects_div {
        height: 160px;
        overflow: auto;
      }
    </style>
  </head>
  <body>
    <div class="nav">
      <%--
      <span class="menuButton"><a class="home" href="\${resource(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="create" action="create">New ${className}</g:link></span>
      --%>
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
            <tr>
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
            <tr>
              <td>${params.modality}</td>
              <td>${params.sdesc}</td>
              <!--
              <td>${params.studyuid}</td>
              <td>${params.uid}</td>
              -->
              <td>${params.snumber}</td>
              <td>${params.simagecount}</td>
              <td>
                <g:if test="${params.modality=='SR'}">
                  <g:link action="seriesImages" id="${sresult.id}"
                          params="[uid:         params.uid,
                                   sdesc:       params.sdesc,
                                   studyuid:    params.studyuid,
                                   snumber:     params.snumber,
                                   simagecount: params.simagecount,
                                   modality:    params.modality]"><g:message code="seriesImages2.action.SR" /></g:link>
                </g:if>
                <g:else>
                  <g:link action="seriesImages" id="${sresult.id}"
                          params="[uid:         params.uid,
                                   sdesc:       params.sdesc,
                                   studyuid:    params.studyuid,
                                   snumber:     params.snumber,
                                   simagecount: params.simagecount,
                                   modality:    params.modality]"><g:message code="seriesImages2.action.IMG" /></g:link>
                </g:else>
              </td>
            </tr>
          </tbody>
        </table>
        
        <div class="objects_div">
        
	        <table class="center_td">
	          <thead>
	            <tr>
	              <!--
	              <th><g:message code="dicom.tag.80018" /></th>
	              -->
	              <th><g:message code="dicom.tag.200013" /></th>
	              <%--<th><g:message code="dicom.tag.20000e" /></th>--%>
	              <th><g:message code="studySearhcResult.label.actions" /></th>
	            </tr>
	          </thead>
	          <tbody>
	            <g:each in="${results}" status="i" var="result">
	              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                <!--
	                <td>${result['80018']}</td>
	                -->
	                <td>${result['200013']}</td>
	                <%--<td>${result['20000e']}</td>--%>
	                <td>
	                  <%-- FIXME: contentType: image/jpeg --%>
	                  <%--<g:link action="image" id="${sresult.id}" params="[uid:result['80018']]">Imagen</g:link>--%>
	                  <g:wadoUrl studyUid="${params.studyuid}"
	                             seriesUid="${params.uid}"
	                             objectUid="${result['80018']}"
	                             reg="${reg}">Ver objeto</g:wadoUrl>
	                </td>
	                <%--
	                [
	                 {80016=1.2.840.10008.5.1.4.1.1.2, 80018=1.2.840.113619.2.30.1.1762295590.1623.978668949.889,
	                  80054=SERVERAE, 80056=ONLINE, 20000d=1.2.840.113619.2.30.1.1762295590.1623.978668949.886,
	                   20000e=1.2.840.113619.2.30.1.1762295590.1623.978668949.887, 200013=2, 201209=},
	                 {80016=1.2.840.10008.5.1.4.1.1.2, 80018=1.2.840.113619.2.30.1.1762295590.1623.978668949.888,
	                  80054=SERVERAE, 80056=ONLINE, 20000d=1.2.840.113619.2.30.1.1762295590.1623.978668949.886,
	                   20000e=1.2.840.113619.2.30.1.1762295590.1623.978668949.887, 200013=1, 201209=}
	                ]
	                --%>
	              </tr>
	            </g:each>
	          </tbody>
	        </table>
	    </div>
        
        <iframe src="" id="show_object"></iframe>
        
      </div>
    </div>
  </body>
</html>
