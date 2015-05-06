'use strict';

angular.module('angulartpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'entity',
                url: '/home',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'angulartpApp.home.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/home/homes.html',
                        controller: 'HomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('home');
                        return $translate.refresh();
                    }]
                }
            })
            .state('homeDetail', {
                parent: 'entity',
                url: '/home/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'angulartpApp.home.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/home/home-detail.html',
                        controller: 'HomeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('home');
                        return $translate.refresh();
                    }]
                }
            });
    });
