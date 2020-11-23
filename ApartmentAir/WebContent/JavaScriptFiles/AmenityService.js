


function showAmenities(){
	
	 
	$("#listOfApartments").hide();
	$("#addAmenityForm").show();
	$("#listOfApartmentsAdmin").hide();
	$("#userProfileDiv").hide();
	$("#userProfileDiv2").hide();
	$("#listOfReservationsAdmin").hide();
	$("#allUsersDiv").hide();
	$("#listOfUsersAdmin").hide();
	$("#allAmenitiesDiv").show();
	$("#allCommentsAdmin").hide();
	getAllAmenities();
}

function getAllAmenities(){
	
	  $.ajax({
		    type: "GET",
		    url: "rest/amenity/getAllAmenities",
		    contentType: "application/json",
		    success: function(amenities){
		    	
		    	alert("Usao u succ");
		    	$("allAmenitiesTable").show();
		    	let i;
				$('#allAmenitiesTable tbody').empty();
				
				for(i=0; i< amenities.length; i++){
					let am= amenities[i];
					let rbr = i +1;
					
					
				$('#allAmenitiesTable tbody').append("<tr><th scope=\"row\">"+am.id+"</th>"+
						"<td>"+am.name+"</td>"+
						"<td><button type=\"button\" onclick=\"editAmenityClick('"+am.id+","+am.name+
						"')\" class=\"btn btn-primary \">Edit</button>" +
						"<button type=\"button\" onclick=\"removeAmenity('"+am.id+"')\" class=\"btn btn-primary\">Remove</button></td></tr>"		
						);
				}
				
		    
		    },
		    error: function () {
		      console.log("100% TE ZAJEBAVA NEKI #ID");
		    }
		  });
	
	
	
	
}


	
	
function createNewAmenity(){
	event.preventDefault();
	alert("Usao u create new amenity");
	
	let name = $("#newAmenityName")[0].value;
	alert(name);
	
	let data = {
			"name": name
	};
	
	a= JSON.stringify(data);
	
	$.ajax({
		type:"POST",
		url: "rest/amenity/createAmenity",
		data: a,
		contentType:"application/json",
		success: function(amenity){
			getAllAmenities();
			alert("Amenity added");
		},
		error: function(){
			alert("Error creating new amenity");
		}
		
	});
	
	
	
	
}	


function editAmenityClick(data){
	
	$("#addAmenityForm").hide();
	$("#allAmenitiesDiv").hide();
	$("#allAmenitiesTable").hide();
	
	fillEditAmenityForm(data);
	$("#editAmenityForm").show();
	
}

function fillEditAmenityForm(data){
	
	let info = data.split(",");
	editAmenityId = info[0];
	$("#editAmenityName").val(info[1]);
	
	
	
	
}

function editAmenity(){
	
	let id = editAmenityId;
	let name = $("#editAmenityName")[0].value;
	alert(name);
	
	let validation= true;
	
	if(name){
		editAmenityName.style.borderColor="white";
	}
	else {
		validation=false;
		editAmenityName.style.borderColor="red";
		alert("Empty field!");
	}
	
	
	if(validation){
		
		let data = {
				"id": id,
				"name": name
			};
		
		let am = JSON.stringify(data);
	 alert(am); // ovo saljem kao promenu
		$.ajax({
			url: "rest/amenity/editAmenity",
			type: "PUT",
			data: am,
			contentType: "application/json",
			success: function(){
				alert("Usao u success edita");
				//$("#editAmenityName").val('');
				$("#addAmenityForm").show();
				getAllAmenities();
			},
			statusCode: {
				400: function(){
					alert("Double entry for amenity name");
				}
			}
		});
	}
}

function removeAmenity(id){
	
	let urlRemove = "rest/amenity/removeAmenity/" + id;
	
	$.ajax({
		url: urlRemove,
		type: "DELETE",
		contentType: "application/json",
		success: function(amenity){
			
				getAllAmenities();
			
		},
		error: function(){
			alert("Error");
			getAllAmenities();
		}
		
	});
	
}


