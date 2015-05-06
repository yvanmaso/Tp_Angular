'use strict';

angular.module('angulartpApp')
    .factory('Person', function ($resource) {
        return $resource('api/persons/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var datenaissFrom = data.datenaiss.split("-");
                    data.datenaiss = new Date(new Date(datenaissFrom[0], datenaissFrom[1] - 1, datenaissFrom[2]));
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
