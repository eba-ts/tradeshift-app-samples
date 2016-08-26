# Tradeshift Third Party Embedded App Sample - Java / Spring.boot + Angular + Tradeshift UI Components

# Description

App modules structure
---------------------
|- application
|- sharedlib

1. application module - Holds basic code examples. Shows interacting methods with TradeShift platform.
2. sharedlib module - Implements main sequence for TradeShift authentication process.
 Can be used as autonomous module for a new App development.


This is a sample starter app for third-party applications. Some of the things it demonstrates are:

-  Show how to use  [Tradeshift's UI components](http://ui.tradeshift.com)  </a>.
-  OAUTH2 authorization.
-  Calling Tradeshift API methods to get a list of invoices.

# Requirements

- Java 8
- Maven 3 or later version
- Application on Tradeshift App Developer

# Test

Do not forget to set Env variable to test pass.

# Build

    Application must be hosted behind HTTPS so that Tradeshift server can pass credentials securely.  When deploying, you need to make sure you host the app using HTTPS.

1. Add environment variables **clientID**, **clientSecret**, **redirectUri**, **tradeshiftAPIDomainName** with data you setup or receive from your Tradeshift.Developer app.

    For example :

            clientID = YourTradeshiftClientId
            clientSecret = b3658f61-d436-445e-949c-3086ccda00e1
            redirectUri = https://your_app_uri.com/oauth2/code
            tradeshiftAPIDomainName = https://api-sandbox.tradeshift.com

     ** About 'tradeshiftAPIDomainName' URI please read [Tradeshift developer documentation](http://apps.tradeshift.com/developers/documentation/).

2. Run from project root directory maven command : ' **mvn clean install** '.

3. Go to the target directory and run next command : ' **java -jar java-springboot-0.0.1.jar** '

4. Update Tradeshift App Main URL in Tradeshift Developer app.

5. Health monitoring - Go to the  `https://your_app_uri.com/health`.  If you get 200 and see payload `{"status":"UP"}` that means the app is up and running.

You can easy run and deploy this project on the  , for this purpose do the following steps:

1. Create new app on Heroku.

2. Configure Heroku environment variables **clientID**, **clientSecret**, **redirectUri**, **tradeshiftAPIDomainName** with data you setup or receive from your Tradeshift.Developer app.
    For more information on adding Heroku env vars see [here](https://github.com/lorenwest/node-config/wiki/Environment-Variables).

3. Deploy on Heroku (see   [Heroku deploy app documentation](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku) ).

4. Update on the Tradeshift app Main URL.

5. Health monitoring - Go to the  `https://your_app_uri.com/health`.  If you get 200 and see payload `{"status":"UP"}` that means the app is up and running.

# Notes

- This sample has no database connected. For production level application you would likely to use database.
- Heroku apps runs under HTTPS automatically.
