(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .factory('DashboardUIService', DashboardUIService);

    DashboardUIService.$inject = ['$timeout'];

    function DashboardUIService($timeout) {
        var stateChange = $timeout(function () {
            // fix sidebar
            var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight()
            var window_height = $(window).height()
            var sidebar_height = $('.sidebar').height()

            if ($('body').hasClass('fixed')) {
                $('.content-wrapper, .right-side').css('min-height', window_height - $('.main-footer').outerHeight())
            } else {
                if (window_height >= sidebar_height) {
                  $('.content-wrapper, .right-side').css('min-height', window_height - neg)
                } else {
                  $('.content-wrapper, .right-side').css('min-height', sidebar_height)
                }
            }
        });

        return stateChange;
    }
})();
