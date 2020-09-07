
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
		alert("Uspesna validacija")

		
		
		let data = {
					"username": $("input[name=usernameLog]").val(),
					"password": $("input[name=passwordLog]").val()
		};
		
		let u = JSON.stringify(data);
		alert("User" + u );
		
		$.ajax({
			url: "rest/auth/login",
			type: "POST",
			data: u,
			contentType: 'application/json',
			dataType: "json",
			success: function(user){
				if(user != null){
					alert("Login successfull!")
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
	alert("Usao u adapt");
	$("#loginBtn").hide();
	$("#regBtn").hide();
	$("#logoutBtn").show();
	
}

function logout(){
	
	$.ajax({
		url:"rest/auth/logout",
		type:"POST",
		contentType:'application/json',
		success: function(){
			window.location.replace("index.html");
		},
		error : function(){
			alert("Neuspesna odjava!")
		}
	});
	
}
