<%@ page import="aei.*" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title><g:message code="aeRegistry.qr.title" /></title>
    <style>
      .odd {
        background-color: #eeeeee;
      }
      .even {
        background-color: #dfdfdf;
      }
      .error {
        background-color: #ffdddd;
        border: 1px solid #ff9999;
        padding: 10px;
      }
      th {
        background-color: #ddddff;
      }
    </style>
  </head>
  <body>
    <g:if test="${flash.message}">
      <div class="error">${flash.message}</div><br/>
    </g:if>
    <div class="body">
      <g:form action="qr">
        <input type="hidden" name="id" value="${reg.id}" />
        <table>
          <tr>
            <td><g:message code="aeRegistry.qr.label.studyDateFrom" /></td>
            <td>
              <%-- FIXME: la cota de anio superior tiene que ser el actual + 5 --%>
              <g:datePicker name="studyDateFrom"
                            value="${((studyDateFrom) ? studyDateFrom : (new Date()-365))}"
                            years="${2000..2015}"
                            precision="day"
                            noSelection="['':'-Choose-']"/>
            </td>
          </tr>
          <tr>
            <td><g:message code="aeRegistry.qr.label.studyDateTo" /></td>
            <td>
              <g:datePicker name="studyDateTo"
                            value="${((studyDateTo) ? studyDateTo : (new Date()))}"
                            years="${2000..2015}"
                            precision="day"
                            noSelection="['':'-Choose-']"/>
            </td>
          </tr>
          <tr>
            <td><g:message code="aeRegistry.qr.label.modalitiesInStudy" /></td>
            <td><input type="text" name="modalitiesInStudy" value="${params.modalitiesInStudy}" /></td>
          </tr>
          <tr>
            <td><g:message code="aeRegistry.qr.label.patientName1" /> *</td>
            <td><input type="text" name="name1" value="${params.name1}" /></td>
          </tr>
          <!-- 
          <tr>
            <td><g:message code="aeRegistry.qr.label.patientName2" /></td>
            <td><input type="text" name="name2" /></td>
          </tr>
           -->
          <tr>
            <td><g:message code="aeRegistry.qr.label.patientName3" /> *</td>
            <td><input type="text" name="name3" value="${params.name3}" /></td>
          </tr>
          <!-- 
          <tr>
            <td><g:message code="aeRegistry.qr.label.patientName4" /></td>
            <td><input type="text" name="name4" /></td>
          </tr>
           -->
          <tr>
            <td><g:message code="aeRegistry.qr.label.patientId" /> *</td>
            <td><input type="text" name="patientId" value="${params.patientId}" /></td>
          </tr>
          <tr>
            <td><g:message code="aeRegistry.qr.label.studyId" /> *</td>
            <td><input type="text" name="studyId" value="${params.studyId}" /></td>
          </tr>
        </table>
        <g:submitButton name="doit" value="${message(code:'aeRegistry.qr.action.send')}" />
        <g:actionSubmit value="${message(code:'aeRegistry.qr.action.cancel')}" action="list" />
      </g:form>
      
      <!-- Muestra resultadoe en bruto -->
      <g:if test="${resultList}">
        <table id="results" border="1">
          <g:each in="${resultList}" var="map">
            <tr>
            <g:each in="${map.keySet()}" var="tag">
              <td>
              <g:if test="${map[tag]}">${tag} => ${map[tag]}</g:if>
              <g:else>&nbsp;</g:else>
              </td>
            </g:each>
            </tr>
          </g:each>
        </table>
      </g:if>
      
      <g:if test="${!params.level}">
        <g:set var="tags" value="['80052','100010','100020','100030','100040','201200','201202','201204']" />
      </g:if>
      <g:if test="${params.level=='ST'}">
        <g:set var="tags" value="['80052','100010','100020','100040','20000d','80061','80050','200010','201206','201208','80020']" />
      </g:if>
      <g:if test="${params.level=='SE'}">
        <g:set var="tags" value="['80052','100010','100020','100040','20000d','80050','200010','200011','201209','80020','80060','20000e']" />
      </g:if>
      <g:if test="${params.level=='IM'}">
        <g:set var="tags" value="['80052','20000d','20000e','80018','200013']" />
      </g:if>
      
      <table border="1">
        <tr>
          <th>#</th>
          <g:each in="${tags}" var="tag">
            <th><g:message code="dicom.tag.${tag}" /></th>
          </g:each>
          <th>Acciones</th>
        </tr>
        <g:each in="${resultList}" var="map" status="i">
          <tr>
            <td>${i+1}</td>
            <g:each in="${tags}" var="tag">
              <td>
                <g:if test="${map[tag]}">${map[tag]}</g:if>
                <g:else>&nbsp;</g:else>
              </td>
            </g:each>
            <td>
              <g:form action="qr" id="${reg.id}">
                from: ${studyDateFrom}<br/>
                to: ${studyDateTo}<br/>
                
                <g:hiddenField name="patientId" value="${map['100020']}" />
                <g:hiddenField name="studyDateFrom" value="${formatDate(format:'dd/MM/yyyy', date:studyDateFrom)}" />
                <g:hiddenField name="studyDateTo" value="${formatDate(format:'dd/MM/yyyy', date:studyDateTo)}" />
                <%--
                FIXME: el formato de la fecha depende del locale
                
                public String getDateFormat( Locale locale )
                {
                  def lang = locale.getLanguage()
                  
                  if ( lang == "en" ) // Ingles
                  {
                    return "MM/dd/yy"
                  }
                  else if ( lang == "es" ) // Espanol
                  {
                    return "dd/MM/yy" 
                  }
                  
                  return ""
                }
                
                --%>
                <g:if test="${!params.level}">
                  <%-- nivel por el que quiero buscar despues --%>
                  <g:hiddenField name="level" value="ST" />
                  <g:submitButton name="doit" value="Estudios" />
                  
                  <%-- prueba con link --%>
                  <g:link action="qr" id="${reg.id}"
                      params="${[patientId:map['100020'], level:'S']}">Estudios</g:link>
                </g:if>
                <g:if test="${params.level=='ST'}">
                  <g:hiddenField name="studyUID" value="${map['20000d']}" />
                  <g:hiddenField name="studyID" value="${map['200010']}" />
                  <g:hiddenField name="studyDate" value="${map['80020']}" />
                  <%-- nivel por el que quiero buscar despues --%>
                  <g:hiddenField name="level" value="SE" />
                  <g:submitButton name="doit" value="Series" />
                </g:if>
                <g:if test="${params.level=='SE'}">
                  <g:hiddenField name="serieUID" value="${map['20000e']}" />
                  <%-- nivel por el que quiero buscar despues --%>
                  <g:hiddenField name="level" value="IM" />
                  <g:submitButton name="doit" value="Imagenes" />
                </g:if>
                <g:if test="${params.level=='IM'}">
                  <g:wadoUrl studyUid="${map['20000d']}" seriesUid="${map['20000e']}" objectUid="${map['80018']}" reg="${reg}">
                    Imagen
                  </g:wadoUrl>
                </g:if>
              </g:form>
              
              
            </td>
          </tr>
        </g:each>
      </table>
    </div>
  </body>
</html>
