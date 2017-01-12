(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('LeagueTableController', LeagueTableController);

    LeagueTableController.$inject = ['entity', 'League'];

    function LeagueTableController(entity,League) {
        var vm = this;

        vm.league = entity;
        vm.leagueTable = League.getTable(vm.league.id);
        console.log(vm.leagueTable);

    }
})();
