(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('item-price', {
            parent: 'entity',
            url: '/item-price?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.itemPrice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/item-price/item-prices.html',
                    controller: 'ItemPriceController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('itemPrice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('item-price-detail', {
            parent: 'entity',
            url: '/item-price/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prototypeApp.itemPrice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/item-price/item-price-detail.html',
                    controller: 'ItemPriceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('itemPrice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ItemPrice', function($stateParams, ItemPrice) {
                    return ItemPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'item-price',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('item-price-detail.edit', {
            parent: 'item-price-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item-price/item-price-dialog.html',
                    controller: 'ItemPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ItemPrice', function(ItemPrice) {
                            return ItemPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('item-price.new', {
            parent: 'item-price',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item-price/item-price-dialog.html',
                    controller: 'ItemPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                productId: null,
                                listPrice: null,
                                salePrice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('item-price', null, { reload: 'item-price' });
                }, function() {
                    $state.go('item-price');
                });
            }]
        })
        .state('item-price.edit', {
            parent: 'item-price',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item-price/item-price-dialog.html',
                    controller: 'ItemPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ItemPrice', function(ItemPrice) {
                            return ItemPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('item-price', null, { reload: 'item-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('item-price.delete', {
            parent: 'item-price',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/item-price/item-price-delete-dialog.html',
                    controller: 'ItemPriceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ItemPrice', function(ItemPrice) {
                            return ItemPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('item-price', null, { reload: 'item-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
