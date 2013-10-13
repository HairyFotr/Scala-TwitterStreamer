package com.streamer.twitter

import scala.collection.mutable.ArrayBuffer
import org.apache.commons.httpclient.util.URIUtil
import org.apache.commons.httpclient.NameValuePair
import org.apache.commons.httpclient.HttpMethod
import org.apache.commons.httpclient.methods.PostMethod
import org.apache.commons.httpclient.methods.GetMethod

import com.streamer.twitter.config._

/**
 * StreamingMethods
 *
 * This trait implements the public streaming API methods, filter, sample, etc.
 */
trait StreamingMethods {

  /**
   * Sample
   *
   * Returns a random sample of all public statuses.
   *
   * @param count Indicates the number of previous statuses to consider for delivery before transitioning to live stream delivery.
   * @param delimited Indicates that statuses should be delimited. Statuses are represented by a length, in bytes, a newline, and the status text that is exactly length bytes. ex. length
   */
  def sample(count: Int = 0, delimited: String = ""): Unit = {
    val params = ArrayBuffer[NameValuePair]()
    if(count > 0) params += new NameValuePair("count", count.toString)
    if(delimited != "") params += new NameValuePair("delimited", delimited)

    stream(buildPost(Config.readString("twitterStreamUrl"), params))
  }

  /**
   * Filter 
   *
   * Returns public statuses that match one or more filter predicates.
   *
   * @param count Indicates the number of previous statuses to consider for delivery before transitioning to live stream delivery.
   * @param delimited Indicates that statuses should be delimited. Statuses are represented by a length, in bytes, a newline, and the status text that is exactly length bytes.
   * @param follow Specifies the list of Twitter user id's to follow
   */
  def filter(follow: Set[Long] = Set[Long](), count: Int = 0, delimited: String = "", locations: String = ""): Unit = {
    val params = ArrayBuffer[NameValuePair]()
    if(!follow.isEmpty) params += new NameValuePair("follow", follow.mkString(","))
    if(locations != "") params += new NameValuePair("locations", locations)

    stream(buildPost(Config.readString("twitterFilterUrl"), params))
  }

  /**
   * Track 
   *
   * Returns public statuses that match one or more filter predicates.
   *
   * @param count Indicates the number of previous statuses to consider for delivery before transitioning to live stream delivery.
   * @param delimited Indicates that statuses should be delimited. Statuses are represented by a length, in bytes, a newline, and the status text that is exactly length bytes.
   * @param track Specifies the list of keywords to keep track of
   */
  def track(track: Set[String] = Set[String](), count: Int = 0, delimited: String = ""): Unit = {
    val params = ArrayBuffer[NameValuePair]()
    if(!track.isEmpty) params += new NameValuePair("track", track.mkString(","))

    stream(buildPost(Config.readString("twitterFilterUrl"), params))
  }

  /**
   * Firehose
   *
   * Returns all public statuses. The Firehose is not a generally available resource.
   * Few applications require this level of access. i.e. you are probably not whitelisted for this.
   *
   * @param count Indicates the number of previous statuses to consider for delivery before transitioning to live stream delivery.
   * @param delimited Indicates that statuses should be delimited. Statuses are represented by a length, in bytes, a newline, and the
   */
  def firehose(count: Int = 0, delimited: String = ""): Unit = {
    val params = ArrayBuffer[NameValuePair]()
    if(count > 0) params += new NameValuePair("count", count.toString)
    if(delimited != "") params += new NameValuePair("delimited", delimited)

    stream(buildPost(Config.readString("twitterFirehoseUrl"), params))
  }

  /**
   * Site Streams
   *
   * @param ids to follow
   */
  def siteStream(follow: Set[Long], withParam: String = "followings"): Unit = {
    val params = ArrayBuffer[NameValuePair]()
    params += new NameValuePair("with", withParam)

    if(!follow.isEmpty) params += new NameValuePair("follow", follow.mkString(","))
    
    stream(buildPost(Config.readString("twitterSiteStreamUrl"), params))
  }

  /**
   * Links
   *
   * Returns all statuses containing http: and https:. The links stream is not a generally available resource.
   * Few applications require this level of access.
   *
   * @param delimited Indicates that statuses should be delimited. Statuses are represented by a length, in bytes, a newline, and the
   */
  def links(delimited: String = ""): Unit = {
    val params = ArrayBuffer[NameValuePair]()
    if(delimited != "") params += new NameValuePair("delimited", delimited)
    
    stream(buildPost(Config.readString("twitterLinksUrl"), params))
  }

  /**
   * Retweet
   *
   * Returns all retweets. The retweet stream is not a generally available resource.
   * Few applications require this level of access.
   *
   * @param delimited Indicates that statuses should be delimited. Statuses are represented by a length, in bytes, a newline, and the
   */
  def retweet(delimited: String = ""): Unit = {
    val params = ArrayBuffer[NameValuePair]()
    if(delimited != "") params += new NameValuePair("delimited", delimited)
    
    stream(buildPost(Config.readString("twitterRetweetUrl"), params))
  }

  def stream(method: HttpMethod): Unit

  def buildPost(baseUrl: String, params: ArrayBuffer[NameValuePair]): PostMethod = {
    val postMethod = new PostMethod(baseUrl)
    postMethod.setRequestBody(params.toArray)
    postMethod
  }
}
