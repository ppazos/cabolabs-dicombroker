package aei

class EmailDestinationConfig extends DestinationConfig {

    String from
    String to
    String subject
    String body

    static constraints = {
      from email: true
      to email: true
      subject nullable: false
      body nullable: false
    }

    def beforeInsert () {
      if (from == null)
        from = "test@tarmac.com"
    }
}
