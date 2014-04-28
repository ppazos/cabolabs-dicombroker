/**
 * 
 */
package aei

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class StudySearchResult {

    // 100010 => MISTER^CT
    String patientName
    
    // 100020 => 2178309
    String patientId
    
    // 100030=Fecha de nacimiento
    String birthDate
    
    // 100040=Sexo
    String sex
    
    // 80020=Fecha del estudio
    String studyDate
    
    // 200010 => 1
    String studyId
    
    // 20000d, no viene en la consulta a nivel de estudio en CCServer, pero si a nivel de serie, lo meto en la serie.
	 // En cambio si viene en DCM4CHEE.
    String studyUID
    
    // 201202 => 2
    int seriesNumber
    
    // 201204 => 111
    int imagesNumber
    
    // 00081030
    String studyDescription
    
    // Desde que PACS se obtuvo el resultado
    AeRegistry source
    
    // 80061
    String modalitiesInStudy
    
    List series = []
    static hasMany = [series : SeriesSearchResult]
    
    static constraints = {
       sex(nullable:true)
       birthDate(nullable:true)
		 studyUID(nullable:true)
       studyDate(nullable:true) // este dato deberia venir siempre del PACS, verificar que se tiene en el QR.
       studyDescription(nullable:true)
    }
    
    static mapping = {
        series cascade: "save-update"
    }
    
}
