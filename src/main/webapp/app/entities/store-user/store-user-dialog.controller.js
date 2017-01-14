(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreUserDialogController', StoreUserDialogController);

    StoreUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StoreUser', 'Store'];

    function StoreUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StoreUser, Store) {
        var vm = this;

        vm.storeUser = entity;
        vm.clear = clear;
        vm.save = save;
        vm.stores = Store.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.storeUser.id !== null) {
                StoreUser.update(vm.storeUser, onSaveSuccess, onSaveError);
            } else {
                StoreUser.save(vm.storeUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:storeUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
