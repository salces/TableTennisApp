'use strict';

describe('Controller Tests', function() {

    describe('League Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLeague, MockImage, MockClub, MockRound;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLeague = jasmine.createSpy('MockLeague');
            MockImage = jasmine.createSpy('MockImage');
            MockClub = jasmine.createSpy('MockClub');
            MockRound = jasmine.createSpy('MockRound');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'League': MockLeague,
                'Image': MockImage,
                'Club': MockClub,
                'Round': MockRound
            };
            createController = function() {
                $injector.get('$controller')("LeagueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tableTennisApp:leagueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
