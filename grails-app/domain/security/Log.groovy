package security
import security.User

class Log {
    String controller
    String action
    User user

    static mapping = {
      user lazy: false
    }
    static constraints = {
      //action inList: ['login', 'logout', 'search', 'send']
      user nullable: false
    }

}
