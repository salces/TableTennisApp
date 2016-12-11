(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('PlayerDialogController', PlayerDialogController);

    PlayerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Player', 'User', 'Club', 'Image','Nationality'];

    function PlayerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Player, User, Club, Image, Nationality) {
        var vm = this;

        vm.player = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.clubs = Club.query();
        vm.images = Image.query();
        vm.nationalities = Nationality.query();
        console.log(vm.nationalities);

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.player.id !== null) {
                Player.update(vm.player, onSaveSuccess, onSaveError);
            } else {
                Player.save(vm.player, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tableTennisApp:playerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
