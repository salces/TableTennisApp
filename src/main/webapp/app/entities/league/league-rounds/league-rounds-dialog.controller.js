(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueRoundsDialogController', LeagueRoundsDialogController);

    LeagueRoundsDialogController.$inject = ['$uibModal','$uibModalInstance','$state' ,'entity'];

    function LeagueRoundsDialogController($uibModal,$uibModalInstance,$state ,entity) {
        var vm = this;
        vm.rounds = entity;
        console.log(vm.rounds)

        vm.clear = clear;
        vm.edit = edit;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }


        function edit(round) {
                $uibModal.open({
                    templateUrl: 'app/entities/league/league-rounds-edit/league-rounds-edit.dialog.html',
                    controller: 'LeagueRoundsEditDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        entity: [function () {
                            return round;
                        }]
                    }
                }).result.then(function () {
                    $state.go('league', null, {reload: 'league'});
                }, function () {
                    $state.go('^');
                });

        }

    }
})();
