(function() {
    'use strict';
    angular
        .module('prototypeApp')
        .factory('StoreUser', StoreUser);

    StoreUser.$inject = ['$resource'];

    function StoreUser ($resource) {
        var resourceUrl =  'api/store-users/:id';

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
