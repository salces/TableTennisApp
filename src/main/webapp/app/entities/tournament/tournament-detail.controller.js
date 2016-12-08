(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentDetailController', TournamentDetailController);

    TournamentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tournament', 'Image', 'TournamentStage'];

    function TournamentDetailController($scope, $rootScope, $stateParams, previousState, entity, Tournament, Image, TournamentStage) {
        var vm = this;

        vm.tournament = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tableTennisApp:tournamentUpdate', function(event, result) {
            vm.tournament = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
