


function showAmenities(){
	
	 getAllAmenities();
	$("#listOfApartments").hide();
	$("#addAmenityForm").show();
	 
	 
}

function getAllAmenities(){
	
	  $.ajax({
		    type: "GET",
		    url: "rest/amenity/getAllAmenities",
		    contentType: "application/json",
		    success: function(amenities){
		    	
		    	$("#allAmenitiesDiv").show();
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