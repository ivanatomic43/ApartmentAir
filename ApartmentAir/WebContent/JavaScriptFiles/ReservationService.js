function showMyReservations(){
	
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#listOfApartments").hide();
	$("#apartmentDetails").hide();
	getAllReservationsGuest();
	
	
}




function getAllReservationsGuest(){
	
	var user = JSON.parse(localStorage.getItem('user'));
	
	$("#listOfReservationsGuest").show();
	
	$.ajax({
		url: "rest/reservation/getAllReservationsGuest/" + user.id,
		type:"GET",
		contentType: "application/json",
		success: function(reservationList){
			
			if(reservationList== null){
				console.log("There is no reservations for guest...")
				return;
			}
			
			
	    	let i;
			$('#listOfReservationsGuestTable tbody').empty();
			
			for(i=0; i< reservationList.length; i++){
				let r= reservationList[i];
				let rbr = i +1;
				
				
			$('#listOfReservationsGuestTable tbody').append("<tr><th scope=\"row\">"+r.id+"</th>"+
					"<td>"+r.apartmentID.type+"</td>"+ "<td>"+r.apartmentID.location.address.street+" "+r.apartmentID.location.address.number+", "+r.apartmentID.location.address.city+"</td>"+ "<td>"+r.dateOfReservation+"</td>"+ "<td>"+r.totalPrice
					+"</td>"+ "<td>"+r.apartmentID.host+"</td>"+ "<td>"+r.reservationStatus+"</td>"+
					"<td><button type=\"button\" onclick=\"cancelReservation('"+r.id+"')\" class=\"btn btn-primary \">Cancel</button>" +
					"<button type=\"button\" onclick=\"leaveComment('"+r.id+"')\" class=\"btn btn-primary\">Leave Comment</button></td></tr>"		
					);
			}
			
			
			
			
		}, 
		error: function(error){
			console.log("Error - function - getAllReservationsGuest()")
		}
		
	});
	
}

function showAdminReservations(){
	
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#listOfApartmentsAdmin").hide();
	$("#apartmentDetails").hide();
	$("#addAmenityForm").hide();
	$("#editAmenityForm").hide();
	$("#allAmenitiesDiv").hide();
	$("#allAmenitiesTable").hide();
	$("#allUsersDiv").hide();
	$("#allUsersTable").hide();
	$("#apartmentListAdmin").hide();

	getAllReservationsAdmin();
}

function getAllReservationsAdmin(){
	
$("#listOfReservationsAdmin").show();
	
	$.ajax({
		url: "rest/reservation/getAllReservationsAdmin",
		type:"GET",
		contentType: "application/json",
		success: function(reservationList){
			
			if(reservationList== null){
				console.log("There is no reservations for admin...")
				return;
			}
			
			
	    	let i;
			$('#listOfReservationsAdminTable tbody').empty();
			
			for(i=0; i< reservationList.length; i++){
				let r= reservationList[i];
				let rbr = i +1;
				
				
			$('#listOfReservationsAdminTable tbody').append("<tr><th scope=\"row\">"+r.id+"</th>"+
					"<td>"+r.apartmentID.type+"</td>"+ "<td>"+r.apartmentID.location.address.street+" "+r.apartmentID.location.address.number+", "+r.apartmentID.location.address.city+"</td>"+ "<td>"+r.dateOfReservation+"</td>"+ "<td>"+r.totalPrice
					+"</td>"+ "<td>"+r.guestID.name+" "+r.guestID.surname+"</td>"+"<td>"+r.apartmentID.host+"</td>"+ "<td>"+r.reservationStatus+"</td>"+
					"<td><button type=\"button\" onclick=\"cancelReservation('"+r.id+"')\" class=\"btn btn-primary \">Cancel</button></tr>"		
					);
			}
			
			
			
			
		}, 
		error: function(error){
			console.log("Error - function - getAllReservationsAdmin()")
		}
		
	});
	
	
}


