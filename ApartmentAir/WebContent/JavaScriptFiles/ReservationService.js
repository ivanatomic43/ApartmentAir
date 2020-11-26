function showMyReservations(){
	
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#listOfApartments").hide();
	$("#apartmentDetails").hide();
	$("#newApartmentForm").hide();
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
					"<td>"+r.apartmentType+"</td>"+ "<td>"+r.street+" "+r.number+", "+r.city+"</td>"+ "<td>"+r.dateOfReservation+"</td>"+ "<td>"+r.totalPrice
					+"$</td>"+ "<td>"+r.hostUsername+"</td>"+ "<td>"+r.reservationStatus+"</td>"+
					"<td><button type=\"button\" onclick=\"cancelReservation('"+r.id+"')\" class=\"btn btn-primary \">Cancel</button></td>" +
					"<td><button type=\"button\" onclick=\"leaveCommentClick('"+r.id+","+r.apartmentID+","+r.reservationStatus+"')\" class=\"btn btn-primary\">Leave Comment</button></td></tr>"		
					);
			}
			
			
			
			
		}, 
		error: function(error){
			console.log("Error - function - getAllReservationsGuest()")
		}
		
	});
	
}

function leaveCommentClick(data){
	
	$("#listOfReservationsGuest").hide();
	$("#newCommentForm").show();
	
	leaveComment(data);
	
}

function leaveComment(data){
	
	alert(data);
	var info= data.split(",");
	var reservationID = info[0];
	var apartmentID = info[1];
	var reservationStatus = info[2];

	
	var user = JSON.parse(localStorage.getItem('user'));
	
	
	alert(reservationID);
	
	alert(reservationStatus);
	
	
	
	
	$("form#commentForm").submit(function(event){
		
		 event.preventDefault();
		 
		var text = $("#newCommentText").val();
		var rate = $("#newCommentRate").val();
		
		alert(reservationStatus);
		
		
		
		if(text == "" || rate == ""){
			alert("Please leave rate  and comment text!");
			return;
		}
		
		if(reservationStatus == "DECLINED" || reservationStatus == "ENDED") {
		alert("USAO U if");
		let comment = {
				"guestID" : user.id,
				"apartmentID": apartmentID,
				"text" : text,
				"reservationID" : reservationID,
				"rating": rate
				
		};
		
		let c = JSON.stringify(comment);
		alert(c);
		
		$.ajax({
		
			url: "rest/comment/leaveComment",
			type:"POST",
			data: c,
			contentType: "application/json",
			success: function(){
				alert("Comment sent!");
				getAllReservationsGuest();
			},
			error: function(error){
				alert("Error leaving the comment!");
				console.log("Error leaving comment...");
			}
			
			
			
			
		});
		} else {
			alert("You can only leave comment for DECLINED and ENDED reservations!");
			return;
		}
		
		
	});
	

	
}

function cancelReservation(data){
	
	var id = data;
	
	$.ajax({
		url: "rest/reservation/cancelReservation/" + id,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Reservation cancelled!");
			getAllReservationsGuest();
		},
		error: function(error){
			console.log("Error cancelling the reservation...");
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
	$("#allCommentsAdmin").hide();
	$("#listOfUsersAdmin").hide();
	$("#listOfReservationsAdmin").show();

	getAllReservationsAdmin();
}

function getAllReservationsAdmin(){
	
	alert("USAO u get all reservationsAdmin");
	

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
			console.log("Error - function - getAllReservationsAdmin()")
		}
		
	});
	
	
}

function showReservationsHost(){
	

	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#listOfApartmentsHost").hide();
	$("#apartmentDetails").hide();
	$("#allUsersDiv").hide();
	$("#allUsersTable").hide();
	$("#apartmentListHost").hide();
	$("#allReservationsHost").show();
	$("#allCommentsHost").hide();
	
	getAllReservationsHost();
	
}

function getAllReservationsHost(){
	
	var user = JSON.parse(localStorage.getItem('user'));
	
	$("#listOfReservationsHost").show();
	
	$.ajax({
		url: "rest/reservation/getAllReservationsHost/" + user.id,
		type:"GET",
		contentType: "application/json",
		success: function(reservationList){
			
			if(reservationList== null){
				console.log("There is no reservations for host...")
				return;
			}
			
			
	    	let i;
			$('#listOfReservationsHostTable tbody').empty();
			
			for(i=0; i< reservationList.length; i++){
				let r= reservationList[i];
				let rbr = i +1;
				
				
			$('#listOfReservationsHostTable tbody').append("<tr><th scope=\"row\">"+r.id+"</th>"+
					"<td>"+r.apartmentType+"</td>"+ 
					"<td>"+r.apartmentID+"</td>"+  "<td>"+r.street+" "+r.number+", "+r.city+"</td>"+ "<td>"+r.dateOfReservation+"</td>"+ "<td>"+r.totalPrice
					+"$</td>"+ "<td>"+r.guestName+" "+r.guestSurname+"</td>"+ "<td>"+r.guestUsername+"</td>"+"<td>"+r.hostUsername+"</td>"+ "<td>"+r.reservationStatus+"</td>"+
					"<td><button type=\"button\" onclick=\"approveReservation('"+r.id+"')\" class=\"btn btn-primary \">Approve</button></td>" +
					"<td><button type=\"button\" onclick=\"declineReservation('"+r.id+"')\" class=\"btn btn-primary \">Decline</button></td>"+
					"<td><button type=\"button\" onclick=\"finishReservation('"+r.id+"')\" class=\"btn btn-primary \">End</button></td>"+
					"</tr>"		
					);
			}
			
			
			
			
		}, 
		error: function(error){
			console.log("Error - function - getAllReservationsHost()")
		}
		
	});
		
}

function approveReservation(data){
	
	var id = data;
	
	$.ajax({
		url: "rest/reservation/approveReservation/" + id,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Reservation approved!");
			getAllReservationsHost();
		},
		error: function(error){
			console.log("Error approving the reservation...");
		}
		
		
		
	});
}

function declineReservation(data){
	
	var id = data;
	
	$.ajax({
		url: "rest/reservation/declineReservation/" + id,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Reservation declined!");
			getAllReservationsHost();
		},
		error: function(error){
			console.log("Error declining the reservation...");
		}
		
		
		
	});
}
function finishReservation(data){
	
	var id = data;
	
	$.ajax({
		url: "rest/reservation/finishReservation/" + id,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Reservation finished!");
			getAllReservationsGuest();
		},
		error: function(error){
			console.log("Error finishing the reservation...");
		}
		
		
		
	});
}



