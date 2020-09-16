var image="";

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
	let location=$("#newAppLocation")[0].value;
	let price=$("#newAppPricePerNight")[0].value;
	let numberOfGuests = $("#newAppNumberOfGuests")[0].value;
	let numberOfRooms = $("#newAppNumberOfRooms")[0].value;
	
	let d = new Date($('#dateForRent').val());
	let day=d.getDate();
	let month=d.getMonth()+1;
	let year=d.getFullYear();
	let dateForRent=day+"."+month+"."+year+".";
	
	let tv='';
	let airConditioner='';
	let parkingLot='';
	let kitchen='';
	let wifi=''
		
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
	
	let data = {
			
			"type": type,
			"status": status,
			"location": location,
			"pricePerNight": pricePerNight,
			"numberOfGuests": numberOfGuests,
			"numberOfRooms": numberOfRooms,
			"dateForRent": dateForRent,
			"amenities": amenities
			
			
	};
	
	
	//ajax
	if(validation){
		
		let app = JSON.stringify(data);
		
		$.ajax({
			url:"rest/apartment/createApartment",
			type: "POST",
			data:app,
			contentType: "application/json",
			success: function(){
				alert("New apartment created!");
				
				
			},
			
		});
		
		
		
		
	} else
		{
		alert("Some fields are empty!");
	}
	
	
}