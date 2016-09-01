app.controller('HomeCtrl', function ($scope, $req, $window, $translate, $q) {

    $scope.ui = ts.ui; // So that we can access UIC from our template
    $scope.aside = ts.ui.get('#home-aside');
    $scope.table = ts.ui.get('#home-table');
    $scope.jwtTable = ts.ui.get('#home-table');
    $scope.topbar = ts.ui.get('#home-topbar');
    $scope.card = ts.ui.get('#home-card');
    $scope.intro = ts.ui.get('#home-table');
    $scope.showTab = 0;

    $q.all([
        $translate(['Table.ID', 'Table.Character', 'Table.Alignment', // getting our i18n data
            'Topbar.Intro', 'Topbar.Table', 'Topbar.Buttons', 'Topbar.Health', 'Topbar.Documents', 'Topbar.JWTTokenInfo', 'Topbar.TaskList',
            'Message.Oopsie daisy!', 'Message.Good job!', 'Message.Server is running!']),
        $req.getDocuments('invoice'),
        $req.getGridData(),
        $req.getJWTInfo(),
        $req.getTasksPage()
    ]).then(function (response) {
        var locale = response[0];
        /***************************/
        $scope.documents = response[1].data;
        /*************************/
        $scope.data = $scope.getArray(response[2].data);
        /*************************/
        $scope.jwtInfo = response[3].data;
        $scope.jwtInfo.formatedExpTime = new Date($scope.jwtInfo.expirationTime).toUTCString();
        $scope.jwtInfo.formatedIssuedAtTime = new Date($scope.jwtInfo.issuedAtTime).toUTCString();
        $scope.tasks = response[4].data;

        taskDateTimeToDate($scope.tasks);

                /* Topbar */
        $scope.topbar
            .tabs([
                {
                    label: locale['Topbar.Intro'],
                    id: 'tab0',
                    icon: 'ts-icon-discovery',
                    onselect: function () {
                        $scope.showTab = 0;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: locale['Topbar.Table'],
                    id: 'tab1',
                    icon: 'ts-icon-apps',
                    onselect: function () {
                        $scope.showTab = 1;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: locale['Topbar.Buttons'],
                    icon: 'ts-icon-code',
                    id: 'tab2',
                    onselect: function () {
                        $scope.showTab = 2;
                        $scope.$apply(); // executing outside of angular
                        scrollTo(0, 0);
                    }
                },
                {
                    label: locale['Topbar.Health'],
                    icon: 'ts-icon-activity',
                    id: 'tab3',
                    onselect: function () {
                        $scope.showTab = 3;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: locale['Topbar.Documents'],
                    icon: 'ts-icon-alldocuments',
                    id: 'tab4',
                    onselect: function () {
                        $scope.showTab = 4;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: locale['Topbar.JWTTokenInfo'],
                    icon: 'ts-icon-info',
                    id: 'tab5',
                    onselect: function () {
                        $scope.showTab = 5;
                        $scope.$apply();
                        scrollTo(0, 0);
                    }
                },
                {
                    label: locale['Topbar.TaskList'],
                    icon: 'ts-icon-heart',
                    id: 'tab6',
                    onselect: function () {
                        $scope.showTab = 6;
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
                    label: locale['Table.ID'], search: {
                    tip: 'Search by ID',
                    onidle: function (value) {
                        $scope.table.search(0, value);
                    }
                }
                },
                {label: locale['Table.Character'], flex: 2, wrap: true},
                {label: locale['Table.Alignment'], flex: 2}
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
                return ts.ui.Notification.success(locale['Message.Good job!']);
            } else if (type == 'alert') {
                return ts.ui.Notification.error(locale['Message.Oopsie daisy!']);
            }
        };

        $scope.completeTask = function (taskId) {
            $req.completeTask(taskId).then(function () {
                $req.getTasksPage().then(function (response) {
                    $scope.tasks = response.data;
                    taskDateTimeToDate($scope.tasks);
                })
            })
        };

        $scope.createTask = function () {
            $req.createTask().then(function () {
                $req.getTasksPage().then(function (response) {
                    $scope.tasks = response.data;
                    taskDateTimeToDate($scope.tasks);
                })
            })
        };
    });

    var taskDateTimeToDate = function (tasks) {
        tasks.forEach(function (task, i) {
            tasks[i].createdOn = new Date(task.createdOn.millis).toUTCString();
            if (tasks[i].completedOn != null) {
                tasks[i].completedOn = new Date(tasks[i].completedOn.millis).toUTCString();
            }
        });
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
});