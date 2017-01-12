(function() {
    'use strict';

    angular
        .module('prototypeApp')
        .config(bootstrapMaterialDesignConfig);

    bootstrapMaterialDesignConfig.$inject = [];

    function bootstrapMaterialDesignConfig() {
       // TODO: enable material design
       // $.material.init();
    }
})();
