(function () {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('League', League);

    League.$inject = ['$resource'];

    function League($resource) {
        var resourceUrl = 'api/leagues/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'getTable': {method: 'POST', isArray: true, params: {id: 'table'}},
            'getLastResults': {method: 'GET', isArray: true, params: {id: 'lastResults'}}
        });
    }
})();
