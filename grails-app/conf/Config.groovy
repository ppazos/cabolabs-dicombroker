// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'security.User'
grails.plugin.springsecurity.authority.className = 'security.Role'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'security.UserRole'

import grails.plugin.springsecurity.SecurityConfigType
grails.plugin.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugin.springsecurity.interceptUrlMap = [
 '/login/index': ['permitAll'],
 '/login/auth': ['permitAll'],

 '/dashboard/**':  ['ROLE_GODLIKE','ROLE_ADMIN'],

 '/user/**':             ['ROLE_GODLIKE','ROLE_ADMIN'],
 '/role/**':             ['ROLE_GODLIKE','ROLE_ADMIN'],

 '/registrationCode/**':  ['ROLE_GODLIKE','ROLE_ADMIN'],
 '/securityInfo/**':      ['ROLE_GODLIKE','ROLE_ADMIN'],

 '/aeRegistry/**':        ['ROLE_GODLIKE','ROLE_DOCTOR'],
 '/studySearchResult/**': ['ROLE_GODLIKE','ROLE_DOCTOR'],
 '/destinationConfig/**': ['ROLE_GODLIKE','ROLE_ADMIN'],
 '/log/**':               ['ROLE_GODLIKE','ROLE_ADMIN']
]

grails.plugin.springsecurity.successHandler.alwaysUseDefault = true
grails.plugin.springsecurity.successHandler.alwaysUseDefaultTargetUrl = true
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/dashboard"

grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.onInteractiveAuthenticationSuccessEvent = { e, appCtx ->
  // println "============ interactive authentication success ========"
}

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        //grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages', //  GSP
	       'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
	       'org.codehaus.groovy.grails.web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
	       'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	       'org.springframework',
	       'org.hibernate'

    warn   'org.mortbay.log'
}

grails {
  mail {
    host = "smtp.gmail.com"
    port = 465
    username = "test@tarmacit.com"
    password = "tarmactest"
    props = ["mail.smtp.auth":"true",
             "mail.smtp.socketFactory.port":"465",
             "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
             "mail.smtp.socketFactory.fallback":"false"
            ]
  }
}
