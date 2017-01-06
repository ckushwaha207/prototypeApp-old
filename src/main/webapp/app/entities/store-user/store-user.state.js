(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('store-user', {
            parent: 'entity',
            url: '/store-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.storeUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/store-user/store-users.html',
                    controller: 'StoreUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('storeUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('store-user-detail', {
            parent: 'entity',
            url: '/store-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.storeUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/store-user/store-user-detail.html',
                    controller: 'StoreUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('storeUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StoreUser', function($stateParams, StoreUser) {
                    return StoreUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'store-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('store-user-detail.edit', {
            parent: 'store-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-user/store-user-dialog.html',
                    controller: 'StoreUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StoreUser', function(StoreUser) {
                            return StoreUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('store-user.new', {
            parent: 'store-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-user/store-user-dialog.html',
                    controller: 'StoreUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                login: null,
                                firstName: null,
                                lastName: null,
                                email: null,
                                activated: false,
                                password: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('store-user', null, { reload: 'store-user' });
                }, function() {
                    $state.go('store-user');
                });
            }]
        })
        .state('store-user.edit', {
            parent: 'store-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-user/store-user-dialog.html',
                    controller: 'StoreUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StoreUser', function(StoreUser) {
                            return StoreUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('store-user', null, { reload: 'store-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('store-user.delete', {
            parent: 'store-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-user/store-user-delete-dialog.html',
                    controller: 'StoreUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StoreUser', function(StoreUser) {
                            return StoreUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('store-user', null, { reload: 'store-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
