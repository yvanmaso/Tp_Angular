'use strict';

angular.module('angulartpApp')
    .controller('PersonController', function ($scope, Person, Home) {
        $scope.persons = [];
        $scope.homes = Home.query();
        $scope.loadAll = function() {
            Person.query(function(result) {
               $scope.persons = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Person.update($scope.person,
                function () {
                    $scope.loadAll();
                    $('#savePersonModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Person.get({id: id}, function(result) {
                $scope.person = result;
                $('#savePersonModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Person.get({id: id}, function(result) {
                $scope.person = result;
                $('#deletePersonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Person.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePersonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.person = {nom: null, prenom: null, genre: null, mail: null, datenaiss: null, profil: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
