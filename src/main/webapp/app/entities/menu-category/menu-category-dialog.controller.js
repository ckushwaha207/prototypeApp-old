(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('MenuCategoryDialogController', MenuCategoryDialogController);

    MenuCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MenuCategory', 'MenuItem', 'Menu'];

    function MenuCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MenuCategory, MenuItem, Menu) {
        var vm = this;

        vm.menuCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.parentcategories = MenuCategory.query({filter: 'menucategory-is-null'});
        $q.all([vm.menuCategory.$promise, vm.parentcategories.$promise]).then(function() {
            if (!vm.menuCategory.parentCategoryId) {
                return $q.reject();
            }
            return MenuCategory.get({id : vm.menuCategory.parentCategoryId}).$promise;
        }).then(function(parentCategory) {
            vm.parentcategories.push(parentCategory);
        });
        vm.menuitems = MenuItem.query();
        vm.menus = Menu.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.menuCategory.id !== null) {
                MenuCategory.update(vm.menuCategory, onSaveSuccess, onSaveError);
            } else {
                MenuCategory.save(vm.menuCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:menuCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
