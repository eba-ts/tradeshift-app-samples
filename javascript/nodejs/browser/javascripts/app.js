var app = angular.module('tradeshiftApp', ['pascalprecht.translate']);
app.config(function ($locationProvider, $translateProvider) {
  $locationProvider.html5Mode({enabled: true, requireBase: false});
  $translateProvider
    .useStaticFilesLoader({ // load our locales
      prefix: 'locales/',
      suffix: '.json'
    })
    .useSanitizeValueStrategy('escape')
    .registerAvailableLanguageKeys(['en', 'ru'])
    .determinePreferredLanguage(function(){ // choose the best language based on browser languages
      var translationKeys = $translateProvider.registerAvailableLanguageKeys(),
          browserKeys = navigator.languages,
          preferredLanguage;

      label: for (var i = 0; i < browserKeys.length; i++){
        for (var j = 0; j < translationKeys.length; j++){
          if (browserKeys[i] == translationKeys[j]){
            preferredLanguage = browserKeys[i];
            break label;
          }
        }
      }
      return preferredLanguage;
    });
})
  .factory('$req', function ($http, $location) {
    var url = $location.absUrl(); // setting absolute URL
    return {
      getGridData: function () {
        return $http.get(url + 'demo/get_grid');
      },
      getAccountData: function () {
        return $http.get(url + 'account/info');
      },
      getHealth: function () {
        return $http.get(url + 'health');
      },
      getLocale: function () { // in case if you want to get your locales from server, currently not used
        return $http.get(url + 'locale');
      }
    }
  });
