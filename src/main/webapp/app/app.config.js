(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .config(NotificationProviderConfig);

    NotificationProviderConfig.$inject = ['NotificationProvider'];

    function NotificationProviderConfig(NotificationProvider) {
        NotificationProvider.setOptions({
            positionX: 'center'
        })
    }

})();
