<%
// Login 여부 확인
%>

<jsp:include page="header.jsp" flush="true" ></jsp:include>
<!-- BODY START -->
<script type="text/javascript" src="http://apis.daum.net/maps/maps3.js?apikey=0a0370638f1e498d51945e5bec378ac7c029a"></script>
<link href="style/controlCenter.css" rel="stylesheet" type="text/css" />

<div class="container">
<div class="row">
<span class="label label-default date label" ></span> <input type="text" class="date hidden" /><button type="button" class="date btn btn-default btn-sm"><span class="glyphicon glyphicon-calendar"></span></button>
</div> <!-- row -->
<script>
  $(function() {
    $( "input.date" ).datepicker({
      showOn: "button",
      buttonImage: "",
      buttonImageOnly: true
    });
    $("input.date").datepicker('setDate', new Date());
    var d = $("input.date").datepicker('getDate');
  	$('span.date').text( d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate() );
  });
  $("button.date").on('click', function(e) {
  	$(".ui-datepicker-trigger").trigger('click');
  	return false;
  });
  $("input.date").on('change', function(e) {
  	// var t = $("input.date").val();
  	// $('span.date').text( t.replace(/(\d{2})\/(\d{2})\/(\d{4})/,'$3-$2-$1') );
  	var d = $("input.date").datepicker('getDate');
  	$('span.date').text( d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate() );
  });
</script>
  
<div class="row" id="map-container" >
<div id="map"></div>
</div> <!-- map container -->

</div> <!-- container -->
<script>
var map;	// http://dna.daum.net/apis/maps
function init() {
	map = new daum.maps.Map(document.getElementById('map'), {
		center: new daum.maps.LatLng(37.537123, 127.005523),
		level: 3	// optional, zoom power
	});		
	
	// make native marker
	var marker = new daum.maps.Marker({
		position: new daum.maps.LatLng(37.537123, 127.005523)
	});
	marker.setMap(map);
	
	// make infoWindow
	var infowindow = new daum.maps.InfoWindow({
		content: '<p style="margin:7px 22px 7px 12px;font:12px/1.5 sans-serif"><strong>안녕하세요~</strong><br/>다음커뮤니케이션입니다.</p>',
		removable : true
	});
	
	daum.maps.event.addListener(marker, "click", function() {
		infowindow.open(map, marker);
	});

	var infowindow_only = new daum.maps.InfoWindow({
		position: new daum.maps.LatLng(37.5367434970359, 127.00491278024688),
		content: '<p style="margin:7px 12px;font-size:12px">인포윈도우만 띄울 수도 있습니다.</p>'
	});
	infowindow_only.open(map);
	
	// event
	daum.maps.event.addListener(map,"dragend",function(){
		var center = map.getCenter();
		document.getElementById("message").innerHTML = "latitude : " + center.getLat() + "<br />longitude: " + center.getLng();
	});
	
	//라인 그리기
	var line = new daum.maps.Polyline();
	line.setPath([new daum.maps.LatLng(37.48779895934866, 127.03130020103005), new daum.maps.LatLng(37.48979895934866, 127.04130020103005)]);
	line.setMap(map);

	//원 그리기
	var circle = new daum.maps.Circle({
		center : new daum.maps.LatLng(37.48579895934866, 127.02530020103005),
		radius : 400,
		strokeWeight : 4,
		strokeColor : "#00ff00"
	});
	circle.setMap(map);
	
	//폴리곤 그리기
	var arr = [];
	arr.push(new daum.maps.LatLng(37.48879895934866, 127.03250020103005));
	arr.push(new daum.maps.LatLng(37.48979895934866, 127.03450020103005));
	arr.push(new daum.maps.LatLng(37.48279895934866, 127.03350020103005));
	arr.push(new daum.maps.LatLng(37.48879895934866, 127.03130020103005));
	var polygon = new daum.maps.Polygon({
		strokeWeight : 3,
		strokeColor : "#1833e5",
		strokeOpacity : 0.6,
		fillColor : "#1833e5",
		fillOpacity : 0.2
	});
	polygon.setPath(arr);
	polygon.setMap(map);
	
	// 지도 범위 
	// 좌표값들 지정
	var points = new Array();
	points[0] = new daum.maps.LatLng(37.529196714213114, 126.92506196011036);
	points[1] = new daum.maps.LatLng(37.529197714213114, 127.17546196011036);
	points[2] = new daum.maps.LatLng(37.529217714213114, 126.92526196011036);
	points[3] = new daum.maps.LatLng(37.529257714213114, 126.92520196011036);
	points[4] = new daum.maps.LatLng(37.530257714213114, 126.92530196011036);
	// 빈 LatLngBounds 객체 생성
	var bounds = new daum.maps.LatLngBounds();
	for(var i = 0; i < points.length; i++)
	{
		// 해당 좌표에 marker 올리기
		new daum.maps.Marker({position:points[i]}).setMap(map);
		// LatLngBounds 객체에 해당 좌표들을 포함
		bounds.extend(points[i]);
	}
	// 좌표가 채워진 LatLngBounds 객체를 이용하여 지도 영역을 확장
	map.setBounds(bounds);
}

function setCenter() {
	map.setCenter(new daum.maps.LatLng(37.53723910162246, 127.003362714821));
}

function panTo() {
	map.panTo(new daum.maps.LatLng(37.53730198471141, 127.00744728571883));
}
</script>



<!-- BODY END -->
<jsp:include page="footer.jsp" ></jsp:include>