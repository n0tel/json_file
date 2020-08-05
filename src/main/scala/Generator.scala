import java.io.PrintWriter
import scala.util.Random

object Generator extends App {
  val file = new PrintWriter("original.json")
  // генерирует файл размером 3.6Gb (генерация занимает примерно пол минуты)
  val numObjects = 100000000
  val builder = new StringBuilder()
  val tStart = System.currentTimeMillis()

  try {
    file.print('[')
    var i = 0

    while (i < numObjects) {
      writeNextObject(file)
      file.print(", ")
      i += 1
    }

    writeNextObject(file)
    file.print(']')
  } finally {
    file.close()
  }

  val tFinish = System.currentTimeMillis()
  println(s"Completed in ${(tFinish - tStart) / 1000} seconds")

  def writeNextObject(writer: PrintWriter): Unit = {
    writer.write(s"""{"a": ${Random.nextInt()}, "b": ${Random.nextInt()}}""")
  }
}
