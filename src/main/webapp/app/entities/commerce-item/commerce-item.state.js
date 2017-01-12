(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('commerce-item', {
            parent: 'entity',
            url: '/commerce-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.commerceItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commerce-item/commerce-items.html',
                    controller: 'CommerceItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commerceItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('commerce-item-detail', {
            parent: 'entity',
            url: '/commerce-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.commerceItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commerce-item/commerce-item-detail.html',
                    controller: 'CommerceItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commerceItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CommerceItem', function($stateParams, CommerceItem) {
                    return CommerceItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'commerce-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('commerce-item-detail.edit', {
            parent: 'commerce-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commerce-item/commerce-item-dialog.html',
                    controller: 'CommerceItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommerceItem', function(CommerceItem) {
                            return CommerceItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commerce-item.new', {
            parent: 'commerce-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commerce-item/commerce-item-dialog.html',
                    controller: 'CommerceItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('commerce-item', null, { reload: 'commerce-item' });
                }, function() {
                    $state.go('commerce-item');
                });
            }]
        })
        .state('commerce-item.edit', {
            parent: 'commerce-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commerce-item/commerce-item-dialog.html',
                    controller: 'CommerceItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommerceItem', function(CommerceItem) {
                            return CommerceItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commerce-item', null, { reload: 'commerce-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commerce-item.delete', {
            parent: 'commerce-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commerce-item/commerce-item-delete-dialog.html',
                    controller: 'CommerceItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CommerceItem', function(CommerceItem) {
                            return CommerceItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commerce-item', null, { reload: 'commerce-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
