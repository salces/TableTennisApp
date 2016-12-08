'use strict';

describe('Controller Tests', function() {

    describe('TournamentStage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTournamentStage, MockPlayer, MockTournament;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTournamentStage = jasmine.createSpy('MockTournamentStage');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockTournament = jasmine.createSpy('MockTournament');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TournamentStage': MockTournamentStage,
                'Player': MockPlayer,
                'Tournament': MockTournament
            };
            createController = function() {
                $injector.get('$controller')("TournamentStageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tableTennisApp:tournamentStageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
