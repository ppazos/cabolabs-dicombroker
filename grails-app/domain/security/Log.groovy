package security
import security.User

class Log {
    String controller
    String action
    int user_id

    static transients = ['username']
    static constraints = {
      action inList: ['login', 'logout', 'search', 'send']
    }

    def getUsername() {
      User.get(this.user_id).username
    }
}
