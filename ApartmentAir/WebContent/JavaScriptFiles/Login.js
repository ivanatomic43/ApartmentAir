$(document).ready(function() {
	$.ajax({
		url: "rest/auth/testlogin",
		type:"GET",
		success: function(data) {
			if(data==null) {
				alert("Niko nije ulogovan!!")
			} else {
					alert("Korisnik je ulogovan!");
			
				}
			
		},
		error: function(data) {
			//alert("Neuspesno!");
		}
	})
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
					alert("USAO, user nije null");
					
				}else{
					alert("Pogrešno korisničko ime ili lozinka");
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
	$("$regBtn").hide();
	
}

function pronasao(){
	
	$.ajax({
		 url: "rest/auth/testAjax",
		 type: "POST",
		 succes: function(){
			 
			
				alert("Login successful!")
			
			//adaptToUser(user);
				$("#headerFrom").hide();
				
			 
			 
		 },
		 error: function(){alert("neuspesno");}
		
		
		
	})
	
}
