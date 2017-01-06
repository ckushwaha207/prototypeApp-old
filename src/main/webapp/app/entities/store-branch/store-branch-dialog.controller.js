(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreBranchDialogController', StoreBranchDialogController);

    StoreBranchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'StoreBranch', 'Location', 'Store'];

    function StoreBranchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, StoreBranch, Location, Store) {
        var vm = this;

        vm.storeBranch = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locations = Location.query({filter: 'branch-is-null'});
        $q.all([vm.storeBranch.$promise, vm.locations.$promise]).then(function() {
            if (!vm.storeBranch.locationId) {
                return $q.reject();
            }
            return Location.get({id : vm.storeBranch.locationId}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });
        vm.stores = Store.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.storeBranch.id !== null) {
                StoreBranch.update(vm.storeBranch, onSaveSuccess, onSaveError);
            } else {
                StoreBranch.save(vm.storeBranch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prototypeApp:storeBranchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
