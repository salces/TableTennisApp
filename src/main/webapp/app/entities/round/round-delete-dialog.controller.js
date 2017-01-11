(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('RoundDeleteController',RoundDeleteController);

    RoundDeleteController.$inject = ['$uibModalInstance', 'entity', 'Round'];

    function RoundDeleteController($uibModalInstance, entity, Round) {
        var vm = this;

        vm.round = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Round.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
