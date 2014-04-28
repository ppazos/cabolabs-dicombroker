
class ReportController {

	/* */
	def create(String study_uid, String series_uid, String object_uid, String sr_text) {
		
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
		String msg = """MSH|^~\\&|MESA_RPT_MGR|EAST_RADIOLOGY|REPOSITORY|XYZ|||ORU^R01|${randomInt()}|P|2.3.1\r
PID|1||${res.patientId}||${res.patientName}|||||||||||||123456444\r
OBR|1|A601Z^MESA_ORDPLC|B601Z^MESA_ORDFIL|B601Z^MESA_ORDFIL^test|||20010501141500|||||||||||||||20010109100821|||F|||||||||||\r
OBX|1|HD|^Study Instance UID||1.3.51.0.7.633918642.633920010109.6339100821||||||F\r
OBX|2|HD|^Series Instance UID||1.3.51.0.7.11111.22222.33333||||||F\r
OBX|3|HD|^SOP Instance UID||1.3.51.0.7.11111.22222.33333.5555||||||F\r
OBX|4|HD|^SR Instance UID||1.3.51.0.7.11111.22222.33333.4444||||||F\r
OBX|5|TX|^SR Text||History todo||||||F\r
OBX|6|TX|^SR Text||Findings ${sr_text}||||||F\r
OBX|7|TX|^SR Text||Conclusions cccccccccccccccccc||||||F\r"""
		
      println msg
		
		// TODO: conexión y envío al PACS (hacer con HAPI!)
		
		render(text: '{"message":"TODO create SR", "status":"ok"}', contentType: "application/json", encoding: "UTF-8")
	   return
	}
	
	// FIXME: esto lo hace HAPI
	static Random randomGenerator = new Random()
	private randomInt()
	{
		return randomGenerator.nextInt(1000000);
	}
}
