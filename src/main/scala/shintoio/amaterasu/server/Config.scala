package shintoio.amaterasu.server

import java.net.URI

/**
  * Created by kirupa on 21/09/16.
  */
object Config {
  var api: String = null
  val apiPort: Int = 8080
  val bindAddress = "127.0.0.1"

  /*def apiPort: Int = {
    val port = new URI(api).getPort
    if (port == -1) 8080 else port
  }*/
}
