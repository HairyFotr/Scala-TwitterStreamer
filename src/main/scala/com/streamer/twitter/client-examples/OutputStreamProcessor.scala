package com.streamer.twitter

import java.io.InputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.zip.GZIPInputStream
import org.apache.commons.httpclient.HttpMethod

import org.json4s._
import org.json4s.jackson.JsonMethods._

// This streamProcessor just outputs the parsed objects on the screen.
//
class OutputStreamProcessor extends StreamProcessor {

  override def process(is: InputStream): Unit = {
    val reader: BufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(is), "UTF-8"))

    var line = reader.readLine()
    while (line != null) {
      //println(line)

      var parsed_json = parse(line)

      //println((parsed_json \ "geo" \ "coordinates"))

      (parsed_json \ "geo" \ "coordinates") match {
        case JArray(List(JDouble(lng), JDouble(lat))) => println("lat: " + lat + " lng: " + lng)
        case _ =>       
      }

      //var coordinates = for { JArray(List(JDouble(lat), JDouble(lng))) <- (parsed_json \ "geo" \ "coordinates") } yield (lat, lng)

      //println("lat: " + coordinates(0) + " lng: " + coordinates(1))

      line = reader.readLine()
    }

    is.close
  }
}
