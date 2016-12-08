'use strict';

describe('Controller Tests', function() {

    describe('Tournament Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTournament, MockImage, MockTournamentStage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTournament = jasmine.createSpy('MockTournament');
            MockImage = jasmine.createSpy('MockImage');
            MockTournamentStage = jasmine.createSpy('MockTournamentStage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tournament': MockTournament,
                'Image': MockImage,
                'TournamentStage': MockTournamentStage
            };
            createController = function() {
                $injector.get('$controller')("TournamentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tableTennisApp:tournamentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
