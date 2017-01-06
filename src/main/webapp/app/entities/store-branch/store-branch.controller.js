(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('StoreBranchController', StoreBranchController);

    StoreBranchController.$inject = ['$scope', '$state', 'StoreBranch'];

    function StoreBranchController ($scope, $state, StoreBranch) {
        var vm = this;

        vm.storeBranches = [];

        loadAll();

        function loadAll() {
            StoreBranch.query(function(result) {
                vm.storeBranches = result;
                vm.searchQuery = null;
            });
        }
    }
})();
