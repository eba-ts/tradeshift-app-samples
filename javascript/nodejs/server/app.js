var express = require('express');
var path = require('path');
var engine = require('consolidate'); // we need this to set HTML engine
var routes = require('./routes/index');
var app = express();
var i18n = require("i18n");
var morgan = require('morgan');

i18n.configure({
    locales: ['en', 'ru'],
    directory: __dirname + '/locales',
    defaultLocale: 'en'
});

// view engine setup
app.set('views', path.join(__dirname, '../browser/views'));
app.engine('html', engine.mustache);
app.set('view engine', 'html');
app.use(morgan('dev'));
app.use(i18n.init);
app.use(express.static(path.join(__dirname, '../browser')));
app.use('/', routes);

module.exports = app;
