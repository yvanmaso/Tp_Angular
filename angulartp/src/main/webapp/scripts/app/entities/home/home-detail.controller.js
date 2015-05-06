'use strict';

angular.module('angulartpApp')
    .controller('HomeDetailController', function ($scope, $stateParams, Home, Person, Heater, Device) {
        $scope.home = {};
        $scope.load = function (id) {
            Home.get({id: id}, function(result) {
              $scope.home = result;
            });
        };
        $scope.load($stateParams.id);
    });
