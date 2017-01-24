(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('ImageDialogController', ImageDialogController);

    ImageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Image', 'User','Notification'];

    function ImageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Image, User, Notification) {
        var vm = this;

        vm.image = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.image.id !== null) {
                Image.update(vm.image, onSaveSuccess, onSaveError);
            } else {
                Image.save(vm.image, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tableTennisApp:imageUpdate', result);
            $uibModalInstance.close(result);
            Notification.success('Obraz dodany/edytowany pomyślnie');
            vm.isSaving = false;
        }

        function onSaveError () {
            Notification.error('Operacja dodawania/edycji nie powiodła się');
            vm.isSaving = false;
        }


        vm.setData = function ($file, image) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        image.data = base64Data;
                        image.dataContentType = $file.type;
                    });
                });
            }
        };

    }
})();
