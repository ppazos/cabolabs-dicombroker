package security

import aei.AeRegistry
import grails.converters.JSON

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
   
   def pacsSecurityUpdate(int userId, int pacsId, boolean hasPermission)
   {
      println params
      
      def user = User.get(userId)
      def pacs = AeRegistry.get(pacsId)
      def ps = PacsSecurity.findByUserAndPacs(user, pacs)
      
      def status = "ok"
      def message = ""
      
      if (hasPermission)
      {
         if (!ps)
         {
            ps = new PacsSecurity(user: user, pacs: pacs)
            if (!ps.save(flush:true))
            {
               println ps.errors
               status = "error"
               message = ps.errors.toString()
            }
         }
         
         if (status == "ok") message = "permission added"
      }
      else
      {
         if (ps) ps.delete(flush: true)
         
         message = "permission removed"
      }
      
      def result = ["status": status, "message": message]
      render result as JSON
     
      return
   }
}
