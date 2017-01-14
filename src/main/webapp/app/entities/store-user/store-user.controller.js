(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreUserController', StoreUserController);

    StoreUserController.$inject = ['$scope', '$state', 'StoreUser'];

    function StoreUserController ($scope, $state, StoreUser) {
        var vm = this;

        vm.storeUsers = [];

        loadAll();

        function loadAll() {
            StoreUser.query(function(result) {
                vm.storeUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
