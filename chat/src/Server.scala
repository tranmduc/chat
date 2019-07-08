import java.io._
import java.util._
import java.net._
import util.control.Breaks._

object Server {
  var array: Array[ClientHandler] = new Array[ClientHandler](100)
  def main(args: Array[String]): Unit = {
    try{
      val ss: ServerSocket = new ServerSocket(1234)
      var i: Int = 0
      println("Server started...")
      while (true) {

        // Accept the incoming request
        var s: Socket = ss.accept()
        println("Creating a new handler for this client...")
        // Create a new handler object for handling this request.
        val mtch: ClientHandler = new ClientHandler(s, i)
        // Create a new Thread with this object.
        println("Adding this client to active client list")
        // add this client to active clients list
        array(i) = mtch
        // start the thread.
        mtch.start()
        // by any naming scheme
        i += 1
      }
    } catch{
      case e: Exception => println(e)
    }
  }
}

class ClientHandler(var s: Socket, var clientNo: Int) extends Thread {
  var clientIndex: Int = clientNo
  var name: String = "Client " + clientNo
  var isloggedin: Boolean = true
  val dis: DataInputStream = new DataInputStream(s.getInputStream)
  val dos: DataOutputStream = new DataOutputStream(s.getOutputStream)

  override def run(): Unit = {
    try{
      var received: String = ""

      while (received != "logout"){
        received = dis.readUTF()
        println(received)
        // break the string into message and recipient part
        val st: StringTokenizer = new StringTokenizer(received, "#")
        val MsgToSend: String = st.nextToken()
        val recipient: String = st.nextToken()

        breakable{
          for (mc <- Server.array) {
            if(mc.name == recipient && mc.isloggedin == true){
              mc.dos.writeUTF(this.name + ": " + MsgToSend)
              mc.dos.flush()

              var file: String = ""
              if(mc.clientIndex < this.clientIndex){
                file = mc.clientIndex + "-" + this.clientIndex + ".txt"
              }else {
                file = this.clientIndex + "-" + mc.clientIndex + ".txt"
              }

              var fr: FileWriter = new FileWriter(file, true)
              var br: BufferedWriter = new BufferedWriter(fr)
              br.write(this.name + ": " + MsgToSend)
              br.newLine()
              br.close()
              fr.close()
              break
            }
          }
        }
      }
      this.isloggedin = false
      this.dis.close()
      this.dos.close()
      s.close()
    } catch {
        case e: Exception => println(e)
    }
  }
}
