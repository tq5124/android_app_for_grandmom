<!DOCTYPE html>
<html>
    <head>
        <script src="https://maps.googleapis.com/maps/api/js?sensor=true" type="text/javascript"></script>
        <title><?php echo $title; ?></title> 
    </head>
    <body>
        <div class="container">
            
        <div class="panel panel-default">            
            <div class="panel-heading">
                <h3 class="panel-title">位置信息</h3>
            </div>
            <div class="panel-body">
                
                <div id="map-canvas" style="width: 350px; height: 280px" align="center"></div>
                <script type="text/javascript"> 
                    markers = new Array();
                    center = new Array();
                    $(document).ready(function(){
                        if(document.URL.indexOf("index.php") > -1)
                            base_url = document.URL.slice(0,document.URL.indexOf("index.php"));
                        else
                            base_url = document.URL.slice(0,document.URL.indexOf("entry/"));            
                        $.ajax({
                            type: 'GET',
                            url: base_url + "/index.php/device/ajax_location/" + <?php echo $device_id?>,
                            async: true,
                            success: function(data){
                            /*
                                for(var i in data["locations"]){
                                    alert(data["locations"][i]);
                                }
                            */
                                markers = data["locations"];
                                center = data["center"];
                                initialize();
                            },
                            dataType: "json"
                        });//end of $.ajax
                    });
                    
                    var map;
                    function initialize() {
                        var myLatlng = new google.maps.LatLng(center["latitude"],center["longitude"]);
                        var myOptions = {
                          zoom: 5,
                          center: myLatlng,
                          mapTypeId: google.maps.MapTypeId.ROADMAP
                        }
                        var map = new google.maps.Map(document.getElementById("map-canvas"), myOptions);
                        
                        // 线条设置
                        var polyOptions = {
                          strokeColor: '#000000',    // 颜色
                          strokeOpacity: 1.0,    // 透明度
                          strokeWeight: 2    // 宽度
                        }
                        poly = new google.maps.Polyline(polyOptions);
                        poly.setMap(map);    // 装载
                        
                        for(var i in markers){
                            var path = poly.getPath();    //获取线条的坐标
                            path.push(new google.maps.LatLng(markers[i]["latitude"],markers[i]["longitude"]));    //为线条添加标记坐标
                            var marker = new google.maps.Marker({
                                position: new google.maps.LatLng(markers[i]["latitude"],markers[i]["longitude"]), 
                                map: map,
                                title:"时间：" + markers[i]["updateAt"]
                            });
                        }
                    }

                    //google.maps.event.addDomListener(window, 'load', initialize);
                </script>
                
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>纬度</th>
                            <th>经度</th>
                            <th>时间</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php foreach ($locations as $row ) {?>
                        <tr>
                            <td><?php echo $row['latitude']; ?></td>
                            <td><?php echo $row['longitude']; ?></td>
                            <td><?php echo $row['updateAt']; ?></td>
                        </tr>
                        <?php } ?>
                    </tbody>
                </table>
            </div>
            
        </div>        
       
           
        </div>
        
    </body>
</html>