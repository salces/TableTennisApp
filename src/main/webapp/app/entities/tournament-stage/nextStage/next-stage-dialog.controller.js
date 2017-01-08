(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('NextStageDialogController', NextStageDialogController);

    NextStageDialogController.$inject = [ 'TournamentStage', '$uibModalInstance', 'Stage', 'LadderDrawer'];

    function NextStageDialogController(TournamentStage, $uibModalInstance, Stage, LadderDrawer) {
        var vm = this;
        vm.stage = Stage;

        vm.clear = clear;
        vm.saveResult = saveResult;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveResult() {
            var parentIndex = LadderDrawer.nodeDataArray.indexOf(vm.stage);

            $uibModalInstance.dismiss();
        }
    }

})();
