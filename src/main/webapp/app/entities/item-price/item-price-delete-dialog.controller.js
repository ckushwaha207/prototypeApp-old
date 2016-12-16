(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('ItemPriceDeleteController',ItemPriceDeleteController);

    ItemPriceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ItemPrice'];

    function ItemPriceDeleteController($uibModalInstance, entity, ItemPrice) {
        var vm = this;

        vm.itemPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ItemPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
