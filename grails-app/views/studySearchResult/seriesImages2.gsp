<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main" />
    <title><g:message code="aei.studySearchResult.list.title" /></title>

    <r:require modules="blockUI" />

    <style>
      #show_object_iframe {
        height: 400px;
        width: 858px;
        overflow: auto;
        border: 1px solid #000;
        margin-top: 10px;
        background-color: #fff;
        display: none;
      }
      #show_object_img {
        /*height: 400px;*/
        max-width: 858px;
        /*overflow: auto;*/
        border: 1px solid #000;
        margin-top: 10px;
        /*background-color: #fff;*/
        /*display: none;*/
      }
      #show_object_a {
        border: 0px;
        display: none;
      }
      #email-destination, #app-destination {
        display: none;
      }
      
      <%-- El alto deberia depender de la cantidad de items en objetos, 
           con 7 o menos, dejar alto automatico, con mas de 7 fijar en 160px --%>
      .objects_div {
        height: 110px;
        overflow: auto;
		    border-bottom: 1px solid #ddd;
      }
           
      #snd_img_frm {
        display: none;
      }

      #show_send_frm {
        display: none;
      }

      #snd_img_frm > #img_container {
        width: 30%;
        float:left;
      }

      #snd_img_frm > #destinations_container {
        width:30%;
        float:left;
      }

      #snd_img_frm > #destination_details {
        width:30%;
        float:left;
      }

      // Styles for the growlUI notification
      div.growlUI { background: url('/dicom-broker/images/check48.png') no-repeat 10px 10px }
      div.growlUI h1, div.growlUI h2 {
        color: white; padding: 5px 5px 5px 75px; text-align: left
      }
 
    </style>
  </head>
  <body>


  <g:javascript>
    $(function() {

      // Re-implementation of what was done with prototype:
      // when chosing an object to show, displays it according to
      // its class ('SR' or 'image')
      $('a.wado_url').click( function(){
        $('#show_send_frm').show();
        if ($(this).hasClass('SR'))
        {
          $('#show_object_img').hide();
          $('#snd_img_frm').hide();

          $('#show_object_iframe').attr('src', this.href);
          $('#show_object_iframe').show();
        }
        else
        {
          $('#show_object_iframe').hide();
          $('#show_object_img').attr('src', this.href);
          $('#form_object_img').attr('src', this.href);

          $('#show_send_frm').show();
          $('#snd_img_frm').find('[name=wado-url]').val(this.href);
        }
      });

      // When pressing the send link to send an image,
      // show the modal dialog to choose destination
      $('#show_send_frm').click( function() {
        $.blockUI( {
          message: $('#snd_img_frm'),
          css: { 
            left: ($(window).width() - 800) /2 + 'px', 
            width: '800px' 
          } 
        });
      });

      
      // When selecting a destination to send the wado-url, 
      // gets its data via ajax and shows the information in the modal dialog
      $('.dest-link').click( function() {
        var id = $(this).find('[name=dest-id]').val();
        $.ajax({
          url: "${createLink(controller: 'StudySearchResult', action: 'destDetails')}",
          type: 'GET',
          data: 'destId='+id,
          success: function(data) {
            $('#dest_name').val(data['name']);
            if (data['class'] == 'aei.EmailDestinationConfig')
            {
              $('#app-destination').hide();
              $('#email-destination').show();

              $('#dest_email').val(data['sended_to']);
              $('#dest_subject').val(data['subject']);
              $('#dest_body').html( $('#show_object_img').attr('src') );
            }
            else if (data['class'] == 'aei.AppDestinationConfig')
            {
              $('#app-destination').show();
              $('#email-destination').hide();
              $('#dest_id').val(data['id']);     
              $('#dest_url').val( $('#show_object_img').attr('src') );
            }
          }
        });
      });
    });

    // When the send image is completed (to an app or through email)
    // shows a growl notification indicating the result
    function completedSend( message ) {
      $.growlUI('Action Completed', message); 
    }

    </g:javascript>

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
             <tr><!--  TODO: i18n ! -->
               <th><g:message code="dicom.tag.80060" /></th>
               <th><g:message code="dicom.tag.8103e" /></th>
               <%--
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
               <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" style="${(serie.id.toString()==params.serieId) ? 'font-weight: bold' : ''}">
                 <g:if test="${serie.id.toString() == params.serieId}">
                   <g:set var="selectedSerie" value="${serie}" />
                 </g:if>
                 <td>${serie.modality}</td>
                 <td>${serie.description}</td>
                 <%--
                 <td>${result['20000d']}</td>
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
        
        <div class="objects_div">
          <table class="center_td">
            <thead>
              <tr>
                <%--<th><g:message code="dicom.tag.80018" /></th>--%>
                <th><g:message code="dicom.tag.200013" /></th>
                <%--<th><g:message code="dicom.tag.20000e" /></th>--%>
                <th><g:message code="dicom.tag.280008" /></th><!-- frames -->
                <th><g:message code="studySearhcResult.label.actions" /></th>
              </tr>
            </thead>
            <tbody>
              <g:each in="${objects}" status="i" var="result">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                  <%--<td>${result['80018']}</td>--%>
                  <td>${result['200013']}</td>
                  <%--<td>${result['20000e']}</td>--%>
                  <td>${result['280008']}</td>
                  <td>
                    <%-- FIXME: contentType: image/jpeg --%>
                    <%--<g:link action="image" id="${selectedStudy.id}" params="[uid:result['80018']]">Imagen</g:link>--%>
                    <%--
                    <g:wadoUrl studyUid="${selectedSerie.studyUID}"
                               seriesUid="${selectedSerie.serieUID}"
                               objectUid="${result['80018']}"
                               reg="${reg}"
                               clazz="${selectedSerie.modality}"><g:message code="seriesImages2.action.seeObj" /></g:wadoUrl>
                    --%>
                    <g:if test="${!result['280008'] || result['280008'] == 1}">
                      <!-- un frame -->
                      <g:wadoUrl studyUid="${selectedSerie.studyUID}"
                                 seriesUid="${selectedSerie.serieUID}"
                                 objectUid="${result['80018']}"
                                 reg="${reg}"
                                 clazz="${selectedSerie.modality}"><g:message code="seriesImages2.action.seeObj" /></g:wadoUrl>
                    </g:if>
                    <g:else>
                      <!-- mas de un frame -->
                      <g:each in="${1..Integer.parseInt(result['280008'])}" var="j">
                        <g:wadoUrl studyUid="${selectedSerie.studyUID}"
                                   seriesUid="${selectedSerie.serieUID}"
                                   objectUid="${result['80018']}"
                                   frameNumber="${j}"
                                   reg="${reg}"
                                   clazz="${selectedSerie.modality}"><g:message code="seriesImages2.action.seeObjFrame" /></g:wadoUrl> ${j}<br/>
                      </g:each>
                    </g:else>
                  </td>
                </tr>
              </g:each>
            </tbody>
          </table>
        </div>
        
        <!-- Enviar la URL de la imagen a un server externo -->
        <g:link url="javascript:void(0)" elementId="show_send_frm">Send </g:link>

        <div id="snd_img_frm">
          <input type="hidden" name="wado-url" />

          <div id="img_container">
            <!-- Image to send -->
            <img src="" id="form_object_img" height="200" width="auto"/>
          </div>

          <div id="destinations_container">
            <!-- Destinations list -->
            <ul>
              <g:each in="${destinations}" status="i" var="destination">
                <li class="dest-link">
                  <input type="hidden" value="${destination.id}" name="dest-id" />
                  <g:link url="javascript:void(0)">${destination.name}</g:link>
                </li>
              </g:each>
            </ul> 
          </div>


          <div id="destination_details">

            <div id="email-destination">
              <g:formRemote name="wadoForm" url="[controller:'studySearchResult', action:'sendEmail']" 
                  onSuccess="completedSend(data)" onFailure="completedSend(errorThrown)" after="\$.unblockUI();">
                <input id="dest_name" name="dest_name">
                <input id="dest_email" name="dest_email">
                <input id="dest_subject" name="dest_subject">
                <textarea id="dest_body" name="dest_body"></textarea>
                <g:actionSubmit value="Send WADO URL" />
              </g:formRemote>
            </div>
          </div>

          <div id="app-destination">
            <g:formRemote name="wadoForm" url="[controller:'studySearchResult', action:'sendToApp']" 
                onSuccess="completedSend(data);" onFailure="completedSend(errorThrown);" after="\$.unblockUI();">
              <g:hiddenField name="dest_id" />
              <g:hiddenField name="dest_url" />
              <g:actionSubmit value="Send WADO URL" />
            </g:formRemote>
          </div>

          <!-- Ir a otra aplicacion una vez que envia la wado url -->
          <!-- <a href="http://46.38.162.152:8090/patientinfo" target="_blank">Show patient</a> -->
        </div>
        <div style="clear:both">

        <!-- Para mostrar SRs -->
        <iframe src="" id="show_object_iframe"></iframe>
        
        <!-- Para mostrar imagenes -->
        <img src="" id="show_object_img" />
      </div>
    </div>
  </body>
</html>
