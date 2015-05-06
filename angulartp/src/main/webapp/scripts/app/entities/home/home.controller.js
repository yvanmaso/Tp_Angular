'use strict';

angular.module('angulartpApp')
    .controller('HomeController', function ($scope, Home, Person, Heater, Device) {
        $scope.homes = [];
        $scope.persons = Person.query();
        $scope.heaters = Heater.query();
        $scope.devices = Device.query();
        $scope.loadAll = function() {
            Home.query(function(result) {
               $scope.homes = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Home.update($scope.home,
                function () {
                    $scope.loadAll();
                    $('#saveHomeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Home.get({id: id}, function(result) {
                $scope.home = result;
                $('#saveHomeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Home.get({id: id}, function(result) {
                $scope.home = result;
                $('#deleteHomeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Home.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHomeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.home = {adresse: null, aire: null, ip: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
