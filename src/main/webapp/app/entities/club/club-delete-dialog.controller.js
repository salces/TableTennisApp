(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('ClubDeleteController',ClubDeleteController);

    ClubDeleteController.$inject = ['$uibModalInstance', 'entity', 'Club', 'Notification'];

    function ClubDeleteController($uibModalInstance, entity, Club, Notification) {
        var vm = this;

        vm.club = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Club.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                    Notification.success('Klub został usunięty pomyślnie');
                }, function () {
                    Notification.error('Wystąpił błąd podczas usuwania klubu')
                });
        }
    }
})();
