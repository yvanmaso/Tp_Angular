'use strict';

angular.module('angulartpApp')
    .controller('DeviceController', function ($scope, Device, Home) {
        $scope.devices = [];
        $scope.homes = Home.query();
        $scope.loadAll = function() {
            Device.query(function(result) {
               $scope.devices = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Device.update($scope.device,
                function () {
                    $scope.loadAll();
                    $('#saveDeviceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Device.get({id: id}, function(result) {
                $scope.device = result;
                $('#saveDeviceModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Device.get({id: id}, function(result) {
                $scope.device = result;
                $('#deleteDeviceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Device.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDeviceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.device = {nom: null, modele: null, marque: null, conso: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
