function showInactiveApartments(){
	
	getAllInactiveApartments();
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#newApartmentForm").hide();
	$("#listOfApartmentsHost").show();
	$("#newApartmentBtn").show();
	$("#activeApartmentsView").hide();
	
	
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
		
	
	
}

function getAllInactiveApartments(){
	
	
	$.ajax({
		url: "rest/apartment/getAllInactiveApartments",
		type: "GET",
		contentType: "application/json",
		success: function(apartmentList){
			
			let i;
			$("#apartmentListHost").empty();
			
			
			for(i=0; i < apartmentList.length; i++){
				let ap= apartmentList[i];
				
				
			$("#apartmentListHost").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
      "<img src=\"https://www.crescentcourt.com/wp-content/uploads/2018/03/suitelife.jpg\" alt=\"Norway\" style=\"width:100%\">"+
      "<div class=\"w3-container w3-white\">"+
        "<h3>" +ap.type+ "</h3>" +
        "<h6 class=\"w3-opacity\">" +ap.pricePerNight+ "</h6>" +
        "<p>"+ ap.status+"</p>"+
        "<p>"+ap.numberOfRooms+"<sup>"+ap.numberOfGuests+"</sup></p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button class=\"w3-button w3-block w3-black w3-margin-bottom\">See detalis</button>"+
        "<button class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
        "<button class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
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

function getAllActiveApartmentsHost(){
	
	$.ajax({
		url: "rest/apartment/getAllActiveApartmentsHost",
		type: "GET",
		contentType: "application/json",
		success: function(apartments){
			
			let i;
			$("#apartmentListHost").empty();
			
			
			for(i=0; i < apartments.length; i++){
				let ap= apartments[i];
				
				
			$("#apartmentListHost").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
      "<img src=\"https://www.crescentcourt.com/wp-content/uploads/2018/03/suitelife.jpg\" alt=\"Norway\" style=\"width:100%\">"+
      "<div class=\"w3-container w3-white\">"+
        "<h3>" +ap.type+ "</h3>" +
        "<h6 class=\"w3-opacity\">" +ap.pricePerNight+ "</h6>" +
        "<p>"+ ap.status+"</p>"+
        "<p>"+ap.numberOfRooms+"<sup>"+ap.numberOfGuests+"</sup></p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button class=\"w3-button w3-block w3-black w3-margin-bottom\">See detalis</button>"+
        "<button class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
        "<button class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
     " </div>" +
    "</div>"
			);	
				
				
			}
			
			
			
		}
	});
	
	
}
