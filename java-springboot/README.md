# Tradeshift Third Party Embedded App Sample - Java / Spring.boot + Angular + Tradeshift UI Components

# Description

This is a sample starter app for third-party applications.  See what functionality it supports in the [master README file](../README.md).   

[Here is a short video](https://drive.google.com/file/d/0Bx2z3BvoWzgtU05QdFludEROZ2c/view) (12 minutes) that shows you how to fully deploy and configure the Java Sample App on Tradeshift. 

Java Maven Modules
---------------------
The app is subdivided into two modules: 
- *sharedlib module* - Implements main sequence for Tradeshift authorization process.  Packaged separately so it can be easily reused in applications.  
- *application module* - Includes sharedlib, and has the code for actual application, including UI.  Shows interacting methods with TradeShift platform.

# Requirements

- Java 8
- Maven 3 or later version
- Once you compile and deploy this server, you will need to configure it in Tradeshift Developer app.  See documentation [here](http://apps.tradeshift.com/developers/documentation/#embedded-app)

# Test

There is one simple test just to let you add more should you choose to use this project as starter for your own. 

# Build

    Application must be hosted behind HTTPS so that Tradeshift server can pass credentials securely.  When deploying, you need to make sure you host the app using HTTPS.

1. Add four environment variables **clientID**, **clientSecret**, **redirectUri**, **tradeshiftAPIDomainName** with information you have set in Tradeshift.Developer app.

            clientID = TradeshiftClientId (From Developer App)
            clientSecret = xxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxx (From Developer App)
            redirectUri = https://your_app_uri.com/oauth2/code (Same as one you configure in Developer App)
            tradeshiftAPIDomainName = https://api-sandbox.tradeshift.com (this is for sandbox, https://api.tradeshift.com for production)

2. Run from project root directory maven command : ' **mvn clean install** '.

3. Go to the target directory and run next command : ' **java -jar java-springboot-0.0.1.jar** '

4. Update Tradeshift App Main URL in Tradeshift Developer app.

5. Health monitoring - Go to the  `https://your_app_uri.com/health`.  If you get 200 and see payload `{"status":"UP"}` that means the app is up and running.

6. For webhooks work correctly, you have to configure settings in your Tradeshift app.
    More info about this [here](http://apps.tradeshift.com/developers/documentation/#webhooks)
    
    For example :

          NAME    sample.app.com                                your app domain name
          URL     "https://sample.app.com/webhooks/receive"     your URL that receive events from tadeshift webhooks 
          EVENTS  choose events that you want hanle(Before document receive,  After document receive, etc.)
      

You can easy run and deploy this project on Heroku, for this purpose do the following steps:

1. Create new app on Heroku.

2. Configure Heroku environment variables **clientID**, **clientSecret**, **redirectUri**, **tradeshiftAPIDomainName** with data you setup or receive from your Tradeshift.Developer app.
    For more information on adding Heroku env vars see [here](https://github.com/lorenwest/node-config/wiki/Environment-Variables).

3. Deploy on Heroku (see   [Heroku deploy app documentation](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku) ).

4. Update on the Tradeshift app Main URL.

5. Health monitoring - Go to the  `https://your_app_uri.com/health`.  If you get 200 and see payload `{"status":"UP"}` that means the app is up and running.

# Notes

- This sample has no database connected. For production level application you would likely to use database.
- Heroku apps run under HTTPS automatically.
