

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'localAETitle', 'error')} ">
    <label for="localAETitle">
      <g:message code="aeRegistry.localAETitle.label" default="Local AE Title" />
    </label>
    <g:field type="text" class="form-control" name="localAETitle" value="${ae.hasProperty('localAETitle') ? ae?.localAETitle : '' }"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'localIP', 'error')} ">
    <label for="localIP">
      <g:message code="aeRegistry.localIP.label" default="Local IP" />
    </label>
    <g:field type="text" class="form-control" name="localIP" value="${ae.hasProperty('localIP') ? ae?.localIP : '' }"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'localPort', 'error')} ">
    <label for="localPort">
      <g:message code="aeRegistry.localPort.label" default="Local Port" />
    </label>
    <g:field type="number" class="form-control" name="localPort" value="${ae.hasProperty('localPort') ? ae?.localPort : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'remoteAETitle', 'error')} ">
    <label for="remoteAETitle">
      <g:message code="aeRegistry.remoteAETitle.label" default="Remote AE Title" />
    </label>
    <g:field type="text" name="remoteAETitle" class="form-control" value="${ae.hasProperty('remoteAETitle') ? ae?.remoteAETitle : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'remoteDomain', 'error')} ">
    <label for="remoteDomain">
      <g:message code="aeRegistry.remoteDomain.label" default="Remote Domain" />
    </label>
    <g:field type="text" name="remoteDomain" class="form-control" value="${ae.hasProperty('remoteDomain') ? ae?.remoteDomain : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'remoteIP', 'error')} ">
    <label for="remoteIP">
      <g:message code="aeRegistry.remoteIP.label" default="Remote IP" />
    </label>
    <g:field type="text" name="remoteIP" class="form-control" value="${ae.hasProperty('remoteIP') ? ae?.remoteIP : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'remotePort', 'error')} ">
    <label for="remotePort">
      <g:message code="aeRegistry.remotePort.label" default="Remote Port" />
    </label>
    <g:field type="number" name="remotePort" class="form-control" value="${ae.hasProperty('remotePort') ? ae?.remotePort : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'remoteWADOPort', 'error')} ">
    <label for="remoteWADOPort">
      <g:message code="aeRegistry.remoteWADOPort.label" default="Remote WADO Port" />
    </label>
    <g:field type="number" name="remoteWADOPort" class="form-control" value="${ae.hasProperty('remoteWADOPort') ? ae?.remoteWADOPort : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'remoteWADOPath', 'error')} ">
    <label for="remoteWADOPath">
      <g:message code="aeRegistry.remoteWADOPath.label" default="Remote WADO Path" />
    </label>
    <g:field type="text" name="remoteWADOPath" class="form-control" value="${ae.hasProperty('remoteWADOPath') ? ae?.remoteWADOPath : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'hl7ServicePort', 'error')} ">
    <label for="hl7ServicePort">
      <g:message code="aeRegistry.hl7ServicePort.label" default="HL7 Service Port" />
    </label>
    <g:field type="number" name="hl7ServicePort" class="form-control" value="${ae.hasProperty('hl7ServicePort') ? ae?.hl7ServicePort : ''}"/>
  </div>

  <div class="form-group fieldcontain ${hasErrors(bean: ae, field: 'active', 'error')} ">
    <label for="active">
      <g:message code="aeRegistry.active.label" default="Is active?" />
    </label>
    <g:checkBox name="active" value="${true}" checked="${ae.active}"/>
  </div>
