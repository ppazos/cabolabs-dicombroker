package security

class LogController {

    def index() {
      redirect(action: "list", params: params)
    }


    def list(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      [logInstanceList: Log.list(params), logInstanceTotal: Log.count()]
    }
}
