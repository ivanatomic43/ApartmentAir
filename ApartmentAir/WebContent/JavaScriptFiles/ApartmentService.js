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
	
	
	$.ajax({
		type:"GET",
		url:"rest/amenity/getAllAmenities",
		contentType:"application/json",
		success:function(amenities){
			console.log(amenities)
			for(let amenity of amenities){
				addAmenity(amenity)
			}
		},
		error:function(){
			alert('error getting amenities')
		}
	})

	
	
	
});

function addAmenity(amenity){
	$("#checkboxes").append(
	"<div class=\"form-check\">" +
			"<input type=\"checkbox\" class=\"form-check-input\" id=\"${amenity.id}\" name=\"amenities\" value=\"${amenity.id}\" >"+
			"<label class=\"form-check-label\" for=\"${amenity.id}\">"+ amenity.name + "</label>" +
	"</div>"
	);
}



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

function getDates(startDate, stopDate) {
    var dateArray = new Array();
    var currentDate = startDate;
    while (currentDate <= stopDate) {
        dateArray.push(new Date (currentDate));
		let date = new Date(currentDate);
		date.setDate(date.getDate() + 1);
        currentDate = date;
    }
    return dateArray;
}

function createApartment(){
	alert("usao u create");
	
	
	event.preventDefault();
	
	let validation = true;
	//basic
	let type='';
	let status='';
	let price=$("#newAppPricePerNight")[0].value;
	let numberOfGuests = $("#newAppNumberOfGuests")[0].value;
	let numberOfRooms = $("#newAppNumberOfRooms")[0].value;
	
	//dates
	let startDate= new Date($('#newAppDateFrom').val());
	let endDate= new Date($("#newAppDateTo").val());
	
	alert(startDate + endDate);

	
	//address
	let street = $("#newAppStreet")[0].value;
	let streetNumber = $("#newAppStreetNumber")[0].value;
	let city = $("#newAppCity")[0].value;
	let postal = $("#newAppPostalNumber")[0].value;
	let postalInt = 0
	if(postal != ""){
		postalInt = parseInt(postal)
	}
	
	let address = {
			street: street,
			number: streetNumber,
			city: city,
			postNumber: postalInt
	}
	alert(address);
	 //location
	let latitude = $("#latitude").val();
	let longitude = $("#longitude").val();
	
	let location ={
			latitude: parseFloat(latitude),
			longitude: parseFloat(longitude),
			address: address
	}
	
	
	//amenities
	let amenities =[];
	var $boxes = $('input[name=amenities]:checked');
	$boxes.each(function(){
		amenities.push($(this).val())
	})
	console.log('all amenities: ' + amenities)
	
		var dateFrom = Date.parse(startDate)
		var dateTo = Date.parse(endDate)
		var validDates = getDates(dateFrom, dateTo)
		console.log(validDates)
	
	
	
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
	if (document.getElementById("ACTIVE").checked) {
		  status = document.getElementById("ACTIVE").value;
		}
	else if (document.getElementById("INACTIVE").checked) {
		  status = document.getElementById("INACTIVE").value;
		}
	else {
		
		alert("Please select status!");
		validation=false;
		
	}
	
	
	
	
	let data = {
			
			"type": type,
			"status": status,
			"location": location,
			"pricePerNight": price,
			"numberOfGuests": numberOfGuests,
			"numberOfRooms": numberOfRooms,
			"dates": validDates,
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
				alert("USAO U SUCCESS")
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
	
	
}