var backApp = angular.module("backApp", [
    "ui.router", 
    "ui.bootstrap", 
    "oc.lazyLoad",  
    "ngSanitize",
    "angularFileUpload",
]); 

var imgs = [];

//AngularJS v1.3.x workaround for old style controller declarition in HTML
backApp.config(['$controllerProvider', function($controllerProvider) {
  // this option might be handy for migrating old apps, but please don't use it
  // in new ones!
  $controllerProvider.allowGlobals();
}]);
/* Setup global settings */
backApp.factory('settings', ['$rootScope', function($rootScope) {
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
backApp.controller('backCtrl', ['$scope', '$rootScope', function($scope, $rootScope) {
    	$scope.$on('$viewContentLoaded', function() {
    		
    		// initial the admin in session to js sessionAdmin object
    		$scope.initSessionAdmin = function(){
    			
    			if($rootScope.curAdmin==undefined ){
    				var url = "../admin/getSessionAdmin";
        			jsonGet(url, null, callback, false, false);
        			function callback(result) {
        				if (result.flag == RESULT_FLAG_SUCCESS) {
        					$rootScope.curAdmin = result.data;
        				} else {
        					$.toaster(result.msg, COMMON_LABEL_WARNING, 'warning');
        				}
        		    }
    			}
    		}// end initSessionAdmin
    		
    		$scope.initSessionAdmin();
	});
}]);


/* Setup Rounting For All Pages */
backApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	
    /* Defined the public injected files here */
    // common list files
    var listCommonFiles = [
          '../assets/global/plugins/datatables/datatables.min.css', 
          '../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css',
          '../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css',
          '../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css',
          
          '../assets/global/plugins/datatables/datatables.all.min.js',
          '../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
          '../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js',

        
          '../assets/global/plugins/datatables/datatables.all.min.js',
          '../assets/global/scripts/datatable.js',
          '../assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js',
          '../assets/pages/scripts/components-bootstrap-select.min.js',
          '../assets/global/plugins/bootstrap-select/css/bootstrap-select.css',
          
          '../assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js',
          '../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js',
          
    ]
    
    // common form files
    var formCommonFiles = [
		'../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css',
		'../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css',
		'../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css',
		   
		
		'../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
		'../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js',
		   
		
		'../assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js',
		'../assets/pages/scripts/components-bootstrap-select.min.js',
		'../assets/global/plugins/bootstrap-select/css/bootstrap-select.css',
		'../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js',
		 
		'../assets/global/plugins/ckeditor/ckeditor.js',
		'../assets/global/plugins/jstree/dist/themes/default/style.min.css',
		'../assets/global/plugins/jstree/dist/jstree.min.js',
    ]
    
	
	
    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/dashboard/dashboard");  
    $stateProvider
        // Dashboard
        .state('dashboard', {
            url: "/dashboard/dashboard",
            templateUrl: "views/dashboard/dashboard.html",            
            data: {pageTitle: 'Admin Dashboard Template'},
            controller: "dashboardCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            '../assets/global/plugins/morris/morris.css',                            
                            '../assets/global/plugins/morris/morris.min.js',
                            '../assets/global/plugins/morris/raphael-min.js',                            
                            '../assets/global/plugins/jquery.sparkline.min.js',
                            '../assets/pages/scripts/dashboard.min.js',
                            '../assets/global/plugins/echarts/echarts.min.js',
                            'js/controllers/dashboardCtrl.js'
                        ] 
                    });
                }]
            }
        })
         // adminList 初始化模板 加载 HTML css js
        .state('adminList', {
            url: "/admin/list",
            templateUrl: "views/admin/adminList.html",
            data: {pageTitle: 'Admin Datatables'},
            controller: "adminListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/adminCtrl.js'),
                    });
                }]
            }
        })
        
         // adminList 初始化模板 加载 HTML css js
        .state('adminForm', {
            url: "/admin/form/:id",
            templateUrl: "views/admin/adminForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "adminFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/adminCtrl.js'),
                    });
                }]
            }
        })  
          // nodeList 初始化模板 加载 HTML css js
        .state('nodeList', {
            url: "/node/list",
            templateUrl: "views/node/nodeList.html",
            data: {pageTitle: 'Node Datatables'},
            controller: "nodeListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/nodeCtrl.js'),
                    });
                }]
            }
        })
        
         // adminList 初始化模板 加载 HTML css js
        .state('nodeForm', {
            url: "/node/form/:id",
            templateUrl: "views/node/nodeForm.html",
            data: {pageTitle: 'Node Edit Form'},
            controller: "nodeFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/nodeCtrl.js'),
                    });
                }]
            }
        }) 
        // gameList 初始化模板 加载 HTML css js
        .state('gameList', {
            url: "/game/list",
            templateUrl: "views/game/gameList.html",
            data: {pageTitle: 'Game Datatables'},
            controller: "gameListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/gameCtrl.js'),
                    });
                }]
            }
        })
      // gameFrom初始化模板 加载 HTML css js
        .state('gameFrom', {
            url: "/game/form/:id",
            templateUrl: "views/game/gameForm.html",
            data: {pageTitle: 'game Edit Form'},
            controller: "gameFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/gameCtrl.js'),
                    });
                }]
            }
        })
        
        
        // linkList 初始化模板 加载 HTML css js
        .state('linkList', {
            url: "/link/list",
            templateUrl: "views/link/linkList.html",
            data: {pageTitle: 'List Datatables'},
            controller: "linkListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/linkCtrl.js'),
                    });
                }]
            }
        })
      // linkFrom初始化模板 加载 HTML css js
        .state('linkFrom', {
            url: "/link/form/:id",
            templateUrl: "views/link/linkForm.html",
            data: {pageTitle: 'link Edit Form'},
            controller: "linkFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/linkCtrl.js'),
                    });
                }]
            }
        })
        
        
        // newsList 初始化模板 加载 HTML css js
        .state('newsList', {
            url: "/news/list",
            templateUrl: "views/news/newsList.html",
            data: {pageTitle: 'Game Datatables'},
            controller: "newsListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/newsCtrl.js'),
                    });
                }]
            }
        })
      //newsFrom初始化模板 加载 HTML css js
        .state('newsFrom', {
            url: "/news/form/:id",
            templateUrl: "views/news/newsForm.html",
            data: {pageTitle: 'news Edit Form'},
            controller: "newsFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/newsCtrl.js'),
                    });
                }]
            }
        })
           // newsTypeList 初始化模板 加载 HTML css js
        .state('newsTypeList', {
            url: "/newsType/list",
            templateUrl: "views/newsType/newsTypeList.html",
            data: {pageTitle: 'Game Datatables'},
            controller: "newsTypeListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/newsTypeCtrl.js'),
                    });
                }]
            }
        })
      // gameFrom初始化模板 加载 HTML css js
        .state('newsTypeFrom', {
            url: "/newsType/form/:id",
            templateUrl: "views/newsType/newsTypeForm.html",
            data: {pageTitle: 'newsType Edit Form'},
            controller: "newsTypeFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/newsTypeCtrl.js'),
                    });
                }]
            }
        })
         // 广告设置
        .state('marqueeList', {
            url: "/marquee/list",
            templateUrl: "views/marquee/marqueeList.html",
            data: {pageTitle: 'Marquee Datatables'},
            controller: "marqueeListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/marqueeCtrl.js'),
                    });
                }]
            }
        })
         // gameFrom初始化模板 加载 HTML css js
        .state('marqueeFrom', {
            url: "/marquee/form/:id",
            templateUrl: "views/marquee/marqueeForm.html",
            data: {pageTitle: 'marquee Edit Form'},
            controller: "marqueeFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/marqueeCtrl.js'),
                    });
                }]
            }
        })
         // 会员设置
        .state('userList', {
            url: "/user/list/:filterUserId",
            templateUrl: "views/user/userList.html",
            data: {pageTitle: 'user Datatables'},
            controller: "userListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/userCtrl.js'),
                    });
                }]
            }
        })
         // userFrom初始化模板 加载 HTML css js
        .state('userFrom', {
            url: "/user/form/:id",
            templateUrl: "views/user/userForm.html",
            data: {pageTitle: 'user Edit Form'},
            controller: "userFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/userCtrl.js'),
                    });
                }]
            }
        })
         // 角色设置
        .state('roleList', {
            url: "/role/list",
            templateUrl: "views/role/roleList.html",
            data: {pageTitle: 'role Datatables'},
            controller: "roleListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/roleCtrl.js'),
                    });
                }]
            }
        })
         // roleFrom初始化模板 加载 HTML css js
        .state('roleFrom', {
            url: "/role/form/:id",
            templateUrl: "views/role/roleForm.html",
            data: {pageTitle: 'role Edit Form'},
            controller: "roleFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/roleCtrl.js'),
                    });
                }]
            }
        })
            // 会员财富值消费记录
        .state('userBalanceLogList', {
            url: "/userBalanceLog/list/:filterUserId",
            templateUrl: "views/userbalanceLog/userbalanceLogList.html",
            data: {pageTitle: 'userBalanceLogList Datatables'},
            controller: "userBalanceLogListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/userBalanceLogCtrl.js'),
                    });
                }]
            }
        })
         // userFrom初始化模板 加载 HTML css js
        .state('userBalanceLogFrom', {
            url: "/userBalanceLog/form/:id",
            templateUrl: "views/userbalanceLog/userbalanceLogForm.html",
            data: {pageTitle: 'userBalanceLog Edit Form'},
            controller: "userBalanceLogFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/userBalanceLogCtrl.js'),
                    });
                }]
            }
        })
           // 会员财富值消费记录
        .state('userDrawList', {
            url: "/userDraw/list",
            templateUrl: "views/userDraw/userDrawList.html",
            data: {pageTitle: 'userDrawList Datatables'},
            controller: "userDrawListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/userDrawCtrl.js'),
                    });
                }]
            }
        })
         // userFrom初始化模板 加载 HTML css js
        .state('userDrawFrom', {
            url: "/userDraw/form/:id",
            templateUrl: "views/userDraw/userDrawForm.html",
            data: {pageTitle: 'userDraw Edit Form'},
            controller: "userDrawFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/userDrawCtrl.js'),
                    });
                }]
            }
        })
        
        // gameList 初始化模板 加载 HTML css js
        .state('contentHtmlList', {
            url: "/content/list",
            templateUrl: "views/content/contentList.html",
            data: {pageTitle: 'Content Datatables'},
            controller: "contentHtmlListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/contentCtrl.js'),
                    });
                }]
            }
        })
      // gameFrom初始化模板 加载 HTML css js
        .state('contentFrom', {
            url: "/content/form/:id",
            templateUrl: "views/content/contentForm.html",
            data: {pageTitle: 'content Edit Form'},
            controller: "contentFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/contentCtrl.js'),
                    });
                }]
            }
        })
        // 系统信息->系统日志
        .state('log', {
            url: "/log/log",
            templateUrl: "../log/log.html",
            data: {pageTitle: 'system logger'},
        })
        
         // prodTypePhyList 初始化模板 加载 HTML css js
        .state('prodTypePhyList', {
            url: "/prodTypePhy/list",
            templateUrl: "views/prodtype/typePhy/prodTypePhyList.html",
            data: {pageTitle: 'ProdTypePhy Datatables'},
            controller: "phyListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypePhyCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypePhyForm 初始化模板 加载 HTML css js
        .state('phyForm', {
            url: "/prodTypePhy/form/:id",
            templateUrl: "views/prodtype/typePhy/prodTypePhyForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "phyFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypePhyCtrl.js'), 
                    });
                }]
            }
        })
        
         // prodTypeAreList 初始化模板 加载 HTML css js
        .state('prodTypeAreList', {
            url: "/prodTypeAre/list",
            templateUrl: "views/prodtype/typeAre/prodTypeAreList.html",
            data: {pageTitle: 'ProdTypeAre Datatables'},
            controller: "areListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeAreCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeAreForm 初始化模板 加载 HTML css js
        .state('areForm', {
            url: "/prodTypeAre/form/:id",
            templateUrl: "views/prodtype/typeAre/prodTypeAreForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "areFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeAreCtrl.js'), 
                    });
                }]
            }
        })
        
         // prodTypeBizList 初始化模板 加载 HTML css js
        .state('prodTypeBizList', {
            url: "/prodTypeBiz/list",
            templateUrl: "views/prodtype/typeBiz/prodTypeBizList.html",
            data: {pageTitle: 'ProdTypeBiz Datatables'},
            controller: "bizListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeBizCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeBizForm 初始化模板 加载 HTML css js
        .state('bizForm', {
            url: "/prodTypeBiz/form/:id",
            templateUrl: "views/prodtype/typeBiz/prodTypeBizForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "bizFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeBizCtrl.js'), 
                    });
                }]
            }
        })        
        
	
	 // prodTypeConList 初始化模板 加载 HTML css js
        .state('prodTypeConList', {
            url: "/prodTypeCon/list",
            templateUrl: "views/prodtype/typeCon/prodTypeConList.html",
            data: {pageTitle: 'ProdTypeCon Datatables'},
            controller: "conListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeConCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeConForm 初始化模板 加载 HTML css js
        .state('conForm', {
            url: "/prodTypeCon/form/:id",
            templateUrl: "views/prodtype/typeCon/prodTypeConForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "conFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeConCtrl.js'), 
                    });
                }]
            }
        })
	
	 // prodTypeHigList 初始化模板 加载 HTML css js
        .state('prodTypeHigList', {
            url: "/prodTypeHig/list",
            templateUrl: "views/prodtype/typeHig/prodTypeHigList.html",
            data: {pageTitle: 'ProdTypeHig Datatables'},
            controller: "higListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeHigCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeHigForm 初始化模板 加载 HTML css js
        .state('higForm', {
            url: "/prodTypeHig/form/:id",
            templateUrl: "views/prodtype/typeHig/prodTypeHigForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "higFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeHigCtrl.js'), 
                    });
                }]
            }
        })
	
	 // prodTypeJarList 初始化模板 加载 HTML css js
        .state('prodTypeJarList', {
            url: "/prodTypeJar/list",
            templateUrl: "views/prodtype/typeJar/prodTypeJarList.html",
            data: {pageTitle: 'ProdTypeJar Datatables'},
            controller: "jarListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeJarCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeJarForm 初始化模板 加载 HTML css js
        .state('jarForm', {
            url: "/prodTypeJar/form/:id",
            templateUrl: "views/prodtype/typeJar/prodTypeJarForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "jarFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeJarCtrl.js'), 
                    });
                }]
            }
        })
	 // prodTypeNekList 初始化模板 加载 HTML css js
        .state('prodTypeNekList', {
            url: "/prodTypeNek/list",
            templateUrl: "views/prodtype/typeNek/prodTypeNekList.html",
            data: {pageTitle: 'ProdTypeNek Datatables'},
            controller: "nekListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeNekCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeNekForm 初始化模板 加载 HTML css js
        .state('nekForm', {
            url: "/prodTypeNek/form/:id",
            templateUrl: "views/prodtype/typeNek/prodTypeNekForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "nekFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeNekCtrl.js'), 
                    });
                }]
            }
        })
	
	 // prodTypePicList 初始化模板 加载 HTML css js
        .state('prodTypePicList', {
            url: "/prodTypePic/list",
            templateUrl: "views/prodtype/typePic/prodTypePicList.html",
            data: {pageTitle: 'ProdTypePic Datatables'},
            controller: "picListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypePicCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypePicForm 初始化模板 加载 HTML css js
        .state('picForm', {
            url: "/prodTypePic/form/:id",
            templateUrl: "views/prodtype/typePic/prodTypePicForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "picFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypePicCtrl.js'), 
                    });
                }]
            }
        })
	
	 // prodTypeSouList 初始化模板 加载 HTML css js
        .state('prodTypeSouList', {
            url: "/prodTypeSou/list",
            templateUrl: "views/prodtype/typeSou/prodTypeSouList.html",
            data: {pageTitle: 'ProdTypeSou Datatables'},
            controller: "souListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeSouCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeSouForm 初始化模板 加载 HTML css js
        .state('souForm', {
            url: "/prodTypeSou/form/:id",
            templateUrl: "views/prodtype/typeSou/prodTypeSouForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "souFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeSouCtrl.js'), 
                    });
                }]
            }
        })
	
	 // prodTypeWidList 初始化模板 加载 HTML css js
        .state('prodTypeWidList', {
            url: "/prodTypeWid/list",
            templateUrl: "views/prodtype/typeWid/prodTypeWidList.html",
            data: {pageTitle: 'ProdTypeWid Datatables'},
            controller: "widListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodTypeWidCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodTypeWidForm 初始化模板 加载 HTML css js
        .state('widForm', {
            url: "/prodTypeWid/form/:id",
            templateUrl: "views/prodtype/typeWid/prodTypeWidForm.html",
            data: {pageTitle: 'Admin Edit Form'},
            controller: "widFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodTypeWidCtrl.js'), 
                    });
                }]
            }
        })
        
        
        // prodCostList 初始化模板 加载 HTML css js
        .state('prodCostList', {
            url: "/prodCost/list",
            templateUrl: "views/prodcost/prodCostList.html",
            data: {pageTitle: 'ProdCost Datatables'},
            controller: "prodCostListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/prodCostCtrl.js'), 
                    });
                }]
            }
        })
        
        // prodCostForm 初始化模板 加载 HTML css js
        .state('prodCostForm', {
            url: "/prodCost/form/:id",
            templateUrl: "views/prodcost/prodCostForm.html",
            data: {pageTitle: 'prodCost Edit Form'},
            controller: "prodCostFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/prodCostCtrl.js'), 
                    });
                }]
            }
        })

        // prodCostList 初始化模板 加载 HTML css js
        .state('prodList', {
            url: "/prod/list",
            templateUrl: "views/prod/prodList.html",
            data: {pageTitle: 'Prod Datatables'},
            controller: "prodListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat(['../assets/project/scripts/petProdTypeSelect.js','js/controllers/prodCtrl.js']), 
                    });
                }]
            }
        })
        
        // prodForm 初始化模板 加载 HTML css js
        .state('prodForm', {
            url: "/prod/form/:id",
            templateUrl: "views/prod/prodForm.html",
            data: {pageTitle: 'prod Edit Form'},
            controller: "prodFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat(['../assets/project/scripts/petProdTypeSelect.js','js/controllers/prodCtrl.js']), 
                    });
                }]
            }
        })
        
        
        // shopList 初始化模板 加载 HTML css js
        .state('shopList', {
            url: "/shop/list/:shopId",
            templateUrl: "views/shop/shopList.html",
            data: {pageTitle: 'shop Datatables'},
            controller: "shopListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/shopCtrl.js'), 
                    });
                }]
            }
        })
        
        // shopForm 初始化模板 加载 HTML css js
        .state('shopForm', {
            url: "/shop/form/:id",
            templateUrl: "views/shop/shopForm.html",
            data: {pageTitle: 'shop Edit Form'},
            controller: "shopFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/shopCtrl.js'), 
                    });
                }]
            }
        })
          // wareTypeMchList 初始化模板 加载 HTML css js
        .state('wareTypeMchList', {
            url: "/wareTypeMch/list",
            templateUrl: "views/waretype/mch/wareTypeMchList.html",
            data: {pageTitle: 'wareTypeMch Datatables'},
            controller: "wareTypeMchListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/wareTypeMchCtrl.js'), 
                    });
                }]
            }
        })
        
        // wareTypeMchForm 初始化模板 加载 HTML css js
        .state('wareTypeMchForm', {
            url: "/wareTypeMch/form/:id",
            templateUrl: "views/waretype/mch/wareTypeMchForm.html",
            data: {pageTitle: 'wareTypeMch Edit Form'},
            controller: "wareTypeMchFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/wareTypeMchCtrl.js'), 
                    });
                }]
            }
        })
        
           // wareTypeSrcList 初始化模板 加载 HTML css js
        .state('wareTypeSrcList', {
            url: "/wareTypeSrc/list",
            templateUrl: "views/waretype/src/wareTypeSrcList.html",
            data: {pageTitle: 'wareTypeSrc Datatables'},
            controller: "wareTypeSrcListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/wareTypeSrcCtrl.js'), 
                    });
                }]
            }
        })
        
        // wareTypeSrcForm 初始化模板 加载 HTML css js
        .state('wareTypeSrcForm', {
            url: "/wareTypeSrc/form/:id",
            templateUrl: "views/waretype/src/wareTypeSrcForm.html",
            data: {pageTitle: 'wareTypeSrc Edit Form'},
            controller: "wareTypeSrcFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/wareTypeSrcCtrl.js'), 
                    });
                }]
            }
        })
        
           // wareTypeSteList 初始化模板 加载 HTML css js
        .state('wareTypeSteList', {
            url: "/wareTypeSte/list",
            templateUrl: "views/waretype/ste/wareTypeSteList.html",
            data: {pageTitle: 'wareTypeSte Datatables'},
            controller: "wareTypeSteListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/wareTypeSteCtrl.js'), 
                    });
                }]
            }
        })
        
        // wareTypeSteForm 初始化模板 加载 HTML css js
        .state('wareTypeSteForm', {
            url: "/wareTypeSte/form/:id",
            templateUrl: "views/waretype/ste/wareTypeSteForm.html",
            data: {pageTitle: 'wareTypeSte Edit Form'},
            controller: "wareTypeSteFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/wareTypeSteCtrl.js'), 
                    });
                }]
            }
        })
        
            // wareTypePrdList 初始化模板 加载 HTML css js
        .state('wareTypePrdList', {
            url: "/wareTypePrd/list",
            templateUrl: "views/waretype/prd/wareTypePrdList.html",
            data: {pageTitle: 'wareTypePrd Datatables'},
            controller: "wareTypePrdListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/wareTypePrdCtrl.js'), 
                    });
                }]
            }
        })
        
        // wareTypePrdForm 初始化模板 加载 HTML css js
        .state('wareTypePrdForm', {
            url: "/wareTypePrd/form/:id",
            templateUrl: "views/waretype/prd/wareTypePrdForm.html",
            data: {pageTitle: 'wareTypePrd Edit Form'},
            controller: "wareTypePrdFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat('js/controllers/wareTypePrdCtrl.js'), 
                    });
                }]
            }
        })
        
          // wareList 初始化模板 加载 HTML css js
        .state('wareList', {
            url: "/ware/list/:filterShopId",
            templateUrl: "views/ware/wareList.html",
            data: {pageTitle: 'ware Datatables'},
            controller: "wareListCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: listCommonFiles.concat('js/controllers/wareCtrl.js'), 
                    });
                }]
            }
        })
        
        // wareForm 初始化模板 加载 HTML css js
        .state('wareForm', {
            url: "/ware/form/:id",
            templateUrl: "views/ware/wareForm.html",
            data: {pageTitle: 'ware Edit Form'},
            controller: "wareFormCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat(['../assets/project/scripts/petWareTypeSelect.js','js/controllers/wareCtrl.js']), 
                    });
                }]
            }
        })
        
        // help 初始化模板 加载 HTML css js
        .state('help', {
            url: "/system/help",
            templateUrl: "views/system/help.html",
            data: {pageTitle: 'help Form'},
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                    });
                }]
            }
        })
        
         // 系统设置 初始化模板 加载 HTML css js
         // wareForm 初始化模板 加载 HTML css js
        .state('sysMgmt', {
            url: "/sysMgmt/sysMgmt",
            templateUrl: "views/sysMgmt/sysMgmt.html",
            data: {pageTitle: 'sysMgmt Form'},
            controller: "sysMgmtCtrl",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'backApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: formCommonFiles.concat(['js/controllers/sysMgmt.js']), 
                    });
                }]
            }
        })

}]);

/* Init global settings and run the app */
backApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
    $rootScope.$settings = settings; // state to be accessed from view
}]);