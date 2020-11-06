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
			"<input type=\"checkbox\" class=\"form-check-input\" id=\""+amenity.id+"\" name=\"amenities\" value=\""+amenity.name+"\">"+
			"<label class=\"form-check-label\" for=\""+amenity.id+"\">"+ amenity.name + "</label>" +
	"</div>"
	);
}



function showNewApartmentForm(){
	$("#listOfApartments").hide();
	$("#newApartmentForm").show();
	$("#listOfApartmentsHost").hide();
	
	
}

function adImageSelected(){
	console.log('Image...');
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
	
	//event.preventDefault();
	
	//let validation = true;
	//basic
	let type='';
	let price=$("#newAppPricePerNight")[0].value;
	let numberOfGuests = $("#newAppNumberOfGuests")[0].value;
	let numberOfRooms = $("#newAppNumberOfRooms")[0].value;
	
	//dates
	let startDate= new Date($('#newAppDateFrom').val());
	let endDate= new Date($("#newAppDateTo").val());
	
	
	
	//address
	let street = $("#newAppStreet")[0].value;
	let streetNumber = $("#newAppStreetNumber")[0].value;
	let city = $("#newAppCity")[0].value;
	let postal = $("#newAppPostalNumber")[0].value;
	let postalInt = 0
	if(postal != ""){
		postalInt = parseInt(postal)
	}
	
	if(price== "" || numberOfRooms == "0" || numberOfGuests == "0" || street== "" || streetNumber=="" || city == "" || postal=="" || startDate=="" || endDate ==""){
		alert('Fill fields...')
		return;
	}
	
	let address = {
			street: street,
			number: streetNumber,
			city: city,
			postNumber: postalInt
	}
	//alert(address);
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
		//console.log(validDates)
	
	
	
	//type check
	if (document.getElementById("apartment").checked) {
		  type = document.getElementById("apartment").value;
		}
	else if (document.getElementById("room").checked) {
		  type = document.getElementById("room").value;
		}
	else {
		
		alert("Please select type!");
		
		return;
	}
	
	let data = {
			
			"type": type,
			"location": location,
			"pricePerNight": price,
			"numberOfGuests": numberOfGuests,
			"numberOfRooms": numberOfRooms,
			"dates": validDates,
			"amenities": amenities,
			"image": image
			
			
	};
	
	let app = JSON.stringify(data);
	alert(app);
		
		$.ajax({
			url:"rest/apartment/createApartment",
			type: "POST",
			data:app,
			contentType: "application/json",
			dataType: "json",
			success: function(apartment){
				alert("USAO U SUCCESS");
				//alert(newApartment);
				if(apartment != null){
				alert("New apartment created!");
				
				} else {
					alert("Neuspesno");
				}
				
				
			}
			
		});
		
		
		
		
	
	
	
}
function getAllApartmentsAdmin(){
	
	$.ajax({
		url: "rest/apartment/getAllApartments",
		type: "GET",
		contentType: "application/json",
		success: function(apartments){
			
			let i;
			$("#apartmentListAdmin").empty();
			
			
			for(i=0; i < apartments.length; i++){
				let ap= apartments[i];
				
				
			$("#apartmentListAdmin").append(
					
			"<div class=\"w3-third w3-margin-bottom\">" +
      "<img src=\""+ap.image+"\" alt=\"Norway\" style=\"width:100%\">"+
      "<div class=\"w3-container w3-white\">"+
        "<h3>" +ap.type+ "</h3>" +
        "<h6 class=\"w3-opacity\">Price: "+ap.pricePerNight+ "$</h6>" +
        "<p> Status: "+ ap.status+"</p>"+
        "<p>Location: "+ ap.location.address.street+" "+ap.location.address.number+", "+ap.location.address.city+"</p>"+
        "<p>Rating: "+ap.numberOfGuests+"</p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button onclick=\"showApartmentDetails('"+ ap.id +"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See detalis</button>"+
        "<button onclick=\"editApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
        "<button onclick=\"deleteApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
     " </div>" +
    "</div>"
			);	
				
				
			}
			
			
			
		}
	});
	
	
	
	
}

function showApartmentDetails(data){
	
	var id = data;
	alert(id);
	
	$("#apartmentDetails").show();
	$("#listOfApartments").hide();
	$("#listOfApartmentsHost").hide();
	$("#listOfApartmentsAdmin").hide();
	$("#apartmentAmenities").show();
	getApartmentDetails(id);
	getApartmentAmenities(id);
	getApartmentComments(id);
}

function getApartmentDetails(id){
	
	$("#apartmentInfo").empty();
	
	$.ajax({
		url: "rest/apartment/getApartment/" + id,
		type:"GET",
		contentType: "application/json",
		success: function(apartment){
			
			
			
			
			
			$("#apartmentInfo").append(
					"<div class=\"w3-container\" >" +
					"<h2 class=\"w3-text-black\">The Facility Details</h2>"+
					"<div class=\"w3-display-container mySlides\"> " +
    "<img src=\""+apartment.image+"\" style=\"width:100%;margin-bottom:-6px\">" +
      "<div class=\"w3-display-bottomleft w3-container w3-black\">"+
        "<p>Photo of the facility</p>" +
      "</div>" +
    "</div>"+
    
  
    "</div>" +
  "<div class=\"w3-row-padding w3-section\">"+
    "<div class=\"w3-col s3\">" +
      "<img class=\"demo w3-opacity w3-hover-opacity-off\" src=\""+apartment.image+"\" style=\"width:100%;cursor:pointer\" onclick=\"currentDiv(1)\" title=\"Photo of the facility\">"+
    "</div>"+

	"</div>"+

  "<div class=\"w3-container\" id =\"apartmentInfo\">"+
    "<h4><strong>Basic information</strong></h4>"+
    "<div class=\"w3-row w3-large\">"+
      "<div class=\"w3-col s6\">"+
        "<p><i class=\"fa fa-fw fa-male\"></i> Type of facility: "+ apartment.type+ "</p>"+
        "<p><i class=\"fa fa-fw fa-bath\"></i> Max people: "+ apartment.numberOfGuests+ "</p>"+
        "<p><i class=\"fa fa-fw fa-bed\"></i> Number of rooms: "+ apartment.numberOfRooms+ "</p>"+
        "<p><i class=\"fa fa-fw fa-bed\"></i> Host: " + apartment.host+ "</p>"+
      "</div>" +
      
      "<h4><strong>Location</strong></h4>"+
      
      "<div class=\"w3-col s6\">"+
      
       " <p><i class=\"fa fa-fw fa-clock-o\"></i> Address: " + apartment.location.address.street + ", "+apartment.location.address.number+ "</p>" +
        "<p><i class=\"fa fa-fw fa-clock-o\"></i> City: " +apartment.location.address.city +  "</p> " +
      "</div>"+
    "</div>"+
    "</div>"+
    "<hr>"		
    	);
			
			
			
		}
		
	});
	
	
	
	
}

function getApartmentAmenities(id){
	
	$("#apartmentAmenities").empty();
	
	$.ajax({
		
		url: "rest/apartment/getApartmentAmenities/" + id,
		type: "GET",
		contentType: "application/json",
		success: function(amenities){
	
			
			let i;
			for(i=0; i <amenities.length; i ++){
				let a = amenities[i];
				 alert(a);
				 $("#apartmentAmenities").append(
			 		"<div class=\"w3-col s6\">" +
			        "<p><i class=\"fa fa-fw fa-shower\"></i>" + a + "</p>"+
			      
			     " </div>");
			   
				
			}
			
			
		}
		
		
		
	});
	
	
}

function getApartmentComments(id){
	
	$("#apartmentComments").empty();
	
	$.ajax({
		url: "rest/comment/getApartmentComments/" + id,
		type: "GET",
		contentType: "application/json",
		success: function(comments){
			alert("USAO U SUCC COMMENTS");
			if(comments == null){
				$("#apartmentComments").append("<p>There is no recensions for this facility.</p>");
				return;
			}
			
		}
		
		
		
		
	});
	
	
	
}



