'use strict';

angular.module('angulartpApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
