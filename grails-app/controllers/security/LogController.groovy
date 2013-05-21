package security

class LogController {

    def index() {
      redirect(action: "list", params: params)
    }


    def list(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      [logInstanceList: Log.list(params), logInstanceTotal: Log.count()]
    }

    def listContent(Integer max){
      def logsList = Log.withCriteria {
        if(params.actionLogSearch)
          eq('action', params.actionLogSearch)
        if(params.userId)
          eq('user_id', params.userId.toInteger())
      }
      render(template: "listContent", model: [logInstanceList: logsList, logInstanceTotal: logsList.size()])
    }

    def search = {

        if (!params.controller && !params.action && !params.userName)
        {
           flash.message = 'Please specify a search criteria'
           redirect(action:'list', params:params)
           return
        }
        def logs = Log.find(params)
        [logInstanceList: logs, logInstanceTotal: logs.count()]
    }
}
