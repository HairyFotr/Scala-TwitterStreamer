package com.streamer.twitter.config

import net.lag.configgy._
import org.apache.commons.codec.binary.Base64

object Config {

  Configgy.configure("config/TwitterStreamer.conf")
  val config = Configgy.config

  /*
   * Register config with JMX
   */
  config.registerWithJmx("com.streamer.twitter")

  def getString(key: String) = config.getString(key)

  def readString(key: String): String = config.getString(key) match {
    case Some(value) => value.toString
    case _ => throw new ConfigurationException(key)
  }

  def readInt(key: String): Int = readString(key).toInt

  def readBoolean(key: String): Boolean = readString(key) match {
    case "true" => true
    case _ => false
  }

  def base64Decode(value: String) = {
    new String(new Base64().decode(value.getBytes))
  }

  class ConfigurationException(method: String) extends Throwable {
    override def toString = "You must supply "+ method +" in the configuration file"
  }
}