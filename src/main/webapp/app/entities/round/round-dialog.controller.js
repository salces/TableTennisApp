(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('RoundDialogController', RoundDialogController);

    RoundDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Round', 'Club', 'League'];

    function RoundDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Round, Club, League) {
        var vm = this;

        vm.round = entity;
        vm.clear = clear;
        vm.save = save;
        vm.clubs = Club.query();
        vm.leagues = League.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.round.id !== null) {
                Round.update(vm.round, onSaveSuccess, onSaveError);
            } else {
                Round.save(vm.round, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tableTennisApp:roundUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
