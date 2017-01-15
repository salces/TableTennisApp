(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Tournament', 'League', 'Player', 'Club', 'DataUtils'];

    function HomeController ($scope, Principal, LoginService, $state, Tournament, League, Player, Club, DataUtils) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.openFile = DataUtils.openFile;
        vm.tournamentMatches = Tournament.getLastMatches();
        vm.leagueResults = League.getLastResults();
        vm.randomPlayer = Player.getRandom();
        vm.randomClub = Club.getRandom();

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
