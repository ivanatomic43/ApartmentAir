
$(document).ready(()=> {
	

	
	$.ajax({
		url: "rest/auth/getLogged",
		type: "GET",
		contentType: "application/json",
		success: function(user) {
			
			if(user != null){
				adaptToUser(user);
				
			} else{
				alert("There is no logged user");
				}
			}
		});

});

function login(){
	
	let validation = true;
	
	if(!$("#usernameLog")[0].value){

		usernameLog.style.borderColor= "red";	
		validation=false;
	}else{
		usernameLog.style.borderColor= "white";
	}
	
	if(!$("#passwordLog")[0].value){

		passwordLog.style.borderColor= "red";	
		validation=false;
	}else{
		passwordLog.style.borderColor= "white";
	}
	
	
	if(validation){
		

		
		
		let data = {
					"username": $("input[name=usernameLog]").val(),
					"password": $("input[name=passwordLog]").val()
		};
		
		let u = JSON.stringify(data);
		
		
		$.ajax({
			url: "rest/auth/login",
			type: "POST",
			data: u,
			contentType: 'application/json',
			dataType: "json",
			success: function(user){
				if(user != null){
					alert("Login successfull!")
						usernameLog.value="";
						passwordLog.value="";	
					adaptToUser(user);
					
				}else{
					alert("Wrong username or password!");
				}
				
			}
		});
		event.preventDefault();
		
	
	} else {
		alert("The fields are empty!")
	}
	
	
	
	
	
};

function adaptToUser(u){
	$("#loginBtn").hide();
	$('#regBtn').hide();
	$("#headerForm").hide();
	$("#loginForm").hide();
	$("#logoutBtn").show();
	
	
	let uloga=u.role
	
	
	if(u.role=="Guest"){
		$("#profileBtn").show();
		$("#usersAdminBtn").hide();
		$("#usersHostBtn").hide();
		$("#apartmentsAdminBtn").hide();
		$("#activeApartmentsBtn").hide();
	} 
	
	if(u.role==="Host"){
		$("#profileBtn").show();
		$("#usersAdminBtn").hide();
		$("#usersHostBtn").show();
		$("#apartmentsAdminBtn").hide();
		$("#activeApartmentsBtn").show();
	}
	
	if(uloga==="Admin"){
		$("#profileBtn").show();
		$("#usersAdminBtn").show();
		$("#usersHostBtn").hide();
		$("#apartmentsAdminBtn").show();
		$("#activeApartmentsBtn").hide();
		
	}
		

	 
}

function logout(){
	
	$.ajax({
		url:"rest/auth/logout",
		type:"POST",
		contentType:'application/json',
		success: function(){
			//window.location.replace("index.html");
			$("#loginForm").show();
			$("#profileBtn").hide();
			$("#logoutBtn").hide();
			$("#userProfileDiv").hide();
			//adaptToUser(user);
			
		},
		error : function(){
			alert("Neuspesna odjava!")
		}
	});
	
}
