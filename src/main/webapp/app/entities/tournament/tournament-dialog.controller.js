(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentDialogController', TournamentDialogController);

    TournamentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tournament', 'Image', 'TournamentStage'];

    function TournamentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tournament, Image, TournamentStage) {
        var vm = this;

        vm.tournament = entity;
        vm.clear = clear;
        vm.save = save;
        vm.images = Image.query();
        vm.tournamentstages = TournamentStage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tournament.id !== null) {
                Tournament.update(vm.tournament, onSaveSuccess, onSaveError);
            } else {
                Tournament.save(vm.tournament, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tableTennisApp:tournamentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
