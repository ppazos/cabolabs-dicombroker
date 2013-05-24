        <div class="list">
          <table>
            <thead>
              <tr>
                <g:sortableColumn property="log" title="${message(code: 'log.log.label', default: 'Log')}" />
                <g:sortableColumn property="email" title="${message(code: 'log.controller.label', default: 'Controller')}" />
                <g:sortableColumn property="name" title="${message(code: 'log.action.label', default: 'Action')}" />
                <g:sortableColumn property="name" title="${message(code: 'log.username.label', default: 'Username')}" />
              </tr>
            </thead>
            <tbody>
              <g:each in="${logInstanceList}" status="i" var="logInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                  <td><g:link action="show" id="${logInstance.id}">${fieldValue(bean: logInstance, field: "id")}</g:link></td>
                  <td>${fieldValue(bean: logInstance, field: "controller")}</td>
                  <td>${fieldValue(bean: logInstance, field: "action")}</td>
                  <td>${fieldValue(bean: logInstance, field: "username")}</td>
                </tr>
              </g:each>
            </tbody>
          </table>
        </div>
        <div class="pagination">
          <g:paginate total="${logInstanceTotal}" action="listContent" />
        </div>

