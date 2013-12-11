/**
 * Initialize App
 */
$(document).ready(function() {
	
	var watcher = {};
	watcher.environment = 'development';
	watcher.path = null;
	watcher.currentLocation = null;
	watcher.geocoder = null;
	watcher.info = null;
	watcher.log = function(msg) {
		if(window.watcher.environment == 'development') {
			if(console) console.log(msg);
		}
	}
	
	window.watcher = watcher;
	
	// make date layout
	$( "input.date" ).datepicker({
		showOn: "button",
		buttonImage: "",
		buttonImageOnly: true
	});
	$("input.date").datepicker('setDate', new Date());
	var d = $("input.date").datepicker('getDate');
	$('span.date').text( d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate() );
	
	// click listeners
	$("button.date").on('click', function(e) {
		$(".ui-datepicker-trigger").trigger('click');
		return false;
	});
	
	$("input.date").on('change', function(e) {
		var d = $("input.date").datepicker('getDate');
		$('span.date').text( d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate() );
		
		loadCoordsOfDay(d);
	});
	
	$("button.logout").on('click', function(e) {
		var now = new Date();
		$.ajax({
			url : 'HandlerServlet',
			data : {action : 'logout', TS : now.getTime() },
			dataType : 'json',
			type : "POST",
			success : function(json) {
				if(json.success-0 > 0) {
					setTimeout(function() {
						window.location = "index.jsp";
					}, 10);
				} else {
					watcher.log('cannot logout. logout failed');
				}
			},
			error : function(json) {
				watcher.log('cannot logout. logout failed');
			}
				
		});
	});

	initMap();
});

/**
 * Initiate Map Object
 */
function initMap() {
	var mapOptions = {
		center: new google.maps.LatLng(-34.397, 150.644),
		zoom: 8,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	watcher.map = new google.maps.Map(document.getElementById("map"), mapOptions);
	
	// { action : "getGPS", fromTS : 1385714459, toTS : 1385724459, phonenumber : "01012345678", TS : 1385734459 }
	loadCoordsOfDay();
}

/**
 * Load and draw Coordinates with date or timestamp get from server
 */
function loadCoordsOfDay(date) {
		if(!date) {
			date = new Date();
		}
		
		date.setHours(0,0,0,0);
		var fromTS = date.getTime()/1000;
		
		date.setHours(24,0,0,0);
		var toTS = date.getTime()/1000;
		
		loadCoords(fromTS, toTS);
}

function loadCoords(fromTS, toTS) {
	if(!fromTS) {
		var d = new Date();
		d.setHours(0,0,0,0);
		fromTS = d.getTime()/1000;
	}
	
	if(!toTS) {
		var d = new Date();
		d.setHours(24,0,0,0);
		toTS = d.getTime()/1000;
	}
	
	$.ajax({
		url : 'http://119.197.38.238:8080/Watcher/HandlerServlet',
		type : "GET",
		dataType : 'json',
		data : { action : 'getGPS', fromTS : fromTS, toTS : toTS },
		success : function(json) {
			if( json.success-0 > 0) {
				// var gpsArray = json.gps; // [ {lat : 37.566113, lon : 126.940148, TS : 1385734459} ]
								// for(var a in b) {}
				
				cleanMap(watcher.map);
				
				if(json.gps.length > 0) {
					var coords = toLatLngArray(json.gps);
					overlayCoords(watcher.map, coords);
					fitMap(watcher.map, coords);
					
					var lastGPS = json.gps[json.gps.length-1] 
					var lastDate = new Date(lastGPS.ts*1000);
					var lastCoord = new google.maps.LatLng(lastGPS.lat, lastGPS.lon);
					
					markCurrentLocation(watcher.map, lastDate, lastCoord);
					addressCurrentLocation(lastDate, lastCoord)
				} else {
					overlayNothing(watcher.map);
				}
				
			} else {
				watcher.log(json.message);
			}
		},
		error : function(json) {
			watcher.log(json);
		}
	})
}

/**
 * Overlay informations
 */
function overlayCoords(map, coords) {
	
	
	var spotSymbol = {
		path : google.maps.SymbolPath.CIRCLE,
		fillColor : 'yellow',
		fillOpacity : 0.65,
		scale : 1,
		strokeColor : 'fold',
		strokeWeight : 14
	};

	var path = new google.maps.Polyline({
		path : coords,
		map : map,
	    strokeColor: "#CC0000",
	    strokeOpacity: 0.75,
	    strokeWeight: 7
	});

	watcher.path = path;
	
}
function overlayNothing(map, coord) {
	if(!coord) {
		coord = new google.maps.LatLng(37.567214,126.9379174);
	}
	
	var contentString = "<p>No Location Recorded Today</p>";
	
	watcher.info = new google.maps.InfoWindow({
	    content: contentString
	});

	watcher.currentLocation = new google.maps.Marker({
	    position: coord,
	    map: map,
	    title:"Nothing!"
	});

	watcher.map.setCenter(coord);
	watcher.map.setZoom(18);
	watcher.info.open(map,watcher.currentLocation);
}

/**
 * Methods for additional information
 */
function markCurrentLocation(map, d, coord) {
	if(!isToday(d)) {
		return;
	}
	
	var pic = watcher.children.pic;
	if(!pic) {
		pic = 'http://s3.amazonaws.com/37assets/svn/765-default-avatar.png';
	}
	var contentString = 
		"<div style='width:300px;height:450px;margin:0;'>" +
			"<img style='width:300px;height:400px;margin:0;' src='"+pic+"'/>"+
			"<span class='name'>"+watcher.children.name+"</span> is Here!<br />"+
			"<span class='address'></span>" +
		"</div>";
	watcher.info = new google.maps.InfoWindow({
	    content: contentString
	});
	
	watcher.currentLocation = new google.maps.Marker({
		map : map,
		animation: google.maps.Animation.DROP,
		position: coord
	});
	
	watcher.info.open(map, watcher.currentLocation);
}

function addressCurrentLocation(d, coord) {
	$("span.address").text("");
	if(!isToday(d)) {
		return;
	}
	
	if(!watcher.geocoder) {
		geocoder = new google.maps.Geocoder();
	}
	
	watcher.geocoder.geocode(
			{'latLng': coord}, 
			function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					if (results[0]) {
						$("span.address").text(results[0].formatted_address);
						
					}
				} else {
					watcher.log("Geocoder failed due to: " + status);
				}
	});
	
}

/**
 * Utilities
 */
function isToday(d) {
	d.setHours(0,0,0,0);
	var now = new Date();
	now.setHours(0,0,0,0);
	return d.getTime() == now.getTime();
}
function toLatLngArray(jsonArray) {
	var coords = [];
	for(var i=0; i< jsonArray.length; i++) {
		var point = new google.maps.LatLng(jsonArray[i].lat, jsonArray[i].lon);
		coords[i] = point;
	}
	return coords;
}

function fitMap(map, coords) {
	var bounds = new google.maps.LatLngBounds();
	for(var i=0; i<coords.length; i++) {
		bounds.extend(coords[i]);
	}
	map.panToBounds(bounds);
	map.fitBounds(bounds);
}

function cleanMap(map) {
	if(watcher && watcher.currentLocation) {
		// erase marker
		watcher.currentLocation.setMap(null);
		watcher.currentLocation = null;
	}
	
	if(watcher && watcher.info) {
		watcher.info.close();
		watcher.info = null;
	}
	
	if(watcher && watcher.path) {
		watcher.path.setMap(null);
		watcher.path = null;
	}
	
}
