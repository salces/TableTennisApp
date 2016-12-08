(function() {
    'use strict';

    angular
        .module('tableTennisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tournament-stage', {
            parent: 'entity',
            url: '/tournament-stage?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TournamentStages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tournament-stage/tournament-stages.html',
                    controller: 'TournamentStageController',
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
        .state('tournament-stage-detail', {
            parent: 'entity',
            url: '/tournament-stage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TournamentStage'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tournament-stage/tournament-stage-detail.html',
                    controller: 'TournamentStageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TournamentStage', function($stateParams, TournamentStage) {
                    return TournamentStage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tournament-stage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tournament-stage-detail.edit', {
            parent: 'tournament-stage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament-stage/tournament-stage-dialog.html',
                    controller: 'TournamentStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TournamentStage', function(TournamentStage) {
                            return TournamentStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tournament-stage.new', {
            parent: 'tournament-stage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament-stage/tournament-stage-dialog.html',
                    controller: 'TournamentStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                phase: null,
                                phaseCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tournament-stage', null, { reload: 'tournament-stage' });
                }, function() {
                    $state.go('tournament-stage');
                });
            }]
        })
        .state('tournament-stage.edit', {
            parent: 'tournament-stage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament-stage/tournament-stage-dialog.html',
                    controller: 'TournamentStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TournamentStage', function(TournamentStage) {
                            return TournamentStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tournament-stage', null, { reload: 'tournament-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tournament-stage.delete', {
            parent: 'tournament-stage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament-stage/tournament-stage-delete-dialog.html',
                    controller: 'TournamentStageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TournamentStage', function(TournamentStage) {
                            return TournamentStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tournament-stage', null, { reload: 'tournament-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
