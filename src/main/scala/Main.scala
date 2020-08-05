import java.io.PrintWriter

import scala.io.{BufferedSource, Source}

object Main extends App {
  // время выполнения на файле в 3.6Gb примерно 4 минуты
  val tStart = System.currentTimeMillis()

  val builder = new StringBuilder()
  val pattern = """\d+""".r
  // sum объявлена как var, чтобы не выделять память под новую переменную в цикле
  var sum = 0L

  processJson("original.json", "result.json") { (source, output) =>
    source.foreach { c =>
      builder += c
      if (c == '}') {
        sum = (pattern findAllIn builder).map(_.toLong).sum
        builder.insert(builder.length - 1, s""", "sum":$sum""")
        output.print(builder.result())
        builder.clear()
      }
    }
    output.print(']')
  }

  val tFinish = System.currentTimeMillis()
  println(s"Completed in ${(tFinish - tStart) / 1000} seconds")

  def processJson(fromFile: String, toFile: String)(op: (BufferedSource, PrintWriter) => Unit): Unit = {
    val source = Source.fromFile(fromFile)
    try {
      val output = new PrintWriter(toFile)
      try {
        op(source, output)
      } finally {
        output.close()
      }
    } finally {
      source.close()
    }
  }

}
