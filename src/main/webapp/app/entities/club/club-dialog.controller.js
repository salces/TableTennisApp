(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('ClubDialogController', ClubDialogController);

    ClubDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Club', 'User', 'Player', 'Image', 'League', 'Notification'];

    function ClubDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Club, User, Player, Image, League, Notification) {
        var vm = this;

        vm.club = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.players = Player.query();
        vm.images = Image.query();
        vm.leagues = League.query();

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
            Notification.success('Klub dodany/edytowany pomyślnie');
            vm.isSaving = false;
        }

        function onSaveError () {
            Notification.success('Operacja dodawania/edycji nie powiodła się"');
            vm.isSaving = false;
        }


    }
})();
