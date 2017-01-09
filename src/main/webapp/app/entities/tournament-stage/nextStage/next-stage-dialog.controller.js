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
            var parentIndex = LadderDrawer.nodeDataArray.indexOf(vm.stage);
            console.log(vm.stage);
            vm.result.currentStageId = vm.stage.key;
            TournamentStage.nextStage(vm.result).$promise.then(function success(response) {
                console.log('Logging success response')
                console.log(response)
                // if(shouldSave(response)){
                    vm.TournamentLadderService.addStage(vm.stage, response);
                // }
            }, function error(response) {
                console.log('Logging error response')
                console.log(response)
            });
            $uibModalInstance.dismiss();
        }


    }

})();
