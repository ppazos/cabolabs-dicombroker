
import aei.AeRegistry
import aei.EmailDestinationConfig

class BootStrap {

     def init = { servletContext ->

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

         // PACS
         AeRegistry pacs1 = new AeRegistry(
                                "remoteAETitle":"DCM4CHEE",
                                "remoteIP": "192.168.1.105",
                                "remotePort":11112,
                                "remoteWADOPort":8080,
                                "remoteWADOPath":"wado", // http://192.168.118.16:8080/wado?requestType....
                                "localAETitle":"QRSCUCHE",
                                //"localIP":"192.168.118.166",
                                "localIP":"10.0.2.15",
                                "localPort":44445,
                                "description":"PACS Virtual")
         
         if (!pacs1.save()) println "PACS: " + pacs1.errors
         
        /* este pacs ya no existe
         AeRegistry pacs2 = new AeRegistry(
            "remoteAETitle":"DCM4CHEE",
            "remoteIP":"46.38.162.152",
            "remotePort":11112,
            "remoteWADOPort":8080,
            "remoteWADOPath":"wado", // http://192.168.118.16:8080/wado?requestType....
            "localAETitle":"QRSCUCHE",
            "localIP":"192.168.1.101",
            "localPort":44445,
            "description":"PACS ZorgGemak")

         if (!pacs2.save()) println "PACS: " + pacs2.errors
         */
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
