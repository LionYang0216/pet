/* Setup Layout Part - Footer */
angular.module('backApp').controller('footerCtrl', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter(); // init footer
        $scope.goContent=function(code){
        	window.location.href="#/content/content/"+code;
        }
        
    });
      
}]);