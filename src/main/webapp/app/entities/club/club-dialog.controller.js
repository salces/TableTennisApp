(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('ClubDialogController', ClubDialogController);

    ClubDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Club', 'User', 'Player', 'Image'];

    function ClubDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Club, User, Player, Image) {
        var vm = this;

        vm.club = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.players = Player.query();
        vm.images = Image.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.club.id !== null) {
                Club.update(vm.club, onSaveSuccess, onSaveError);
            } else {
                Club.save(vm.club, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tableTennisApp:clubUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
