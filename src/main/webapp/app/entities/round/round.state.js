(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('round', {
            parent: 'entity',
            url: '/round?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rounds'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/round/rounds.html',
                    controller: 'RoundController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('round-detail', {
            parent: 'entity',
            url: '/round/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Round'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/round/round-detail.html',
                    controller: 'RoundDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Round', function($stateParams, Round) {
                    return Round.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'round',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('round-detail.edit', {
            parent: 'round-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/round/round-dialog.html',
                    controller: 'RoundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Round', function(Round) {
                            return Round.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('round.new', {
            parent: 'round',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/round/round-dialog.html',
                    controller: 'RoundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('round', null, { reload: 'round' });
                }, function() {
                    $state.go('round');
                });
            }]
        })
        .state('round.edit', {
            parent: 'round',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/round/round-dialog.html',
                    controller: 'RoundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Round', function(Round) {
                            return Round.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('round', null, { reload: 'round' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('round.delete', {
            parent: 'round',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/round/round-delete-dialog.html',
                    controller: 'RoundDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Round', function(Round) {
                            return Round.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('round', null, { reload: 'round' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
