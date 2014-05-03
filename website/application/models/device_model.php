<?php

class Device_model extends CI_Model{
	function __construct(){
		parent::__construct();
        /*
            info_table中info_type字段的对应关系：
                1：  通讯录 phone_contact表
                2:   位置信息 phone_location表
                3:   通话记录 phone_calling_record表
                4:   短信记录 phone_sms_record表
        */
        $tables = array(1 => 'phone_contact',2 => 'phone_location',3 => 'phone_calling_record',4 => 'phone_sms_record');
	}
    
    public function get_all_devices($num,$offset,$user_id){
        $sql = 'select * from device where user_id = ? LIMIT '. $num. ','.$offset;
        $query = $this->db->query($sql,array($user_id));
        if($query->num_rows() == 0){
            return array();
        }
        else{
            return $query->result_array();
        }
    }
    
    public function count_all_devices(){
        $sql = 'select count(*) from device';
        $query = $this->db->query($sql);
        $temp = $query->row_array();
        return $temp['count(*)'];
    }
    
    /*返回更新设备基本信息*/
    function reply_device_info($IMEI,$phoneNumber,$phoneType,$system){
        //$IMEI = base64_decode($IMEI);
        
        $sql = 'select count(*) from device where IMEI = ?';
        $query = $this->db->query($sql,array($IMEI));
        $count = $query->row_array();
        
        if($count['count(*)'] == 0){
            $sql = 'insert into device(IMEI) values(?)';
            $this->db->query($sql,array($IMEI));
            return 1;
        }
        else{
            return 1;   
        }
    }
 
    /*返回更新设备位置信息*/
    function reply_device_locations($IMEI,$latitude,$longitude,$username,$password){
        //$IMEI = base64_decode($IMEI);
        //$latitude = base64_decode($latitude);
        //$longitude = base64_decode($longitude);
        

        $password = md5($password);
        $sql = "SELECT id FROM admin_user WHERE username= ? AND password= ? ";
        $query = $this->db->query($sql, array($username, $password));
        if ( !$query->num_rows() ){
            return -1;
        }
        $user_id = $query->row()->id;

        
        $sql = 'select id from device where IMEI = ? and user_id = ?';
        $query = $this->db->query($sql,array($IMEI,$user_id));
                
        if($query->num_rows() == 0){
            $sql = 'insert into device(IMEI,user_id) values(?,?)';
            $this->db->query($sql,array($IMEI,$user_id));

            $sql = 'select id from device where IMEI = ? and user_id = ?';
            $query = $this->db->query($sql,array($IMEI,$user_id));
        }
        
        $temp = $query->row_array();
        $device_id = $temp['id'];

        $sql = 'insert into phone_location(latitude,longitude) values(?,?)';
        $query = $this->db->query($sql,array($latitude,$longitude));
        
        $sql = 'select id from phone_location ORDER BY id desc LIMIT 1';
        $query = $this->db->query($sql);
        $temp_result = $query->row_array();
        
        $sql = 'insert into info_table(device_id,info_id,info_type) values(?,?,?)';
        $query = $this->db->query($sql,array($device_id,$temp_result['id'],2));
        
        $sql = 'select * from info_table where device_id = ? and info_type = ?';
        $query = $this->db->query($sql,array($device_id,2));
        if($query->num_rows() > 10){
            $sql = 'select info_id from info_table where device_id = ? and info_type = ? ORDER BY info_id LIMIT 1';
            $query = $this->db->query($sql,array($device_id,2));
            $temp_result = $query->row_array();
            
            $sql = 'delete from phone_location where id = ?';
            $query = $this->db->query($sql,array($temp_result['info_id']));
            
            $sql = 'delete from info_table where device_id = ? and info_id = ? and info_type = ?';
            $query = $this->db->query($sql,array($device_id,$temp_result['info_id'],2));
            

            return 1;
        }
    }
 
    function get_device_locations($device_id){
        $sql = 'select info_id from info_table where device_id = ? and info_type = ? ORDER BY info_id DESC LIMIT 10';
        $query = $this->db->query($sql,array($device_id,2)); 
        if($query->num_rows() == 0){
            return array();
        }
        else{
            $locations = array();
            $temp_results = $query->result_array();
            foreach($temp_results as $temp_result){
                $sql = 'select latitude,longitude,updateAt from phone_location where id = ?';
                $query = $this->db->query($sql,array($temp_result['info_id'])); 
                $temp = $query->row_array();
                array_push($locations,$temp);
            }
            return $locations;
        }
    }
    
    //设备位置中心点
    function get_device_locations_center($locations){
        $latitude = 0.0;
        $longitude = 0.0;
        foreach($locations as $row){
            $latitude = $latitude + $row['latitude'];
            $longitude = $longitude + $row['longitude'];
        }
        if(count($locations) > 0){
            $latitude = $latitude / count($locations);
            $longitude = $longitude / count($locations);
        }
        return array('latitude'=>$latitude,'longitude'=>$longitude);
    }
   
    function delete($device_id){
        $tables = array(1 => 'phone_contact',2 => 'phone_location',3 => 'phone_calling_record',4 => 'phone_sms_record');
        
        $sql = 'SELECT info_id,info_type FROM `info_table` AS A WHERE (1 = (SELECT count(info_id) FROM info_table AS B WHERE A.info_id = B.info_id AND A.info_type = B.info_type)) AND device_id = ?';
        $query = $this->db->query($sql,array($device_id)); 
        $temp = $query->result_array();
        foreach($temp as $row){
            $sql = 'delete from `' . $tables[$row['info_type']] . '` where id = ?';
            $query = $this->db->query($sql,array($row['info_id']));  
            $sql = 'delete from `info_table` where device_id = ? and info_id = ? and info_type = ?';
            $query = $this->db->query($sql,array($device_id,$row['info_id'],$row['info_type']));  
        }
        $sql = 'select IMEI from device where id = ?';
        $query = $this->db->query($sql,array($device_id)); 
        
        $temp = $query->row_array();
        $IMEI = $temp['IMEI'];
        $sql = 'select result_id from command where command like ? and status <> 0';
        $query = $this->db->query($sql,array('%:'.$IMEI)); 
        $temp = $query->result_array();
        foreach($temp as $row){
            $sql = 'select result from result where id = ?';
            $query = $this->db->query($sql,array($row['result_id']));
            $file = $query->row_array();
            $myfile = './uploads/'.$file['result'];
            if (file_exists($myfile)){
                $result=unlink ($myfile);
            }
            $sql = 'delete from result where id = ?';
            $query = $this->db->query($sql,array($row['result_id'])); 
        }
        $sql = 'delete from command where command like ?';
        $query = $this->db->query($sql,array('%:'.$IMEI)); 
        
        $sql = 'delete from device where id = ?';
        $query = $this->db->query($sql,array($device_id)); 
        return 1;
    }
}
?>