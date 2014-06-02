package security

import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder

import aei.AeRegistry

class PacsSecurity implements Serializable  {

   AeRegistry pacs
   User user
   
   Date dateCreated
   
   static mapping = {
      autoTimestamp false
      id composite: ['pacs', 'user']
      version false
   }
   
   
   boolean equals(other) {
      if (!(other instanceof PacsSecurity)) {
         return false
      }

      other.user?.id == user?.id && other.pacs?.id == pacs?.id
   }

   int hashCode() {
      def builder = new HashCodeBuilder()
      if (user) builder.append(user.id)
      if (pacs) builder.append(pacs.id)
      builder.toHashCode()
   }
}
