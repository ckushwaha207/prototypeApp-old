'use strict';

describe('Controller Tests', function() {

    describe('StoreBranch Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStoreBranch, MockLocation, MockStore;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStoreBranch = jasmine.createSpy('MockStoreBranch');
            MockLocation = jasmine.createSpy('MockLocation');
            MockStore = jasmine.createSpy('MockStore');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StoreBranch': MockStoreBranch,
                'Location': MockLocation,
                'Store': MockStore
            };
            createController = function() {
                $injector.get('$controller')("StoreBranchDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'prototypeApp:storeBranchUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
