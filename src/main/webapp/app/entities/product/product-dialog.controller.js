(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('ProductDialogController', ProductDialogController);

    ProductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Product', 'ItemPrice', 'Category'];

    function ProductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Product, ItemPrice, Category) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.save = save;
        vm.prices = ItemPrice.query({filter: 'product-is-null'});
        $q.all([vm.product.$promise, vm.prices.$promise]).then(function() {
            if (!vm.product.priceId) {
                return $q.reject();
            }
            return ItemPrice.get({id : vm.product.priceId}).$promise;
        }).then(function(price) {
            vm.prices.push(price);
        });
        vm.categories = Category.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
