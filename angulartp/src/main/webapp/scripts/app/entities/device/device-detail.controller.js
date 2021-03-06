'use strict';

angular.module('angulartpApp')
    .controller('DeviceDetailController', function ($scope, $stateParams, Device, Home) {
        $scope.device = {};
        $scope.load = function (id) {
            Device.get({id: id}, function(result) {
              $scope.device = result;
            });
        };
        $scope.load($stateParams.id);
    });
