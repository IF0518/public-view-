<?php

$server="localhost";

$username="root";

$password="";

$db="Android";


$con = mysqli_connect($server, $username, $password, $db);


if(!$con){
	
die("Connection failed" . mysqli_connect_error());

}


$db=mysqli_select_db($con,'Android');

if(!$db){
	
die("database selection failed." . mysqli_error($con));

}

$response=array();
if(isset($_GET['apicall'])){
	
switch($_GET['apicall']){
		
case 'signup':
		
if(parametersAvailable(array('username', 'password', 'firstname', 'lastname', 'phone', 'email', 'gender'))){
		
$uname = $_POST['username'];
			
$passwd= md5($_POST['password']);
			
$firstname = $_POST['firstname'];

$lastname = $_POST['lastname'];

$phone = $_POST['phone'];
			
$email    = $_POST['email'];
			
$gender   = $_POST['gender'];
			
			
$stmt = $con->prepare("SELECT username, email FROM users WHERE username = ? OR email = ?");
			
$stmt->bind_param("is",$uname,$email);			
$stmt->execute();
			
$stmt->store_result();
			
if($stmt->num_rows()>0){ 
				
$response['error'] = true;
				
$response['message']='User already registered';				
				
$stmt->close();
			
}
			
			
else{
				
$stmt = $con->prepare("INSERT INTO users (username, password, firstname, lastname, phone, email, gender)VALUES(?,?,?,?,?,?,?)");
				
$stmt->bind_param("issssss",$uname,$passwd,$firstname,$lastname,$phone,$email,$gender);				
				
if($stmt->execute()){
					
$stmt = $con->prepare("SELECT username, password, firstname,lastname,phone, email, gender FROM users WHERE username=?");
					
$stmt->bind_param("i",$username);					
$stmt->execute();
					
$stmt->bind_result($user,$pass,$firstname,$lastname,$phone,$email,$gender);
					
$stmt->fetch();
					
					
$user=array('username' => $user,
'password' => $pass,
'firstname' => $firstname,
'lastname'=>$lastname,
'phone'=>$phone,
'email' =>$email,
'gender'=>$gender
);
$stmt->close();
		
$response['error'] = false;					
$response['message'] ='User registered successfully';					
$response['user'] = $user;
					
				
				
} 
				
}
				
}else{
					
$response['error'] = true;
					
$response['message']='user not registered,try again';
				
} 
					
			
break;		
case 'login':
			
if(parametersAvailable(array('username','password'))){
				
$uname = $_POST['username'];
				
$passwd = md5($_POST['password']);
				
				
$stmt = $con->prepare("SELECT username,password,firstname,lastname,phone,email,gender FROM users WHERE username=? AND password = ?");
				
$stmt->bind_param("is",$uname,$passwd);
				
$stmt->execute();
				
$stmt->store_result();
			 
if($stmt->num_rows()>0){
				 
$stmt->bind_result($user,$pass,$firstname,$lastname,$phone,$email,$gender);
				 
$stmt->fetch();
				 
				 
$user = array('username'=>$user,
'password'=>$pass,
'firstname'=>$firstname,
'lastname'=>$lastname,
'phone'=>$phone,'email'=>$email,
'gender'=>$gender);

$stmt->close();				 
				
$response['error']=false;				 
$response['message']='User login successifully';				 
$response['user']=$user;
						 
} else{
				 
$response['error'] = true;				
$response['message']='Wrong username or password';
			 
}

		
}		 
break;
		 
		 
default:
		
 $response['error'] = true;
		
 $response['message'] = 'Invalid parameters passed';
		 
	
 }
 
}else{
	 
$response['error'] = true;
	
$response['message']='Invalid url call';

}
echo json_encode($response);
function parametersAvailable($parameters){	 
foreach($parameters as $param){		 
if(!isset($_POST[$param])){ 			 
return false;		 
}	 
}	 
return true;
} 
?>
