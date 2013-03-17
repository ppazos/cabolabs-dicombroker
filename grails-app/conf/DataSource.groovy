dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			//dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			//url = "jdbc:hsqldb:mem:devDB"
			
			pooling = true
	        driverClassName = "com.mysql.jdbc.Driver"
	        url = "jdbc:mysql://localhost:3306/dicom-broker?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
	        dbCreate = "create-drop"
	        username = "root"
	        password = ""
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:prodDb;shutdown=true"
		}
	}
}