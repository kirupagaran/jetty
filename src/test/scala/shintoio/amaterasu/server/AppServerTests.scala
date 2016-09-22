package shintoio.amaterasu.server

import org.scalatest.{FlatSpec, Matchers}
/**
  * Created by kirupa on 21/09/16.
  */
class AppServerTests extends FlatSpec with Matchers{
  "HttpServer " should "produce a response for the request" in{
    HttpServer.start()
  }
}
