(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreUserDetailController', StoreUserDetailController);

    StoreUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StoreUser', 'Store'];

    function StoreUserDetailController($scope, $rootScope, $stateParams, previousState, entity, StoreUser, Store) {
        var vm = this;

        vm.storeUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prototypeApp:storeUserUpdate', function(event, result) {
            vm.storeUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
