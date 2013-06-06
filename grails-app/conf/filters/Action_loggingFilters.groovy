package filters
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import security.Log
import security.User

class Action_loggingFilters {

    def springSecurityService
    def filters = {

        loginLog(controller:'dashboard', action:'index'){
          after = {
            if(request.getHeader("referer") ==~ ".*login/auth"){
              def log = new Log(controller: controllerName, action: "login", user: User.get(springSecurityService.principal.id))
              if(!log.save())
                println log.errors
            }
          }
        }

        logoutLog(controller:'logout', action:'index'){
          before = {
            if (springSecurityService.isLoggedIn())
            {
              def log = new Log(controller: controllerName, action: actionName, user: User.get(springSecurityService.principal.id))
              if(!log.save())
                println log.errors
            }
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'search'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, user: User.get(springSecurityService.principal.id))
            if(!log.save())
              println log.errors
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'sendEmail'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, user: User.get(springSecurityService.principal.id))
            if(!log.save())
              println log.errors
          }
        }

        searchImagesLogs(controller:'studySearchResult', action:'sendToApp'){
          after = {
            def log = new Log(controller: controllerName, action: actionName, user: User.get(springSecurityService.principal.id))
            if(!log.save())
              println log.errors
          }
        }
    }
}
