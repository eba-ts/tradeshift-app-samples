var express = require('express');
var router = express.Router();
var config = require('../config/config');
var request = require('request');
var base64 = require('base-64');

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index');
});

/* GET token */
router.get('/oauth2/get_token', function(req, res) {
  res.redirect(config.authUrl); // redirects to the /oauth2/code
});

/* Get config code */
router.get('/oauth2/code', function(req, res){
  request({ // make POST request upon code retrieval
    url: config.tokenUrl,
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
        res.send(body);
      } else {
        res.send(error);
      }
    }
  )
});

/* GET some data */
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
