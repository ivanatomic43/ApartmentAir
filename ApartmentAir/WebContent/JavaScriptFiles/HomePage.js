
$(document).ready(function(){

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
	 $("#listOfApartments").show();
	 $("#hostPart").show();
	 $("#searchForm").hide();
	 $("#loginForm").hide();
	 $("#registrationForm").hide();
	 $("#editUserForm").hide();
	 $("#editAmenityForm").hide();
	 $("#userProfileDiv").hide();
	 $("#newApartmentForm").hide();
	 $("#addAmenityForm").hide();
	 $("#allAmenitiesDiv").hide();
	 $("#apartmentDetails").hide();
	 $("#listOfReservationsHost").hide();
	 $("#listOfReservationsGuest").hide();
	 $("#listOfReservationsAdmin").hide();
	 
	 
 }
 
 var slideIndex = 1;
 showDivs(slideIndex);

 function plusDivs(n) {
   showDivs(slideIndex += n);
 }

 function currentDiv(n) {
   showDivs(slideIndex = n);
 }

 function showDivs(n) {
   var i;
   var x = document.getElementsByClassName("mySlides");
   var dots = document.getElementsByClassName("demo");
   if (n > x.length) {slideIndex = 1}
   if (n < 1) {slideIndex = x.length}
   for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";
   }
   for (i = 0; i < dots.length; i++) {
     dots[i].className = dots[i].className.replace(" w3-opacity-off", "");
   }
   x[slideIndex-1].style.display = "block";
   dots[slideIndex-1].className += " w3-opacity-off";
 }
