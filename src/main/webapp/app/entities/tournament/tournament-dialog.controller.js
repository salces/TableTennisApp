(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentDialogController', TournamentDialogController);

    TournamentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tournament', 'Image', 'Player', 'AlertService'];

    function TournamentDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Tournament, Image, Player, AlertService) {
        var vm = this;

        vm.tournament = entity;
        vm.tournament.phase = 1;
        vm.chosenPlayersColor = 'color:orange';
        vm.clear = clear;
        vm.save = save;
        vm.images = Image.query();
        vm.chosenPlayers = [];
        vm.onAddPlayer = onAddPlayer;
        vm.onRemovePlayer = onRemovePlayer;
        vm.changeChosenPlayersColor = changeChosenPlayersColor;

        initGrids();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.tournament.chosenPlayers = toIdArray(vm.chosenPlayers);
            if (vm.tournament.id !== null) {
                Tournament.update(vm.tournament, onSaveSuccess, onSaveError);
            } else {
                Tournament.save(vm.tournament, onSaveSuccess, onSaveError);
            }

            function toIdArray(players) {
                var playersId = [];
                for(var i = 0; i < players.length; i++){
                    playersId.push(players[i].id)
                }
                return playersId;
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
                for (var i = 0; i < data.length; i++) {
                    transformedData.push({
                        id: data[i].id,
                        name: data[i].name,
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
                    {name: 'Id', field: 'id'},
                    {name: 'Imie', field: 'name'},
                    {name: 'Nazwisko', field: 'surname'},
                    {
                        name: ' ',
                        cellTemplate: '<input type="button" class="btn btn-success" value="Dodaj" style="width:100%;border-radius: 0px" ' +
                        'ng-click="grid.appScope.vm.onAddPlayer(row.entity)" />',
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
                    {name: 'Id', field: 'id'},
                    {name: 'Imie', field: 'name'},
                    {name: 'Nazwisko', field: 'surname'},
                    {
                        name: ' ',
                        cellTemplate: '<input type="button" class="btn btn-danger" value="Usuń" style="width:100%;border-radius: 0px" ng-click="grid.appScope.vm.onRemovePlayer(row.entity)" />',
                        enableColumnMenu: false,
                        enableSorting: false,
                        enableFiltering: false
                    }
                ]
            };
        }

        function onAddPlayer(player) {
            var index = vm.availablePlayers.indexOf(player);
            vm.availablePlayers.splice(index, 1);
            vm.chosenPlayers.push(player);
            vm.changeChosenPlayersColor();
        }

        function onRemovePlayer(player) {
            var index = vm.chosenPlayers.indexOf(player);
            vm.chosenPlayers.splice(index, 1)
            vm.availablePlayers.push(player);
            vm.changeChosenPlayersColor();
        }

        function changeChosenPlayersColor() {
            if(vm.tournament.phase * 2 > vm.chosenPlayers.length){
                vm.chosenPlayersColor = 'color:orange';
            } else if(vm.tournament.phase * 2 == vm.chosenPlayers.length){
                vm.chosenPlayersColor = 'color:green';
            } else {
                vm.chosenPlayersColor = 'color:red';
            }
        }

    }
})();
