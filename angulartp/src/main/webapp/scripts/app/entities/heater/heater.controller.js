'use strict';

angular.module('angulartpApp')
    .controller('HeaterController', function ($scope, Heater, Home) {
        $scope.heaters = [];
        $scope.homes = Home.query();
        $scope.loadAll = function() {
            Heater.query(function(result) {
               $scope.heaters = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Heater.update($scope.heater,
                function () {
                    $scope.loadAll();
                    $('#saveHeaterModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Heater.get({id: id}, function(result) {
                $scope.heater = result;
                $('#saveHeaterModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Heater.get({id: id}, function(result) {
                $scope.heater = result;
                $('#deleteHeaterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Heater.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHeaterConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.heater = {nom: null, modele: null, marque: null, conso: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
