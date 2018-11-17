<html>
  <%-- <%@ page import="org.codehaus.groovy.grails.plugins.PluginManagerHolder" %>--%>
  <%@ page import="security.PacsSecurity" %>
  <head>
    <meta name='layout' content='springSecurityUI'/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
  </head>

  <body>

    <h3><g:message code="default.pacsSecurity.label" args="[entityName]"/></h3>

    <g:form action="update" name='userEditForm' class="button-style">
      <table>
        <g:each in="${doctorList}" var="doctor">
          <tr>
            <td>${doctor.username}</td>
            <g:each in="${pacsList}" var="pacs">
              <td>
                <div>${pacs.remoteAETitle}</div>
                <div>${pacs.description}</div>
                <!-- TODO: sacar el valor de la config actual -->
                <div><g:checkBox name="hasPermission" value="${false}" data-user_id="${doctor.id}" data-pacs_id="${pacs.id}" checked="${PacsSecurity.exists(doctor, pacs)}" /></div>
              </td>
            </g:each>
          </tr>
        </g:each>
      </table>
    </g:form>

    <script>
    $(document).ready(function() {

      $("[name=hasPermission]").click( function() {

        userId = $(this).data("user_id");
        pacsId = $(this).data("pacs_id");
        hasPermission = this.checked; // boolean http://api.jquery.com/prop/

        console.log(userId, pacsId, this);


        $.post("${createLink(action:'pacsSecurityUpdate')}",
               { userId: userId, pacsId: pacsId, hasPermission: hasPermission },
               function(a, b, c) {
		           alert( "success" );
		           console.log(a, b, c);
		         })
		         .done(function() {
		           alert( "second success" );
		         })
		         .fail(function() {
		           alert( "error" );
		         })
		         .always(function() {
		           alert( "finished" );
		         });
      });
    });
    </script>

  </body>
</html>
