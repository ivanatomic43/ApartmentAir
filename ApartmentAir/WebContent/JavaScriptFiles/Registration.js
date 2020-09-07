/*$(document).ready(function() {
	 let inputElem = $("input[name=ime]");
	  
	 let forma = $("form");
	 forma.submit(function(event){
		 alert("Submit dogadjaj!")
		 
		 let data = {
			 
			"name":  $("input[name=nameReg]").val(),
			"surname": $("input[name=lastnameReg]").val(),
			 "username" : $("input[name=usernameReg]"),
			 "password" : $("input[name=passwordReg]"),
			 "gender" :  $("input[name=genderReg]")
			 
		 };
		 
		 
		 
		 
		 
		 evenet.preventDefault();
	 });
	 
	
	
	
	
});

*/

function registration(){
	
	let name = $("#nameReg")[0].value;
	let surname = $("#surnameReg")[0].value;
	let username = $("#usernameReg")[0].value;
	let password = $("#passwordReg")[0].value;
	let confirmpassword = $("#ConPasswordReg")[0].value;
	let gender = $("#genderReg")[0].value;
	
	
	let validation = true;
	
	let letters = /^[A-Za-z]+$/;
	
	if(name.match(letters)){
		nameReg.style.borderColor= "white";
	}else{
		validation=false;
	    nameReg.style.borderColor= "red";
	}
	
	if(surname.match(letters)){
		surnameReg.style.borderColor= "white";
	}else{
		validation=false;
	    surnameReg.style.borderColor= "red";
	}
	
	  if(password)
	    {
	    	passwordReg.style.borderColor= "white";

	    }else{
	    	validation=false;
	    	passwordReg.style.borderColor= "red";
	    }
	    
	    if(confirmpassword.length == password.length)
	    {
	    	ConPasswordReg.style.borderColor= "white";

	    }else{
	    	validation=false;
	    	ConfirmPasswordReg.style.borderColor= "red";
	    }
	
	    
	    if(gender)
	    {
	    	genderReg.style.borderColor= "white";

	    }else{
	    	validation=false;
	    	genderReg.style.borderColor= "red";
	    }
	
	    event.preventDefault();
	    
	alert("STIGAO DO VALIDATION");
	alert("Validation: " + validation);
	
	
	let data =  {
			"username": $("input[name=usernameReg]").val(),
			"password": $("input[name=passwordReg]").val(),
			"name": $("input[name=nameReg]").val(),
			"surname": $("input[name=surnameReg]").val(),
			"gender": $("input[name=genderReg]").val()
};
	
	u= JSON.stringify(data);
	alert("USER ZA REG: " + u);
	
		if(validation){
			
			alert("USAO u if");
			alert("user:" + u );
			$.ajax({
				url:"rest/auth/signUp",
				type: "POST",
				data:u,
				contentType: "application/json",
				success: function(){
					alert("Registration successful! You can now log in!");
					
					
				},
				
				statusCode: {
					400: function() {
						alert("Username exist. Try another username.");
						//$('#error').text("Greska pri unosu, korisnicko ime vec postoji!");
						//$("#error").show().delay(5000).fadeOut();
					}
				}
				
				
			})
		}
}