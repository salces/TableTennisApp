(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueDialogController', LeagueDialogController);

    LeagueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'League', 'Image', 'Club', 'Round', 'Notification'];

    function LeagueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, League, Image, Club, Round, Notification) {
        var vm = this;

        vm.league = entity;
        vm.clear = clear;
        vm.save = save;
        vm.images = Image.query();
        vm.clubs = Club.query();
        vm.rounds = Round.query();


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.league.id !== null) {
                League.update(vm.league, onSaveSuccess, onSaveError);
            } else {
                console.log(vm.league)
                League.save(vm.league, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tableTennisApp:leagueUpdate', result);
            $uibModalInstance.close(result);
            Notification.success('Liga dodana/edytowana pomyślnie');
            vm.isSaving = false;
        }

        function onSaveError () {
            Notification.error('Operacja dodawania/edycji nie powiodła się');
            vm.isSaving = false;
        }


    }
})();
