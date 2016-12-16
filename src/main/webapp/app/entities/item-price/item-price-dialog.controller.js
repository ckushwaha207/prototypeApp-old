(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('ItemPriceDialogController', ItemPriceDialogController);

    ItemPriceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ItemPrice', 'Product'];

    function ItemPriceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ItemPrice, Product) {
        var vm = this;

        vm.itemPrice = entity;
        vm.clear = clear;
        vm.save = save;
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.itemPrice.id !== null) {
                ItemPrice.update(vm.itemPrice, onSaveSuccess, onSaveError);
            } else {
                ItemPrice.save(vm.itemPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:itemPriceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
