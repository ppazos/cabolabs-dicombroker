package aei

class SeriesSearchResult {
    
    static belongsTo = StudySearchResult
    
    // 80060
    String modality
    
    // 8103e
    String description
    
    // 200011, numero de serie en el estudio
    String number
    
    // 201209, cantidad de elementos en la serie
    String elements
    
    // 20000d, no viene en la consulta a nivel de estudio en CCServer, pero si a nivel de serie, lo meto en la serie.
    String studyUID
    
    // 20000e
    String serieUID
    
    static constraints = {
        modality(nullable:true)
        description(nullable:true)
        number(nullable:true)
        elements(nullable:true)
    }
}
