
import java.io.{File, ByteArrayInputStream}
import java.nio.file.{Files, Paths}
import javax.imageio.{ImageWriteParam, IIOImage, ImageIO}

import unfiltered.Cycle

//import java.util.Base64
import sun.misc.BASE64Decoder;
import _root_.unfiltered.request.Body
import _root_.unfiltered.request.Path
import unfiltered.response.{Ok, ResponseString}
import unfiltered.filter.Plan
import unfiltered.jetty.SocketPortBinding
import unfiltered.request._


object SimpleHttpServer extends App{
  val server = HttpServer.create(new InetSocketAddress("192.168.1.149", 8080), 0)
  server.createContext("/get_custom", new RootHandler())
  server.setExecutor(null)
  server.start()
  println("------ waiting for Request ------")
}

class RootHandler extends HttpHandler {
  def handle(httpExchange: HttpExchange) {
    val data = httpExchange.getRequestBody
    val imageByte = (new BASE64Decoder()).decodeBuffer(data);
    val bytes = new ByteArrayInputStream(imageByte)
    val image = ImageIO.read(bytes)
    ImageIO.write(image, "png", new File("image.png"))
    println("------ Image receiving complete ------")

    val res = new FeatureExtraction("Image.png");
    val response = Classification_RF.Randomforest();

    httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*")
    httpExchange.sendResponseHeaders(200, response.length())
    val outStream = httpExchange.getResponseBody
    outStream.write(response.getBytes)
    outStream.close()
  }
}