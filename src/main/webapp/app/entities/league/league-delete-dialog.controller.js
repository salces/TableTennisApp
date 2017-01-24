(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueDeleteController',LeagueDeleteController);

    LeagueDeleteController.$inject = ['$uibModalInstance', 'entity', 'League'];

    function LeagueDeleteController($uibModalInstance, entity, League) {
        var vm = this;

        vm.league = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            League.delete({id: id},
                function () {
                    Notification.success('Liga została usunięty pomyślnie');
                    $uibModalInstance.close(true);
                }, function () {
                    Notification.error('Wystąpił błąd podczas usuwania ligi');
                });
        }
    }
})();
