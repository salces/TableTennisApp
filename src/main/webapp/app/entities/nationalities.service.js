(function() {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('Nationality', Nationality);

    Nationality.$inject = ['$resource'];

    function Nationality ($resource) {
        var resourceUrl =  'api/nationalities';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
