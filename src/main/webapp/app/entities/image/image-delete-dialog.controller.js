(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('ImageDeleteController',ImageDeleteController);

    ImageDeleteController.$inject = ['$uibModalInstance', 'entity', 'Image', 'Notification'];

    function ImageDeleteController($uibModalInstance, entity, Image, Notification) {
        var vm = this;

        vm.image = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Image.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                    Notification.success('Obraz został usunięty pomyślnie');
                }, function () {
                    Notification.error('Wystąpił błąd podczas usuwania obrazu');
                });
        }
    }
})();
