(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('NextStageDialogController', NextStageDialogController);

    NextStageDialogController.$inject = ['TournamentStage', 'TournamentLadderService', '$uibModalInstance', 'Stage', 'LadderDrawer'];

    function NextStageDialogController(TournamentStage, TournamentLadderService, $uibModalInstance, Stage, LadderDrawer) {
        var vm = this;
        vm.stage = Stage;
        vm.TournamentLadderService = TournamentLadderService;

        vm.clear = clear;
        vm.saveResult = saveResult;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveResult() {
            vm.result.currentStageId = vm.stage.key;
            TournamentStage.nextStage(vm.result).$promise.then(function success(response) {
                    vm.TournamentLadderService.addStage(vm.stage, vm.result, response);
            }, function error(response) {
            });
            $uibModalInstance.dismiss();
        }


    }

})();
