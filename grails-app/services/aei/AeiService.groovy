/**
 * 
 */
package aei


import pdg2009.QR
import org.apache.commons.cli.CommandLine
import org.dcm4che2.data.*

/*
 * Preguntas y respuestas>
 * private static final int[] PATIENT_RETURN_KEYS = {
        Tag.PatientName,
        Tag.PatientID,
        Tag.PatientBirthDate,
        Tag.PatientSex,
        Tag.NumberOfPatientRelatedStudies,
        Tag.NumberOfPatientRelatedSeries,
        Tag.NumberOfPatientRelatedInstances };

    private static final int[] PATIENT_MATCHING_KEYS = { 
        Tag.PatientName,
        Tag.PatientID,
        Tag.IssuerOfPatientID,
        Tag.PatientBirthDate,
        Tag.PatientSex };

    private static final int[] STUDY_RETURN_KEYS = {
        Tag.StudyDate,
        Tag.StudyTime,
        Tag.AccessionNumber,
        Tag.StudyID,
        Tag.StudyInstanceUID,
        Tag.NumberOfStudyRelatedSeries,
        Tag.NumberOfStudyRelatedInstances };

    private static final int[] STUDY_MATCHING_KEYS = {
        Tag.StudyDate,
        Tag.StudyTime,
        Tag.AccessionNumber,
        Tag.ModalitiesInStudy,
        Tag.ReferringPhysicianName,
        Tag.StudyID,
        Tag.StudyInstanceUID };

    private static final int[] PATIENT_STUDY_MATCHING_KEYS = {
        Tag.StudyDate,
        Tag.StudyTime,
        Tag.AccessionNumber,
        Tag.ModalitiesInStudy,
        Tag.ReferringPhysicianName,
        Tag.PatientName,
        Tag.PatientID,
        Tag.IssuerOfPatientID,
        Tag.PatientBirthDate,
        Tag.PatientSex,
        Tag.StudyID,
        Tag.StudyInstanceUID };

    private static final int[] SERIES_RETURN_KEYS = {
        Tag.Modality,
        Tag.SeriesNumber,
        Tag.SeriesInstanceUID,
        Tag.NumberOfSeriesRelatedInstances };

    private static final int[] SERIES_MATCHING_KEYS = {
        Tag.Modality,
        Tag.SeriesNumber,
        Tag.SeriesInstanceUID,
        Tag.RequestAttributesSequence
    };

    private static final int[] INSTANCE_RETURN_KEYS = {
        Tag.InstanceNumber,
        Tag.SOPClassUID,
        Tag.SOPInstanceUID, };
 * 
 */

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class AeiService {

    // Busquedas>
    // 1. Por paciente, quiero los estudios, cualquiera en un rango de fechas.
    //    Por nombre de paciente, o por identificador de paciente.
    // 2. Por estudios (uso los ids obtenidos antes), pido por modalidad particular.
    //    Quiero la cantidad de imagenes de cada estudio.
    // 3. Por imagenes: uso el id de un estudio particular.
    //    Quiero todos los ids de las imagenes.
    
    // Como pedir imagenes>
    // - Si el estudio tiene menos de 11 imagenes, quiero todos los ids de las imagenes.
    // - Si el estudio tiene 11 o mas imagenes, pide 10 normalmente distribuidas (si son 101 imagenes pide las imagenes: 0, 10, 20, 30, 40, .. 100 ) 
    
    private boolean isPortAvailable(int port)
    {
        try
        {
            ServerSocket srv = new ServerSocket(port);
            srv.close();
            srv = null;
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }
    
    // Busca un puerto libre
    private int portNumber()
    {
        boolean encontrado = false
        int port = new Double((Math.random()*65536)).intValue()
        
        println "Port Number: " + port
        while ( !isPortAvailable(port) )
        {
            port = new Double((Math.random()*65536)).intValue()
            println "Port Number: " + port
        }
        
        return port
    }
    
    //def sendDICOMQuery()
    def sendDICOMQuery1( AeRegistry reg,
                        String name1, String lastname1,
                        String patientID,
                        Integer fromY, Integer fromM, Integer fromD,
                        Integer toY, Integer toM, Integer toD )
    {
        println "sendDICOMQuery1"
        
        //ArrayList<String> modalitiesInStudy = new ArrayList<String>();
        //modalitiesInStudy.add("CT");
        
        //ArrayList<String> additionalReturnKeys = new ArrayList<String>();
        //additionalReturnKeys.add( QR.patientNameTag );
                
        CommandLine cl = QR.make_patient_studies_query (
                           reg.localAETitle, portNumber(), //reg.localPort,
                           reg.remoteAETitle, reg.remoteIP, reg.remotePort,
                           name1, lastname1,
                           patientID,
                           fromY, fromM, fromD,
                           toY, toM, toD
                         )
        
        // Quiero que me devuelva los resultados en una forma simple de acceder>
        // List<List<Map>> donde el Map es un par key->value
        //
        List<DicomObject> result = QR.send_query( cl )
        
        // Retorna una list de mapa: TagName -> Value
        def ret = []
        
        for (DicomObject o : result)
        {
            def map = [:]
            //println "  <objeto>"
            
            //System.out.println( o.toString() ); // Imprime todo el objeto con sus contenidos.
            
            // Configuracion de tags que quiero
            //Map<String,Integer> tags = new HashMap<String,Integer>();
            //tags.put("Patient Name TAG", Integer.parseInt( QR.patientNameTag,16));
            //tags.put("Study Instance UID TAG", Integer.parseInt( QR.studyInstanceUIDTag,16));
            //tags.put("Series Instance UID TAG", Integer.parseInt( QR.seriesInstanceUIDTag,16));
            //tags.put("SOP Instance UID TAG", Integer.parseInt( QR.sopInstanceUIDTag,16));
            
            if ( o instanceof BasicDicomObject )
            {
                //Iterator<String> itags = tags.keySet().iterator();
                Iterator<DicomElement> iter = o.iterator()
                //String tagname
                DicomElement elem
                
                while (iter.hasNext())
                {
                    elem = iter.next()
                    
                    // tag name -> value
                    //map[ o.nameOf(elem.tag()) ] = new String( elem.getBytes() ).trim()
                    
                    // tag -> value
                    map[ Integer.toHexString(elem.tag()) ] = new String( elem.getBytes() ).trim()
                    
                    /*
                    System.out.print( "<element>\n"   +
                            "\t<value_rep>" + elem.vr() + "</value_rep>\n" +
                            "\t<name>" + o.nameOf( elem.tag() ) + "</name>\n" +
                            "\t<tag>"       + Integer.toHexString( elem.tag() ) +"</tag>\n" +
                            "\t<value>"     + new String( elem.getBytes() ).trim() +"</value>\n" +
                            "</element>\n" );
                    */

                }
                
                ret << map
                
                /*
                while (itags.hasNext())
                {
                    tagname = itags.next();

                    elem = ((BasicDicomObject)o).get( tags.get(tagname) );
                    
                    System.out.println( "    " + tagname + ": " + elem );
                }
                */
            }
            
            //println "  </objeto>"
        }
   
        return ret
 
    } // sendDICOMQuery1
    
    // Busca estudios de un paciente
	/**
	 * FIXME: no se estan usando name1 y lastname1 para la consulta a QR.
	 * @param reg
	 * @param name1
	 * @param lastname1
	 * @param patientID
	 * @param studyID
	 * @param fromY
	 * @param fromM
	 * @param fromD
	 * @param toY
	 * @param toM
	 * @param toD
	 * @return
	 */
    def sendDICOMQuery2( AeRegistry reg,
            String name1, String lastname1,
            String patientID, String studyID,
            Integer fromY, Integer fromM, Integer fromD,
            Integer toY, Integer toM, Integer toD )
    {
        println "sendDICOMQuery2"
        
        
        // FIXME: quiero poder hacer query por name
        CommandLine cl = QR.make_studies_query( 
                            reg.localAETitle, portNumber(), //reg.localPort, 
                            reg.remoteAETitle, reg.remoteIP, reg.remotePort,
                            patientID, studyID,
                            fromY, fromM, fromD,
                            toY, toM, toD)
        
        // Quiero que me devuelva los resultados en una forma simple de acceder>
        // List<List<Map>> donde el Map es un par key->value
        //
        List<DicomObject> result = QR.send_query( cl )
        
        // Retorna una list de mapa: TagName -> Value
        def ret = []
        
        for (DicomObject o : result)
        {
            def map = [:]
            if ( o instanceof BasicDicomObject )
            {
                Iterator<DicomElement> iter = o.iterator()
                DicomElement elem
                
                while (iter.hasNext())
                {
                    elem = iter.next()
                    
                    //println "TAG: " + elem.tag()
                    
                    // tag name -> value
                    //map[ o.nameOf(elem.tag()) ] = new String( elem.getBytes() ).trim()
                    map[ Integer.toHexString(elem.tag()) ] = new String( elem.getBytes() ).trim()
                }
                ret << map
            }
        }
        
        return ret
    
    } // sendDICOMQuery2
    
    
    // Series
    def sendDICOMQuery3( AeRegistry reg,
                         String studyID,
                         String studyUID,
                         String studyDate ) // studyDate: aaaammdd
    {
        println "sendDICOMQuery3:"
        println " - studyID: " + studyID
        println " - studyUID: " + studyUID
        println " - studyDate: " + studyDate
        println "- - - - - - - - - - - - - - - - -"
        
        CommandLine cl = QR.make_study_series_query( 
                            reg.localAETitle, portNumber(), //reg.localPort, 
                            reg.remoteAETitle, reg.remoteIP, reg.remotePort,
                            studyID, studyUID,
                            studyDate,
                            null)
                            
//        CommandLine make_study_series_query( 
//            String localAE, Integer localPort, 
//            String remoteAE, String remoteIP, Integer remotePort,
//            String studyId, String studyUID,
//            String modality )
        
        // Quiero que me devuelva los resultados en una forma simple de acceder>
        // List<List<Map>> donde el Map es un par key->value
        //
        List<DicomObject> result = QR.send_query( cl )
        
        println "Resultado DICOM QUERY 3"
        println result
        
        // Retorna una list de mapa: TagName -> Value
        def ret = []
        
        for (DicomObject o : result)
        {
            def map = [:]
            if ( o instanceof BasicDicomObject )
            {
                Iterator<DicomElement> iter = o.iterator()
                DicomElement elem
                
                while (iter.hasNext())
                {
                    elem = iter.next()
                    
                    //println "TAG: " + elem.tag()
                    
                    //println "=== CLASS del elem: " + elem.getClass()
                    
                    // tag name -> value
                    /*
                    if ( elem instanceof SimpleDicomElement ) // la que soporta getBytes()
                    {
                        map[ Integer.toHexString(elem.tag()) ] = new String( elem.getBytes() ).trim()
                    }
                    else if ( elem instanceof SequenceDicomElement )
                    {
                        println "=== Tiene "+ elem.countItems() +" items"
                        for (int i=0; i<elem.countItems(); i++)
                        {
                            map[ Integer.toHexString(elem.tag()) ] = new String( elem.getFragment(i) ).trim()
                        }
                    }
                    */
                    
                    // No me deja hacer instanceof SimpleDicomElement, por lo que
                    // sabiendo que getBytes sobre SequenceDicomElement y 
                    // getFragment sobre SimpleDicomElement da error, cacheo
                    // la except, y si ocurre, pruebo con el otro metodo.
                    try
                    {
                        map[ Integer.toHexString(elem.tag()) ] = new String( elem.getBytes() ).trim()
                    }
                    catch( Exception e)
                    {
                        println "=== countItems "+ elem.countItems()
						println "=== hasItems: " + elem.hasItems()
						
						println "=== hasFragments: " + elem.hasFragments()
						println "=== hasDicomObjects: " + elem.hasDicomObjects()
						
						println "=== length: " + elem.length()
						println "=== isEmpty: " + elem.isEmpty()
                        println "AeiService EXCEPTION: " + e.getMessage() 
						if (elem.hasFragments())
						{
							for (int i=0; i<elem.countItems(); i++)
							{
								map[ Integer.toHexString(elem.tag()) ] = new String( elem.getFragment(i) ).trim()
							}
						}
						else if (!elem.isEmpty()) // si le pongo elem.hasDicomObjects() me da true y cuando pido el objeto es null.
						{
							// FIXME: como proceso el nuevo DicomObject? tiene que ser recursivo?
							// Este es un caso que el dicomobject no tiene elements, si no que tiene otro dicomobject adentro.
							DicomObject o2 = elem.getDicomObject() // puede tener mas de uno
							println "Objeto del elem: " + o2
							println "Cantidad de elementos del objeto: " + o2.size()
							println "==================================================="
						}
                    }
                } // while iter.hasNext
                
                ret << map
            }
        }
        
        return ret
    
    } // sendDICOMQuery3
    
    // Imagenes
    /**
     * 
     * @param reg
     * @param StudyUID Se agrega para consulta en COMEPA, MACIEL y ClearCanvas Server andan sin este parametro.
     * @param serieUID En Maciel y ClearCanvas Server solo con este parametro anda bien.
     * @return
     */
    def sendDICOMQuery4( AeRegistry reg,
                         String studyUID,
                         String serieUID )
    {
        println "=== sendDICOMQuery4 ==="
        
        CommandLine cl = QR.make_serie_images_query ( 
                            reg.localAETitle, portNumber(), //reg.localPort, 
                            reg.remoteAETitle, reg.remoteIP, reg.remotePort,
                            studyUID, // Se agrega para consulta en COMEPA, MACIEL y ClearCanvas Server andan sin este parametro.
                            serieUID, null )
                            
//       make_serie_images_query( 
//            String localAE, Integer localPort, 
//            String remoteAE, String remoteIP, Integer remotePort,
//            String serieUID, String serieID )
        
        // Quiero que me devuelva los resultados en una forma simple de acceder>
        // List<List<Map>> donde el Map es un par key->value
        //
        List<DicomObject> result = QR.send_query( cl )
        
        println "===== Resultado DICOM QUERY 4 ==="
        println "===== " + result
        
        // Retorna una list de mapa: TagName -> Value
        def ret = []
        
        for (DicomObject o : result)
        {
            def map = [:]
            if ( o instanceof BasicDicomObject )
            {
                Iterator<DicomElement> iter = o.iterator()
                DicomElement elem
                
                while (iter.hasNext())
                {
                    elem = iter.next()
                    
                    //println "TAG: " + elem.tag()
                    
                    //println "=== CLASS del elem: " + elem.getClass()
                    
                    // tag name -> value
                    /*
                    if ( elem instanceof SimpleDicomElement ) // la que soporta getBytes()
                    {
                        map[ Integer.toHexString(elem.tag()) ] = new String( elem.getBytes() ).trim()
                    }
                    else if ( elem instanceof SequenceDicomElement )
                    {
                        println "=== Tiene "+ elem.countItems() +" items"
                        for (int i=0; i<elem.countItems(); i++)
                        {
                            map[ Integer.toHexString(elem.tag()) ] = new String( elem.getFragment(i) ).trim()
                        }
                    }
                    */
                    
                    // No me deja hacer instanceof SimpleDicomElement, por lo que
                    // sabiendo que getBytes sobre SequenceDicomElement y 
                    // getFragment sobre SimpleDicomElement da error, cacheo
                    // la except, y si ocurre, pruebo con el otro metodo.
                    try
                    {
                        map[ Integer.toHexString(elem.tag()) ] = new String( elem.getBytes() ).trim()
                    }
                    catch( Exception e)
                    {
                        println "===== Tiene "+ elem.countItems() +" items"
                        for (int i=0; i<elem.countItems(); i++)
                        {
                            map[ Integer.toHexString(elem.tag()) ] = new String( elem.getFragment(i) ).trim()
                        }
                    }
                } // while iter.hasNext
                
                ret << map
            }
        }
        
        println "=== /sendDICOMQuery4 ==="
        
        return ret
    
    } // sendDICOMQuery4
}
