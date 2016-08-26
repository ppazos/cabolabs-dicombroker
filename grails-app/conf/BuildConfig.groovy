grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
   // http://jira.grails.org/browse/GRAILS-6230
   // http://stackoverflow.com/questions/6029737/grails-maven-dependency-resolution
   // http://stackoverflow.com/questions/1867064/grails-and-local-maven-dependencies
    inherits("global") {
    }

    log "error"
    checksums false

    repositories {
      //mavenRepo 'http://myhost/nexus/content/groups/public' // local repo
      //grailsRepo "http://plugins.grails.org/"
		mavenRepo "http://repo.grails.org/grails/repo/"
      grailsPlugins()
      grailsHome()
      grailsCentral()

      mavenLocal()
      mavenCentral()
    }
   // http://grails.org/doc/1.3.7/guide/3.%20Configuration.html#3.7 Dependency Resolution
   //dependency.resolution {
      //runtime 'net.sf.ehcache:ehcache:1.7.1'
      //ivySettings.setVariable("ivy.checksums", "") -> changed for 'checksums false' when upgrade to grails 2.2.1
   //}
   // http://stackoverflow.com/questions/9119768/intellij-idea-11-0-2-fails-to-resolve-dependencies-on-grails-2-0-0
   plugins {
      runtime ":hibernate:$grailsVersion"
      runtime ":jquery:1.8.3"
      runtime ":jquery-ui:1.8.24"
      runtime ":famfamfam:1.0" 
      runtime ":resources:1.1.6"

      build ":tomcat:$grailsVersion"

      compile ":mail:1.0"
      compile ":spring-security-core:1.2.7.3"
      compile ":spring-security-ui:0.2"
   }
	
	dependencies {
		runtime('ca.uhn.hapi:hapi-base:2.2') {
			
		}
		runtime('ca.uhn.hapi:hapi-structures-v231:2.2') {
		
	   }
	}
}
