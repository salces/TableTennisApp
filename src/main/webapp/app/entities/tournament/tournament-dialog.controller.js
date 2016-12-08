(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentDialogController', TournamentDialogController);

    TournamentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tournament', 'Image', 'TournamentStage', 'Player', 'AlertService'];

    function TournamentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Tournament, Image, TournamentStage, Player, AlertService) {
        var vm = this;

        vm.tournament = entity;
        vm.clear = clear;
        vm.save = save;
        vm.images = Image.query();
        vm.tournamentstages = TournamentStage.query();
        // vm.availablePlayers = [];
        vm.chosenPlayers = [];
        vm.onAddPlayer = onAddPlayer;
        vm.onRemovePlayer = onRemovePlayer;

        initGrids();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.tournament.id !== null) {
                Tournament.update(vm.tournament, onSaveSuccess, onSaveError);
            } else {
                Tournament.save(vm.tournament, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tableTennisApp:tournamentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function initGrids() {
            Player.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.availablePlayers = transformData(data);
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }

            function transformData(data) {
                var transformedData = [];
                for(var i = 0; i < data.length; i++){
                    transformedData.push({
                        name : data[i].name,
                        surname: data[i].surname
                    });
                }
                return transformedData
            }


            initAvailablePlayersGrid();
            initChosenPlayersGrid();
        }

        function initAvailablePlayersGrid() {
            $scope.availablePlayersGrid = {
                enableSorting: true,
                enableFiltering: true,
                paginationPageSizes: [25, 50, 75],
                paginationPageSize: 25,
                data: 'vm.availablePlayers',
                columnDefs: [
                    {name: 'Name', field: 'name'},
                    {name: 'Surname', field: 'surname'},
                    {
                        name: ' ',
                        cellTemplate: '<input type="button" class="btn btn-success" value="Add" style="width:100%;border-radius: 0px" ng-click="grid.appScope.vm.onAddPlayer(row.entity)" / >',
                        enableColumnMenu: false,
                        enableSorting: false,
                        enableFiltering: false
                    }
                ]
            };
        }

        function initChosenPlayersGrid() {
            $scope.chosenPlayersGrid = {
                enableSorting: true,
                enableFiltering: true,
                paginationPageSizes: [25, 50, 75],
                paginationPageSize: 25,
                data: 'vm.chosenPlayers',
                columnDefs: [
                    {name: 'Name', field: 'name'},
                    {name: 'Surname', field: 'surname'},
                    {
                        name: ' ',
                        cellTemplate: '<input type="button" class="btn btn-danger" value="Remove" style="width:100%;border-radius: 0px" ng-click="grid.appScope.vm.onRemovePlayer(row.entity)" />',
                        enableColumnMenu: false,
                        enableSorting: false,
                        enableFiltering: false
                    }
                ]
            };
        }

        function onAddPlayer(player) {
            var index = vm.availablePlayers.indexOf(player);
            vm.availablePlayers.splice(index,1);
            vm.chosenPlayers.push(player);
        }

        function onRemovePlayer(player) {
            var index = vm.chosenPlayers.indexOf(player);
            vm.chosenPlayers.splice(index,1)
            vm.availablePlayers.push(player);
        }

    }
})();
