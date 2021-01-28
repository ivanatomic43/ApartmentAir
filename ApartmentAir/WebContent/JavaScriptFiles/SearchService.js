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

function searchUsersHost(){
	
	
	let username = $("#usernameHostSearch").val();
	let gender = $("#genderHostSearch option:selected").val();
	let role = $("#roleHostSearch option:selected").val();
	
	
	
	
	let data = { "username": username,
				"gender": gender,
				"role": role
				
			
			
				};
	
	let u = JSON.stringify(data);
	
	
	$.ajax({
		url: "rest/search/searchUsersHost",
		type: "POST",
		data: u,
		contentType: "application/json",
		success: function(users){
			
			if(users == null){
				alert("There is no match!");
				return;
			}
			
			
			$("#allUsersDivHost").show();
	    	let i;
			$('#allUsersTableHost tbody').empty();
			
			for(i=0; i< users.length; i++){
				let u= users[i];
				let rbr = i +1;
				
				
			$('#allUsersTableHost tbody').append("<tr><th scope=\"row\">"+u.id+"</th>"+
					"<td>"+u.name+"</td>"+ "<td>"+u.surname+"</td>"+ "<td>"+u.username+"</td>"+ "<td>"+u.gender+"</td>"+ "<td>"+u.role+"</td>"+
					"<td>" +
					"</td></tr>"		
					);
			}
			
			
			
		}
		
		
	});
	
	
}

function cancelSearchHost(){
	
	
    document.getElementById("usernameHostSearch").value = "";
    $('#genderHostSearch option:first').prop('selected',true);
    $('#roleHostSearch option:first').prop('selected',true);
    getAllUsersHost();
	
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
	 
	  if(user == null || user.role==="GUEST"){
		  
		  document.getElementById("guestSearchStartDate").value = "";
		  document.getElementById("guestSearchEndDate").value = "";
		  document.getElementById("guestSearchCity").value = "";
		  
		  //advanced
		  document.getElementById("advancedDateFrom").value = "";
		  document.getElementById("advancedDateTo").value = "";
		  document.getElementById("advancedCity").value = "";
		  document.getElementById("advancedNumberOfGuests").value = "";
		  document.getElementById("priceMIN").value = "";
		  document.getElementById("priceMAX").value = "";
		  document.getElementById("numberOfRoomMIN").value = "";
		  document.getElementById("numberOfRoomMAX").value = "";
	
		  $('#filterType option:first').prop('selected',true);
		  $('#filterSort option:first').prop('selected',true);
		  
		  
		  
		  $('#sort option:first').prop('selected',true);
		  
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
		  document.getElementById("adminSearchStartDate").value = "";
		  document.getElementById("adminSearchEndDate").value = "";
		  document.getElementById("adminSearchCity").value = "";
		  $('#sort option:first').prop('selected',true);
		  getAllApartmentsAdmin();
	  }
	  else if( user.role ==="HOST"){
		  document.getElementById("hostSearchStartDate").value = "";
		  document.getElementById("hostSearchEndDate").value = "";
		  document.getElementById("hostSearchCity").value = "";
		  $('#sort option:first').prop('selected',true);
		  getAllActiveApartmentsHost();
	  }
	
	
}
//pretrazuje sve apartmane ali ih ispisuje u kartici active apartments
function searchApartmentsHost(){
	
	
	event.preventDefault();
	 
	let startDate = null;
	let endDate = null;
	let city= null;
	//let sort = null;
	
	let dateFrom= null;
	let dateTo = null;
	
	if($("#hostSearchStartDate").val() != ""){
		startDate = $("#hostSearchStartDate").val();
		dateFrom = Date.parse(startDate);
	}
	
	if($("#hostSearchEndDate").val() != ""){
		endDate = $("#hostSearchEndDate").val();
		dateTo = Date.parse(endDate);
	}
	
	if($("#hostSearchCity").val() != ""){
		city = $("#hostSearchCity").val();
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
			$("#apartmentListHost").empty();
			
			for(i=0; i < apartmentList.length; i++){
				let ap= apartmentList[i];
				
				
			$("#apartmentListHost").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
					"<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
				"<div class=\"w3-container w3-white\">"+
					"<h3>" +ap.type+ "</h3>" +
					"<h6 class=\"w3-opacity\">Price: " +ap.pricePerNight+ "$</h6>" +
					  "<p>Status: "+ ap.status+"</p>"+
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

function searchReservationsAdmin(){
	
	$("#listOfReservationsAdmin").show();
	
	let usernameAdminSearchReservations = null;
	
	if($("#usernameAdminSearchReservations").val() != ""){
		usernameAdminSearchReservations = $("#usernameAdminSearchReservations").val();
	}
	
	let sortReservationsAdmin = $("#sortReservationsAdmin").val();
	let filterReservationsAdmin = $("#filterReservationsAdmin").val();
	
	
	let data = {
			"username" : usernameAdminSearchReservations,
			"sort" : sortReservationsAdmin,
			"filter" : filterReservationsAdmin
	};


	let searchData = JSON.stringify(data);
	
	
		
	$.ajax({
			url: "rest/search/searchReservations",
			type: "POST",
			data: searchData,
			contentType: "application/json",
			success: function(reservationList) {
				
			 if(reservationList == null){
				 alert("There is no match!");
				 return;
			 }
				
				$("#allReservationsAdmin").show();
				$("#listOfReservationsAdminTable").show();
		    	let i;
				$('#listOfReservationsAdminTable tbody').empty();
				
				for(i=0; i< reservationList.length; i++){
					let r= reservationList[i];
					let rbr = i +1;
					
					
					$('#listOfReservationsAdminTable tbody').append("<tr><th scope=\"row\">"+r.id+"</th>"+
							"<td>"+r.apartmentType+"</td>"+ "<td>"+r.street+" "+r.number+", "+r.city+"</td>"+ "<td>"+r.dateOfReservation+"</td>"+ "<td>"+r.totalPrice
							+"$</td>"+ "<td>"+r.guestName+" "+r.guestSurname+"</td>"+ "<td>"+r.guestUsername+"</td>"+"<td>"+r.hostUsername+"</td>"+ "<td>"+r.reservationStatus+"</td>"+
							"</tr>"		
							);
					
					
				}
				
				
				
			},
			error: function(error){
				console.log("Error getting search results...");
			}
		});
}	
 function cancelSearchReservationsAdmin(){
	
	  document.getElementById("usernameAdminSearchReservations").value = "";
		 
		 getAllReservationsAdmin();
	 
	 
 }

 function searchReservationsHost(){
		
		$("#listOfReservationsHost").show();
		
		let usernameHostSearchReservations = null;
		
		if($("#usernameHostSearchReservations").val() != ""){
			usernameHostSearchReservations = $("#usernameHostSearchReservations").val();
		}
		
		let sortReservationsHost = $("#sortReservationsHost").val();
		let filterReservationsHost = $("#filterReservationsHost").val();
		
		
		let data = {
				"username" : usernameHostSearchReservations,
				"sort" : sortReservationsHost,
				"filter" : filterReservationsHost
		};


		let searchData = JSON.stringify(data);
		
			
		$.ajax({
				url: "rest/search/searchReservations",
				type: "POST",
				data: searchData,
				contentType: "application/json",
				success: function(reservationList) {
					
				 if(reservationList == null){
					 alert("There is no match!");
					 return;
				 }
					
					$("#allReservationsHost").show();
					$("#listOfReservationsHostTable").show();
			    	let i;
					$('#listOfReservationsHostTable tbody').empty();
					
					for(i=0; i< reservationList.length; i++){
						let r= reservationList[i];
						let rbr = i +1;
				
						
						$('#listOfReservationsHostTable tbody').append("<tr><th scope=\"row\">"+r.id+"</th>"+
								"<td>"+r.apartmentType+"</td>"+ "<td>"+r.apartmentID+"</td>"+ "<td>"+r.street+" "+r.number+", "+r.city+"</td>"+ "<td>"+r.dateOfReservation+"</td>"+ "<td>"+r.totalPrice
								+"$</td>"+ "<td>"+r.guestName+" "+r.guestSurname+"</td>"+ "<td>"+r.guestUsername+"</td>"+"<td>"+r.hostUsername+"</td>"+ "<td>"+r.reservationStatus+"</td>"+
								"<td><button type=\"button\" onclick=\"approveReservation('"+r.id+"')\" class=\"btn btn-primary \">Approve</button></td>" +
								"<td><button type=\"button\" onclick=\"declineReservation('"+r.id+"')\" class=\"btn btn-primary \">Decline</button></td>"+
								"<td><button type=\"button\" onclick=\"finishReservation('"+r.id+"')\" class=\"btn btn-primary \">End</button></td>"+
								"</tr>"		
								);
						
						
					}
					
					
					
				},
				error: function(error){
					console.log("Error getting search results...");
				}
			});
	}	
function cancelSearchReservationsHost(){
		
		  document.getElementById("usernameHostSearchReservations").value = "";
			 
			 getAllReservationsHost();
		 
}

function advancedSearchGuest(){
	
	event.preventDefault();
	
	let advancedDateFrom = null;
	let advancedDateTo = null;
	let advancedCity = null;
	let advancedMaxPeople = null;
	let advancedPriceMin = null;
	let advancedPriceMax = null;
	let advancedRoomsMin = null;
	let advancedRoomsMax = null;
	

	let advancedAmenities = [];
	
	if($("#advancedDateFrom").val() != ""){
		advancedDateFrom = $("#advancedDateFrom").val();
	}
	
	if($("#advancedDateTo").val() != ""){
		advancedDateTo = $("#advancedDateTo").val();
	}
	
	if($("#advancedCity").val() != ""){
		advancedCity = $("#advancedCity").val();
	}
	
	if($("#advancedNumberOfGuests").val() != ""){
		advancedMaxPeople = $("#advancedNumberOfGuests").val();
	}
	
	if($("#priceMIN").val() != ""){
		advancedPriceMin = $("#priceMIN").val();
	}
	
	if($("#priceMAX").val() != ""){
		advancedPriceMax = $("#priceMAX").val();
	}
	
	if($("#numberOfRoomMIN").val() != ""){
		advancedRoomsMin = $("#numberOfRoomMIN").val();
	}
	
	if($("#numberOfRoomMAX").val() != ""){
		advancedRoomsMax = $("#numberOfRoomMAX").val();
	}
	
	
	let advancedType = $("#filterType").val();
	let advancedSort = $("#filterSort").val();
	
	
	let data = {
			
			"dateFrom": advancedDateFrom,
			"dateTo" : advancedDateTo,
			"location" : advancedCity,
			"sort" : advancedSort,
			"guestsMax" : parseInt(advancedMaxPeople),
			"priceMin" : parseFloat(advancedPriceMin),
			"priceMax" : parseFloat(advancedPriceMax),
			"roomsMin" : parseInt(advancedRoomsMin),
			"roomsMax": parseInt(advancedRoomsMax),
			"type" : advancedType,
			
	};
	
	
	let searchData = JSON.stringify(data);

	
	
	$.ajax({
		url: "rest/search/advancedSearch",
		type: "POST",
		data: searchData,
		contentType: "application/json",
		success: function(apartmentList){
			
			if(apartmentList == null){
				alert("There is no match!");
				return;
			}
			
			 var user = JSON.parse(localStorage.getItem('user'));
			 
			 if(user.role === "GUEST" || user.role === null) {
			
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
			
			 } else if (user.role ==="ADMIN"){
				 

				 	let i;
				 	$("#apartmentListAdmin").empty();
			
				 	for(i=0; i < apartmentList.length; i++){
				 		let ap= apartmentList[i];
				
				
				 		$("#apartmentListAdmin").append(
					
				 				"<div class=\"w3-third w3-margin-bottom\">" +
				 				"<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
				 				"<div class=\"w3-container w3-white\">"+
				 				"<h3>" +ap.type+ "</h3>" +
				 				"<h6 class=\"w3-opacity\">Price:" +ap.pricePerNight+ "$</h6>" +
				 				"<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
				 				"<p>Status: "+ap.status+"</p>"+
				 				"<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
				 				"<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See Details</button>"+
				 				" </div>" +
				 		"</div>");
				 	}
				 
				 
			 } else if(user.role ==="HOST"){
				 


					let i;
					$("#apartmentListHost").empty();
					
					
					for(i=0; i < apartmentList.length; i++){
						let ap= apartmentList[i];
						
						
					$("#apartmentListHost").append(
							
					"<div class=\"w3-third w3-margin-bottom\">" +
		      "<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
		      "<div class=\"w3-container w3-white\">"+
		        "<h3>" +ap.type+ "</h3>" +
		        "<h6 class=\"w3-opacity\">Price: " +ap.pricePerNight+ "</h6>" +
		        "<p>Status: "+ ap.status+"</p>"+
		        "<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
		        "<p>Rating: "+ap.numberOfGuests+"</p>"+
		        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
		        "<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See detalis</button>"+
		        "<button onclick=\"editApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
		        "<button onclick=\"deleteApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
		     " </div>" +
		    "</div>"
					);	
				 
				 
			 }
			}
			
		},
		error: function(error){
			console.log("Error - advanced search...");
			
		}
	});
	
	
	
	
}