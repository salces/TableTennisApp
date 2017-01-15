(function () {
    'use strict';
    angular
        .module('tableTennisApp')
        .service('TournamentLadderService', TournamentLadderService);

    TournamentLadderService.$inject = ['$uibModal'];

    function TournamentLadderService($uibModal) {
        var vm = this;
        vm.create = create;


        function create(stages) {
            vm.ladderDrawer = new LadderDrawer(stages, $uibModal);
            vm.draw = vm.ladderDrawer.draw;
            vm.addStage = vm.ladderDrawer.addStage;
        }

        function LadderDrawer(stages, $uibModal) {
            var vm = this;
            vm.stages = stages;
            vm.draw = draw;
            vm.nodeDataArray = [];
            vm.addStage = addStage;

            function draw() {
                var $ = go.GraphObject.make;
                vm.myDiagram =
                    $(go.Diagram, "ladder",
                        {
                            initialContentAlignment: go.Spot.TopLeft,
                            "undoManager.isEnabled": true,
                            layout: $(go.TreeLayout,
                                {angle: 180, layerSpacing: 100})
                        }
                    );

                vm.nodeDataArray = transformStages(stages);
                var myModel = new go.TreeModel(vm.nodeDataArray);

                vm.myDiagram.nodeTemplate =
                    $(go.Node, "Vertical", {background: "#44CCFF"},
                        $(go.Panel, "Table",
                            $(go.TextBlock, "Default Text",
                                {row: 0, column: 0, margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                                new go.Binding("text", "firstPlayer")),
                            $(go.TextBlock, "Default Text",
                                {row: 0, column: 1, margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                                new go.Binding("text", "firstPlayerScore"))),
                        $(go.Shape, {figure: "LineH", fill: "lightgreen"}),
                        $(go.Panel, "Table",
                            $(go.TextBlock, "Default Text",
                                {row: 0, column: 0, margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                                new go.Binding("text", "secondPlayer")),
                            $(go.TextBlock, "Default Text",
                                {row: 0, column: 1, margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                                new go.Binding("text", "secondPlayerScore"))),
                        {
                            doubleClick: addMatchResult
                        }
                    )
                ;
                vm.myDiagram.linkTemplate = $(go.Link,
                    {routing: go.Link.Orthogonal, corner: 5},
                    $(go.Shape));

                vm.myDiagram.model = myModel;
            }

            function transformStages(stages) {
                var newStages = [];
                for (var i = 0; i < stages.length; i++) {
                    newStages.push(transformStage(stages[i]));
                }
                return newStages;
            }

            function transformStage(stage) {
                var firstPlayer = stage.firstPlayerName + ' ' + stage.firstPlayerSurname;
                var secondPlayer = stage.secondPlayerName + ' ' + stage.secondPlayerSurname;
                if(stage.firstPlayerName == null) firstPlayer = '';
                if(stage.firstPlayerSurname == null) firstPlayer = '';
                if(stage.secondPlayerName == null) secondPlayer = '';
                if(stage.secondPlayerSurname == null) secondPlayer = '';
                var newStage = {
                    key: stage.id,
                    firstPlayer: firstPlayer,
                    secondPlayer: secondPlayer,
                    firstPlayerScore: stage.firstPlayerScore,
                    secondPlayerScore: stage.secondPlayerScore
                };

                if (stage.nextStageId != null) {
                    newStage.parent = stage.nextStageId;
                }

                return newStage;
            }

            function addMatchResult(e, node) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament-stage/nextStage/next-stage-dialog.html',
                    controller: 'NextStageDialogController',
                    controllerAs: 'vm',
                    resolve: {
                        Stage: node.de,
                        LadderDrawer: vm
                    }
                });
            }

            function addStage(prevStage, result, newStage) {
                console.log('prev stage')
                console.log(prevStage)
                console.log('newStage')
                console.log(newStage)
                vm.myDiagram.startTransaction('add stage');
                var key;
                if (!doesExistEmpty() && !isComplete(newStage)) {
                    vm.stages.push(newStage);
                    var transformedStage = transformStage(newStage);
                    vm.myDiagram.model.addNodeData(transformedStage);
                    key = transformedStage.key;
                } else if (isComplete(newStage) && !doesExistEmpty()) {
                }
                else {
                    var existingStage = getExisting();
                    var transformedStage = transformStage(newStage);
                    vm.myDiagram.model.setDataProperty(existingStage, 'secondPlayer', transformedStage.secondPlayer);
                    key = existingStage.key;
                }
                vm.myDiagram.model.setDataProperty(prevStage, 'parent', key);
                vm.myDiagram.model.setDataProperty(prevStage, 'firstPlayerScore', result.firstPlayerScore);
                vm.myDiagram.model.setDataProperty(prevStage, 'secondPlayerScore', result.secondPlayerScore);

                vm.myDiagram.commitTransaction('add stage');
            }

            function doesExistEmpty() {
                for (var i = 0; i < vm.nodeDataArray.length; i++) {
                    if (!vm.nodeDataArray[i].secondPlayer) {
                        return true;
                    }
                }
                return false;
            }

            function getExisting() {
                for (var i = 0; i < vm.nodeDataArray.length; i++) {
                    if (!vm.nodeDataArray[i].secondPlayer) {
                        return vm.nodeDataArray[i];
                    }
                }
            }

            function isComplete(stage) {
                if (stage.phase === 'FINAL'
                    && stage.firstPlayerName
                    && stage.firstPlayerSurname
                    && stage.secondPlayerName
                    && stage.secondPlayerSurname) {
                    return true;
                } else {
                    return false;
                }
            }


        }
    }
})();
