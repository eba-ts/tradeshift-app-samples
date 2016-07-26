# Spring Boot - Angular/UI

# Description

This is a sample app for third-party applications. Some of the things it demonstrates are:

-  Show how to use <a href="ui.tradeshift.com"> Tradeshift's UI components </a>.
-  OAUTH2 authorization.
-  Calling Tradeshift API methods to get a list of invoices.

# Requirements

- Java 8
- Maven 3 or later version
- Application on Tradeshift App Developer

# Build


    Application is working by HTTPS protocol.
    Be sure, that you have correctly configured necessary data for that purpose, ssl certificates etc.

1. Add environment variables **clientID**, **clientSecret**, **redirectUri**, **tradeshiftAPIDomainName** with data you setup or receive from your Tradeshift.Developer app.
    
    For example : 
    
            clientID = YourTradeshiftClientId
            clientSecret = b3658f61-d436-445e-949c-3086ccda00e1
            redirectUri = https://your_app_uri.com/redirect_uri
            tradeshiftAPIDomainName = https://api-tradeshift.com
       
     ** About 'tradeshiftAPIDomainName' uri you can read in <a href="http://apps.tradeshift.com/developers/documentation/">Tradeshift development documentation.</a>

2. Run from project root directory maven command : ' **mvn clean install** '.

3. Go to the target directory and run next command : ' **java -jar java-springboot-0.0.1.jar** '

4. Update Tradeshift app Main URL.

5. Check that app runs correctly,  go to the  `https://your_app_uri.com/health` , you can see the next message `{"status":"UP"}`  that means that app successfully started.




You can easy run and deploy this project on the <a href="https://heroku.com">Heroku</a>, for this purpose do the following steps:

1. Create new app on Heroku.

2. Configure Heroku environment variables **clientID**, **clientSecret**, **redirectUri**, **tradeshiftAPIDomainName** with data you setup or receive from your Tradeshift.Developer app.
    For more information on adding Heroku env vars see <a href="https://github.com/lorenwest/node-config/wiki/Environment-Variables">here</a> .

3. Deploy on Heroku (see   <a href="https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku">Heroku deploy app documentation</a>).

4. Update on the Tradeshift app Main URL.

5. Check that app runs correctly,  go to the  `https://your_app_uri.com/health` , you can see the next message `{"status":"UP"}`  that means that app successfully started.

# Notes 

- This sample has no database connected. For production level application you would likely to use database.
- Heroku automatically configure necessary data for the HTTPS protocol.
