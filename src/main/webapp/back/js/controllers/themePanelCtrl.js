/* Setup Layout Part - Theme Panel */
angular.module('backApp').controller('themePanelCtrl', ['$scope', function($scope) {    
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel
    });
}]);
