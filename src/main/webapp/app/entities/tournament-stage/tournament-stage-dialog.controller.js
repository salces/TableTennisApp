(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentStageDialogController', TournamentStageDialogController);

    TournamentStageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TournamentStage', 'Player', 'Tournament'];

    function TournamentStageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TournamentStage, Player, Tournament) {
        var vm = this;

        vm.tournamentStage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.players = Player.query();
        vm.tournamentstages = TournamentStage.query();
        vm.tournaments = Tournament.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tournamentStage.id !== null) {
                TournamentStage.update(vm.tournamentStage, onSaveSuccess, onSaveError);
            } else {
                TournamentStage.save(vm.tournamentStage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tableTennisApp:tournamentStageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
