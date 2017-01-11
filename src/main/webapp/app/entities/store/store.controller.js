(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreController', StoreController);

    StoreController.$inject = ['$scope', '$state', 'Store', 'DashboardUIService'];

    function StoreController ($scope, $state, Store, DashboardUIService) {
        var vm = this;

        vm.stores = [];

        loadAll();

        function loadAll() {
            Store.query(function(result) {
                vm.stores = result;
                vm.searchQuery = null;
            });
        }
    }
})();
