/**
 *
 */
package aei

import aei.AeiService
//import aei.PruebaService

import java.text.DateFormat

import com.thoughtworks.xstream.XStream

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class AeRegistryController {

   def scaffold = aei.AeRegistry

   def aeiService
   //def pruebaService

   def list() {
      def regs = AeRegistry.list( params )
      [list: regs]
   }

   def edit(AeRegistry ae)
   {
      [ae: ae]
   }

   def update(AeRegistry ae)
   {
      ae.properties = params
      ae.save()
      redirect action: 'list'
   }

    /**
     * Para hacer un Query/Retrieve.
     */
    def qr() {

        if (!params.id)
        {
            flash.message = "atributo id obligatorio"
            redirect(action:list)
            return
        }

        def reg = AeRegistry.get( params.id )

        if (!reg)
        {
            flash.message = "no existe el registry pair con id " + params.id
            redirect(action:list)
            return
        }

        if (params.doit)
        {
           // println params

            // Si fuera un campo de una clase de dominio, la podria bindear son .properties
            //def studyDateData = params.keySet().findAll{ it.startsWith("studyDate") }

           println params

           def resultList = []

           if (!params.level)
           {
               // que no me hagan una consulta generica
               if ( !params.name1 && !params.name3 && !params.patientId)
               {
                  println "Especifique algun dato para realizar la busqueda"
                  flash.message = "Especifique algun dato marcado con * para realizar la busqueda"
                  return [reg: reg]
               }

               // Ok fecha (es para la busqueda a nivel de paciente)

               println "PARAMS: " + params

               try {
                  resultList = aeiService.sendDICOMQuery1( reg,
                          ((params.name1) ? params.name1 : null), // nombre 1
                          ((params.name3) ? params.name3 : null), // apellido 1
                          ((params.patientId) ? params.patientId : null),
                          params.studyDateFrom_year  as Integer,
                          params.studyDateFrom_month as Integer,
                          params.studyDateFrom_day   as Integer,
                          params.studyDateTo_year    as Integer,
                          params.studyDateTo_month   as Integer,
                          params.studyDateTo_day     as Integer )

//                  resultList = aeiService.sendDICOMQuery2( reg,
//                      ((params.name1) ? params.name1 : null), // nombre 1
//                      ((params.name3) ? params.name3 : null),
//                      ((params.patientId) ? params.patientId : null),
//                      null, //String studyID, viene en la busqueda sendDICOMQuery1
//                      studyDateFrom, studyDateTo )
              } catch (Exception e) {
                  flash.message = e.message
              }

              XStream xstream = new XStream()
              println "================================================================="
              println xstream.toXML(resultList)
              println "=================================================================\n\n"

                // Respuesta: necesito tag->valor
                // Con esta respuesta puedo preguntar a make_studies_query por patientID
                /*
                    [ Specific Character Set => ISO_IR 100
                    Query/Retrieve Level => PATIENT
                    Retrieve AE Title => SERVERAE
                    Patient's Name => MISTER^CT
                    Patient ID => 2178309
                    Number of Patient Related Studies => 1
                    Number of Patient Related Series => 2
                    Number of Patient Related Instances => 111 ]
                 */

              return [reg: reg,
                      resultList: resultList,
                      studyDateFrom: ((params.studyDateFrom_year) ?
                                       new Date( (params.studyDateFrom_year  as Integer) - 1900,
                                                 (params.studyDateFrom_month as Integer) - 1,
                                                  params.studyDateFrom_day   as Integer) : null),
                      studyDateTo: ((params.studyDateTo_year) ?
                                     new Date( (params.studyDateTo_year as Integer) - 1900,
                                               (params.studyDateTo_month as Integer) - 1,
                                                params.studyDateTo_day as Integer) : null)]
           } // !level
           else if (params.level == "ST") // busco por estudio
           {
              // in: patientId
              // in: studyDateFrom
              // in: studyDateTo
              println "PARAMS: " + params

              def studyDateFrom = DateFormat.getDateInstance().parse(params.studyDateFrom )
              def studyDateTo   = DateFormat.getDateInstance().parse(params.studyDateTo )

              println "studyDateFrom: " + studyDateFrom + " (" + studyDateFrom.year + ","+ studyDateFrom.month + ")"
              println "studyDateTo: " + studyDateTo

              try {
                 resultList = aeiService.sendDICOMQuery2(
                                reg,
                                null, //String name1
                                null, //String lastname1
                                ((params.patientId) ? params.patientId : null),
                                null, //String studyID
                                studyDateFrom.year+1900, studyDateFrom.month+1, studyDateFrom.date,
                                studyDateTo.year+1900, studyDateTo.month+1, studyDateTo.date)
              } catch (Exception e) {
                  flash.message = e.message
              }

              return [reg: reg,
                      resultList: resultList,
                      studyDateFrom: ((studyDateFrom) ? studyDateFrom : null),
                      studyDateTo: ((studyDateTo) ? studyDateTo : null)]
           }
           else if (params.level == "SE") // busco por series
           {
              // in: studyUID
              println "PARAMS: " + params

              // TODO: corregir para tirar mensaje de error amigable
              if (!params.studyUID) throw new Exception("study UID es olbigatorio para buscar por series")

              try {
                 resultList = aeiService.sendDICOMQuery3(
                                reg,
                                null, //params.studyID,
                                params.studyUID,
                                null //params.studyDate
                              )
                 //println "======================="
                 //println "RESULT LIST: " + resultList
                 //println "======================="
              } catch (Exception e) {
                  flash.message = e.message
                  println "======================="
                  println "EXCEPT: " + e.message
                  println "======================="
              }

              return [reg: reg, resultList: resultList]
           }
           else if (params.level == "IM")
           {
               // in: serieUID
               println "PARAMS: " + params

               // TODO: corregir para tirar mensaje de error amigable
               if (!params.serieUID) throw new Exception("serieUID es olbigatorio para buscar por series")

               try {
                  resultList = aeiService.sendDICOMQuery4(
                                 reg,
                                 null, // StudyUID
                                 params.serieUID
                               )
                  //println "======================="
                  //println "RESULT LIST: " + resultList
                  //println "======================="
               } catch (Exception e) {
                   flash.message = e.message
                   println "======================="
                   println "EXCEPT: " + e.message
                   println "======================="
               }

               return [reg: reg, resultList: resultList]
           }
        } // doit

        return [reg: reg]

    } // qr action
}
