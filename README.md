# SDKMAN! Vendor Announce API

This is a dedicated microservice API used for Announcements on the SDKMAN! platform. It is used by SDKMAN! to announce breaking
news about Candidate Releases and other important events. It's Broadcast Messages can be consumed through [Broadcast API](https://github.com/sdkman/sdkman-broadcast-api)
endpoints, as is the case with the SDKMAN! Bash client. It also has the ability to publish it's Messages to social media
sites such as Twitter.

## Running it up locally

You will need to have MongoDB up and running locally on the default port.

    $ docker run -d --net=host mongo:latest

Once running, step into the project folder and build.
 
    $ ./gradlew clean assemble

We can now run the app up locally with a simple

    $ java -jar build/libs/application.jar

## Running tests

The service has a comprehensive suite of Acceptance Tests, as well as Unit Tests.

		$ ./gradlew check

## Environment Variables

The application can be configured at runtime by using environment variables.

#### MongoDB

`MONGO_HOST`: Host

`MONGO_PORT`: Port

`MONGO_DB_NAME`: Database Name

`MONGO_USERNAME`: Username

`MONGO_PASSWORD`: Password

#### Twitter

`TWITTER_CONSUMER_KEY`: Twitter Consumer Key

`TWITTER_CONSUMER_SECRET`: Twitter Consumer Secret

`TWITTER_ACCESS_TOKEN`: Twitter Access Token

`TWITTER_ACCESS_TOKEN_SECRET`: Twitter Access Token Secret
