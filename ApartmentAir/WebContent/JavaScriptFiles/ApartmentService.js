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
			console.log(amenities);
			for(let amenity of amenities){
				addAmenity(amenity)
			}
		},
		error:function(){
			console.log('error getting amenities');
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
	
	$("#checkboxesSearch").append(
			"<div class=\"form-check\">" +
					"<input type=\"checkbox\" class=\"form-check-input\" id=\""+amenity.id+"\" name=\"amenities\" value=\""+amenity.name+"\">"+
					"<label class=\"form-check-label\" for=\""+amenity.id+"\">"+ amenity.name + "</label>" +
			"</div>"
			);
	$("#checkboxesEdit").append(
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
	
	
	document.getElementById("newAppPricePerNight").value = "";
	document.getElementById("newAppStreet").value = "";
	document.getElementById("newAppStreetNumber").value = "";
	document.getElementById("newAppCity").value = "";
	document.getElementById("newAppPostalNumber").value = "";
	document.getElementById("newAppNumberOfGuests").value= "0";
	document.getElementById("newAppNumberOfRooms").value= "0";
	document.getElementById("newAppDateFrom").value= "";
	document.getElementById("newAppDateTo").value= "";
	document.getElementById("newAppImage").value= "";
	document.getElementById("latitude").value= "";
	document.getElementById("longitude").value= "";
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
	
	event.preventDefault();
	
	//let validation = true;
	//basic
	let type='';
	let price=$("#newAppPricePerNight")[0].value;
	let numberOfGuests = $("#newAppNumberOfGuests")[0].value;
	let numberOfRooms = $("#newAppNumberOfRooms")[0].value;
	
	//dates
	let startDate=$('#newAppDateFrom').val();
	let endDate= $("#newAppDateTo").val();
	
	
	
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
			success: function(apartment){
			
				//alert(newApartment);
				if(apartment != null){
				alert("New apartment created!");
				$("#newApartmentForm").hide();
				getAllInactiveApartments();
				
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
        "<p>Rating: "+ap.rating+"</p>"+
        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
        "<button onclick=\"showApartmentDetails('"+ ap.id +"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See detalis</button>"+
        "<button onclick=\"editApartmentClick('"+ap.id+","+ap.type+","+ap.pricePerNight+","+ap.numberOfGuests+","+ap.numberOfRooms+","+ap.amenities+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Edit facility</button>"+
        "<button onclick=\"deleteApartment('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">Delete facility</button>"+
     " </div>" +
    "</div>"
			);	
				
				
			}
			
			
			
		}
	});
	
	
	
	
}

function editApartmentClick(data){

	$("#listOfApartments").hide();
	$("#listOfApartmentsAdmin").hide();
	$("#listOfApartmentsHost").hide();
	$("#editApartmentForm").show();
	
	fillEditApartmentForm(data);
	
}

function fillEditApartmentForm(data){
	
	let info = data.split(",");
	apartmentID = info[0];
	apartmentType = info[1];
	alert(apartmentType);
	$("#editApartmentPricePerNight").val(info[2]);
	$("#editApartmentNumberOfGuests").val(info[3]);
	$("#editApartmentNumberOfRooms").val(info[4]);
	
	

	
	if(apartmentType === "Apartment"){
		$('input[name=apartmentType]').prop('checked', true);
	}
	 if(apartmentType ==="Room"){
		 $('input[name=roomType]').prop('checked', true);
	 }
	
	 
	 
	
}
function editApartment(){
	
	event.preventDefault();
	
	let id= apartmentID;
	let type = "";
	let editPricePerNight = $("#editApartmentPricePerNight").val();
	let editNumberOfGuests= $("#editApartmentNumberOfGuests").val();
	let editNumberOfRooms = $("#editApartmentNumberOfRooms").val();

	
	if(editPricePerNight == "" || editNumberOfGuests == "0" || editNumberOfRooms =="0"){
		alert("Please fill the form!");
		return;
	}
	
	
	if (document.getElementById("apartmentEdit").checked) {
		  type = document.getElementById("apartmentEdit").value;
		}
	else if (document.getElementById("roomEdit").checked) {
		  type = document.getElementById("roomEdit").value;
		}
	else {
		
		alert("Please select type!");
		
		return;
	}
	
   let data= {
		  
		   "id": id,
		"type"  : type,
		"numberOfRooms": editNumberOfRooms,
		"numberOfGuests": editNumberOfGuests,
		"pricePerNight": editPricePerNight
		
   };
	
      let u= JSON.stringify(data);
	alert(u);
	$.ajax({
		
		url:"rest/apartment/editApartment",
		type: "PUT",
		data: u,
		contentType: "application/json",
		success: function(){
		  	
			
			$("#editApartmentForm").hide();
			
			var user = JSON.parse(localStorage.getItem('user'));
			
			if(user.role ==="ADMIN"){
				$("#listOfApartmentsAdmin").show();
				getAllApartmentsAdmin();
			} else if(user.role ==="HOST"){
				$("#listOfApartmentsHost").show();
				getAllActiveApartmentsHost();
			}
			
			
		}
		
		
	});
	
	
	
	
	
}

function showApartmentDetails(data){
	
	var id = data;
	document.getElementById("reservationMessage").value = "";
	document.getElementById("reservationNights").value = "1";
	
	
	$("#apartmentDetails").show();
	$("#listOfApartments").hide();
	$("#listOfApartmentsHost").hide();
	$("#listOfApartmentsAdmin").hide();
	$("#apartmentAmenities").show();
	$("#allCommentsHost").hide();
	$("#allCommentsAdmin").hide();
	$("#listOfUsersHost").hide();
	getApartmentDetails(id);
	getApartmentAmenities(id);
	getApartmentComments(id);
	getApartmentDates(id);
}


function backToList(){
	
	$("#apartmentDetails").hide();
	
	var user = JSON.parse(localStorage.getItem('user'));
	
	if(user === null || user.role === "GUEST" ){
		
		$("#listOfApartments").show();
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
	        "<p>Rating: "+ap.rating+"</p>"+
	        "<p class=\"w3-large\"><i class=\"fa fa-bath\"></i> <i class=\"fa fa-phone\"></i> <i class=\"fa fa-wifi\"></i></p>"+
	        "<button onclick=\"showApartmentDetails('"+ap.id+"')\" class=\"w3-button w3-block w3-black w3-margin-bottom\">See Details</button>"+
	     " </div>" +
	    "</div>"
				);	
					
					
				}
				
				
			}
			
		});
		
	} else if (user.role === "HOST"){
		
		$("#listOfApartmentsHost").show();
		showActiveApartments();
		
	} else if (user.role === "ADMIN") {
		
		$("#listOfApartmentsAdmin").show();
		getAllApartmentsAdmin();
		
	}
	
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
					"<button style=\"margin-top:25px;\" class=\"w3-button w3-block w3-black\" onclick=\"backToList()\">Back to list</button>" +
					"<div class=\"w3-display-container mySlides\"> " +
    "<img src=\""+apartment.image+"\" style=\"width:100%;margin-bottom:-6px\">" +
      "<div class=\"w3-display-bottomleft w3-container w3-black\">"+
        "<p>Photo of the facility</p>" +
      "</div>" +
    "</div>"+
    
  
    "</div>" +
  

  "<div class=\"w3-container\" id =\"apartmentInfo\">"+
    "<h4><strong>Basic information</strong></h4>"+
    "<div class=\"w3-row w3-large\">"+
      "<div class=\"w3-col s6\">"+
        "<p><i class=\"fa fa-fw fa-male\"></i> Type of facility: "+ apartment.type+ "</p>"+
        "<p><i class=\"fa fa-fw fa-bath\"></i> Max people: "+ apartment.numberOfGuests+ "</p>"+
        "<p><i class=\"fa fa-fw fa-bed\"></i> Number of rooms: "+ apartment.numberOfRooms+ "</p>"+
        "<p><i class=\"fa fa-fw fa-bed\"></i> Price per night: "+ apartment.pricePerNight+ "$</p>"+
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
			
			
			$("form#makeReservation").submit(function(event){
				 
				 var user = JSON.parse(localStorage.getItem('user'));
					if(user == null){
						alert("Only guests can make a reservation! Please, log in or register...");
						return;
					}
					event.preventDefault();
					
					let apartmentID = apartment.id;
					alert(apartment.id);
					
					var startDate = $('#daterange').data('daterangepicker').startDate;
					var endDate = moment($('#daterange').data('daterangepicker').endDate).toDate();
					
					var date = new Date(startDate);
					
					let numberOfNightss = $('#reservationNights').val();
					let numberOfNights = parseInt(numberOfNightss);
					let price = apartment.pricePerNight;
					let message = $('#reservationMessage').val();
					let guestID = user.id;
					let hostUsername = apartment.host;
					let guestUsername = user.username;
					if(startDate == '' || endDate == ''){
						alert('Pick a date!');
						return;
					}
					
					
					let data = {
							"apartmentID": apartmentID,
							"dateOfReservation": date,
							"numberOfNights": numberOfNights,
							"totalPrice": price,
							"message": message,
							"guestID": guestID,
							"hostUsername": hostUsername,
							"guestUsername": guestUsername
					};
					
					let r = JSON.stringify(data);
					
					alert(r);
					$.ajax({
						url: "rest/reservation/createNewReservation",
						type: "POST",
						contentType: "application/json",
						data: r,
						success: function(reservation){
							
							showMyReservations();
						},
						error: function(error){
							alert("Pick a valid date/ Date is not available!")
							console.log("Error...");
						}
						
					});
				 
				 
				 
				 
				 
				 
				 
			 });
			
			
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
				
				 $("#apartmentAmenities").append(
			 		"<div class=\"w3-col s6\">" +
			        "<p><i class=\"fa fa-fw fa-shower\"></i>" + a + "</p>"+
			      
			     " </div>");
			   
				
			}
			
			
		}
		
		
		
	});
	
	
}

function getApartmentComments(id){
	
	
	
	$.ajax({
		url: "rest/comment/getApartmentComments/" + id,
		type: "GET",
		contentType: "application/json",
		success: function(comments){
			$("#apartmentComments").empty();
			if(comments == null){
				$("#apartmentComments").append("<p>There is no recensions for this facility.</p>");
				return;
			}
			
			let i ;
			for(i=0; i <comments.length; i ++){
				let c= comments[i];
				
				
				
			$("#apartmentComments").append(
					"<div class=\"w3-row\">" +
							"<div class=\"w3-col m2 text-center\">" +
									"<img class=\"w3-circle\" src=\"images/avatar.jpg\" style=\"width:96px;height:96px\">" +
							"</div>" +
							"<div class=\"w3-col m10 w3-container\">" +
								"<h4><b>"+c.guestName+" "+c.guestSurname+"</b><span class=\"w3-opacity w3-medium\">  Rate: "+c.rating+"</span></h4>"+
								"<p>" +c.text+"</p><br>" +
							"</div>" +
						"</div>");
				}
			}
		
		
		
		
	});
	
	
	
}

function getApartmentDates(id){
	
	$("#apartmentDates").empty();
	let startDate='';
	let endDate='';
	
	$.ajax({
		url: "rest/apartment/getApartmentDates/"+ id,
		type: "GET",
		contentType: "application/json",
		success: function(dates){
			
			let firstDate= dates[0];
			let lastDate = dates[dates.length-1];
			
			var date1 = new Date(firstDate); 
			var date2 = new Date(lastDate);
			
			let date3 = moment(date1, date1.toString()).format("YY/MM/DD")
			let date4 = moment(date2, date2.toString()).format("YY/MM/DD")
			
			$("#apartmentDates").append("<div class=\"w3-col s6\">" +
			        "<p><i class=\"fa fa-fw fa-shower\"></i>From: " + date3 + "</p>"+
			        "<p><i class=\"fa fa-fw fa-shower\"></i>To: " + date4 + "</p>"+ 
				     " </div>");
			
			fillTheDatepicker(date3, date4, date1, date2);
			
		}
		
		
	});

	
	
	
	
}

function fillTheDatepicker(date3, date4, date1, date2){
	
	$('input[name="daterange"]').daterangepicker({
	    opens: 'left',
	    startDate: date3,
	    endDate: date4,
	    locale: {
	      format: 'YYYY/MM/DD'
	    }
	  }, function(start, end, label) {
		  
		 // alert(start);
		  //alert(date1);
		 // alert(end);
		 // alert(date2);
		
			  startDate = start.format('YYYY-MM-DD');
			  endDate = end.format('YYYY-MM-DD');
			  console.log("A new date selection was made: " + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD')); 
		
		  
	  });
	
	
}




