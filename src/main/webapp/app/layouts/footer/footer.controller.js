(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function FooterController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.$state = $state;
    }
})();
