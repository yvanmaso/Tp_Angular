'use strict';

angular.module('angulartpApp')
    .factory('Heater', function ($resource) {
        return $resource('api/heaters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
