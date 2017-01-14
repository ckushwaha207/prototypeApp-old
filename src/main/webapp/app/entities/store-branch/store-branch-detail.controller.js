(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreBranchDetailController', StoreBranchDetailController);

    StoreBranchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StoreBranch', 'Location', 'Store'];

    function StoreBranchDetailController($scope, $rootScope, $stateParams, previousState, entity, StoreBranch, Location, Store) {
        var vm = this;

        vm.storeBranch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prototypeApp:storeBranchUpdate', function(event, result) {
            vm.storeBranch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
