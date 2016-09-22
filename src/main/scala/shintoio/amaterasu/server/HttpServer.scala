package shintoio.amaterasu.server

import java.io._

import org.eclipse.jetty.server._
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import java.util

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.ErrorHandler
import org.eclipse.jetty.util.IO

import scala.io.Source

//import scala.util.parsing.json.JSONArray
//import scala.util.parsing.json.JSONObject

object HttpServer {

  def main(args: Array[String]): Unit = {
    start()
    //stop()
  }

  var server: Server = null;

  def start() {
    if (server != null) throw new IllegalStateException("started")

    val threadPool = new QueuedThreadPool(Runtime.getRuntime.availableProcessors() * 16)
    threadPool.setName("Jetty")

    server = new Server(threadPool)
    val connector = new ServerConnector(server)
    connector.setPort(Config.apiPort)

    val handler = new ServletContextHandler
    handler.addServlet(new ServletHolder(new Servlet()), "/")
    handler.setErrorHandler(new ErrorHandler())

    server.setHandler(handler)
    server.addConnector(connector)
    server.start()

    println("Server started on port ")
  }

  def stop() {
    if (server == null) throw new IllegalStateException("!started")

    server.stop()
    server.join()
    server = null

    println("Server stopped")
  }

  private class Servlet extends HttpServlet {
    override def doPost(request: HttpServletRequest, response: HttpServletResponse): Unit = doGet(request, response)

    override def doGet(request: HttpServletRequest, response: HttpServletResponse): Unit = {
      val url = request.getRequestURL + (if (request.getQueryString != null) "?" + request.getQueryString else "")
      println("handling URL - " + url)

      try {
        handle(request, response)
        println("finished handling")
      } catch {
        case e: Exception =>
          println("error handling", e)
          response.sendError(500, "" + e)
      }
    }
  }

    def handle(request: HttpServletRequest, response: HttpServletResponse): Unit = {
      val uri = request.getRequestURI
      if (uri.startsWith("/jar")) downloadFile("/Users/devarajan/work/amaterasu_project/temp/HttpServer.jar", response)
      else if (uri.startsWith("/text")) downloadFile("/Users/devarajan/work/amaterasu_project/temp/fileserve.txt", response)
      /*else if (uri.startsWith("/jre/") && Config.jre != null) downloadFile(Config.jre, response)
      else if (uri.startsWith("/api/broker")) handleBrokerApi(request, response)
      else if (uri.startsWith("/api/topic")) handleTopicApi(request, response)
      else if (uri.startsWith("/api/partition")) handlePartitionApi(request, response)
      else if (uri.startsWith("/health")) handleHealth(response)
      else if (uri.startsWith("/quitquitquit")) handleQuit(request, response)
      else if (uri.startsWith("/abortabortabort")) handleAbort(request, response)*/
      else response.sendError(404, "URL not found")
    }

    def downloadFile(file: String, response: HttpServletResponse): Unit = {
      response.setContentType("application/zip")
      response.setHeader("Content-Length", "" + file.length())
      response.setHeader("Content-Disposition", "attachment; filename=\"" + file + "\"")
      IO.copy(new FileInputStream(file), response.getOutputStream)
    }

    def handleHealth(response: HttpServletResponse): Unit = {
      response.setContentType("text/plain; charset=utf-8")
      response.getWriter.println("ok")
    }
}