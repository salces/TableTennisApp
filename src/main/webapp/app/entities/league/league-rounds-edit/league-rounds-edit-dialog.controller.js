(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueRoundsEditDialogController', LeagueRoundsEditDialogController);

    LeagueRoundsEditDialogController.$inject = ['$uibModalInstance','entity'];

    function LeagueRoundsEditDialogController($uibModalInstance,entity) {
        var vm = this;
        vm.roundId = entity;
        vm.clear = clear;

        console.log(entity)

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
