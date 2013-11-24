requirejs.config({
	
	paths : {
		'jquery'		: 'lib/jquery-1.10.2.min',
		'json'			: 'lib/json2.min',
		'underscore'	: 'lib/underscore-min',
		'mustache'		: 'lib/mustache',
		'bootstrap'		: 'lib/bootstrap.min',
		'amplify'		: 'lib/amplify.min',
		'holder'		: 'lib/holder',
		'normalize'		: 'lib/normalize',
		'map'			: 'http://apis.daum.net/maps/maps3.js?apikey=0a0370638f1e498d51945e52cfbec378ac7c029a',
		
		'text' 			: 'lib/text',
		'css'			: 'lib/css',
		
		'jquery.ui' : 'lib/jquery-ui-1.10.3.custom.min',
		'jquery.dataTables' : 'lib/jquery.dataTables.min',
		'jquery.multi-select' : 'lib/jquery.multi-select',
		'jquery.placeholder' : 'lib/jquery.placeholder.min',
		'jquery.stacktable' : 'lib/jquery.stacktable',
		'jquery.tagsinput' : 'lib/jquery.tagsinput',
		'jquery.ui.touch-punch' : 'lib/jquery.ui.touch-punch',
		'jquery.validate' : 'lib/jquery.validate.min',
		
		'flatui.checkbox' : 'lib/flatui-checkbox',
		'flatui.radio' : 'lib/flatui.radio'
		
	},
	map: {
	  '*': {
	    
	  }
	},
	shim : {
		'jquery' : {
			exports : '$'
		},
		
		'jquery.ui' : ['jquery'],
		'jquery.dataTables' : ['jquery'],
		'jquery.multi-select' : ['jquery'],
		'jquery.placeholder' : ['jquery'],
		'jquery.stacktable' : ['jquery'],
		'jquery.tagsinput' : ['jquery'],
		'jquery.ui.touch-punch' : ['jquery', 'jquery.ui'],
		'jquery.validate' : ['jquery'],
		
		'underscore' : {
			exports : '_'
		},
		
		'json' : {
			exports : 'JSON'
		},
		
		'amplify' : {
			deps : ['json'],
			exports : 'amplify'
		},
	
		'mustache' : {
			exports : 'Mustache'
		},
		
		'bootstrap' : {
			deps : ['jquery']
		},
		
		'map' : {
			exports : 'daum'
		},
		
		'css' : {
			deps : ['normalize']
		}
		
		
	}
});


requirejs([
	'jquery',  
	'amplify',
	'normalize',
	'bootstrap', 
	'css!style/jquery-ui-1.10.3.custom.min',
	'css!style/bootstrap.min',
	'css!style/flat-ui'], function($, amplify) {
		
	$(document).ready( function(e) {
		amplify.store('env', 'development')
		
		if( amplify.store('env') === 'development' )
			amplify.store('host', 'http://localhost')
		else if( amplify.store('env') ===  'release')
			amplify.store('host', 'http://playbook.cafe24.com')
		
		window.kompass = {};
				
	});
});
