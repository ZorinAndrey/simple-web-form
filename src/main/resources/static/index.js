(function () {
    angular
        .module('simple-web-form', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/welcome', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/form', {
                templateUrl: 'form/form.html',
                controller: 'formController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
    }
})();

angular.module('simple-web-form').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
});