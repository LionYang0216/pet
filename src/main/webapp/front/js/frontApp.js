/* Front App */
var frontApp = angular.module("frontApp", [
    "ui.router", 
    "ui.bootstrap", 
    "oc.lazyLoad",  
    "ngSanitize",
    'ngRoute', 'ngSocial', 'embedCodepen', 'ngSanitize','pascalprecht.translate', "angularFileUpload",
]); 

var imgs = [];


//AngularJS v1.3.x workaround for old style controller declarition in HTML
frontApp.config(['$controllerProvider','$translateProvider', function($controllerProvider,$translateProvider) {
  // this option might be handy for migrating old apps, but please don't use it
  // in new ones!
  $controllerProvider.allowGlobals();
  
  
  $translateProvider.useStaticFilesLoader({
		files: [{
		  prefix: './i18n/locale-',
		  suffix: '.json'
		 }]
	});
	/**
	 *@event 语言注册
	*/
	$translateProvider.registerAvailableLanguageKeys(['en', 'zh'], {
	   'en_US': 'en',
	   'zh_CN': 'zh'
	}); 
	$translateProvider.determinePreferredLanguage('');
	$translateProvider.fallbackLanguage('zh');
    
}]);

/********************************************
 END: BREAKING CHANGE in AngularJS v1.3.x:
*********************************************/

/* Setup global settings */
frontApp.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageContentWhite: true, // set page content layout
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        assetsPath: '../assets',
        globalPath: '../assets/global',
        layoutPath: '../assets/layouts/layout',
    };

    $rootScope.settings = settings;

    return settings;
}]);

/* Setup App Main Controller */
frontApp.controller('frontCtrl', ['$scope', '$rootScope', function($scope, $rootScope) {
    
	$scope.$on('$viewContentLoaded', function() {
        //App.initComponents(); // init core components
        //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive 
        //Layout.initOWL();

	});
	
	$rootScope.curLang = "zh"; // global lang current lang
    
    
}]);






/* Setup Layout Part - Footer */
frontApp.controller('footerCtrl', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        //Layout.initFooter(); // init footer  	
    	$scope.getLinkList()
    });
    
    
	var reqData={
	    "draw": 1,
	    "columns": [
	        {
	            "data": null,
	            "name": "",
	            "searchable": false,
	            "orderable": false,
	            "search": {
	                "value": "",
	                "regex": false
	            }
	        },
	        
	        {
	            "data": "id",
	            "name": "",
	            "searchable": false,
	            "orderable": false,
	            "search": {
	                "value": "",
	                "regex": false
	            }
	        }
	    ],
	    "order": [
	        {
	            "column": 1,
	            "dir": "desc"
	        }
	    ],
	    "start": 0,
	    "length":100,
	    "search": {
	        "value": "",
	        "regex": false
	    }
	}
    
    
    //获取数据  
    $scope.getLinkList = function(){  
    	var url = "../link/getLinkList";
    	reqData.draw+=1;
		jsonPost(url, reqData, callback, true, false);
		function callback(result) {
			if (result.flag == RESULT_FLAG_SUCCESS) {
				var linkList=result.data;
				for(var i in linkList){
					linkList[i].lastUpdateTimeLayout= dateTimeString(linkList[i].lastUpdateTime);
				}
				$scope.linkList =linkList;
				$scope.$apply();
			} else {
				toastr.error(result.msg, COMMON_LABEL_ERROR);
			}
	    }
    }  
}]);

/* Setup Rounting For All Pages */
frontApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	
	
    // common form files
    var formCommonFiles = [
          '../assets/global/plugins/ckeditor/ckeditor.js',
    ]
    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/main/home");
    $stateProvider
        
         // main首页 加载 HTML css js
        .state('main', {
            url: "/main/home",
            templateUrl: "views/main/home.html",
            data: {pageTitle: 'Home'},
            controller: "homeCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'frontApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            'js/controllers/homeCtrl.js','../assets/global/plugins/imageflow/imageflow.js'
                        ]
                    });
                }]
            }
        })
        
        
         //productList页 加载 HTML css js
        .state('productList', {
            url: "/prod/prodList",
            templateUrl: "views/prod/prodList.html",
            data: {pageTitle: 'Product'},
            controller: "prodListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'frontApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            'js/controllers/prodCtrl.js',
                        ]
                    });
                }]
            }
        })
        
        
         // prodDetail页 加载 HTML css js
	    .state('prodDetail', {
	        url: "/prod/prodDetail/:id",
	        templateUrl: "views/prod/prodDetail.html",
	        data: {pageTitle: 'ProdDetail'},
	        controller: "prodDetailCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/prodCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
        
         // wareList页 加载 HTML css js
        .state('wareList', {
            url: "/ware/wareList/:id",
            templateUrl: "views/ware/wareList.html",
            data: {pageTitle: 'Ware'},
            controller: "wareListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'frontApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                        	'../assets/project/scripts/petWareTypeSelect.js',
                            'js/controllers/wareCtrl.js',
                        ]
                    });
                }]
            }
        })
        
         // wareDetail页 加载 HTML css js
	    .state('wareDetail', {
	        url: "/ware/wareDetail/:id",
	        templateUrl: "views/ware/wareDetail.html",
	        data: {pageTitle: 'wareDetail'},
	        controller: "wareDetailCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/wareCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
        
       
        
          // gameList页 加载 HTML css js
        .state('gameList', {
            url: "/game/gameList",
            templateUrl: "views/game/gameList.html",
            data: {pageTitle: 'Game'},
            controller: "gameListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'frontApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                            'js/controllers/gameCtrl.js',
                        ]
                    });
                }]
            }
        })
    
	    // gameDetail页 加载 HTML css js
	    .state('gameDetail', {
	        url: "/game/gameDetail/:id",
	        templateUrl: "views/game/gameDetail.html",
	        data: {pageTitle: 'Game'},
	        controller: "gameDetailCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/gameCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	    // 注册页 加载 HTML css js
	    .state('userReg', {
	        url: "/user/userReg",
	        templateUrl: "views/user/userReg.html",
	        data: {pageTitle: 'User'},
	        controller: "userRegCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/userCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	    	    
	     // 个人中心页 加载 HTML css js
	    .state('myCenter', {
	        url: "/my/myCenter",
	        templateUrl: "views/my/myCenter.html",
	        data: {pageTitle: 'myCenter'},
	        controller: "myCenterCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                    	'../assets/global/plugins/ckeditor/ckeditor.js',
	                    	'../assets/project/scripts/petWareTypeSelect.js',
	                    	'js/controllers/myCenterCtrl.js','js/controllers/myHeaderCtrl.js',
	                    	'js/controllers/myWorkListCtrl.js', 'js/controllers/myWorkFormCtrl.js',
	                    	'js/controllers/myFavCtrl.js','js/controllers/myBalanceCtrl.js',
	                        'js/controllers/myShopFormCtrl.js','js/controllers/myInfoCtrl.js',
	                        'js/controllers/myWareListCtrl.js','js/controllers/myWareFormCtrl.js',
	                        'js/controllers/myDrawForm.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	    

	    
	     // 个人中心页页头 加载 HTML css js
	    .state('myHeader', {
	        url: "/user/myHeader",
	        templateUrl: "views/user/myHeader.html",
	        data: {pageTitle: 'myHeader'},
	        controller: "myHeaderCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/myHeaderCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	    // 我的作品 加载 HTML css js
	    .state('myWork', {
	        url: "/user/myWork",
	        templateUrl: "views/user/myWorkList.html",
	        data: {pageTitle: 'myWork'},
	        controller: "myWorkListCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/myWorkListCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	    
	    // 我的店铺 加载 HTML css js
	    .state('myShop', {
	        url: "/my/myShop",
	        templateUrl: "views/my/myShop.html",
	        data: {pageTitle: 'myShop'},
	        controller: "myShopCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/myShopCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	     // 我的资料 加载 HTML css js
	    .state('myInfo', {
	        url: "/my/myInfo",
	        templateUrl: "views/my/myInfo.html",
	        data: {pageTitle: 'myInfo'},
	        controller: "myInfoCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/myInfoCtrl.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	    
	      // 我的商店信息 加载 HTML css js
	    .state('shopForm', {
	        url: "/my/myShopForm",
	        templateUrl: "views/my/myShopForm.html",
	        data: {pageTitle: 'myShopForm'},
	        controller: "myShopFormCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files:formCommonFiles.concat(['js/controllers/myShopFormCtrl.js'])
	                });
	            }]
	        }
	    })
	    
	    
	     // 我的商店产品列表 加载 HTML css js
	    .state('myWareList', {
	        url: "/my/myWareList",
	        templateUrl: "views/my/myWareList.html",
	        data: {pageTitle: 'myWareList'},
	        controller: "myWareListCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files:['js/controllers/myWareListCtrl.js']
	                });
	            }]
	        }
	    })
	    
	     // myWareForm页 加载 HTML css js
	    .state('myWareForm', {
	        url: "/my/myWareForm/:id",
	        templateUrl: "views/my/myWareForm.html",
	        data: {pageTitle: 'myWareForm'},
	        controller: "myWareFormCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files:formCommonFiles.concat(['../assets/project/scripts/petWareTypeSelect.js','js/controllers/myWareForm.js']),
	                });
	            }]
	        }
	    })
	    
	    
	     // myWorkForm页 加载 HTML css js
	    .state('myWorkForm', {
	        url: "/my/myWorkForm/:id",
	        templateUrl: "views/my/myWorkForm.html",
	        data: {pageTitle: 'myWorkForm'},
	        controller: "myWorkFormCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files:formCommonFiles.concat(['js/controllers/myWorkForm.js']),
	                });
	            }]
	        }
	    })
	    
	      
	     // 新闻动态列表 加载 HTML css js
	    .state('newsList', {
	        url: "/news/newsList",
	        templateUrl: "views/news/newsList.html",
	        data: {pageTitle: 'newsList'},
	        controller: "newsListCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files:['js/controllers/newsCtrl.js']
	                });
	            }]
	        }
	    })
	    
	     // 新闻详情页 加载 HTML css js
	    .state('newsDetail', {
	        url: "/news/newsDetail/:id",
	        templateUrl: "views/news/newsDetail.html",
	        data: {pageTitle: 'newsDetail'},
	        controller: "newsDetailCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files:['js/controllers/newsCtrl.js']
	                });
	            }]
	        }
	    })
	    
	    
	    // wareDetail页 加载 HTML css js
	    .state('content', {
	        url: "/content/content/:id",
	        templateUrl: "views/content/content.html",
	        data: {pageTitle: 'content'},
	        controller: "contentCtrl",
	        resolve: {
	            deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                return $ocLazyLoad.load({
	                    name: 'frontApp',
	                    insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
	                    files: [
	                        'js/controllers/content.js',
	                    ]
	                });
	            }]
	        }
	    })
	    
	    
	        
}]);

/* Init global settings and run the app */

frontApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
    $rootScope.$settings = settings; // state to be accessed from view
}]);


//分页算法  (数组多少页)
var calculateIndexes = function (current, length, displayLength) {  
   var indexes = [];  
   var start = Math.round(current - displayLength / 2);  
   var end = Math.round(current + displayLength / 2);  
    if (start <= 1) {  
        start = 1;  
        end = start + displayLength - 1;  
       if (end >= length - 1) {  
           end = length - 1;  
        }  
    }  
    if (end >= length - 1) {  
       end = length;  
        start = end - displayLength + 1;  
       if (start <= 1) {  
           start = 1;  
        }  
    }  
    for (var i = start; i <= end; i++) {  
        indexes.push(i);  
    }  
    return indexes;  
  };  