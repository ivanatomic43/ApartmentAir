
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
						localStorage.setItem("user", JSON.stringify(user));
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

function registration(){
	
	let name = $("#nameReg")[0].value;
	let surname = $("#surnameReg")[0].value;
	let username = $("#usernameReg")[0].value;
	let password = $("#passwordReg")[0].value;
	let confirmpassword = $("#ConPasswordReg")[0].value;
	
	let gender = $("#genderReg").val();
	
	

	
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
	    
	    if(confirmpassword == password)
	    {
	    	ConPasswordReg.style.borderColor= "white";

	    }else{
	    	validation=false;
	    	ConfirmPasswordReg.style.borderColor= "red";
	    	alert("Passwords did  not match");
	    }
	
	    
	    if(gender)
	    {
	    	genderReg.style.borderColor= "white";

	    }else{
	    	validation=false;
	    	genderReg.style.borderColor= "red";
	    }
	
	    event.preventDefault();
	    

	
	
	let data =  {
			"username": $("input[name=usernameReg]").val(),
			"password": $("input[name=passwordReg]").val(),
			"name": $("input[name=nameReg]").val(),
			"surname": $("input[name=surnameReg]").val(),
			"gender": gender
};
	
	u= JSON.stringify(data);
	
	
		if(validation){
		
			$.ajax({
				url:"rest/auth/signUp",
				type: "POST",
				data:u,
				contentType: "application/json",
				success: function(user){
					alert("Registration successful! Welcome to ApartmentAir!");
					//afterRegFunction();
					localStorage.setItem("user", JSON.stringify(user));
					adaptToUser(user);
					
					
				},
				
				statusCode: {
					400: function() {
						alert("Username exist. Try another username.");
						//$('#error').text("Greska pri unosu, korisnicko ime vec postoji!");
						
					}
				}
				
				
			});
		} else{
			alert("Fields are empty!");
		}
		
}

function afterRegFunction(){
	$("#loginBtn").hide();
	$('#regBtn').hide();
	$("#headerForm").hide();
	$("#loginForm").hide();
	$("#registrationForm").hide();
	$("#logoutBtn").show();
	$("#profileBtn").show();
	$("#usersAdminBtn").hide();
	$("#usersHostBtn").hide();
	$("#apartmentsAdminBtn").hide();
	$("#nonactiveApartmentsBtn").hide();
	$("#listOfApartments").show();
	$("#amenitiesAdminBtn").hide();
	$("#commentsAdminBtn").hide();
	$("#commentsHostBtn").hide();
	$("#reservationsHostBtn").hide();
	$("#reservationsAdminBtn").hide();
	$("#reservationsGuestBtn").show();
	
	
}

function adaptToUser(u){
	$("#loginBtn").hide();
	$('#regBtn').hide();
	$("#headerForm").hide();
	$("#loginForm").hide();
	$("#logoutBtn").show();
	$("#registrationForm").hide();
	
	
	//let uloga=parseString(u.role);
	//alert(uloga);
	
	if(u.role=="GUEST"){
		$("#profileBtn").show();
		$("#usersAdminBtn").hide();
		$("#usersHostBtn").hide();
		$("#apartmentsAdminBtn").hide();
		$("#inactiveApartmentsBtn").hide();
		$("#listOfApartments").show();
		$("#hostPart").show();
		$("#amenitiesAdminBtn").hide();
		$("#listOfApartmentsAdmin").hide();
		$("#listOfApartmentsHost").hide();
		$("#commentsAdminBtn").hide();
		$("#commentsHostBtn").hide();
		$("#reservationsHostBtn").hide();
		$("#reservationsAdminBtn").hide();
		$("#reservationsGuestBtn").show();
		$("#sortAndFilter").show();
		
	} 
	
	if(u.role==="HOST"){
		$("#profileBtn").show();
		$("#usersAdminBtn").hide();
		$("#usersHostBtn").show();
		$("#apartmentsAdminBtn").hide();
		$("#inactiveApartmentsBtn").show();
		$("#listOfApartments").hide();
		$("#hostPart").hide();
		$("#newApartmentBtn").show();
		$("#amenitiesAdminBtn").hide();
		$("#listOfApartmentsAdmin").hide();
		$("#activeApartmentsBtn").show();
		//$("#listOfApartmentsHost").show();
		$("#commentsAdminBtn").hide();
		$("#commentsHostBtn").show();
		$("#reservationsHostBtn").show();
		$("#reservationsAdminBtn").hide();
		$("#reservationsGuestBtn").hide();
	}
	
	if(u.role==="ADMIN"){
		$("#profileBtn").show();
		$("#usersAdminBtn").show();
		$("#usersHostBtn").hide();
		$("#apartmentsAdminBtn").show();
		$("#inactiveApartmentsBtn").hide();
		$("#listOfApartments").hide();
		$("#hostPart").hide();
		$("#listOfApartmentsAdmin").show();
		$("#amenitiesAdminBtn").show();
		$("#listOfApartmentsHost").hide();
		getAllApartmentsAdmin();
		$("#commentsAdminBtn").show();
		$("#commentsHostBtn").hide();
		$("#reservationsHostBtn").hide();
		$("#reservationsAdminBtn").show();
		$("#reservationsGuestBtn").hide();
		
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
			$("#usersHostBtn").hide();
			$("#inactiveApartmentsBtn").hide();
			
			$("#userProfileDiv").hide();
			$("#usersAdminBtn").hide();
			$("#apartmentsAdminBtn").hide();
			$("#listOfApartments").hide();
			$("#newApartmentForm").hide();
			$("#amenitiesAdminBtn").hide();
			$("#addAmenityForm").hide();
			$("#allAmenitiesDiv").hide();
			$("#allAmenitiesTable").hide();
			$("#editUserForm").hide();
			$("#allUsersDiv").hide();
			$("#allUsersTable").hide();
			$("#listOfApartmentsAdmin").hide();
			$("#listOfApartmentsHost").hide();
			$("#activeApartmentsBtn").hide();
			$("#apartmentDetails").hide();
			$("#commentsAdminBtn").hide();
			$("#commentsHostBtn").hide();
			$("#reservationsHostBtn").hide();
			$("#reservationsAdminBtn").hide();
			$("#reservationsGuestBtn").hide();
			$("#listOfUsersAdmin").hide();
			$("#listOfReservationsGuest").hide();
			$("#listOfReservationsAdmin").hide();
			$("#listOfReservationsHost").hide();
			$("#allCommentsHost").hide();
			$("#newCommentForm").hide();
			$("#allCommentsAdmin").hide();
			
			//adaptToUser(user);
			
		},
		error : function(){
			alert("Neuspesna odjava!")
		}
	});
	
}
