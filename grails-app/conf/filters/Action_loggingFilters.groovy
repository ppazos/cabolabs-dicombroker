package filters
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import security.Log

class Action_loggingFilters {

    def springSecurityService
    def filters = {

        logoutLog(controller:'logout', action:'index'){
          before = {
            if (springSecurityService.isLoggedIn())
            {
              def log = new Log(controller: controllerName, action: actionName, username: springSecurityService.principal.username)
              log.save()
            }
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'search'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, username: springSecurityService.principal.username)
            log.save()
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'sendEmail'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, username: springSecurityService.principal.username)
            log.save()
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'sendToApp'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, username: springSecurityService.principal.username)
            log.save()
          }
        }
    }
}
