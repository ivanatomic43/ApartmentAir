
function updatePriceInput(val) {
          document.getElementById('priceInput').value=val; 
        }

function updateRoomsInput(val) {
    document.getElementById('roomsInput').value=val; 
  }







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
 
