dataSource {
   pooled = true
   driverClassName = "org.h2.Driver"
   username = "sa"
   password = ""
   // logSql = true -> uncomment this to log sql queries to the console
}
hibernate {
   cache.use_second_level_cache=true
   cache.use_query_cache=true
   cache.region.factory_class='net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
   development {
      dataSource {
		   //dbCreate = "create-drop" // one of 'create', 'create-drop','update'
         url = "jdbc:h2:mem:devDb"
			
         pooling = true
         //driverClassName = "com.mysql.jdbc.Driver"
	      //url = "jdbc:mysql://localhost:3306/dicom-broker?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
         dbCreate = "create-drop"
         //username = "root"
         //password = "root"
      }
   }
   test {
      dataSource {
         dbCreate = "update"
         url = "jdbc:h2:mem:testDb"
      }
   }
   production {
      dataSource {
         dbCreate = "update"
         url = "jdbc:h2:file:prodDb;shutdown=true"
      }
   }
}

