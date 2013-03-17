
import aei.AeRegistry

class BootStrap {

     def init = { servletContext ->
         
		 /*
         // PACS
         AeRegistry pacs1 = new AeRegistry(
                                "remoteAETitle":"DCM4CHEE",
                                "remoteIP":"192.168.1.107",
                                "remotePort":11112,
                                "remoteWADOPort":8080,
                                "remoteWADOPath":"wado", // http://192.168.118.16:8080/wado?requestType....
                                "localAETitle":"QRSCUCHE",
                                //"localIP":"192.168.118.166",
                                "localIP":"192.168.1.101",
                                "localPort":44445,
                                "description":"PACS Virtual")
         
         if (!pacs1.save()) println "PACS: " + pacs1.errors
         */
         
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
     }
     def destroy = {
     }
} 