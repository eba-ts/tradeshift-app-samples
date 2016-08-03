
app.controller('HomeCtrl', function($scope, $req, $window, $translate, $q) {

    $scope.ui = ts.ui; // So that we can access UIC from our template
    $scope.aside = ts.ui.get('#home-aside');
    $scope.table = ts.ui.get('#home-table');
    $scope.topbar = ts.ui.get('#home-topbar');
    $scope.card = ts.ui.get('#home-card');
    $scope.showTab = 1;

    $q.all([
      $translate(['Table.ID', 'Table.Character', 'Table.Alignment', // getting our i18n data
                  'Topbar.Table', 'Topbar.Form', 'Topbar.Buttons',
                  'Message.Oopsie daisy!', 'Message.Good job!', 'Message.Server is running!']),
      $req.getAccountData(),
      $req.getGridData()
    ]).then(function(response){
      var locale = response[0];
      /***************************/
      $scope.info = response[1].data['ts:CompanyAccountInfo'];
      if ($scope.info) {
        $scope.card.render({
          id: $scope.info['ts:CompanyAccountId'][0],
          data: {
            name: $scope.info['ts:CompanyName'] ? $scope.info['ts:CompanyName'][0] : 'none',
            location: $scope.info['ts:Country'] ? $scope.info['ts:Country'][0] : 'none',
            size: $scope.info['ts:Size'][0] ? $scope.info['ts:Size'][0] : 'none',
            connection: 0,
            industry: $scope.info['ts:Industry'] ? $scope.info['ts:Industry'][0] : 'none',
            logo: $scope.info['ts:LogoURL'] ? $scope.info['ts:LogoURL'][0] : 'none'
          }
        })
      }
      /*************************/
      $scope.data = $scope.getArray(response[2].data);

      /* Table */
      $scope.table // to load when data ready
        .selectable()
        .cols([
          {
            label: locale['Table.ID'], search: {
            tip: 'Search by ID',
            onidle: function (value) {
              $scope.table.search(0, value);
            }}
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

      /* Topbar */
      $scope.topbar
        .tabs([
          {
            label: locale['Topbar.Table'],
            id: 'tab1',
            icon: 'ts-icon-apps',
            onselect : function() {
              $scope.showTab = 1;
              $scope.$apply();
              scrollTo(0,0);
            }
          },
          {
            label: locale['Topbar.Buttons'],
            icon: 'ts-icon-activity',
            id: 'tab2',
            onselect: function() {
              $scope.showTab = 2;
              $scope.$apply(); // executing outside of angular
              scrollTo(0,0);
            }
          },
          {
            label: locale['Topbar.Form'],
            icon: 'ts-icon-code',
            id: 'tab3',
            onselect: function() {
              $scope.showTab = 3;
              $scope.$apply();
              scrollTo(0,0);
            }
          }
        ])
        .green();

      $scope.getNotification = function(type){
        if (type == 'success'){
          return ts.ui.Notification.success(locale['Message.Good job!']);
        } else if (type == 'alert'){
          return ts.ui.Notification.error(locale['Message.Oopsie daisy!']);
        }
      }
    });

    /* get array of arrays from array of objects */
    $scope.getArray = function(data){
      var result = [];
      data.forEach(function(item){
        var array = [];
        for (p in item){
          array.push(item[p] + ''); // here we get array of strings
        }
        result.push(array);
      });
      return result; // array of arrays
    };

    /* Checks if server is up */
    $scope.checkHealth = function(){
      $req.getHealth().then(function(response){
        ts.ui.Notification.success(response.data);
      }, function(response){
        ts.ui.Notification.error(response.status + ' ' + response.statusText);
      })
    };
  });