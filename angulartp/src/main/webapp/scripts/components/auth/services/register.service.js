'use strict';

angular.module('angulartpApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


