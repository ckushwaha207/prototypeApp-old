(function() {
    'use strict';
    angular
        .module('prototypeApp')
        .factory('StoreBranch', StoreBranch);

    StoreBranch.$inject = ['$resource'];

    function StoreBranch ($resource) {
        var resourceUrl =  'api/store-branches/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
