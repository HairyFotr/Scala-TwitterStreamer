package com.streamer.twitter

import java.io.InputStream
import org.apache.commons.httpclient.HttpMethod

/**
 * StreamProcessor
 *
 * In order to process the stream of tweets, you have to concretely define
 * on your subclass what to do with the InputStream
 */
abstract class StreamProcessor {

  // This method customizes the handling of the stream
  def process(is: InputStream): Unit

  def process(method: HttpMethod): Unit = { this.process(method.getResponseBodyAsStream()) }

}
