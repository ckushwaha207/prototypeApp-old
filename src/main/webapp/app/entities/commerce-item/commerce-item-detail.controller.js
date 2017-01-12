(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('CommerceItemDetailController', CommerceItemDetailController);

    CommerceItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CommerceItem', 'Product', 'Order'];

    function CommerceItemDetailController($scope, $rootScope, $stateParams, previousState, entity, CommerceItem, Product, Order) {
        var vm = this;

        vm.commerceItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prototypeApp:commerceItemUpdate', function(event, result) {
            vm.commerceItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
