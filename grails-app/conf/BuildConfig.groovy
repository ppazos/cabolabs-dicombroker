grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.reload.enabled = true

forkConfig = [maxMemory: 1024, minMemory: 64, debug: false, maxPerm: 512]
grails.project.fork = [
   test: forkConfig, // configure settings for the test-app JVM
   run: forkConfig, // configure settings for the run-app JVM
   war: forkConfig, // configure settings for the run-war JVM
   console: forkConfig // configure settings for the Swing console JVM
]

grails {
   tomcat {
       jvmArgs = ["-Duser.timezone=UTC", "-Dserver.port=8090"]
   }
}


//grails.project.war.file = "target/${appName}-${appVersion}.war"

// include XSDs and XSLTs in the war
grails.war.resources = { stagingDir, args ->
   copy(todir: "${stagingDir}/xsd") {
       fileset(dir: "xsd", includes: "*.*")
   }
}

grails.server.port.http = 8099

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
   // http://jira.grails.org/browse/GRAILS-6230
   // http://stackoverflow.com/questions/6029737/grails-maven-dependency-resolution
   // http://stackoverflow.com/questions/1867064/grails-and-local-maven-dependencies
    inherits("global") {
    }

    log "error"
    checksums false

    repositories {
      inherits true // Whether to inherit repository definitions from plugins

      //mavenRepo 'http://myhost/nexus/content/groups/public' // local repo
      //grailsRepo "http://plugins.grails.org/"
		//mavenRepo "http://repo.grails.org/grails/repo/"
      grailsPlugins()
      grailsHome()
      grailsCentral()

      mavenLocal()
      mavenCentral()

      mavenRepo "http://repo.spring.io/milestone/"
    }
   // http://grails.org/doc/1.3.7/guide/3.%20Configuration.html#3.7 Dependency Resolution
   //dependency.resolution {
      //runtime 'net.sf.ehcache:ehcache:1.7.1'
      //ivySettings.setVariable("ivy.checksums", "") -> changed for 'checksums false' when upgrade to grails 2.2.1
   //}
   // http://stackoverflow.com/questions/9119768/intellij-idea-11-0-2-fails-to-resolve-dependencies-on-grails-2-0-0
   plugins {
      //runtime ":hibernate:$grailsVersion"
      runtime ':hibernate4:4.3.10'

      runtime ':jquery:1.11.1' //":jquery:1.8.3"
      runtime ":jquery-ui:1.8.24"
      runtime ":famfamfam:1.0"
      /*runtime ":resources:1.2.14"*/ //":resources:1.1.6" // new asset pipeline?

      build ":tomcat:7.0.55.2" //":tomcat:$grailsVersion"

      // plugins for the compile step
      compile ':scaffolding:2.1.2'
      compile ':cache:1.1.8'
      compile ':asset-pipeline:2.5.7'

      compile ":mail:1.0.7"
      compile ":spring-security-core:2.0.0"//":spring-security-core:1.2.7.3"
      compile ":spring-security-ui:1.0-RC3" //":spring-security-ui:0.2"
   }

	dependencies {
		runtime('ca.uhn.hapi:hapi-base:2.2') {

		}
		runtime('ca.uhn.hapi:hapi-structures-v231:2.2') {

	   }
	}
}
