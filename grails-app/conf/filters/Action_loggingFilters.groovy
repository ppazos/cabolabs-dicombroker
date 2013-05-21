package filters
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import security.Log
import security.User

class Action_loggingFilters {

    def springSecurityService
    def filters = {

        logoutLog(controller:'logout', action:'index'){
          before = {
            if (springSecurityService.isLoggedIn())
            {
              def log = new Log(controller: controllerName, action: actionName, user_id: springSecurityService.principal.id)
              log.save()
            }
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'search'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, user_id: springSecurityService.principal.id)
            log.save()
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'sendEmail'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, user_id: springSecurityService.principal.id)
            log.save()
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'sendToApp'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, user_id: springSecurityService.principal.id)
            log.save()
          }
        }
    }
}
