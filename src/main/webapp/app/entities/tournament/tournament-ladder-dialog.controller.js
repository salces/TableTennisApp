(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentLadderDialogController', TournamentLadderDialogController);

    TournamentLadderDialogController.$inject = ['entity', 'TournamentStage','TournamentLadderService' , '$uibModalInstance', '$uibModal'];

    function TournamentLadderDialogController(entity, TournamentStage,TournamentLadderService , $uibModalInstance, $uibModal) {
        var vm = this;
        vm.tournament = entity;
        vm.tournamentStage = TournamentStage;
        vm.clear = clear;

        $uibModalInstance.rendered.then(function () {
            TournamentStage.get(vm.tournament.id).$promise.then(success, error);

            function success(data, headers) {
                TournamentLadderService.create(data);
                TournamentLadderService.draw();
            }

            function error() {

            }
        });

        // function addStage(stage) {
        //     vm.ladderDrawer.addStage(stage);
        //     vm.ladderDrawer.draw();
        // }

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }
    }

    // function LadderDrawer(stages, $uibModal) {
    //     var vm = this;
    //     vm.stages = stages;
    //     vm.draw = draw;
    //     vm.nodeDataArray = [];
    //     vm.addStage = addStage;
    //
    //     function draw() {
    //         var $ = go.GraphObject.make;
    //         vm.myDiagram =
    //             $(go.Diagram, "ladder",
    //                 {
    //                     initialContentAlignment: go.Spot.TopLeft,
    //                     "undoManager.isEnabled": true,
    //                     layout: $(go.TreeLayout,
    //                         {angle: 180, layerSpacing: 100})
    //                 }
    //             );
    //
    //         vm.nodeDataArray = transformStages(stages);
    //         var myModel = new go.TreeModel(vm.nodeDataArray);
    //
    //         vm.myDiagram.nodeTemplate =
    //             $(go.Node, "Vertical", {background: "#44CCFF"},
    //                 $(go.TextBlock, "Default Text",
    //                     {margin: 6, stroke: "white", font: "bold 16px sans-serif"},
    //                     new go.Binding("text", "firstPlayer")),
    //                 $(go.Shape, {figure: "LineH", fill: "lightgreen"}),
    //                 $(go.TextBlock, "Default Text",
    //                     {margin: 6, stroke: "white", font: "bold 16px sans-serif"},
    //                     new go.Binding("text", "secondPlayer")),
    //                 {
    //                     doubleClick: addMatchResult
    //                 }
    //             )
    //         ;
    //         vm.myDiagram.linkTemplate = $(go.Link,
    //             {routing: go.Link.Orthogonal, corner: 5},
    //             $(go.Shape));
    //
    //         vm.myDiagram.model = myModel;
    //     }
    //
    //     function transformStages(stages) {
    //         var newStages = [];
    //         for (var i = 0; i < stages.length; i++) {
    //             var stage = {
    //                 key: stages[i].id,
    //                 firstPlayer: stages[i].firstPlayerName + stages[i].firstPlayerSurname,
    //                 secondPlayer: stages[i].secondPlayerName + stages[i].secondPlayerSurname,
    //             };
    //
    //             if (stages[i].nextStageId != null) {
    //                 stage.parent = stages[i].nextStageId;
    //             }
    //             newStages.push(stage);
    //         }
    //         return newStages;
    //     }
    //
    //     function addMatchResult(e, node) {
    //         $uibModal.open({
    //             templateUrl: 'app/entities/tournament-stage/nextStage/next-stage-dialog.html',
    //             controller: 'NextStageDialogController',
    //             controllerAs: 'vm',
    //             resolve: {
    //                 Stage : node.de,
    //                 LadderDrawer: vm
    //             }
    //         });
    //     }
    //
    //     function addStage(stage) {
    //         vm.stages.push(stage);
    //     }
    // }

})();
