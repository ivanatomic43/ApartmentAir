function showHostComments(){
	
	
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#newApartmentForm").hide();
	$("#listOfApartmentsHost").hide();
	$("#listOfReservationsHost").hide();
	$("#listOfUsersHost").hide();
		getAllCommentsHost();
	
	
}

function getAllCommentsHost(){
	
	var user = JSON.parse(localStorage.getItem('user'));
	
	$("#allCommentsHost").show();
	
	$.ajax({
		
		url: "rest/comment/getAllCommentsHost/" + user.id,
		type: "GET",
		contentType: "application/json",
		success: function(commentsList){
			

			if(commentsList== null){
				console.log("There is no comments...")
				return;
			}
			
			
	    	let i;
			$('#allCommentsHostTable tbody').empty();
			
			for(i=0; i< commentsList.length; i++){
				let c= commentsList[i];
				let rbr = i +1;
				
				
			$('#allCommentsHostTable tbody').append("<tr><th scope=\"row\">"+c.id+"</th>"+
					"<td>"+c.guestName+" "+c.guestSurname+"</td>"+ "<td>"+c.text+"</td>"+ "<td>"+c.rating+"</td>"+ "<td>"+c.apartmentType
					+"</td>"+ "<td>"+c.status+"</td>"+
					"<td><button type=\"button\" onclick=\"approveComment('"+c.id+","+c.status+"')\" class=\"btn btn-primary \">Approve</button></td>" +
					"<td><button type=\"button\" onclick=\"declineComment('"+c.id+","+c.status+"')\" class=\"btn btn-primary\">Decline</button></td></tr>"		
					);
			}
			
			
		},
		error: function(error){
			alert("Error getting comments...");
		}
		
		
	});
	
	
	
	
}
function approveComment(data){
	
	var info = data.split(",");
	var commentID = info[0];
	var commentStatus = info[1];
	
	if(commentStatus == "WAITING_FOR_APPROVAL") {
	
	$.ajax({
		url: "rest/comment/approveComment/" + commentID,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Comment approved!");
			getAllCommentsHost();
		}, 
		error: function(error){
			
		}
		
		
	});
	} else if (commentStatus == "APPROVED" || commentStatus == "DENIED"){
		alert("The comment has already been reviewed");
		return;
	}
	
}

function declineComment(data){
	
	var info = data.split(",");
	var commentID = info[0];
	var commentStatus = info[1];
	
	if(commentStatus == "WAITING_FOR_APPROVAL") {
	
	$.ajax({
		url: "rest/comment/declineComment/" + commentID,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Comment declined!");
			getAllCommentsHost();
		}, 
		error: function(error){
			
		}
		
		
	});
	} else if (commentStatus == "APPROVED" || commentStatus == "DENIED"){
		alert("The comment has already been reviewed");
		return;
	}
	
}


function showAdminComments(){
	
	
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#editUserForm").hide();
	$("#addAmenityForm").hide();
	$("#editAmenityForm").hide();
	$("#allAmenitiesDiv").hide();
	$("#allUsersDiv").hide();
	$("#listOfApartmentsAdmin").hide();
	$("#listOfReservationsAdmin").hide();
	
		getAllCommentsAdmin();
	
	
}

function getAllCommentsAdmin(){
	
	//var user = JSON.parse(localStorage.getItem('user'));
	
	$("#allCommentsAdmin").show();
	
	$.ajax({
		
		url: "rest/comment/getAllCommentsAdmin",
		type: "GET",
		contentType: "application/json",
		success: function(commentsList){
			

			if(commentsList== null){
				console.log("There is no comments...")
				return;
			}
			
			
	    	let i;
			$('#allCommentsAdminTable tbody').empty();
			
			for(i=0; i< commentsList.length; i++){
				let c= commentsList[i];
				let rbr = i +1;
				
				
			$('#allCommentsAdminTable tbody').append("<tr><th scope=\"row\">"+c.id+"</th>"+
					"<td>"+c.guestName+" "+c.guestSurname+"</td>"+ "<td>"+c.text+"</td>"+ "<td>"+c.rating+"</td>"+ "<td>"+c.apartmentType
					+"</td>"+ "<td>"+c.status+"</td>"+
					"</tr>"		
					);
			}
			
			
		},
		error: function(error){
			alert("Error getting comments...");
		}
		
		
	});
	
	
	
	
}

