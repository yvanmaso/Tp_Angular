'use strict';

angular.module('angulartpApp')
    .controller('HeaterDetailController', function ($scope, $stateParams, Heater, Home) {
        $scope.heater = {};
        $scope.load = function (id) {
            Heater.get({id: id}, function(result) {
              $scope.heater = result;
            });
        };
        $scope.load($stateParams.id);
    });
