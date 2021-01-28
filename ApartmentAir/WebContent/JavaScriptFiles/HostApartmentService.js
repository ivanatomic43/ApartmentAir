function showInactiveApartments(){
	
	getAllInactiveApartments();
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#newApartmentForm").hide();
	$("#listOfApartmentsHost").show();
	$("#newApartmentBtn").show();
	$("#activeApartmentsView").hide();
	$("#apartmentDetails").hide();
	$("#listOfReservationsHost").hide();
	$("#allCommentsHost").hide();
	$("#listOfUsersHost").hide();
	
	
}

function showActiveApartments(){
	    getAllActiveApartmentsHost();
		$("#userProfileDiv").hide();
		$("#userProfileDiv2").hide();
		$("#editUserForm").hide();
		$("#newApartmentForm").hide();
		$("#listOfApartmentsHost").show();
		$("#newApartmentBtn").hide();
		$("#activeApartmentsView").show();
		$("#apartmentDetails").hide();
		$("#listOfReservationsHost").hide();
		$("#allCommentsHost").hide();
		$("#apartmentListHost").show();
		$("#listOfUsersHost").hide();
		
		
	
	
}

function getAllInactiveApartments(){
	$("#apartmentListHost").empty();
	
	$.ajax({
		url: "rest/apartment/getAllInactiveApartments",
		type: "GET",
		contentType: "application/json",
		success: function(apartmentListInactive){
			
			if(apartmentListInactive == null){
				console.log("There is no inactive apartments...");
				return;
			}
			
			let i;
			
			
			
			for(i=0; i < apartmentListInactive.length; i++){
				let ap= apartmentListInactive[i];
				
				
			$("#apartmentListHost").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
      "<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
      "<div class=\"w3-container w3-white\">"+
        "<h3>" +ap.type+ "</h3>" +
        "<h6 class=\"w3-opacity\">Price: " +ap.pricePerNight+ "$</h6>" +
        "<p>Status: "+ ap.status+"</p>"+
        "<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See detalis</button>"+
        "<button onclick=\"editApartmentClick('"+ap.id+","+ap.type+","+ap.pricePerNight+","+ap.numberOfGuests+","+ap.numberOfRooms+","+ap.amenities+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
        "<button onclick=\"deleteApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
        "<button onclick=\"makeActive('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Make active</button>"+
     " </div>" +
    "</div>"
			);	
				
				
			}
			
			
			
		}
	});
	
}




function makeActive(data){
	
	var id = data;
	alert(id);
	
	$.ajax({
		url: "rest/apartment/makeActive/" + id,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Facility is now active!");
			getAllInactiveApartments();
		}
		
		
	});
	
}

function deleteApartment(data){
	 var id = data;
	 
	 $.ajax({
		 url: "rest/apartment/deleteApartment/" + id,
		 type: "DELETE",
		 contentType: "application/json",
		 success: function(isReserved){
			 
			 
			 if(isReserved == true){
				 alert("You can't delete this apartment, it's reserved...");
				 return;
			 }
			 
			 alert("Apartment deleted!");
			 
			 var user = JSON.parse(localStorage.getItem('user'));
			 
			 if(user.role ==="ADMIN") {
				 getAllApartmentsAdmin();
				 return;
			 }
			 
			 getAllActiveApartmentsHost();
			 
		 },
		 statusCode: {
			 400: function(){
				 alert("You cannot delete apartment, its reserved");
			 }
		 },
		 error: function(error){
			 console.log("Error deleting apartment...");
			 
		 }
	 });
	
	
}

function getAllActiveApartmentsHost(){
	
	$.ajax({
		url: "rest/apartment/getAllActiveApartmentsHost",
		type: "GET",
		contentType: "application/json",
		success: function(apartments){
			
			if(apartments == null){
				console.log("There is no active apartments...");
			}
			
			let i;
			$("#apartmentListHost").empty();
			
			
			for(i=0; i < apartments.length; i++){
				let ap= apartments[i];
				
				
			$("#apartmentListHost").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
      "<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
      "<div class=\"w3-container w3-white\">"+
        "<h3>" +ap.type+ "</h3>" +
        "<h6 class=\"w3-opacity\">Price: " +ap.pricePerNight+ "$</h6>" +
        "<p>Status: "+ ap.status+"</p>"+
        "<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
        "<p>Rating: "+ap.rating+"</p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See detalis</button>"+
        "<button onclick=\"editApartmentClick('"+ap.id+","+ap.type+","+ap.pricePerNight+","+ap.numberOfGuests+","+ap.numberOfRooms+","+ap.amenities+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
        "<button onclick=\"deleteApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
     " </div>" +
    "</div>"
			);	
				
				
			}
			
			
			
		}
	});
	
	
}


