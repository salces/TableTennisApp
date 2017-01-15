(function () {
    'use strict';
    angular
        .module('tableTennisApp')
        .factory('League', League);

    League.$inject = ['$resource'];

    function League($resource) {
        var resourceUrl = 'api/leagues/:public/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true, params: {public: 'public'}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }, params: {public: 'public'}
            },
            'update': {method: 'PUT'},
            'getTable': {method: 'POST', isArray: true, params: {public:'public', id: 'table'}},
            'getLastResults': {method: 'GET', isArray: true, params: {public:'public', id: 'lastResults'}}
        });
    }
})();
