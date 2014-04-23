
class ReportController {

	/* */
	def create(String object_uid, String sr_text) {
		
		if (!object_uid)
		{
			render(text: '{"status":"error","message":"Object UID text is empty"}', contentType: "application/json", encoding: "UTF-8")
			return
		}
		
		if (!sr_text)
		{
			render(text: '{"status":"error", "message":"Report text is empty"}', contentType: "application/json", encoding: "UTF-8")
			return
		}
		
		render(text: '{"message":"TODO create SR", "status":"ok"}', contentType: "application/json", encoding: "UTF-8")
	   return
	}
}
