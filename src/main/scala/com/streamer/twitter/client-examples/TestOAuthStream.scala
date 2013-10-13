package com.streamer.twitter

import com.streamer.twitter.config._
import com.streamer.twitter.oauth._
import com.streamer.twitter._

object TestOAuthStream extends App {
  val consumer = Consumer(Config.readString("consumer.key"), Config.readString("consumer.secret"))
  val token = Token(Config.readString("access.token"), Config.readString("access.secret"))
  val processor = new TweetProcessor() 

  val twitterClient = new OAuthStreamingClient(consumer, token, processor)
  //twitterClient.sample()
  //twitterClient.filter(locations = "-180,-90,180,90")
  //twitterClient.siteStream(Set(16741237,14344469,134879387,39848709,9980812,55600683,18948541,16031975,69128362,14452238,12301142,13058772,17765013,32692341,14470999,188093253,21493276,102131542,71363379,29239854,8526432))
  twitterClient.track(Set("Apple", "ipad"))
}

import java.io.{InputStream, InputStreamReader, BufferedReader}
import java.util.zip.GZIPInputStream

import org.json4s._
import org.json4s.jackson.JsonMethods._

class TweetProcessor extends StreamProcessor {
  override def process(is: InputStream): Unit = {
    val reader: BufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(is), "UTF-8"))

    var line = reader.readLine()
    while(line != null) {
      var json = parse(line)
      
      (json \ "text") match {
        case JString(text) => println(text)
        case _ =>
      }
      
      line = reader.readLine()
    }

    is.close
  }
}
