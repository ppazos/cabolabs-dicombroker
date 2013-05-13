package aei

class NotifierService {

  def mailService
  def sendEmail(dest_email, dest_subject, dest_body) {
    mailService.sendMail {
      to dest_email
      subject dest_subject
      body dest_body
    }
  }

}
