(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('store-branch', {
            parent: 'entity',
            url: '/store-branch',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.storeBranch.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/store-branch/store-branches.html',
                    controller: 'StoreBranchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('storeBranch');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('store-branch-detail', {
            parent: 'entity',
            url: '/store-branch/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.storeBranch.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/store-branch/store-branch-detail.html',
                    controller: 'StoreBranchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('storeBranch');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StoreBranch', function($stateParams, StoreBranch) {
                    return StoreBranch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'store-branch',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('store-branch-detail.edit', {
            parent: 'store-branch-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-branch/store-branch-dialog.html',
                    controller: 'StoreBranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StoreBranch', function(StoreBranch) {
                            return StoreBranch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('store-branch.new', {
            parent: 'store-branch',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-branch/store-branch-dialog.html',
                    controller: 'StoreBranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('store-branch', null, { reload: 'store-branch' });
                }, function() {
                    $state.go('store-branch');
                });
            }]
        })
        .state('store-branch.edit', {
            parent: 'store-branch',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-branch/store-branch-dialog.html',
                    controller: 'StoreBranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StoreBranch', function(StoreBranch) {
                            return StoreBranch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('store-branch', null, { reload: 'store-branch' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('store-branch.delete', {
            parent: 'store-branch',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store-branch/store-branch-delete-dialog.html',
                    controller: 'StoreBranchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StoreBranch', function(StoreBranch) {
                            return StoreBranch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('store-branch', null, { reload: 'store-branch' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
