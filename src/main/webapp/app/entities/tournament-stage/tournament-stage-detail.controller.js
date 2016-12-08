(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentStageDetailController', TournamentStageDetailController);

    TournamentStageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TournamentStage', 'Player', 'Tournament'];

    function TournamentStageDetailController($scope, $rootScope, $stateParams, previousState, entity, TournamentStage, Player, Tournament) {
        var vm = this;

        vm.tournamentStage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tableTennisApp:tournamentStageUpdate', function(event, result) {
            vm.tournamentStage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
