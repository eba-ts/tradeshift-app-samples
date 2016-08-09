angular.module('tradeshiftApp', ['ngRoute','pascalprecht.translate'])
    .factory('$req', function ($http, $location) {
        var url = $location.protocol() + '://' + $location.host() + ':' + $location.port(); // to work both locally and on the server
        return {
            getData: function () {
                return $http.get(url + '/demo/grid-data');

            },
            getLocale: function () { //get your locale from server
                return $http.get(url + '/locale');
            },
            getHealth: function () {
                return $http.get(url + '/health');
            },
        }
    })
    .factory('$docs', function ($http, $location) {
        var url = $location.protocol() + '://' + $location.host() + ':' + $location.port(); // to work both locally and on the server
        return {
            getData: function (documentType) {
                return $http.get(url + '/document/documents',{
                    params: {documentType: documentType}
                });
            }
        }
    })
    .factory('$cInfo', function ($http, $location) {
        var url = $location.protocol() + '://' + $location.host() + ':' + $location.port(); // to work both locally and on the server
        return {
            getData: function () {
                return $http.get(url + '/account/info');
            }
        }
    })

    .config(function ($routeProvider, $translateProvider) {
        $routeProvider
            .when('/', {templateUrl: 'views/home.html', controller: 'HomeCtrl'})
            .otherwise('/');
        $translateProvider.useSanitizeValueStrategy('escape');
        $translateProvider.useUrlLoader('/locale/translations');//get Translation from back-end
        $translateProvider.preferredLanguage('en');

    })
    .controller('HomeCtrl', function ($scope, $req, $docs, $cInfo ,$window, $location, $translate) {
        $scope.ui = ts.ui; // So that we can access UIC from HTML
        $scope.aside = ts.ui.get('#home-aside');
        $scope.table = ts.ui.get('#home-table');
        $scope.topbar = ts.ui.get('#home-topbar');
        $scope.companycard = ts.ui.get('#home-companycard');
        $scope.showTab = 1;

        $req.getLocale().then(function (response) {
            $translate.use(response.data);
            $translate([
                'Topbar.Table',
                'Topbar.Buttons',
                'Topbar.Form',
                'Topbar.Info',
                'Topbar.Documents',
                'Table.Character',
                'Table.Alignment',
                'Table.ID',
                'Message.Search by ID'
            ]).then(function (translation) {
                $scope.trans = translation;

                /* Topbar */
                $scope.topbar
                    .tabs([
                        {
                            label: $scope.trans['Topbar.Table'], //Table translated label
                            id: 'tab1',
                            icon: 'ts-icon-apps',
                            onselect: function () {
                                $scope.showTab = 1;
                                $scope.$apply();
                                scrollTo(0, 0);
                            }
                        },
                        {
                            label: $scope.trans['Topbar.Buttons'], //'Buttons'translated label
                            icon: 'ts-icon-activity',
                            id: 'tab2',
                            onselect: function () {
                                $scope.showTab = 2;
                                $scope.$apply(); // executing outside of angular
                                scrollTo(0, 0);
                            }
                        },
                        {
                            label: $scope.trans['Topbar.Form'], //'Form'
                            icon: 'ts-icon-code',
                            id: 'tab3',
                            onselect: function () {
                                $scope.showTab = 3;
                                $scope.$apply();
                                scrollTo(0, 0);
                            }
                        },
                        {
                            label: $scope.trans['Topbar.Info'], //'Company Info',
                            icon: 'ts-icon-info',
                            id: 'tab4',
                            onselect: function () {
                                $scope.showTab = 4;
                                $scope.$apply();
                                $cInfo.getData().then(function (response) {
                                    $scope.companycard.render (
                                        response.data
                                    )
                                })
                            }
                        },{
                            label: $scope.trans['Topbar.Documents'], //'DOCUMENTS',
                            icon: 'ts-icon-activity',
                            id: 'tab5',
                            onselect: function () {
                                $scope.showTab = 5;
                                $scope.$apply();
                                $docs.getData('invoice').then(function (response) {
                                    $scope.documents = response.data;
                                })
                            }
                        },

                    ])
                    .green();

            });

            $req.getData().then(function (response) {

                $scope.data = $scope.getArray(response.data);

                /* Table */
                $scope.table // to load when data ready
                    .selectable()
                    .cols([
                        {
                            label: $scope.trans['Table.ID'], search: {
                            tip: $scope.trans['Message.Search by ID'],
                            onidle: function (value) {
                                $scope.table.search(0, value);
                            }
                        }
                        },
                        {label: $scope.trans['Table.Alignment'], flex: 2, wrap: true},
                        {label: $scope.trans['Table.Character'], flex: 2}
                    ])
                    .rows(
                    $scope.data
                )
                    .sortable(function (index, ascending) {
                        $scope.table.sort(index, ascending);
                    })
                    .max(3)
                    .editable(function onedit(ri, ci, value) {
                        this.cell(ri, ci, value);
                    })
                    .sort(0, true);
            });


            $scope.getConfirm = function () {
                ts.ui.Dialog.confirm('Are you sure?', {
                    onaccept: function () {
                        ts.ui.Notification.success('You are sure.');
                    },
                    oncancel: function () {
                        ts.ui.Notification.warning('You are not sure.');
                    }
                });
            };

            $scope.submitForm = function () {
                ts.ui.Notification.success('Submit message :)');
            }

            $scope.buttonMessage = function () {
                ts.ui.Notification.success('Submit message :)');
            }

            /* Checks if server is up */
            $scope.checkHealth = function(){
                $req.getHealth().then(function(response){
                    ts.ui.Notification.success(response.data);
                }, function(response){
                    ts.ui.Notification.error(response.status + ' ' + response.statusText);
                })
            };

            // builds row array for Table example from Json object
            $scope.getArray = function (data) {
                var result = [];
                data.forEach(function (item) {
                    var row = [item.id, item.alignment, item.character]
                    result.push(row);
                });
                return result;
            };
        });
    });