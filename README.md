# Twitter Streaming Client

## Motivation
Create a library to support the Twitter streaming API, the reconnect strategy, OAuth authentication, and error policies specified on their documentation.

## What's in the package
- OAuth support
- Apache HttpClient based.
- Back off strategy built in, so in the case of unexpected errors the library will reconnect:
  - TCP errors start at 250 miliseconds and cap at 16 seconds
  - HTTP errors start at 10 seconds and cap at 240 seconds
- Easy to implement your own parsing and processing of tweets.

## Give it a try
Create `src/main/resources/config/TwitterStreamer.conf` (see `TwitterStreamer.conf.template`) and run `sbt update run`.

## Usage example
First you need to define what you want to do with the stream. Here's an example that just prints every line we get to stdout:

    import com.streamer.twitter._
    import com.streamer.twitter.oauth._
    import com.streamer.twitter.config._
    import java.io.InputStream
    import java.io.InputStreamReader
    import java.io.BufferedReader
    
    class CustomProcessor extends StreamProcessor {
      override def process(is: InputStream): Unit = {
        val reader: BufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))
    
        var line = reader.readLine()
        while (line != null) {
          println(line)
          line = reader.readLine()
        }
        is.close
      }
    }
    
    object TestOAuthStream {
      def main(args: Array[String]) = {
        val consumer = Consumer(Config.readString("consumer.key"), Config.readString("consumer.secret"))
        val token = Token(Config.readString("access.token"), Config.readString("access.secret"))
        val processor = new CustomProcessor()
    
        val twitterClient = new OAuthStreamingClient(consumer, token, processor)
        twitterClient.sample()
      }
    }
    
There are a few other examples, one with a JSON parser included, in `src/main/scala/com/streamer/twitter/client-examples`.

## API Methods
- Sample Returns a random sample of all public statuses.
- Filter Returns public statuses that match one or more filter predicates.
- Firehose Returns all public statuses. (not a generally available resource)
- Links Returns all statuses containing http: and https:. (not a generally available resource)
- Retweet Returns all retweets. (not a generally available resource)
- Sites Stream Returns all events for the users you specify to follow and that OAuth'ed to your application.

## License
Copyright (c) 2010 Alejandro Crosa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

Alejandro Crosa <<alejandrocrosa@gmail.com>>
