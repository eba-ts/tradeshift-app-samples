app.controller('HomeCtrl', function ($scope, $req, $window, $translate, $q) {

    $scope.ui = ts.ui; // So that we can access UIC from our template
    $scope.aside = ts.ui.get('#home-aside');
    $scope.table = ts.ui.get('#home-table');
    $scope.jwtTable = ts.ui.get('#home-table');
    $scope.topbar = ts.ui.get('#home-topbar');
    $scope.card = ts.ui.get('#home-card');
    $scope.intro = ts.ui.get('#home-table');
    $scope.showTab = 0;
    $scope.locale;
    $translate(['Table.ID', 'Table.Character', 'Table.Alignment', // getting our i18n data
        'Topbar.Intro', 'Topbar.Table', 'Topbar.Buttons', 'Topbar.Health', 'Topbar.Documents', 'Topbar.JWTTokenInfo',
        'Topbar.TaskList', 'Topbar.WebHooks', 'Topbar.Errors', 'Message.Oopsie daisy!', 'Message.Good job!', 'Message.Server is' +
        ' running!', 'ErrorMessage.AuthFailed', 'ErrorMessage.Undefined', 'ErrorMessage.NotFound']).then(function (response) {

        $scope.locale = response;

    })
    $q.all([
        $req.getDocuments('invoice'),
        $req.getGridData(),
        $req.getJWTInfo(),
        $req.getTasksPage()
    ]).then(function (response) {
        /***************************/
        $scope.eventList = null;

        $scope.documents = response[0].data;
        /*************************/
        $scope.data = $scope.getArray(response[1].data);
        /*************************/
        $scope.jwtInfo = response[2].data;
        $scope.jwtInfo.formatedExpTime = new Date($scope.jwtInfo.expirationTime).toUTCString();
        $scope.jwtInfo.formatedIssuedAtTime = new Date($scope.jwtInfo.issuedAtTime).toUTCString();
        $scope.tasks = response[3].data;

        taskDateTimeToDate($scope.tasks);

        /* Topbar */
        $scope.topbar
            .tabs([
                {
                    label: $scope.locale['Topbar.Intro'],
                    id: 'tab0',
                    icon: 'ts-icon-discovery',
                    onselect: function () {
                        $scope.showTab = 0;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.Table'],
                    id: 'tab1',
                    icon: 'ts-icon-apps',
                    onselect: function () {
                        $scope.showTab = 1;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.Buttons'],
                    icon: 'ts-icon-code',
                    id: 'tab2',
                    onselect: function () {
                        $scope.showTab = 2;
                        $scope.$apply(); // executing outside of angular
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.Health'],
                    icon: 'ts-icon-activity',
                    id: 'tab3',
                    onselect: function () {
                        $scope.showTab = 3;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.Documents'],
                    icon: 'ts-icon-alldocuments',
                    id: 'tab4',
                    onselect: function () {
                        $scope.showTab = 4;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.JWTTokenInfo'],
                    icon: 'ts-icon-info',
                    id: 'tab5',
                    onselect: function () {
                        $scope.showTab = 5;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.TaskList'],
                    icon: 'ts-icon-heart',
                    id: 'tab6',
                    onselect: function () {
                        $scope.showTab = 6;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.WebHooks'],
                    icon: 'ts-icon-heart',
                    id: 'tab7',
                    onselect: function () {
                        $scope.showTab = 7;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: $scope.locale['Topbar.Errors'],
                    icon: 'ts-icon-heart',
                    id: 'tab8',
                    onselect: function () {
                        $scope.showTab = 8;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                }
            ])
            .dark();


        /* Table */
        $scope.table // to load when data ready
            .selectable()
            .cols([
                {
                    label: $scope.locale['Table.ID'], search: {
                    tip: 'Search by ID',
                    onidle: function (value) {
                        $scope.table.search(0, value);
                    }
                }
                },
                {label: $scope.locale['Table.Character'], flex: 2, wrap: true},
                {label: $scope.locale['Table.Alignment'], flex: 2}
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


        $scope.getNotification = function (type) {
            if (type == 'success') {
                return ts.ui.Notification.success($scope.locale['Message.Good job!']);
            } else if (type == 'alert') {
                return ts.ui.Notification.error($scope.locale['Message.Oopsie daisy!']);
            }
        };

        $scope.completeTask = function (taskId) {
            $req.completeTask(taskId).then(function () {
                $req.getTasksPage().then(function (response) {
                    $scope.tasks = response.data;
                    taskDateTimeToDate($scope.tasks);
                }, function (response) {
                    $scope.showError(response);
                });
            }, function (response) {
                $scope.showError(response);
            });
        };

        $scope.createTask = function () {
            $req.createTask().then(function () {
                $req.getTasksPage().then(function (response) {
                    $scope.tasks = response.data;
                    taskDateTimeToDate($scope.tasks);
                })
            }, function (response) {
                $scope.showError(response);
            });
        };
    }, function (response) {
        $scope.showError(response);
    });

    var taskDateTimeToDate = function (tasks) {
        tasks.forEach(function (task, i) {
            tasks[i].createdOn = new Date(task.createdOn.millis).toUTCString();
            if (tasks[i].completedOn != null) {
                tasks[i].completedOn = new Date(tasks[i].completedOn.millis).toUTCString();
            }
        });
    }

    $scope.showError = function (response) {
        console.error("error : " + response.data.message);
        switch (response.status)
        {
            case 401:
            case 403:
                ts.ui.Notification.error($scope.locale['ErrorMessage.AuthFailed']);
                break;
            case 404:
                ts.ui.Notification.error($scope.locale['ErrorMessage.NotFound']);
                break;
            case 422:
                ts.ui.Notification.error(response.data.message);
                break;
            default:
                ts.ui.Notification.error($scope.locale['ErrorMessage.Undefined']);
                break;
        }
    }

    /* Table Component requires [[],[],[]] structure. So we need to make array of arrays from array of objects */
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

    /* Checks if server is up */
    $scope.checkHealth = function () {
        $req.getHealth().then(function (response) {
            if (typeof response.data === 'object') { // Java returns object
                ts.ui.Notification.success(response.data.status);
            } else { // Node returns string
                ts.ui.Notification.success(response.data);
            }
        }, function (response) {
            ts.ui.Notification.error(response.status + ' ' + response.statusText);
        })
    };

    if (!!window.EventSource) {
        console.log("Event source available");
        var source = new EventSource('/webhooks/eventsStatus');
        source.addEventListener('message', function (e) {
            console.log(e.data);
            $scope.eventList = JSON.parse(e.data);
            $scope.$apply();
            ts.ui.Notification.success("You received " + $scope.eventList.length + " new event");
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

});