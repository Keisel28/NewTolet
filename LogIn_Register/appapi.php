<?php 
 require_once 'dbconnect.php';
 
 $response = array();
 
if(isset($_GET['apicall'])) 
{
    
    switch($_GET['apicall']) 
	{
 
        case 'register':
 
        //checking the parameters required are available or not 
		if(isTheseParametersAvailable(array('username','email','password','usertype')))
		{
 
		//getting the values 
			$username = $_POST['username']; 
			$email = $_POST['email']; 
			$password = md5($_POST['password']);
			$usertype = $_POST['usertype']; 
 
			$stmt = $conn->prepare("SELECT id FROM usertbl WHERE username = ? OR email = ?");
			$stmt->bind_param("ss", $username, $email);
			$stmt->execute();
			$stmt->store_result();
  
			if($stmt->num_rows > 0)
			{
				$response['error'] = true;
				$response['message'] = 'User already registered';
				$stmt->close();
			}
			else
			{
				$stmt = $conn->prepare("INSERT INTO usertbl (username, email, password, usertype) VALUES (?, ?, ?, ?)");
				$stmt->bind_param("ssss", $username, $email, $password, $usertype);
 
				if($stmt->execute())
				{
					$stmt = $conn->prepare("SELECT id, id, username, email, usertype FROM usertbl WHERE username = ?"); 
					$stmt->bind_param("s",$username);
					$stmt->execute();
					$stmt->bind_result($userid, $id, $username, $email, $usertype);
					$stmt->fetch();
					
					$user = array(
						'id'=>$id, 
						'username'=>$username, 
						'email'=>$email,
						'usertype'=>$usertype
					);
 
					$stmt->close();
 
					$response['error'] = false; 
					$response['message'] = 'User registered successfully'; 
					$response['user'] = $user; 
				}
			}
		}
		else
		{
			$response['error'] = true; 
			$response['message'] = 'required parameters are not available'; 
		}
 
        break; 
 
        case 'login':
 
        //this part will handle the login 
 
        break; 
 
        default: 
        $response['error'] = true; 
        $response['message'] = 'Invalid Operation Called';
    }
 
}else{
    $response['error'] = true; 
    $response['message'] = 'Invalid API Call';
}
 
 echo json_encode($response);
 
 function isTheseParametersAvailable($params){
 
	foreach($params as $param){

		if(!isset($_POST[$param])){
 
			return false; 
		}
	}
 
 return true; 
 }
 ?>