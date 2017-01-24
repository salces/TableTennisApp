(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('PlayerDeleteController',PlayerDeleteController);

    PlayerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Player', 'Notification'];

    function PlayerDeleteController($uibModalInstance, entity, Player, Notification) {
        var vm = this;

        vm.player = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Player.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                    Notification.success('Zawodnik został usunięty');
                }, function () {
                    Notification.error('Wystąpił błąd podczas usuwania zawodnika.');
                });
        }
    }
})();
