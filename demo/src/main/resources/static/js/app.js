  angular.module('tradeshiftApp', [])
    .constant('Settings', {
      url: 'http://localhost:8080'
    })
    .factory('$req', function ($http, Settings) {
      return {
        getData: function(){
          return $http.get(Settings.url + '/demo/get_grid');
        },
        getToken: function(){
          return $http.get(Settings.url + '/oauth2/get_token');
        }
      }
    })
    .controller('HomeCtrl', function ($scope, $req) {
      $scope.ui = ts.ui; // So that we can access UIC from HTML
      $scope.aside = ts.ui.get('#home-aside');
      $scope.table = ts.ui.get('#home-table');
      $scope.topbar = ts.ui.get('#home-topbar');
      $scope.showTab = 1;
      $req.getData().then(function(response){
        console.log(response);
      });
      /* Table */
      $scope.table
        .selectable()
        .cols([
          {label: 'ID', search: {
              tip: 'Search by ID',
              onidle: function(value) {
                $scope.table.search(0, value);
              }
            }
          },
          {label: 'Character', flex: 2, wrap: true},
          {label: 'Alignment', flex: 2}
        ])
        .rows([
          [1, 'Paladin Knight', 'Lawful Good'],
          [2, 'Barbarian Queen', 'Neutral Evil'],
          [3, 'Global Senior Vice President of Sales', 'Chaotic Evil'],
          [4, 'Jonathan the Piggy', 'Glorious Good'],
          [5, 'Potato Chip', 'Radiant Good']
        ])
        .sortable(function(index, ascending) {
          $scope.table.sort(index, ascending);
        })
        .max(3)
        .editable(function onedit(ri, ci, value){
          this.cell(ri, ci, value);
        })
        .sort(0, true);

      /* Topbar */
      $scope.topbar
        .tabs([
          {
            label: 'Table',
            id: 'tab1',
            icon: 'ts-icon-apps',
            onselect : function() {
              $scope.showTab = 1;
              $scope.$apply();
              scrollTo(0,0);
            }
          },
          {
            label: 'Buttons',
            icon: 'ts-icon-activity',
            id: 'tab2',
            onselect: function() {
              $scope.showTab = 2;
              $scope.$apply(); // executing outside of angular
              scrollTo(0,0);
            }
          },
          {
            label: 'Form',
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

      $scope.getConfirm = function(){
        ts.ui.Dialog.confirm('Are you sure?', {
          onaccept: function() {
            ts.ui.Notification.success('You are sure.');
          },
          oncancel: function() {
            ts.ui.Notification.warning('You are not sure.');
          }
        });
      };

      $scope.submitForm = function(){
        ts.ui.Notification.success('Submit message :)');
      }
    });