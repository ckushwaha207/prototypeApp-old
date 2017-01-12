(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('CommerceItemController', CommerceItemController);

    CommerceItemController.$inject = ['$scope', '$state', 'CommerceItem'];

    function CommerceItemController ($scope, $state, CommerceItem) {
        var vm = this;

        vm.commerceItems = [];

        loadAll();

        function loadAll() {
            CommerceItem.query(function(result) {
                vm.commerceItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
