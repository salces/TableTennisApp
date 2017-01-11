(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueRoundsEditDialogController', LeagueRoundsEditDialogController);

    LeagueRoundsEditDialogController.$inject = ['$uibModalInstance','entity','Round'];

    function LeagueRoundsEditDialogController($uibModalInstance,entity,Round) {
        var vm = this;
        vm.round = entity;
        vm.result = {
            id: vm.round.id
        };

        vm.clear = clear;
        vm.edit = edit;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function edit() {
            console.log(vm.result);
            Round.addMatch(vm.result).$promise.then(function success() {

            }, function error() {

            });
            vm.clear();
        }

    }
})();
