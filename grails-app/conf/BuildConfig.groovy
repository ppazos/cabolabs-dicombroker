grails.project.dependency.resolution = {
    // http://jira.grails.org/browse/GRAILS-6230
    // http://stackoverflow.com/questions/6029737/grails-maven-dependency-resolution
	// http://stackoverflow.com/questions/1867064/grails-and-local-maven-dependencies
    inherits "global" // inherit Grails' default dependencies
	repositories {
        //mavenRepo 'http://myhost/nexus/content/groups/public' // local repo
        //grailsRepo "http://plugins.grails.org/"
		
		//grailsPlugins()
		//grailsHome()
		//mavenCentral()
    }
    // http://grails.org/doc/1.3.7/guide/3.%20Configuration.html#3.7 Dependency Resolution
    dependencies {
	    runtime 'net.sf.ehcache:ehcache:1.7.1'
        ivySettings.setVariable("ivy.checksums", "")
    }
	// http://stackoverflow.com/questions/9119768/intellij-idea-11-0-2-fails-to-resolve-dependencies-on-grails-2-0-0
	plugins {
        runtime ":hibernate:$grailsVersion"
        build ":tomcat:$grailsVersion"
    }
}