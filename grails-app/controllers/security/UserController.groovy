package security

import aei.AeRegistry


// Copiada al hacer s2ui-override user security
// Junto con las vistas create, edit y search de Spring Security UI
class UserController extends grails.plugins.springsecurity.ui.UserController {
   
   /* lista de usuarios con rol medico y su asignacion a los distintos AeRegistriy (conexiones a PACS) */
   def pacsSecurity()
   {
      def pacsList = AeRegistry.findAllByActive(true)
      def rdoctor = Role.findByAuthority('ROLE_DOCTOR')
      def doctorList = UserRole.findAllByRole(rdoctor).collect { it.user }
      
      return [pacsList: pacsList, doctorList: doctorList]
   }
   
   def pacsSecurityUpdate(int userId, int pacsId)
   {
      println params
      render "ok"
      return
   }
}
