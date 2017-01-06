(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreBranchDeleteController',StoreBranchDeleteController);

    StoreBranchDeleteController.$inject = ['$uibModalInstance', 'entity', 'StoreBranch'];

    function StoreBranchDeleteController($uibModalInstance, entity, StoreBranch) {
        var vm = this;

        vm.storeBranch = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StoreBranch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
