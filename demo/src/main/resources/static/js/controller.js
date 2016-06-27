  angular.module('tradeshiftApp')
    .controller('HomeCtrl', ['$scope', function ($scope) {
      $scope.ui = ts.ui; // So that we can access UIC from HTML
      $scope.aside = ts.ui.get('#home-aside');
      $scope.table = ts.ui.get('#home-table');
      $scope.topbar = ts.ui.get('#home-topbar');

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
              $('#tab2').hide();
              $('#tab1').show();
              $('#tab3').hide();
              $('#tab4').hide();

              scrollTo(0,0);
            }
          },
          {
            label: 'Buttons',
            icon: 'ts-icon-activity',
            id: 'tab2',
            onselect: function() {
              $('#tab2').show();
              $('#tab1').hide();
              $('#tab3').hide();
              $('#tab4').hide();
              scrollTo(0,0);
            }
          },
          {
            label: 'Form',
            icon: 'ts-icon-code',
            id: 'tab3',
            onselect: function() {
              $('#tab3').show();
              $('#tab1').hide();
              $('#tab2').hide();
              $('#tab4').hide();
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
    }]);