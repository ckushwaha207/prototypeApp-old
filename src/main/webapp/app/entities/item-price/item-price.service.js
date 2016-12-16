(function() {
    'use strict';
    angular
        .module('prototypeApp')
        .factory('ItemPrice', ItemPrice);

    ItemPrice.$inject = ['$resource'];

    function ItemPrice ($resource) {
        var resourceUrl =  'api/item-prices/:id';

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
