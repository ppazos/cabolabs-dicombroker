/**
 *
 */
package aei

//import hce.core.composition.* // Composition y EventContext
//import hce.HceService
import aei.AeiService
import aei.AeRegistry
import grails.converters.*

//@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.0-RC2' )
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class StudySearchResultController {

    def scaffold = aei.StudySearchResult

    def aeiService


    /**
     * Envia la WADO URL de una imagen a otro servidor.
     */
    def sendWadoUrl() {

       println params

       def text

       //def url = new URL ("http://62.195.116.153/edafm-his/consultation")
       def url = new URL ("http://62.195.116.153/edafm-his/consultation?wado-url="+params.'wado-url')
       def conn
       def code

       //String data = "wado-url="+params.'wado-url' // Si es get, no tiene body...
       //conn.doOutput = true

       // Si es get, no tiene body...
       //Writer wr = new OutputStreamWriter(conn.outputStream)
       //wr.write(data)
       //wr.flush()
       //wr.close()

       try
       {
          conn = url.openConnection()
          conn.setRequestMethod("GET")
          conn.connect()
          code = conn.responseCode
          if (code == 200)
          {
             text = conn.content.text // Leer de una conexion abierta se puede hacer una sola vez.
          }
          else
          {
             text = "Unable to get page from ${url}: code ${conn.responseCode} ${conn.responseMessage}"
          }
       }
       catch (Exception e)
       {
          println e.toString()
          text = 'error: ' + e.toString() + " " + code
       }

       println text

       render text
    }


    def index() {
        redirect(action:'list')
    }

    def list() {

        def studies = StudySearchResult.list(params)
        return [studies: studies]
    }


    def search() {

        // Cuidado!: si patientId es null va a traer todos los estudios de todos los pacientes!
        //def patientId = params.patientId
        println params
        if (!params.patientId && !params.patientName && !params.patientLastname)
        {
           flash.message = 'Please specify a search criteria'
           redirect(action:'list', params:params)
           return
        }

        def pacss = AeRegistry.list() // pacs configurados
        def pacsErrors = [:] // Se usa al final para saber si no se pudo conectar al pacs

        pacss.each { pacs ->

            if (pacs.active)
            {

                try {
                    /* busca a nivel de paciente. es para ver si el paciente esta en el pacs, no para obtener el estudio...
                    def results = aeiService.sendDICOMQuery1( pacs,
                                  ((name?.primerNombre) ? name?.primerNombre : null), // nombre 1
                                  ((name?.primerApellido) ? name?.primerApellido : null), // apellido 1
                                  pid.extension,
                                  null, //params.studyDateFrom_year  as Integer,
                                  null, //params.studyDateFrom_month as Integer,
                                  null, //params.studyDateFrom_day   as Integer,
                                  null, //params.studyDateTo_year    as Integer,
                                  null, //params.studyDateTo_month   as Integer,
                                  null //params.studyDateTo_day     as Integer
                              )
                    */

                    // FIXME: aunque le pase nombre y apellido, no los considera en la query...
                    // busca a nivel de estudio
                    def results = aeiService.sendDICOMQuery2(
                        pacs,
                        params.patientName, // nombre 1, puede ser vacio
                        params.patientLastname, // apellido 1, puede ser vacio
                        params.patientId, // puede ser vacio
                        null, //String studyID,
                        null, //params.studyDateFrom_year  as Integer,
                        null, //params.studyDateFrom_month as Integer,
                        null, //params.studyDateFrom_day   as Integer,
                        null, //params.studyDateTo_year    as Integer,
                        null, //params.studyDateTo_month   as Integer,
                        null //params.studyDateTo_day     as Integer
                    )

						  println "= = = = = = = = = = = = = = = = = ="
						  println results

                    // Marco como que no hubo error para este pacs
                    pacsErrors[pacs] = false

                    if (results.size() > 0)
                    {
                        results.each { result ->

                            println "   resutl: " + result

                            // No guardar un resultado que ya se obtuvo
                            def resExists = StudySearchResult.withCriteria {
                               /* Con esta condicion si la persona tenia dos estudios, solo encontraba uno...
                                  eq('patientId', pself.externalRef.objectId.value)
                                  eq('studyDate', result['80020'])
                                *
                                */
                                //eq('patientId', patientId) // creo que solo con el studyId alcanza
                                eq('studyId', result['200010']) // con studyId alcanza...
                            }

                            if (resExists.size() == 0)
                            {
                                /*
                                 * 80005:ISO_IR 100,
                                 * 80020:20010105,
                                 * 80030:083501,
                                 * 80050:0000000001,
                                 * 80052:STUDY,
                                 * 80054:SERVERAE,
                                 * 80056:ONLINE,
                                 * 80061:CT,
                                 * 100010:MISTER^CT,
                                 * 100020:2178309,
                                 * 100032:,
                                 * 100040:,
                                 * 20000d:1.2.840.113619.2.30.1.1762295590.1623.978668949.886, StudyInstanceUID
                                 * 200010:40933,
                                 * 201200:,
                                 * 201202:,
                                 * 201204:
                                 * ]
                                 */
                                def ssres = new StudySearchResult
                                (
                                    patientName:       result['100010'], // 100010 => MISTER^CT
                                    //patientId:    patientId, //result['100020'], // FIXME: hacer que el id que se guarda sea el del partySelf del episodio, asi tengo vinculados los resutlados al episodio.
                                    patientId:         result['100020'],
                                    birthDate:         result['100030'],
                                    sex:               result['100040'],
                                    studyDate:         result['80020'],
                                    studyId:           result['200010'],
												studyUID:          result['20000d'],
                                    seriesNumber:      result['201206'],
                                    imagesNumber:      result['201208'],
                                    studyDescription:  result['81030'],
                                    modalitiesInStudy: result['80061'],
                                    source:       pacs // Desde que PACS se obtuvo el resultado
                                )


                                // 17/04/2010
                                // =======================================================
                                // ==== NUEVO: busca tambien series por pedido del tutor
                                // ==== de mostrar todas las series al seleccionar una.
                                // =======================================================

                                // TODO: si hay un error en la comunicacion aca, el estudio
                                //       se queda sin series cuando en realidad tiene.

                                println "--------------------------------------------------"
                                println "--------------------------------------------------"
                                println "---- BUSCANDO series para studyID: " + ssres.studyId

                                def resultsSeries = aeiService.sendDICOMQuery3(
                                        pacs, // source
                                        ssres.studyId, // studyId
                                        null, // studyUID
                                        null ) // studyDate

                                resultsSeries.each { resultSerie ->

                                    def seriessr = new SeriesSearchResult
                                    (
                                        modality:    resultSerie['80060'],
                                        description: resultSerie['8103e'],
                                        number:      resultSerie['200011'],
                                        elements:    resultSerie['201209'],
                                        // Si no viene en resultSerie (pasa en COMEPA) lo saco de resultado de estudio que si deberia venir.
                                        studyUID:    ((resultSerie['20000d']) ? resultSerie['20000d'] : result['20000d']),
                                        serieUID:    resultSerie['20000e']
                                    )

                                    ssres.addToSeries( seriessr )

                                    // TODO: salvar seriessr???
                                    //if (!seriessr.save() ....
                                }

                                println "---- FIN buscar series para studyID: " + ssres.studyId
                                println "--------------------------------------------------"
                                println "--------------------------------------------------"

                                // =======================================================
                                // =======================================================

                                if (!ssres.save())
                                {
                                    println "   Search: error al salvar result " + ssres.errors + "---------"
                                    println "   errores en series: "
                                    ssres.series.each{ unaserie ->
                                       println "    - " + unaserie.errors
                                    }
                                    println "Search ---------------------------------------"
                                }
                            }
                            else
                            {
                                println "Search: estudio ya encontrado"
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    println "   Search Exception: " + e.getMessage()
                    println e.printStackTrace()

                    // FIXME: para ver si anda mal el QR deberia tirar la except aca
                    //        pero la catchea adentro...

                    // Hubo error para este pacs
                    pacsErrors[pacs] = true
                }
            } // if pacs.active
        } // foreach pacs

        redirect(action:'list', params:params)
    }


    def studySeries2() {

		// El estudio seleccionado tiene las series adentro.
        def selectedStudy = StudySearchResult.get( params.id )

        return [selectedStudy: selectedStudy]

    } // studySeries2


    def seriesImages2() {

        def selectedStudy = StudySearchResult.get( params.id )
        def selectedSerie = SeriesSearchResult.get( params.serieId )
        def destinations = DestinationConfig.getAll()

        println "=== seriesImages2 ==="
        println "=== selectedStudy: " + selectedStudy
        println "=== selectedSerie: " + selectedSerie
        println ""

        // imagenes y reportes
        def objects = []
        try
        {
			    objects = aeiService.sendDICOMQuery4( selectedStudy.source,
                                                selectedSerie.studyUID, // Se agrega para consulta en COMEPA, MACIEL y ClearCanvas Server andan sin este parametro.
                                                selectedSerie.serieUID )
        }
        catch (Exception e)
        {
          flash.message = "studySearchResult.error.pacsComm"
      		println "======AAAA====="
    			println e.getMessage()
		    	println "======AAAA====="
        }

        return [selectedStudy:  selectedStudy,
                selectedSerie:  selectedSerie,
                objects:        objects,
                reg:            selectedStudy.source,
                destinations:   destinations
        ]

    } // seriesImages2

    // Returns the destination details
    def destDetails() {
      def dest = DestinationConfig.get( params.destId )
      render dest as JSON
    }


    // Sends the wado url through email to the destination selected
    /* =======================================================================================
        Workaround to avoid error when calling the sendEmail() action twice from ajax
        The email is sended inside the controller instead of using the service

        Gmail heuristics makes a reverse dns check if any links are present on the email body
          -> If the link contains an invalid ip or domain gets sended to Spam
    ========================================================================================== */

    def mailService

    def sendEmail() {
      def text = message( code: 'default.email.success' )
      try {
        if(params.dest_send_confirmation == "true" && !AeRegistry.get(params.int('dest_id')).remoteDomain){
          mailService.sendMail {
            to params.dest_email
            subject params.dest_subject
            body( view: '/mail/check_spam' )
          }
        }

        mailService.sendMail {
          to params.dest_email
          subject params.dest_subject
          body params.dest_body
        }
      }
      catch (Exception e)
      {
        println(e.message)
        text = message( code: 'default.email.error')
      }
      render text
    }

    /* ======================================== */
    /* ======================================== */


    // Sends the wado url received through params to the AppDestinationConfig selected
    // using httpBuilder
    def sendToApp() {
      def text = ""
      try {
        def appDestination = AppDestinationConfig.get(params.app_dest_id)
        def http = new HTTPBuilder("http://${appDestination.ip}:${appDestination.port}")

        http.request(GET, JSON) {
          uri.path = "/${appDestination.path}"
          uri.query = [wado_url: params.dest_url]
          response.success = { resp, json ->
              text = message(code: 'default.success')
          }
        }
      }
      catch (Exception e)
      {
        println e.message
        text = message(code: 'default.error')
      }
      render text
    }


    // Dummy action to receive request
    def receiveFromApp() {
      println params
      render params as JSON
    }
}
