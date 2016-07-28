# Node.js + Angular + UI Components

## Description
This is a sample project which shows how to create app on Tradeshift using Node.js backend, with Angular.js on frontend, utilizing Tradeshift's [UI Components](http://ui.tradeshift.com).
The app authenticated with Tradeshift server using OAUTH2 authorization workflow, and retrieves current company data as an example of calling Tradeshift API. Additionally app has multiple language supportusing English and Russian languages.  To add languages, add files to browser/locales directory. 

## Requirements
- [NodeJS](https://nodejs.org/en/) version 4 and higher
- Application on Tradeshift App Developer

## Build
You can build and run project on your local server(`localhost`):
- Add environment variables shown in `config/config.js`. For more information on adding env vars see [here](https://github.com/lorenwest/node-config/wiki/Environment-Variables)
- Install dependencies - `npm install`
- To allow you to develop locally but access Tradeshift servers, use [ngrok](https://ngrok.com/docs#expose) or similar tools, which allow you to explose a local server to the internet.
- Follow instructions in Tradeshift.Developer app to create a new App.  Documentation [here](http://apps.tradeshift.com/documentation). 
- Configure your _OAUTH2 REDIRECT URI_ in the Tradeshift Developer app to `http://ngrocURL/oauth2/code` 
- Update `authUrl` in your `config/config.js` file (use _TRADESHIFT AUTHORIZATION SERVER URL_)
- Run server - `npm start` 
- Go to Tradeshift account on sandbox and open the app you created in Tradeshift.Developer app. 

## Deployment
You can easily deploy this project on [Heroku](https://www.heroku.com/), as follows:
- Create new Node.js app on Heroku and connect your local git repository with heroku.. 
- Configure Heroku environment variables (`CLIENT_ID`, `CLIENT_SECRET`, `AUTH_URL`) with data from your Tradeshift App in Heroku app settings.
- Deploy on Heroku (see deploy app documentation)
- Update Main URL in Tradeshift.Developer app to point to the deployed App URL. . 

## Testing
Project uses Karma/Jasmine for testing Angular app and Mocha/Supertest for testing server side. For running tests you need to:
- Install dependencies via `npm install`
- Run `npm test` for server side test run
- Run `karma start karma.config.js` for client side test run

## Notes
- By default, the app points to Tradeshift's sandbox, if you want to change it to production - change `TOKEN_URL` and `ACCOUNT_DATA_URL` (see `config/config.js`)
- This sample has no persistence, only small config file. For production level application you will likely need to add database of your choice. 
- For internationalization, we implemented both on client and server-side, but by default use client-side. Working sample API is provided. For client side we've used [`angular-translate` module](https://angular-translate.github.io/)

