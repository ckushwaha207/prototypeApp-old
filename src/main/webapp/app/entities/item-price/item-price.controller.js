(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('ItemPriceController', ItemPriceController);

    ItemPriceController.$inject = ['$scope', '$state', 'ItemPrice'];

    function ItemPriceController ($scope, $state, ItemPrice) {
        var vm = this;

        vm.itemPrices = [];

        loadAll();

        function loadAll() {
            ItemPrice.query(function(result) {
                vm.itemPrices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
