(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('ImageDetailController', ImageDetailController);

    ImageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Image', 'User'];

    function ImageDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Image, User) {
        var vm = this;

        vm.image = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tableTennisApp:imageUpdate', function(event, result) {
            vm.image = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
