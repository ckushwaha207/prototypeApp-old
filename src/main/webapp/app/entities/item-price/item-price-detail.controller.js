(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('ItemPriceDetailController', ItemPriceDetailController);

    ItemPriceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ItemPrice', 'Product'];

    function ItemPriceDetailController($scope, $rootScope, $stateParams, previousState, entity, ItemPrice, Product) {
        var vm = this;

        vm.itemPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prototypeApp:itemPriceUpdate', function(event, result) {
            vm.itemPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
