import java.io._
import java.net._
import java.util.Scanner

object Client2 {
  val ServerPort: Int = 1234

  def main(args: Array[String]): Unit = {
    val scn: Scanner = new Scanner(System.in)
    // getting localhost ip
    val ip: InetAddress = InetAddress.getByName("localhost")
    // establish the connection
    val s: Socket = new Socket(ip, ServerPort)
    // obtaining input and out streams
    val dis: DataInputStream = new DataInputStream(s.getInputStream)
    val dos: DataOutputStream = new DataOutputStream(s.getOutputStream)
    // sendMessage thread
    val sendMessage: Thread = new Thread(new Runnable{
      override def run(): Unit = {
        while (true) {
          // read the message to deliver.
          var msg: String = scn.nextLine()
          try { // write on the output stream
            dos.writeUTF(msg)
          }catch {
            case e: Exception => println(e)
          }
        }
      }
    })
    // readMessage thread
    val readMessage: Thread = new Thread(new Runnable{
      override def run(): Unit = {
        while (true) {
          try {
            // read the message sent to this client
            var msg: String = dis.readUTF()
            println(msg)
          } catch {
            case e: Exception => println(e)

          }
        }
      }
    })
    sendMessage.start()
    readMessage.start()
  }
}
