'use strict';

angular.module('angulartpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('heater', {
                parent: 'entity',
                url: '/heater',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'angulartpApp.heater.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heater/heaters.html',
                        controller: 'HeaterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heater');
                        return $translate.refresh();
                    }]
                }
            })
            .state('heaterDetail', {
                parent: 'entity',
                url: '/heater/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'angulartpApp.heater.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/heater/heater-detail.html',
                        controller: 'HeaterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('heater');
                        return $translate.refresh();
                    }]
                }
            });
    });
