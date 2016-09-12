var express = require('express');
var router = express.Router();
var config = require('../config/config');
var request = require('request');
var base64 = require('base-64');
var xml2js = require('xml2js');
var session = require('express-session');
var i18n = require('i18n');

/* Function which checks config variables */
var throwError = function(variables){
  if (variables instanceof Object && !(variables instanceof Array)){
    for(key in variables) {
      if (!variables[key]) throw new Error('No ' + key + ' provided.Please set it into your config variable.');
    }
  } else {
    throw new Error('Wrong argument in throwError(). Please, make sure that you pass an object.');
  }
};

throwError({clientSecret: config.clientSecret}); // check if user has specified a client secret
router.use(session({ // Initialize session middleware so we can store there our token
  secret: config.clientSecret,
  resave: false,
  saveUninitialized: true
}));

/* GET home page. */
router.get('/', function(req, res) {
  if (!req.session.access_token) {
    throwError({tradeshiftAPIServerURL: config.tradeshiftAPIServerURL, clientId: config.clientId, redirectUri: config.redirectUri}); //check config variables
    request.get({
      url: config.tradeshiftAPIServerURL + 'auth/login',
      qs: {
        response_type: 'code',
        client_id: config.clientId,
        redirect_uri: config.redirectUri,
        scope: 'offline'
      }
    });
  }
  res.render('index');
});

/* Get config code */
router.get('/oauth2/code', function(req, res){ // if no token, redirecting here
  request.post({ // request access_token upon code retrieval
    url: config.tradeshiftAPIServerURL + 'auth/token',
    headers: {
      Authorization: 'Basic ' + base64.encode(config.clientId + ':' + config.clientSecret) 
    },
    formData: {
      code: req.query.code,
      grant_type: 'authorization_code'
    }
  }, function(error, response, body){
      if (error) throw error;
        body = JSON.parse(body);
        req.session.access_token = body.access_token; // setting to session received access_token
        request = request.defaults({ // setting default auth header with provided token
          headers: { Authorization: 'Bearer ' + req.session.access_token }
        });
        res.redirect('/');
    }
  )
});

/* Get current account information */
router.get('/account/info', function(req, res){
  request.get({url: config.tradeshiftAPIServerURL + 'rest/external/account/info'},
    function(error, response, body){
       if (!error) {
         xml2js.parseString(body, function(err, result) { // Parse XML
           res.json(result);
         })
       }  else {
         res.json(error);
       }
    })
});

router.get('/demo/grid-data', function(req, res){
  res.send([
    {"id":1,"character":"Barbarian Queen","alignment":"Neutral Evil"},
    {"id":2,"character":"Global Senior Vice President of Sales","alignment":"Chaotic Evil"},
    {"id":3,"character":"Jonathan the Piggy","alignment":"Glorious Good"},
    {"id":4,"character":"Paladin Knight","alignment":"Lawful Good"},
    {"id":5,"character":"Potato Chip","alignment":"Radiant Good"}
  ])
});

/* API for requesting server-side locale */
router.get('/locale', function(req, res){
  res.send(i18n.getCatalog(req));
});

/* Check if server is up */
router.get('/health', function(req, res){
  res.send('Server is up and running!').status(200);
});

module.exports = router;
