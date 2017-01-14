(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('OrderDialogController', OrderDialogController);

    OrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Order', 'CommerceItem'];

    function OrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Order, CommerceItem) {
        var vm = this;

        vm.order = entity;
        vm.clear = clear;
        vm.save = save;
        vm.commerceitems = CommerceItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.order.id !== null) {
                Order.update(vm.order, onSaveSuccess, onSaveError);
            } else {
                Order.save(vm.order, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:orderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
