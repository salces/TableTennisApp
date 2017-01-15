(function() {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('Tournament', Tournament);

    Tournament.$inject = ['$resource'];

    function Tournament ($resource) {
        var resourceUrl =  'api/tournaments/:public/:id';

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
            'getLastMatches': { method:'GET', isArray: true, params: {public: 'public', id: 'lastMatches'} }
        });
    }
})();
