(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('ClubDetailController', ClubDetailController);

    ClubDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Club', 'User', 'Player', 'Image', 'League'];

    function ClubDetailController($scope, $rootScope, $stateParams, previousState, entity, Club, User, Player, Image, League) {
        var vm = this;

        vm.club = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tableTennisApp:clubUpdate', function(event, result) {
            vm.club = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
