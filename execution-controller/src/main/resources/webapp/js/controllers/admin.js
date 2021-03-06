/*******************************************************************************
 * (C) Copyright 2016 Jerome Comte and Dorian Cransac
 *  
 * This file is part of STEP
 *  
 * STEP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * STEP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *  
 * You should have received a copy of the GNU Affero General Public License
 * along with STEP.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
angular.module('adminControllers', [ 'dataTable', 'step' ])

.controller('AdminCtrl', ['$scope', 'stateStorage',
    function($scope) {
      $scope.autorefresh = true;
   }])   

.controller('UserListCtrl',
    function($scope, $interval, $http, helpers, $uibModal) {
      $scope.datatable = {}
      
      $scope.loadTable = function loadTable() {
        $http.get("rest/admin/users").then(
          function(response) {
            var data = response.data;
            var dataSet = [];
            for (i = 0; i < data.length; i++) {
              dataSet[i] = [ data[i].username, data[i].role];
            }
            $scope.tabledef.data = dataSet;
          });
        };

        $scope.tabledef = {};
        $scope.tabledef.columns = [ { "title" : "Username"}, { "title" : "Role" }, 
                                    {"title":"Actions", "width":"120px", "render":function ( data, type, row ) {
              var html = '<div class="input-group">' +
                '<div class="btn-group">' +
                '<button type="button" class="btn btn-default" aria-label="Left Align" onclick="angular.element(\'#UserListCtrl\').scope().editUser(\''+row[0]+'\')">' +
                '<span class="glyphicon glyphicon glyphicon glyphicon-pencil" aria-hidden="true"></span>' +
                '<button type="button" class="btn btn-default" aria-label="Left Align" onclick="angular.element(\'#UserListCtrl\').scope().removeUser(\''+row[0]+'\')">' +
                '<span class="glyphicon glyphicon glyphicon glyphicon-trash" aria-hidden="true"></span>' +
                '</button> '+
                '</div></div>';
              return html;
            }}];

        $scope.tabledef.actions = [{"label":"Reset passwords","action":function() {$scope.forAllSelected('resetPwd')}},
                                   {"label":"Remove","action":function() {$scope.forAllSelected('removeUser')}}];
        
        $scope.loadTable();
        
        $scope.forAllSelected = function(fctName) {
          var rows = $scope.datatable.getSelection().selectedItems;
          
          for(i=0;i<rows.length;i++) {
            $scope[fctName](rows[i][0]);       
          }
        };
          
        $scope.resetPwd = function(id) {
          $http.post("rest/admin/user/"+id+"/resetpwd").then(function() {
            $scope.loadTable();
          });
        }
        
        $scope.removeUser = function(username) {
          $http.delete("rest/admin/user/"+username).then(function() {
            $scope.loadTable();
          });
        }
        
        $scope.addUser = function() {
          $scope.showEditUserPopup({});
        }
        
        $scope.editUser = function(username) {
          $http.get("rest/admin/user/"+username).then(function(response) {
            var user = response.data;
            $scope.showEditUserPopup(user);
          });
        }
        
        $scope.showEditUserPopup = function(user) {
          var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            templateUrl: 'editUserModalContent.html',
            controller: 'editUserModalCtrl',
            resolve: {
              user: function () {
                return user;
              }
            }
            });
            
            modalInstance.result.then(function() {$scope.loadTable()}, function () {}); 
          }

      })
      
.controller('editUserModalCtrl', function ($scope, $uibModalInstance, $http, $location, AuthService, user) {
   $scope.roles = AuthService.getConf().roles;
   $scope.user = user;
   
   if(!user.role) {
     user.role = $scope.roles[0];     
   }
   
   $scope.save = function() {
     $http.post("rest/admin/user", user).then(function() {
       $uibModalInstance.close();  
     });
   }
   
   $scope.cancel = function() {
     $uibModalInstance.close();     
   }
})

.controller('MyAccountCtrl',
    function($scope, $rootScope, $interval, $http, helpers, $uibModal) {
      $scope.$state = 'myaccount';
      
      $scope.changePwd=function() {
        $uibModal.open({animation: false,templateUrl: 'partials/changePasswordForm.html',
          controller: 'ChangePasswordModalCtrl', resolve: {}});  
      }
      
      $scope.user = {};
      $http.get("rest/admin/user/"+$rootScope.context.userID).then(function(response) {
        var user = response.data;
        $scope.user=user;
      });
    })

.controller('ChangePasswordModalCtrl', function ($scope, $rootScope, $uibModalInstance, $http, $location) {
  
  $scope.model = {newPwd:""};
  $scope.repeatPwd = ""
  
  $scope.save = function () {  
    if($scope.repeatPwd!=$scope.model.newPwd) {
      $scope.error = "New password doesn't match"
    } else {
      $http.post("rest/admin/myaccount/changepwd",$scope.model).then(function() {
        $uibModalInstance.close();
      },function() {
        $scope.error = "Unable to change password. Please contact your administrator.";
      });      
    }
  };

  $scope.cancel = function () {
    $uibModalInstance.close();
  };
});
