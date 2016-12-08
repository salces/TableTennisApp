(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentStageDeleteController',TournamentStageDeleteController);

    TournamentStageDeleteController.$inject = ['$uibModalInstance', 'entity', 'TournamentStage'];

    function TournamentStageDeleteController($uibModalInstance, entity, TournamentStage) {
        var vm = this;

        vm.tournamentStage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TournamentStage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
