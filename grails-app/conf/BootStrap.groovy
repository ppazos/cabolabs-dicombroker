
import aei.AeRegistry
import aei.AppDestinationConfig
import aei.EmailDestinationConfig

import security.User
import security.Role
import security.UserRole

class BootStrap {

  def init = { servletContext ->

    def godlikeRole = Role.findByAuthority('ROLE_GODLIKE') ?: new Role(authority: 'ROLE_GODLIKE').save(failOnError: true)
    def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
    def doctorRole = Role.findByAuthority('ROLE_DOCTOR') ?: new Role(authority: 'ROLE_DOCTOR').save(failOnError: true)

    def godlikeUser = User.findByUsername('godlike') ?: 
          new User(username: 'godlike', password: '1234', enabled: true)
         .save(failOnError: true)
    if (!godlikeUser.authorities.contains(godlikeRole)) { UserRole.create godlikeUser, godlikeRole }

    def adminUser = User.findByUsername('admin') ?: 
          new User(username: 'admin', password: '1234', enabled: true)
         .save(failOnError: true)
    if (!adminUser.authorities.contains(adminRole)) { UserRole.create adminUser, adminRole }

    def doctorUser = User.findByUsername('doctor') ?: 
          new User(username: 'doctor', password: '1234', enabled: true)
         .save(failOnError: true)
    if (!doctorUser.authorities.contains(doctorRole)) { UserRole.create doctorUser, doctorRole }

    /* ============================================================================== */
    EmailDestinationConfig emailDest1 = new EmailDestinationConfig(
                                               "name"      : "Sebastian Sierra",
                                               "sended_to" : "sebastian@tarmacit.com",
                                               "subject"   : "test",
                                               "body"      : "test body"
                                         )
    if (!emailDest1.save()) println "EmailDest: " + emailDest1.errors

    EmailDestinationConfig emailDest2 = new EmailDestinationConfig(
                                               "name"      : "Sebastian Sierra 2",
                                               "sended_to" : "sebastiansier@gmail.com",
                                               "subject"   : "test",
                                               "body"      : "test body"
                                         )
    if (!emailDest2.save()) println "EmailDest: " + emailDest2.errors


    AppDestinationConfig appDest1 = new AppDestinationConfig(
                                               "name"      : "Dicombroker app",
                                               "ip"        : "localhost",
                                               "port"      : 8080,
                                               "path"      : "dicom-broker/studySearchResult/receiveFromApp"
                                         )
    if (!appDest1.save()) println "AppDest: " + appDest1.errors

    // PACS
 /*    AeRegistry pacs1 = new AeRegistry(                           "remoteAETitle":"DCM4CHEE",
                           "remoteDomain": "tarmacit.zapto.org",
                           "remoteIP": "192.168.1.110",
                           "remotePort":11112,
                           "remoteWADOPort":8080,
                           "remoteWADOPath":"wado", // http://192.168.118.16:8080/wado?requestType....
                           "localAETitle":"QRSCUCHE",
                           //"localIP":"192.168.118.166",
                           "localIP":"192.168.1.108",
                           "localPort":44445,
                           "description":"PACS Virtual")
    
    if (!pacs1.save()) println "PACS: " + pacs1.errors
    */
         
    /* este pacs ya no existe */
	 /* WARNING: remotePort tambien debe ser forwaded si esta detras de NAT*/
    AeRegistry pacs2 = new AeRegistry(
        "remoteAETitle":"DCM4CHEE",
		  "remoteDomain": "remote.dcm4chee", // put this in your hosts file if you dont have a domain
        "remoteIP": "192.168.1.111",
        "remotePort": 11112,
        "remoteWADOPort":8080,
		  "hl7ServicePort": 2575,
        "remoteWADOPath":"wado", // http://192.168.118.16:8080/wado?requestType....
        "localAETitle":"QRSCUCHE",
        "localIP":"192.168.1.100",
        "localPort":44445,
        "description":"PACS VM")

    if (!pacs2.save()) println "PACS: " + pacs2.errors
    
    /*         
     AeRegistry pacs3 = new AeRegistry(
        "remoteAETitle":"DCM4CHEE",
        "remoteIP":"pacs.webweaving.org",
        "remotePort":11112,
        "remoteWADOPort":443, // 443 es para HTTPS!
        "remoteWADOPath":"wado", // http://192.168.118.16:8080/wado?requestType....
        "localAETitle":"QRSCUCHE",
        "localIP":"192.168.1.101",
        "localPort":44445,
        "description":"PACS ZorgGemak2")

     if (!pacs3.save()) println "PACS: " + pacs3.errors
    */
  }
  
  def destroy = {
  }
} 
