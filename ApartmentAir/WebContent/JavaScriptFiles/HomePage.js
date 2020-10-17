
$(document).ready(function(){

	$.ajax({
		url: "rest/apartment/getAllApartments",
		type: "GET",
		contentType: "application/json",
		success: function(apartments){
			
			let i;
			$("#apartmentList").empty();
			
			for(i=0; i < apartments.length; i++){
				let ap= apartments[i];
				
				
			$("#apartmentList").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
      "<img src=\"https://www.crescentcourt.com/wp-content/uploads/2018/03/suitelife.jpg\" alt=\"Norway\" style=\"width:100%\">"+
      "<div class=\"w3-container w3-white\">"+
        "<h3>" +ap.type+ "</h3>" +
        "<h6 class=\"w3-opacity\">" +ap.pricePerNight+ "</h6>" +
        "<p>"+ ap.type+"</p>"+
        "<p>"+ap.numberOfRooms+"<sup>"+ap.numberOfGuests+"</sup></p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button class=\"w3-button w3-block w3-black w3-margin-bottom\">Choose Room</button>"+
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
	
}

 function showRegistrationForm(){
	
	 $("#registrationForm").show();
	 $("#loginForm").hide();
	 $("#searchForm").hide();
	 $("#listOfApartments").hide();
	 
	 
	
	
}

 function homePageClick(){
	 $("#listOfApartments").show();
	 
	 $("#searchForm").hide();
	 $("#loginForm").hide();
	 $("#registrationForm").hide();
	 $("#editUserForm").hide();
	 $("#editAmenityForm").hide();
	 $("#userProfileDiv").hide();
	 $("#newApartmentForm").hide();
	 $("#addAmenityForm").hide();
	 $("#allAmenitiesDiv").hide();
	 
	 
	 
 }
 
