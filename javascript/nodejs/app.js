var express = require('express');
var path = require('path');
var engine = require('consolidate'); // we need this to set HTML engine
var routes = require('./routes/index');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.engine('html', engine.mustache);
app.set('view engine', 'html');

app.use(express.static(path.join(__dirname, 'public')));
app.use('/', routes);

module.exports = app;
