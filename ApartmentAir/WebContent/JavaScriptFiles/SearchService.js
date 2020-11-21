function searchUsersAdmin(){
	
	
	let username = $("#usernameAdminSearch").val();
	let gender = $("#gender option:selected").val();
	let role = $("#role option:selected").val();
	
	
	
	
	let data = { "username": username,
				"gender": gender,
				"role": role
				
			
			
				};
	
	let u = JSON.stringify(data);
	
	
	$.ajax({
		url: "rest/search/searchUsersAdmin",
		type: "POST",
		data: u,
		contentType: "application/json",
		success: function(users){
			
			if(users == null){
				alert("There is no match!");
				return;
			}
			
			
			$("#allUsersDiv").show();
	    	let i;
			$('#allUsersTable tbody').empty();
			
			for(i=0; i< users.length; i++){
				let u= users[i];
				let rbr = i +1;
				
				
			$('#allUsersTable tbody').append("<tr><th scope=\"row\">"+u.id+"</th>"+
					"<td>"+u.name+"</td>"+ "<td>"+u.surname+"</td>"+ "<td>"+u.username+"</td>"+ "<td>"+u.gender+"</td>"+ "<td>"+u.role+"</td>"+
					"<td><button type=\"button\" onclick=\"changeRole('"+u.id+","+u.role+
					"')\" class=\"btn btn-primary \">Change Role</button>" +
					"<button type=\"button\" onclick=\"removeUser('"+u.id+"')\" class=\"btn btn-primary\">Remove</button></td></tr>"		
					);
			}
			
			
			
		}
		
		
	});
	
	
}

function cancelSearch(){
	
	
    document.getElementById("usernameAdminSearch").value = "";
    $('#gender option:first').prop('selected',true);
    $('#role option:first').prop('selected',true);
    getAllUsers();
	
}

function searchApartments(){
	
	event.preventDefault();
	 
	let startDate = null;
	let endDate = null;
	let city= null;
	
	let dateFrom= null;
	let dateTo = null;
	
	if($("#guestSearchStartDate").val() != ""){
		startDate = $("#guestSearchStartDate").val();
		dateFrom = Date.parse(startDate);
	}
	
	if($("#guestSearchEndDate").val() != ""){
		endDate = $("#guestSearchEndDate").val();
		dateTo = Date.parse(endDate);
	}
	
	if($("#guestSearchCity").val() != ""){
		city = $("#guestSearchCity").val();
	}

	let sort = $("#sort").val();
	
	
	let data = {
			"dateFrom": dateFrom,
			"dateTo": dateTo,
			"location" : city,
			"sort": sort
	};
	
	let searchData=JSON.stringify(data);
	
	$.ajax({
		url: "rest/search/basicSearch",
		type: "POST",
		data: searchData,
		contentType: "application/json",
		success: function(apartmentList){
			
			if(apartmentList == null){
				
				alert("No search results!");
				return;
				
			}
			let i;
			$("#apartmentList").empty();
			
			for(i=0; i < apartmentList.length; i++){
				let ap= apartmentList[i];
				
				
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
			"</div>");
			}
		},
		error: function(error){
			console.log("Search error...");
		}
		
		
		
		
	});
}

function searchApartmentsAdmin(){
	
	event.preventDefault();
	 
	let startDate = null;
	let endDate = null;
	let city= null;
	//let sort = null;
	
	let dateFrom= null;
	let dateTo = null;
	
	if($("#adminSearchStartDate").val() != ""){
		startDate = $("#adminSearchStartDate").val();
		dateFrom = Date.parse(startDate);
	}
	
	if($("#adminSearchEndDate").val() != ""){
		endDate = $("#adminSearchEndDate").val();
		dateTo = Date.parse(endDate);
	}
	
	if($("#adminSearchCity").val() != ""){
		city = $("#adminSearchCity").val();
	}

	let sort = $("#sort").val();
	alert("SORT OPTIONA: " + sort);
	
	let data = {
			"dateFrom": dateFrom,
			"dateTo": dateTo,
			"location" : city,
			"sort": sort
	};
	
	let searchData=JSON.stringify(data);
	
	$.ajax({
		url: "rest/search/basicSearch",
		type: "POST",
		data: searchData,
		contentType: "application/json",
		success: function(apartmentList){
			
			if(apartmentList == null){
				
				alert("No search results!");
				return;
				
			}
			let i;
			$("#apartmentListAdmin").empty();
			
			for(i=0; i < apartmentList.length; i++){
				let ap= apartmentList[i];
				
				
			$("#apartmentListAdmin").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
					"<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
				"<div class=\"w3-container w3-white\">"+
					"<h3>" +ap.type+ "</h3>" +
					"<h6 class=\"w3-opacity\">Price: " +ap.pricePerNight+ "$</h6>" +
					"<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
					"<p>Rating: "+ap.numberOfRooms+"</p>"+
					"<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
					"<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See Details</button>"+
					 "<button onclick=\"editApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
				        "<button onclick=\"deleteApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
					" </div>" +
			"</div>");
			}
		},
		error: function(error){
			console.log("Search error...");
		}
		
		
		
		
	});
	
	
	
}

function cancelSearchResults(){
	
	 var user = JSON.parse(localStorage.getItem('user'));
	 
	  if((user == null) || user.role==="GUEST"){
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
			
	  } else if( user.role ==="ADMIN"){
		  getAllApartmentsAdmin();
	  }
	
	
	
}


