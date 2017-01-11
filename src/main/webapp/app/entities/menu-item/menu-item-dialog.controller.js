(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('MenuItemDialogController', MenuItemDialogController);

    MenuItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MenuItem'];

    function MenuItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MenuItem) {
        var vm = this;

        vm.menuItem = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.menuItem.id !== null) {
                MenuItem.update(vm.menuItem, onSaveSuccess, onSaveError);
            } else {
                MenuItem.save(vm.menuItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:menuItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
