package com.streamer.twitter

import java.io.InputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.zip.GZIPInputStream
import org.apache.commons.httpclient.HttpMethod


// This streamProcessor just outputs the parsed objects on the screen.
//
class OutputStreamProcessor extends StreamProcessor {

  override def process(is: InputStream): Unit = {
    val reader: BufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(is), "UTF-8"))

    var line = reader.readLine()
    while (line != null) {
      println(line)
      line = reader.readLine()
    }

    is.close
  }
}
