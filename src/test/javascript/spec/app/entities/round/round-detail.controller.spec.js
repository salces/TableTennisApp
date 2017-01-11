'use strict';

describe('Controller Tests', function() {

    describe('Round Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRound, MockClub, MockTournamentMatch, MockLeague;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRound = jasmine.createSpy('MockRound');
            MockClub = jasmine.createSpy('MockClub');
            MockTournamentMatch = jasmine.createSpy('MockTournamentMatch');
            MockLeague = jasmine.createSpy('MockLeague');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Round': MockRound,
                'Club': MockClub,
                'TournamentMatch': MockTournamentMatch,
                'League': MockLeague
            };
            createController = function() {
                $injector.get('$controller')("RoundDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tableTennisApp:roundUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
