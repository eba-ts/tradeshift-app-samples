/* Main configuration file */
module.exports = {
    clientId: process.env.CLIENT_ID, // your app's oauth2 client id
    clientSecret: process.env.CLIENT_SECRET, // your app's oauth2 client secret
    authUrl:  process.env.AUTH_URL, // your app's authorization server url. Update this if you've changed the redirect uri
    tradeshiftAPIServerURL:  process.env.SERVER_URL || 'https://api-sandbox.tradeshift.com/tradeshift/' // main url of the requested server
};
