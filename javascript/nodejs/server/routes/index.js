var express = require('express');
var router = express.Router();
var config = require('../config/config');
var request = require('request');
var xml2js = require('xml2js');
var session = require('express-session');
var i18n = require('i18n');
var passport = require('passport');

/* GET home page. */
router.get('/', function(req, res){
  if (!req.user){ // If not authorized
    return res.redirect('/auth');
  }
  res.render('index');
});

/* Use passport to authenticate with Tradeshift */
router.get('/auth', passport.authenticate('tradeshift', {scope: 'offline'}));
router.get('/oauth2/code', passport.authenticate('tradeshift'), function(req, res){
  request = request.defaults({ // setting default auth header with received access_token token
    headers: { Authorization: 'Bearer ' + req.user.access_token }
  });
  res.redirect('/');
});

/* Get current account information */
router.get('/account/info', function(req, res){
  request.get({url: config.tradeshiftAPIServerURL + 'rest/external/account/info'},
    function(error, response, body){
       if (!error){
         xml2js.parseString(body, function(err, result){ // Parse XML
           res.json(result);
         })
       }  else {
         res.json(error);
       }
    })
});

/* Get mock data from server */
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
