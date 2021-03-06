'use strict';

describe('Controller Tests', function() {

    describe('Club Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClub, MockUser, MockPlayer, MockImage, MockLeague;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClub = jasmine.createSpy('MockClub');
            MockUser = jasmine.createSpy('MockUser');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockImage = jasmine.createSpy('MockImage');
            MockLeague = jasmine.createSpy('MockLeague');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Club': MockClub,
                'User': MockUser,
                'Player': MockPlayer,
                'Image': MockImage,
                'League': MockLeague
            };
            createController = function() {
                $injector.get('$controller')("ClubDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tableTennisApp:clubUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
