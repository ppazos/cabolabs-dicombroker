/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class WADOTagLib {

    /*
     * AeRegistry reg: de donde se obtiene la configuracion del WADO remoto.
     * String studyUid
     * String seriesUid
     * String objectUid
     * String contentType: opcional 
     */
    def wadoUrl = { attrs, body ->
       
        if (!attrs.reg) throw new Exception("reg es obligatorio")
        
        // no pueden ser vacios
        def studyUid = attrs.studyUid
        def seriesUid = attrs.seriesUid
        def objectUid = attrs.objectUid
        
        if(!studyUid) throw new Exception("studyUid es obligatorio")
        if(!seriesUid) throw new Exception("seriesUid es obligatorio")
        if(!objectUid) throw new Exception("objectUid es obligatorio")
        
        // puede venir otra cosa como image/jpeg
        def contentType = "application/dicom"
        if (attrs.contentType) contentType = attrs.contentType
        
        
        def scheme = "http://"
        if (attrs.reg.remoteWADOPort == 443) scheme = "https://"
        
        def url = ""
        def params = "requestType=WADO&"+
                     //"contentType="+ URLEncoder.encode( contentType ) +"&"+ // EN EL MACIEL, EL TIPO POR DEFECTO ES image/jpeg
                     "studyUID="+ URLEncoder.encode( studyUid ) +"&"+
                     "seriesUID="+ URLEncoder.encode( seriesUid ) +"&"+
                     "objectUID="+ URLEncoder.encode( objectUid )
        
        if (attrs.frameNumber) params += "&frameNumber="+ attrs.frameNumber
                     
        
        //url = attrs.reg.remoteIP + ":" + attrs.reg.remoteWADOPort
        url = attrs.reg.remoteDomain ? attrs.reg.remoteDomain : attrs.reg.remoteIP
        url = url + ":" + attrs.reg.remoteWADOPort
        if (attrs.reg.remoteWADOPath) url += "/" + attrs.reg.remoteWADOPath
        
        
        //out << response.encodeURL(scheme + url + "?" + params)
        out << '<a href="'+ scheme + url + "?" + params + '" class="wado_url'+ ((attrs.clazz)?' '+attrs.clazz:'') +
		         '" onclick="return false;" data-object_uid="'+ objectUid +
					'" data-study_uid="'+ studyUid +'" data-series_uid="'+ seriesUid +'" data-pacs_id="'+ attrs.reg.id +'">'
        out << body()
        out << '</a>'
    }
}
