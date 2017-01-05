(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .controller('SidebarController', SidebarController);

    SidebarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function SidebarController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;


    }
})();
