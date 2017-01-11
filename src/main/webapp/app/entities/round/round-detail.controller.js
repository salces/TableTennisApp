(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('RoundDetailController', RoundDetailController);

    RoundDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Round', 'Club', 'TournamentMatch', 'League'];

    function RoundDetailController($scope, $rootScope, $stateParams, previousState, entity, Round, Club, TournamentMatch, League) {
        var vm = this;

        vm.round = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tableTennisApp:roundUpdate', function(event, result) {
            vm.round = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
