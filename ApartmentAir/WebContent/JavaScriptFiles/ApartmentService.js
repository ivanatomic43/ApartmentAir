var image="";

$(document).ready(function(){

	map.on('click', function(evt){
	    console.info(evt.pixel);
	    console.info(map.getPixelFromCoordinate(evt.coordinate));
	    console.info(ol.proj.toLonLat(evt.coordinate));
	    var coords = ol.proj.toLonLat(evt.coordinate);
	    var lat = coords[1];
	    var lon = coords[0];
	    var locTxt = "Latitude: " + lat + " Longitude: " + lon;
	    // coords is a div in HTML below the map to display
	    $('#latitude').val(lat) 
	    $('#longitude').val(lon) 
	});
});

function showNewApartmentForm(){
	$("#listOfApartments").hide();
	$("#newApartmentForm").show();
	
	
}

function adImageSelected(){
	console.log('jou');
  var reader = new FileReader();
  reader.readAsDataURL($('#newAppImage')[0].files[0]);
  var me = this;
  reader.onload = function () {
    image = reader.result;
//	  
//  }
  }
}



function createApartment(){
	alert("usao u create");
	
	
	
	
	let validation = true;
	let type='';
	let status='';
	let price=$("#newAppPricePerNight")[0].value;
	let numberOfGuests = $("#newAppNumberOfGuests")[0].value;
	let numberOfRooms = $("#newAppNumberOfRooms")[0].value;
	
	let dateFrom= new Date($('#newAppDateFrom').val());
	let dateTo = new Date($("#newAppDateTo").val());
	
	alert(dateFrom + dateTo);

		
	let amenities =[];
	
	if(document.getElementById("tv").checked){
		tv = document.getElementById("tv").value;
		amenities.push(tv);

	}
	
	if(document.getElementById("airconditioner").checked){
		airConditioner = document.getElementById("airconditioner").value;
		amenities.push(airConditioner);
	}
	if(document.getElementById("parkinglot").checked){
		parkingLot = document.getElementById("parkinglot").value;
		amenities.push(parkingLot);

	}
	if(document.getElementById("kitchen").checked){
		 kitchen = document.getElementById("kitchen").value;
		 amenities.push(kitchen);

	}
	if(document.getElementById("wifi").checked){
		 wifi= document.getElementById("wifi").value;
		 amenities.push(wifi);
	}
	
	//type check
	if (document.getElementById("apartment").checked) {
		  type = document.getElementById("apartment").value;
		}
	else if (document.getElementById("room").checked) {
		  type = document.getElementById("room").value;
		}
	else {
		
		alert("Please select type!");
		validation=false;
		
	}
	
	//status check
	if (document.getElementById("active").checked) {
		  status = document.getElementById("active").value;
		}
	else if (document.getElementById("inactive").checked) {
		  status = document.getElementById("inactive").value;
		}
	else {
		
		alert("Please select status!");
		validation=false;
		
	}
	
	
	
	
	/*
	let data = {
			
			"type": type,
			"status": status,
			"location": location,
			"pricePerNight": price,
			"numberOfGuests": numberOfGuests,
			"numberOfRooms": numberOfRooms,
			"dateForRent": dateForRent,
			"amenities": amenities
			
			
	};
	
	
	//ajax
	if(validation){
		
		let app = JSON.stringify(data);
		alert(app);
		
		$.ajax({
			url:"rest/apartment/createApartment",
			type: "POST",
			data:app,
			contentType: "application/json",
			dataType: "json",
			success: function(newApartment){
				alert(newApartment);
				if(newApartment != null){
				alert("New apartment created!");
				} else {
					alert("Neuspesno");
				}
				
				
			}
			
		});
		
		
		
		
	} else
		{
		alert("Some fields are empty!");
	}
	
	*/
}