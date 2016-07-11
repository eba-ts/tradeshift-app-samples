# Node.js + Angular + UI Components

## Description
This is a sample project which shows how to create app on Tradeshift using Node.js, Angular and Tradeshift's [UI Components](ui.tradeshift.com).
App shows authorization workflow and retrieves current company data as an example of API.

## Requirements
- [NodeJS](https://nodejs.org/en/) version 4 and higher
- Application on Tradeshift App Developer

## Build
You can build and run project on your local server(`localhost`):
- Add environment variables shown in `config/config.js`. For more information on adding env vars see [here](https://github.com/lorenwest/node-config/wiki/Environment-Variables)
- Configure your _OAUTH2 REDIRECT URI_ in the Tradeshift app to `http://localhost:3000/oauth2/code` 
- Update `authUrl` in your `config/config.js` file (use _TRADESHIFT AUTHORIZATION SERVER URL_)
- Install dependencies - `npm install`
- Run server - `npm start` 
- Go to `localhost:3000`

## Deployment
You can easily deploy this project on [Heroku](https://www.heroku.com/), for this purpose do the following steps:
- Create new Node.js app on [Heroku](https://www.heroku.com/)
- Configure Heroku env variables (`CLIENT_ID`, `CLIENT_SECRET`, `AUTH_URL`) with data from your Tradeshift App in Heroku app settings.
- Deploy on Heroku (see deploy app documentation)
- Update Tradeshift app Main URL

## Notes
- Default environment is sandbox, if you want to change it to production - change `TOKEN_URL` and `ACCOUNT_DATA_URL` (see `config/config.js`)
- This sample has no database connected, only small config file. For production level application you would likely to use database.
- For local building and testing you might want to use [ngrok](https://ngrok.com/docs#expose), which allows you to explose a local server to the internet.
