(function () {
    'use strict';

    angular
        .module('tableTennisApp')
        .controller('TournamentLadderDialogController', TournamentLadderDialogController);

    TournamentLadderDialogController.$inject = ['entity', 'TournamentStage', '$uibModalInstance'];

    function TournamentLadderDialogController(entity, TournamentStage, $uibModalInstance) {
        var vm = this;
        vm.tournament = entity;
        vm.tournamentStage = TournamentStage;
        vm.clear = clear;

        $uibModalInstance.rendered.then(function () {
            TournamentStage.get(vm.tournament.id).$promise.then(success,error);

            function success(data,headers) {
                var ladderDrawer = new LadderDrawer(data);
                ladderDrawer.draw();
            }

            function error() {

            }
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }
    }

    function LadderDrawer(stages) {
        var vm = this;
        vm.stages = stages;
        vm.draw = draw;

        function draw() {
            var $ = go.GraphObject.make;
            var myDiagram =
                $(go.Diagram, "ladder",
                    {
                        initialContentAlignment: go.Spot.TopLeft,
                        "undoManager.isEnabled": true,
                        layout: $(go.TreeLayout,
                            {angle: 180, layerSpacing: 100})
                    }
                );

            var nodeDataArray = transformStages(stages);
            var myModel = new go.TreeModel(nodeDataArray);

            myDiagram.nodeTemplate =
                $(go.Node, "Vertical", {background: "#44CCFF"},
                    $(go.TextBlock, "Default Text",
                        {margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                        new go.Binding("text", "firstPlayer")),
                    $(go.Shape, {figure: "LineH", fill: "lightgreen"}),
                    $(go.TextBlock, "Default Text",
                        {margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                        new go.Binding("text", "secondPlayer"))
                );
            myDiagram.linkTemplate =$(go.Link,
                                    {routing: go.Link.Orthogonal, corner: 5},
                                    $(go.Shape));

            myDiagram.model = myModel;
        }

        function transformStages(stages) {
            var newStages = [];
            for (var i = 0; i < stages.length; i++) {
                var stage = {
                    key: stages[i].id,
                    firstPlayer: stages[i].firstPlayerName + stages[i].firstPlayerSurname,
                    secondPlayer: stages[i].secondPlayerName + stages[i].secondPlayerSurname,
                };

                if(stages[i].nextStageId != null){
                    stage.parent = stages[i].nextStageId;
                }
                newStages.push(stage);
            }
            return newStages;
        }
    }

})();
