
// HAPI
import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.HL7Exception
import ca.uhn.hl7v2.HapiContext
import ca.uhn.hl7v2.app.Connection
import ca.uhn.hl7v2.app.ConnectionListener
import ca.uhn.hl7v2.app.HL7Service
import ca.uhn.hl7v2.app.Initiator
import ca.uhn.hl7v2.model.Message
import ca.uhn.hl7v2.parser.Parser
import ca.uhn.hl7v2.protocol.ReceivingApplication
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler

import aei.AeRegistry

class ReportController {

   /* */
   def create(String study_uid, String series_uid, String object_uid, String sr_text, int pacs_id) {
      
      if (!study_uid)
      {
         render(text: '{"status":"error","message":"Study UID is empty"}', contentType: "application/json", encoding: "UTF-8")
         return
      }
      if (!series_uid)
      {
         render(text: '{"status":"error","message":"Series UID is empty"}', contentType: "application/json", encoding: "UTF-8")
         return
      }
      if (!object_uid)
      {
         render(text: '{"status":"error","message":"Object UID is empty"}', contentType: "application/json", encoding: "UTF-8")
         return
      }
      if (!sr_text)
      {
         render(text: '{"status":"error", "message":"Report text is empty"}', contentType: "application/json", encoding: "UTF-8")
         return
      }
      
      def res = aei.StudySearchResult.findByStudyUID( study_uid )
      
      
      // TODO: crear el mensaje en un job y schedulear el envío aquí
      // TODO: crear el mensaje utilizando HAPI
      
      // Escape caracters en sr_text
      sr_text = sr_text.replaceAll("\r\n", "\\\\.br\\\\") // Windows Enter
      sr_text = sr_text.replaceAll("\n", "\\\\.br\\\\") // Unix Enter
      
      println sr_text
      
      // TODO: agregar history y conclusions como textareas en al gui (?)
      
      String msg = "MSH|^~\\&|MESA_RPT_MGR|EAST_RADIOLOGY|REPOSITORY|XYZ|||ORU^R01|${randomInt()}|P|2.3.1\r" +
                   "PID|1||${res.patientId}||${res.patientName}|||||||||||||123456444\r" +
                  // "PID|1||5555555||MISTER^CR|||||||||||||123456444\r"+
                   "OBR|1|A601Z^MESA_ORDPLC|B601Z^MESA_ORDFIL|B601Z^MESA_ORDFIL^test|||20010501141500|||||||||||||||20010109100821|||F|||||||||||\r" +
                   "OBX|1|HD|^Study Instance UID||${study_uid}||||||F\r"+
                   "OBX|2|HD|^Series Instance UID||${series_uid}||||||F\r"+
                   "OBX|3|HD|^SOP Instance UID||${object_uid}||||||F\r"+
                   "OBX|4|HD|d^SR Instance UID||1.3.51.0.7.11111.22222.33333.${randomInt()}||||||F\r"+
                   //"OBX|5|TX|e^SR Text||History todo||||||F\r" +
                   "OBX|5|TX|^SR Text||Findings ${sr_text}||||||F" //\r"+
                   //"OBX|7|TX|^SR Text||Conclusions cccccccccccccccccc||||||F"
      
      /*
      String msg = "MSH|^~\\&|HIS|RIH|EKG|EKG|199904140038||ADT^A01|12345|P|2.2\r" +
               "PID|0001|00009874|00001122|A00977|SMITH^JOHN^M|MOM|19581119|F|NOTREAL^LINDA^M|C|564 SPRING ST^^NEEDHAM^MA^02494^US|0002|(818)565-1551|(425)828-3344|E|S|C|0000444444|252-00-4414||||SA|||SA||||NONE|V1|0001|I|D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P\r"+
              "NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION\r"+
               "PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P\r"+
               "PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE\r"+
              "AL1||SEV|001^POLLEN\r"+
               "GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333\r"+
               "IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554";
      */
      println msg
      

      // El PACS al que enviar el SR debe ser el mismo donde esta la imagen
      // que se informa.
      AeRegistry pacs = AeRegistry.get(pacs_id)
      
      
      // Conexión y envío al PACS con HAPI
      // TODO: guardar el envio como log que luego pueda listarse
      
      // server
      //ca.uhn.hl7v2.HapiContext ctx = new ca.uhn.hl7v2.DefaultHapiContext()
      //ca.uhn.hl7v2.app.HL7Service server1 = ctx.newServer(8080, false)
      
      // client
      HapiContext context = new DefaultHapiContext()
      
      
      Connection connection = context.newClient(pacs.remoteIP, pacs.hl7ServicePort, false)
      
      // The initiator is used to transmit unsolicited messages
      Initiator initiator = connection.getInitiator()
      
      Parser p = context.getPipeParser()
      Message msgh = p.parse(msg)
      Message response = initiator.sendAndReceive(msgh)
      
      // TODO: guardar la respuesta como log que luego pueda listarse
      String responseString = p.encode(response)
      println "Received response:\n" + responseString
      // /client
      
      render(text: '{"message":"SR was sent to the PACS", "status":"ok"}', contentType: "application/json", encoding: "UTF-8")
      return
   }
   
   // FIXME: esto lo hace HAPI
   static Random randomGenerator = new Random()
   private randomInt()
   {
      return randomGenerator.nextInt(1000000);
   }
}
