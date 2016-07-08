# Spring Boot - Angular/UI

This is a sample app for third-party applications. Some of the things it demonstrates are:

1. Show how to use Tradeshift's UI
2. OAUTH2 authorization.
3. Collaboration with remote rest service by API methods for get list of documents.


To build this project you must have the following requirements:

- Java 8
- Maven 3 or later version



Built :

    Application is working by HTTPS protocol.
    Be sure, that you have correctly configured necessary data for that purpose, ssl certificates etc.

1. Add environment variables clientID, clientSecret, with data from your Tradeshift App.

2. Fill 'src/main/resources/application.properties' file with correct data from your Tradeshift App.
   About restServiceDomainName uri you can read in Tradeshift development documentation.

3. Run from project root directory maven command : ' mvn clean install '.

4. Go to the target directory and run next command : ' java -jar java-springboot-0.0.1.jar '

5. Update Tradeshift app Main URL.



You can easy run and deploy this project on the https://heroku.com, for this purpose do the following steps:

1. Create new app on Heroku

2. Configure Heroku environment variables clientID, clientSecret, with data from your Tradeshift App, in Heroku app
 settings.

3. Fill 'src/main/resources/application.properties' file with correct data from your Tradeshift App.
      About restServiceDomainName uri you can read in Tradeshift development documentation.

4. Deploy on Heroku (see deploy app documentation https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku)

5. Update on the Tradeshift app Main URL.
