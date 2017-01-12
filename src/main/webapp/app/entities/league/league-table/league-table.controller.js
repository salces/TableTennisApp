(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueTableController', LeagueTableController);

    LeagueTableController.$inject = ['entity', 'League','previousState'];

    function LeagueTableController(entity,League,previousState) {
        var vm = this;
        vm.previousState = previousState
        vm.league = entity;
        vm.leagueTable = League.getTable(vm.league.id);
        console.log(vm.leagueTable);

    }
})();
