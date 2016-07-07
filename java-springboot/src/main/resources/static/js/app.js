angular.module('tradeshiftApp', ['ngRoute'])
    .factory('$req', function ($http, $location) {
        var url = $location.protocol() + '://' + $location.host() + ':' + $location.port(); // to work both locally and on the server
        return {
            getData: function () {
                return $http.get(url + '/demo/get_grid');
            },
            getToken: function () {
                return $http.get(url + '/oauth2/get_token');
            }
        }
    })
    .factory('$docs', function ($http, $location) {
        var url = $location.protocol() + '://' + $location.host() + ':' + $location.port(); // to work both locally and on the server
        return {
            getData: function (documentType) {
                return $http.get(url + '/document/get_documents', {
                    params: {documentType: documentType}
                });
            }
        }
    })
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: 'views/home.html', controller: 'HomeCtrl'})
            .otherwise('/');
    })
    .controller('HomeCtrl', function ($scope, $req, $docs, $window, $location) {

        if ($window.sessionStorage.getItem("isAuthorized") === null) {
            $window.location.href = $location.protocol() + '://' + $location.host() + ':' + $location.port() + "/oauth2/get_token";
            $window.sessionStorage.setItem("isAuthorized", true);
        }

        $scope.ui = ts.ui; // So that we can access UIC from HTML
        $scope.aside = ts.ui.get('#home-aside');
        $scope.table = ts.ui.get('#home-table');
        $scope.topbar = ts.ui.get('#home-topbar');
        $scope.showTab = 1;
        $req.getData().then(function (response) {
            $scope.data = $scope.getArray(response.data);

            /* Table */
            $scope.table // to load when data ready
                .selectable()
                .cols([
                    {
                        label: 'ID', search: {
                        tip: 'Search by ID',
                        onidle: function (value) {
                            $scope.table.search(0, value);
                        }
                    }
                    },
                    {label: 'Character', flex: 2, wrap: true},
                    {label: 'Alignment', flex: 2}
                ])
                .rows($scope.data)
                .sortable(function (index, ascending) {
                    $scope.table.sort(index, ascending);
                })
                .max(3)
                .editable(function onedit(ri, ci, value) {
                    this.cell(ri, ci, value);
                })
                .sort(0, true);
        });
        /* Topbar */
        $scope.topbar
            .tabs([
                {
                    label: 'Table',
                    id: 'tab1',
                    icon: 'ts-icon-apps',
                    onselect: function () {
                        $scope.showTab = 1;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: 'Buttons',
                    icon: 'ts-icon-activity',
                    id: 'tab2',
                    onselect: function () {
                        $scope.showTab = 2;
                        $scope.$apply(); // executing outside of angular
                        scrollTo(0, 0);
                    }
                },
                {
                    label: 'Form',
                    icon: 'ts-icon-code',
                    id: 'tab3',
                    onselect: function () {
                        $scope.showTab = 3;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: 'DOCUMENTS',
                    icon: 'ts-icon-activity',
                    id: 'tab4',
                    onselect: function () {
                        $scope.showTab = 4;
                        $scope.$apply();
                        $docs.getData('invoice').then(function (response) {
                            $scope.documents = response.data;
                        })
                    }
                },
            ])
            .green();

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
        /* get array of arrays from array of objects */
        $scope.getArray = function (data) {
            var result = [];
            data.forEach(function (item) {
                var array = [];
                for (p in item) {
                    array.push(item[p] + ''); // here we get array of strings
                }
                result.push(array);
            });
            return result; // array of arrays
        };
        $scope.submitForm = function () {
            ts.ui.Notification.success('Submit message :)');
        }
    })

