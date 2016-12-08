(function() {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('TournamentStage', TournamentStage);

    TournamentStage.$inject = ['$resource'];

    function TournamentStage ($resource) {
        var resourceUrl =  'api/tournament-stages/:id';

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
