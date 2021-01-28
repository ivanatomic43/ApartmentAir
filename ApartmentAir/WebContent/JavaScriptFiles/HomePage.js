
$(document).ready(function(){

	var user = JSON.parse(localStorage.getItem('user'));
	 //alert(user.role);
	
	
	 $("#listOfApartments").show();
	 $("#newCommentForm").hide();
	$.ajax({
		url: "rest/apartment/getAllActiveApartments",
		type: "GET",
		contentType: "application/json",
		success: function(apartments){
			
			let i;
			$("#apartmentList").empty();
			
			if(apartments== null){
				console.log("There is no active apartments!");
				return;
			}
			
			for(i=0; i < apartments.length; i++){
				let ap= apartments[i];
				
				
			$("#apartmentList").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
      "<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
      "<div class=\"w3-container w3-white\">"+
        "<h3>" +ap.type+ "</h3>" +
        "<h6 class=\"w3-opacity\">Price:" +ap.pricePerNight+ "$</h6>" +
        "<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
        "<p>Rating: "+ap.numberOfRooms+"</p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See Details</button>"+
     " </div>" +
    "</div>"
			);	
				
				
			}
			
			
		}
		
	});
	
	 
	
});





function showLoginForm(){

	$("#loginForm").show();
	$("#registrationForm").hide();
	$("#searchForm").hide();
	$("#listOfApartments").hide();
	$("#apartmentDetails").hide();
	
}

 function showRegistrationForm(){
	
	 $("#registrationForm").show();
	 $("#loginForm").hide();
	 $("#searchForm").hide();
	 $("#listOfApartments").hide();
	 $("#apartmentDetails").hide();
	 
	
	
}

 function homePageClick(){
	 
	 var user = JSON.parse(localStorage.getItem('user'));
	 
	 if(user === null) {
		 $("#listOfApartments").show();
		 
		 $("#loginBtn").show();
		 $("#regBtn").show();
		 $("#loginForm").hide();
		 $("#registrationForm").hide();
		 
		 $.ajax({
				url: "rest/apartment/getAllActiveApartments",
				type: "GET",
				contentType: "application/json",
				success: function(apartments){
					
					let i;
					$("#apartmentList").empty();
					
					if(apartments== null){
						console.log("There is no active apartments!");
						return;
					}
					
					for(i=0; i < apartments.length; i++){
						let ap= apartments[i];
						
						
					$("#apartmentList").append(
							
					"<div class=\"w3-third w3-margin-bottom\">" +
		      "<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
		      "<div class=\"w3-container w3-white\">"+
		        "<h3>" +ap.type+ "</h3>" +
		        "<h6 class=\"w3-opacity\">Price:" +ap.pricePerNight+ "$</h6>" +
		        "<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
		        "<p>Rating: "+ap.numberOfRooms+"</p>"+
		        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
		        "<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See Details</button>"+
		     " </div>" +
		    "</div>"
					);	
						
						
					}
					
					
				}
				
			});
		 
		 
	 } else if( user.role ==="GUEST") {
		 
		 $("#listOfApartments").show();
		 $("#editUserForm").hide();
		 $("#userProfileDiv").hide();
		 $("#userProfileDiv2").hide();
		 $("#apartmentDetails").hide();
		 $("#listOfReservationsGuest").hide();
		 $("#newCommentForm").hide();
		 
		 
		 
		 $.ajax({
				url: "rest/apartment/getAllActiveApartments",
				type: "GET",
				contentType: "application/json",
				success: function(apartments){
					
					let i;
					$("#apartmentList").empty();
					
					if(apartments== null){
						console.log("There is no active apartments!");
						return;
					}
					
					for(i=0; i < apartments.length; i++){
						let ap= apartments[i];
						
						
					$("#apartmentList").append(
							
					"<div class=\"w3-third w3-margin-bottom\">" +
		      "<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
		      "<div class=\"w3-container w3-white\">"+
		        "<h3>" +ap.type+ "</h3>" +
		        "<h6 class=\"w3-opacity\">Price:" +ap.pricePerNight+ "$</h6>" +
		        "<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
		        "<p>Rating: "+ap.rating+"</p>"+
		        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
		        "<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See Details</button>"+
		     " </div>" +
		    "</div>"
					);	
						
						
					}
					
					
				}
				
			});
		 
	 } else if (user.role === "ADMIN"){
		 
		 $("#editUserForm").hide();
		 $("#editAmenityForm").hide();
		 $("#userProfileDiv").hide();
		 $("#addAmenityForm").hide();
		 $("#allAmenitiesDiv").hide();
		 $("#apartmentDetails").hide();
		 $("#listOfReservationsAdmin").hide();
		 $("#allCommentsAdmin").hide();
		 $("#listOfUsersAdmin").hide();
		 $("#listOfApartmentsAdmin").show();
		 $("#createHostForm").hide();
		 getAllApartmentsAdmin();
	 
	 } else if(user.role ==="HOST"){
		 
		
			$("#userProfileDiv").hide();
			$("#newApartmentForm").hide();
			
			
			$("#editUserForm").hide();
			
			$("#listOfApartmentsHost").hide();
		
			$("#apartmentDetails").hide();
			$("#listOfUsersHost").hide();
		
		
			$("#listOfReservationsHost").hide();
			$("#allCommentsHost").hide();
		
		 
		 
	 }
	 
	
	
	 
	 
 }
 

