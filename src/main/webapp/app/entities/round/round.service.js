(function() {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('Round', Round);

    Round.$inject = ['$resource'];

    function Round ($resource) {
        var resourceUrl =  'api/rounds/:public/:id';

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
            'update': { method:'PUT' },
            'getForTournament': {method : 'GET', isArray:true, params:{public: 'public'}},
            'addMatch' : {method: 'POST', params: {id : 'match'}}
        });
    }
})();
