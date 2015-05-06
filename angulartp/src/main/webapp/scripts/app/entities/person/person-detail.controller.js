'use strict';

angular.module('angulartpApp')
    .controller('PersonDetailController', function ($scope, $stateParams, Person, Home) {
        $scope.person = {};
        $scope.load = function (id) {
            Person.get({id: id}, function(result) {
              $scope.person = result;
            });
        };
        $scope.load($stateParams.id);
    });
