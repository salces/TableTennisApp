(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueRoundsDialogController', LeagueRoundsDialogController);

    LeagueRoundsDialogController.$inject = ['$uibModalInstance','entity'];

    function LeagueRoundsDialogController($uibModalInstance,entity) {
        var vm = this;
        vm.rounds = entity;

        vm.clear = clear;
        vm.edit = edit;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }


        function edit(round) {
            console.log(round);
        }
    }
})();
