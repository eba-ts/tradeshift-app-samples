var express = require('express');
var router = express.Router();
var config = require('../config/config');
var request = require('request');
var base64 = require('base-64');
var xml2js = require('xml2js');
var session = require('express-session');

router.use(session({ // Initialize session middleware so we can store there our token
  secret: config.clientSecret,
  resave: false,
  saveUninitialized: true
}));

/* GET home page. */
router.get('/', function(req, res) {
  if (!req.session.access_token) {
    return res.redirect(config.authUrl); // redirects to the /oauth2/code
  }
  res.render('index');
});

/* Get config code */
router.get('/oauth2/code', function(req, res){ // if no token, redirecting here
  request({ // request access_token upon code retrieval
    url: config.tradeshiftAPIServerURL + 'auth/token',
    method: 'POST',
    headers: {
      Authorization: 'Basic ' + base64.encode(config.clientId + ':' + config.clientSecret) 
    },
    formData: {
      code: req.query.code,
      grant_type: 'authorization_code'
    }
  }, function(error, response, body){
      if (!error){
        body = JSON.parse(body);
        req.session.access_token = body.access_token; // setting to session received access_token
        res.redirect('/');
      } else {
        res.send(error);
      }
    }
  )
});

/* Get current account information */
router.get('/info', function(req, res){
  request({
    url: config.tradeshiftAPIServerURL + 'rest/external/account/info',
    method: 'GET',
    headers: {
      Authorization: 'Bearer ' + req.session.access_token
    }
  }, function(error, response, body){
       if (!error) {
         xml2js.parseString(body, function(err, result) { // Parse XML
           res.json(result);
         })
       }  else {
         res.json(error);
       }

  })
});

router.get('/demo/get_grid', function(req, res){
  res.send([
    {"id":1,"character":"Barbarian Queen","alignment":"Neutral Evil"},
    {"id":2,"character":"Global Senior Vice President of Sales","alignment":"Chaotic Evil"},
    {"id":3,"character":"Jonathan the Piggy","alignment":"Glorious Good"},
    {"id":4,"character":"Paladin Knight","alignment":"Lawful Good"},
    {"id":5,"character":"Potato Chip","alignment":"Radiant Good"}
  ])
});

module.exports = router;
