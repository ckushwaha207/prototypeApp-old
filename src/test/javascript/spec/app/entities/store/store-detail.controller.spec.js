'use strict';

describe('Controller Tests', function() {

    describe('Store Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStore, MockStoreBranch, MockStoreUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStore = jasmine.createSpy('MockStore');
            MockStoreBranch = jasmine.createSpy('MockStoreBranch');
            MockStoreUser = jasmine.createSpy('MockStoreUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Store': MockStore,
                'StoreBranch': MockStoreBranch,
                'StoreUser': MockStoreUser
            };
            createController = function() {
                $injector.get('$controller')("StoreDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'prototypeApp:storeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
