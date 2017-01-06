(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreDetailController', StoreDetailController);

    StoreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Store', 'StoreBranch', 'StoreUser'];

    function StoreDetailController($scope, $rootScope, $stateParams, previousState, entity, Store, StoreBranch, StoreUser) {
        var vm = this;

        vm.store = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prototypeApp:storeUpdate', function(event, result) {
            vm.store = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
