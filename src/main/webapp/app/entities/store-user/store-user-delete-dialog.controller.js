(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreUserDeleteController',StoreUserDeleteController);

    StoreUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'StoreUser'];

    function StoreUserDeleteController($uibModalInstance, entity, StoreUser) {
        var vm = this;

        vm.storeUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StoreUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
