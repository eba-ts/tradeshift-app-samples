
module.exports = function(config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine'],
    /* Important to have here all files related to the project.
       Double check your dependencies in Angular module and HTML. */
    files: [
      'https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.8/angular.min.js',
      'https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.8/angular-mocks.js',
      'https://cdnjs.cloudflare.com/ajax/libs/angular-translate/2.11.1/angular-translate.min.js',
      'https://cdnjs.cloudflare.com/ajax/libs/angular-translate-loader-static-files/2.11.1/angular-translate-loader-static-files.min.js',
      'https://d5wfroyti11sa.cloudfront.net/prod/client/ts-6.0.0-alpha.8.min.js',
      'https://cdn.jsdelivr.net/lodash/4.14.0/lodash.min.js',
      'javascripts/*.js',
      'tests/*.spec.js'
    ],
    exclude: [],
    preprocessors: {},
    reporters: ['progress'],
    port: 9876,
    colors: true,
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    singleRun: false,
    concurrency: Infinity
  })
};
