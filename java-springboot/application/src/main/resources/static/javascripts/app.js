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
        .determinePreferredLanguage(function () { // choose the best language based on browser languages
            var translationKeys = $translateProvider.registerAvailableLanguageKeys(),
                browserKeys = navigator.languages,
                preferredLanguage;

            label: for (var i = 0; i < browserKeys.length; i++) {
                for (var j = 0; j < translationKeys.length; j++) {
                    if (browserKeys[i] == translationKeys[j]) {
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
                return $http.get(url + 'demo/grid-data');
            },
            getDocuments: function (documentType) { // Call to Tradeshift API
                return $http.get(url + '/document/documents', {
                    params: {documentType: documentType}
                });
            },
            getHealth: function () {
                return $http.get(url + 'health');
            },
            getJWTInfo: function () {
                return $http.get(url + '/jwt/id-token');
            },
            getTasksPage: function () {
                return $http.get(url + '/tasks?limit=25&pageNumber=0');
            },
            getLocale: function () { // in case if you want to get your locales from server, currently not used
                return $http.get(url + 'locale');
            },
            completeTask: function (taskId) { // in case if you want to get your locales from server, currently not used
                return $http.put(url + 'tasks/complete/' + taskId,                                       // 1. url
                    {},                                           
                    { params: { action: "complete" } }   
                );
            },
            createTask: function () { // in case if you want to get your locales from server, currently not used
                return $http.post(url + 'tasks');
            }
        }
    });

    if (!!window.EventSource) {
        console.log("Event source available");
        var source = new EventSource('/webhooks/eventsStatus');
        source.addEventListener('message', function (e) {
            console.log(e.data);
            ts.ui.Notification.success(e.data);
            $docs.getData('invoice').then(function (response) {
                $scope.documents = response.data;
            })
        });

        source.addEventListener('open', function (e) {
            console.log("Connection was opened.");
        }, false);

        source.addEventListener('error', function (e) {
            console.log("Connection was closed.");
        }, false);
    } else {
        console.log("No SSE available");
    }
