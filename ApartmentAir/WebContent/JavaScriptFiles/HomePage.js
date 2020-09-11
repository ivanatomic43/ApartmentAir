
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
	
}

 function showRegistrationForm(){
	
	 $("#registrationForm").show();
	 $("#loginForm").hide();
	 $("#searchForm").hide();
	 
	 
	
	
}

 function homePageClick(){
	 $("#searchForm").show();
	 $("#loginForm").hide();
	 $("#registrationForm").hide();
	 
 }
