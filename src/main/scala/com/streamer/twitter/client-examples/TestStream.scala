package com.streamer.twitter

import com.streamer.twitter.config._

object TestStream extends App {
  val processor = new OutputStreamProcessor()
  val username = Config.readString("username")
  val password = Config.readString("password")

  val twitterClient = new BasicStreamingClient(username, password, processor)
  twitterClient.filter(locations = "-180,-90,180,90")
}
