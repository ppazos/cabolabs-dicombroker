<script>
  $(document).ready(function(){
    $('#email-dest-fields').hide();

    // Check wich radio button should be checked

    if( $('#port').val() != "")
    {
      $('#app-dest-fields').show();
      $('#email-dest-fields').hide();
      $('#1-dest-type').attr('checked',true);
    }
    else if( $('#sended_from').val() != "")
    {
      $('#app-dest-fields').hide();
      $('#email-dest-fields').show();
      $('#2-dest-type').attr('checked',true);
    }
      
    // When the radio button is changed, hide
    // the 'other' form and clean its inputs
    $(".dest-type").change( function() {
      if($(".dest-type:checked").val() == "1")
      {
        $('#app-dest-fields').show();
        $('#email-dest-fields').hide();
        $('#email-dest-fields :input').each( function(){
          this.value = "";
        });
      }
      else 
      {
        $('#app-dest-fields').hide();
        $('#email-dest-fields').show();
        $('#app-dest-fields :input').each( function(){
          this.value = "";
        });
      }
    });
  });
</script>

<g:radio class="dest-type" name="dest-type" id="1-dest-type" value="1" checked="true"/>App Destination
<g:radio class="dest-type" name="dest-type" id="2-dest-type" value="2" />Email Destination

<div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="destinationConfig.name.label" default="Name" />
	</label>
	<g:textField name="name" value="${destinationConfigInstance?.name}"/>
</div>

<!-- EmailDestinationConfig form -->
<div id="email-dest-fields">  
  <div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'sended_from', 'error')} ">
    <label for="sended_from">
      <g:message code="destinationConfig.sended_from.label" default="Sendedfrom" />
    </label>
    <g:field type="email" name="sended_from" value="${destinationConfigInstance.hasProperty('sended_from') ? destinationConfigInstance?.sended_from : '' }"/>
  </div>

  <div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'sended_to', 'error')} ">
    <label for="sended_to">
      <g:message code="destinationConfig.sended_to.label" default="Sendedto" />
    </label>
    <g:field type="email" name="sended_to" value="${destinationConfigInstance.hasProperty('sended_to') ? destinationConfigInstance?.sended_to : '' }"/>
  </div>

  <div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'subject', 'error')} ">
    <label for="subject">
      <g:message code="destinationConfig.subject.label" default="Subject" />
    </label>
    <g:textField name="subject" value="${destinationConfigInstance.hasProperty('subject') ? destinationConfigInstance?.subject : ''}"/>
  </div>

  <div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'body', 'error')} ">
    <label for="body">
      <g:message code="destinationConfig.body.label" default="Body" />
    </label>
    <g:textField name="body" value="${destinationConfigInstance.hasProperty('body') ? destinationConfigInstance?.body : ''}"/>
  </div>
</div>

<div id="app-dest-fields">
  <!-- AppDestinationConfig form -->
  <div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'ip', 'error')} ">
    <label for="ip">
      <g:message code="destinationConfig.ip.label" default="Ip" />
    </label>
    <g:textField name="ip" value="${destinationConfigInstance.hasProperty('ip') ? destinationConfigInstance?.ip : ''}"/>
  </div>

  <div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'path', 'error')} ">
    <label for="path">
      <g:message code="destinationConfig.path.label" default="Path" />
    </label>
    <g:textField name="path" value="${destinationConfigInstance.hasProperty('path') ? destinationConfigInstance?.path : ''}"/>
  </div>

  <div class="fieldcontain ${hasErrors(bean: destinationConfigInstance, field: 'port', 'error')}">
    <label for="port">
      <g:message code="destinationConfig.port.label" default="Port" />
      <span class="required-indicator">*</span>
    </label>
    <g:field name="port" type="number" value="${destinationConfigInstance.hasProperty('port') ? destinationConfigInstance?.port : ''}" />
  </div>
</div>


