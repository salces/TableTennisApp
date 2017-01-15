(function() {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('Club', Club);

    Club.$inject = ['$resource'];

    function Club ($resource) {
        var resourceUrl =  'api/clubs/:public/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {public: 'public'}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }, params: {public: 'public'}
            },
            'update': { method:'PUT' },
            'getRandom': {method: 'GET', isArray: false, params: {public: 'public', id: 'random'}}
        });
    }
})();
