(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueDetailController', LeagueDetailController);

    LeagueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'League', 'Image', 'Club', 'Round'];

    function LeagueDetailController($scope, $rootScope, $stateParams, previousState, entity, League, Image, Club, Round) {
        var vm = this;

        vm.league = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tableTennisApp:leagueUpdate', function(event, result) {
            vm.league = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
