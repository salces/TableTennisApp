(function() {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('Player', Player);

    Player.$inject = ['$resource'];

    function Player ($resource) {
        var resourceUrl =  'api/players/:public/:id';

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
            'getRandom': {method: 'get', isArray: false, params: {public: 'public', id: 'random'}}
        });
    }
})();
