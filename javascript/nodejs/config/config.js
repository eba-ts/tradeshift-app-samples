/* All data comes from env variables */
module.exports = {
    clientId: process.env.CLIENT_ID,
    clientSecret: process.env.CLIENT_SECRET,
    authUrl: process.env.AUTH_URL,
    tokenUrl: process.env.TOKEN_URL
};
