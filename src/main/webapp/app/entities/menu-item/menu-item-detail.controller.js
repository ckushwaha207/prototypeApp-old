(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('MenuItemDetailController', MenuItemDetailController);

    MenuItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MenuItem'];

    function MenuItemDetailController($scope, $rootScope, $stateParams, previousState, entity, MenuItem) {
        var vm = this;

        vm.menuItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prototypeApp:menuItemUpdate', function(event, result) {
            vm.menuItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
