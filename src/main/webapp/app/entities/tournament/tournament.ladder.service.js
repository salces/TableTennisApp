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
                        $(go.TextBlock, "Default Text",
                            {margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                            new go.Binding("text", "firstPlayer")),
                        $(go.Shape, {figure: "LineH", fill: "lightgreen"}),
                        $(go.TextBlock, "Default Text",
                            {margin: 6, stroke: "white", font: "bold 16px sans-serif"},
                            new go.Binding("text", "secondPlayer")),
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
                var newStage = {
                    key: stage.id,
                    firstPlayer: stage.firstPlayerName + ' ' + stage.firstPlayerSurname,
                    secondPlayer: stage.secondPlayerName + ' ' + stage.secondPlayerSurname,
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

            function addStage(prevStage, newStage) {
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
                    vm.myDiagram.model.setDataProperty(existingStage, 'secondPlayer', newStage.firstPlayerName + ' ' + newStage.secondPlayerName);
                    key = existingStage.key;
                }
                vm.myDiagram.model.setDataProperty(prevStage, 'parent', key);

                vm.myDiagram.commitTransaction('add stage');
            }

            function doesExistEmpty() {
                for (var i = 0; i < vm.nodeDataArray.length; i++) {
                    if (vm.nodeDataArray[i].secondPlayer === 'null null') {
                        return true;
                    }
                }
            }

            function getExisting() {
                for (var i = 0; i < vm.nodeDataArray.length; i++) {
                    if (vm.nodeDataArray[i].secondPlayer === 'null null') {
                        return vm.nodeDataArray[i];
                    }
                }
            }

            function isComplete(stage) {
                if (stage.phase === 'FINAL'
                    && stage.firstPlayerName != null
                    && stage.firstPlayerSurname != null
                    && stage.secondPlayerName != null
                    && stage.secondPlayerSurname != null) {
                    return true;
                } else {
                    return false;
                }
            }


        }
    }
})();
