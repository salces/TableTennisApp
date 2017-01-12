(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentLadderDialogController', TournamentLadderDialogController);

    TournamentLadderDialogController.$inject = ['entity', 'TournamentStage','TournamentLadderService' , '$uibModalInstance', '$uibModal'];

    function TournamentLadderDialogController(entity, TournamentStage,TournamentLadderService , $uibModalInstance, $uibModal) {
        var vm = this;
        vm.tournament = entity;
        vm.tournamentStage = TournamentStage;
        vm.clear = clear;

        $uibModalInstance.opened.then(function () {
            TournamentStage.get({id: vm.tournament.id}).$promise.then(success, error);
            function success(data, headers) {
                console.log(data);
                TournamentLadderService.create(data);
                TournamentLadderService.draw();
            }

            function error() {

            }
        });


        function clear() {
            $uibModalInstance.dismiss('cancel');
        }
    }

})();
