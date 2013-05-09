modules = {
  application {
    resource url:'js/application.js'
  }

  blockUI {
    resource url:'js/jquery.blockUI.js'
    dependsOn 'jquery'
  }
}
