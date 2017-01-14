(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('CategoryDialogController', CategoryDialogController);

    CategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Category', 'Product'];

    function CategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Category, Product) {
        var vm = this;

        vm.category = entity;
        vm.clear = clear;
        vm.save = save;
        vm.parentcategories = Category.query({filter: 'category-is-null'});
        $q.all([vm.category.$promise, vm.parentcategories.$promise]).then(function() {
            if (!vm.category.parentCategoryId) {
                return $q.reject();
            }
            return Category.get({id : vm.category.parentCategoryId}).$promise;
        }).then(function(parentCategory) {
            vm.parentcategories.push(parentCategory);
        });
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.category.id !== null) {
                Category.update(vm.category, onSaveSuccess, onSaveError);
            } else {
                Category.save(vm.category, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:categoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
