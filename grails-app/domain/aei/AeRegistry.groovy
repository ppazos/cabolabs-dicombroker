/**
 * 
 */
package aei

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 * 
 * Esta clase modela un registro de un Application Entity,
 * y sera usada para registrar PACS en el sistema local
 * para poder acceder a sus imagenes.
 *
 */
public class AeRegistry {

    String localAETitle     // Nombre del application entity
    String localIP          // Aireccion IP del equipo donde esta el AE
    int    localPort        // Puerto donde escucha el proceso del AE
    
    String remoteAETitle    // Nombre del application entity remoto
    String remoteDomain     // Dominio del equipo donde esta el AE remoto
    String remoteIP         // Aireccion IP del equipo donde esta el AE remoto
    int    remotePort       // Puerto donde escucha el proceso del AE remoto
    int    remoteWADOPort   // Puerto en el que escucha el manejador WADO remoto
    String remoteWADOPath   // Path de la url, ip:port/path
    
	 // HL7 Service should be published on the same remoteIP but using this port
	 int hl7ServicePort
	 
    String description // Descripcion de texto libre para multiuso.
    
    boolean active = true   // Se pone el false si se intenta conectar pero no esta accesible
    int badConnectionCount = 0 // Se va aumentando si no se puede conectar
    
    static constraints = {
        localAETitle(nullable:false, blank:false)
        localIP(nullable:false, blank:false, maxLength:16)
        localPort(nullable:false)
        
        remoteAETitle(nullable:false, blank:false)
        remoteIP(nullable:false, blank:false, maxLength:16)
        remotePort(nullable:false)
        remoteWADOPort(nullable:false)
        remoteWADOPath(nullable:true, blank:true)

        // Valid remote host
        remoteDomain validator: { host, obj ->
        
          def ip
          try {
            ip = InetAddress.getByName(host)
          }
          catch (UnknownHostException e) { // Host cant be reached
            return "host_cant_be_reached"
          }
          
          if(obj.remoteIP != ip.getHostAddress()) return "host_ip_doesnt_match_remoteIP"
        }
    }
}
