function getMyUser(){
	
	$("#userProfileDiv2").empty();
	
	$.ajax({
		url: "rest/auth/getLogged",
		type: "GET",
		contentType: "application/json",
		success: function(user){
			$("#userProfileDiv").show();
			$("#userProfileDiv2").show();
			$("#listOfApartments").hide();
			$("#addAmenityForm").hide();
			$("#allAmenitiesDiv").hide();
			$("#listOfApartmentsAdmin").hide();
			$("#listOfApartmentsHost").hide();
			
			$("#userProfileDiv2").append("<div class=\"row gutters-sm\"  style=\"background-color: #F2F0DD\">" +
      "<div class=\"col-md-4 mb-3\">" +
        "<div class=\"card-for-profile\">" +
          "<div class=\"card-body-for-profile\"> " +
            "<div class=\"d-flex flex-column align-items-center text-center\">"+
              "<img src=\"https://c8.alamy.com/comp/T98NFR/avatar-user-basic-abstract-circle-background-flat-color-icon-T98NFR.jpg\" alt=\"Admin\" class=\"rounded-circle\" width=\"150\">" +
              "<div class=\"mt-3\">" +
                "<h4>" + user.name + "</h4>" +
                "<p class=\"text-secondary mb-1\">" + user.surname + "</p>" +
                
               "<button class=\"btn btn-primary\" onclick=\"editUserClick('"+user.id+","+user.name+","+user.surname+","+user.username+","+user.password+","+user.gender+"')\">Edit profile</button>" +

             " </div>" +
            "</div>"+
          "</div>" +
        "</div>"+

      "</div>" +
     " <div class=\"col-md-8\">" +
        "<div class=\"card-for-profile mb-3\">" +
         " <div class=\"card-body-for-profile\">" +
            "<div class=\"row\">"+
             " <div class=\"col-sm-3\">" +
                "<h6 class=\"mb-0\">FIRST NAME:</h6>"+
              "</div>"+
              "<div class=\"col-sm-9 text-secondary\">" +
                user.name +
              "</div>" +
            "</div>"+
            "<hr>" +
            "<div class=\"row\">" +
              "<div class=\"col-sm-3\">" +
                "<h6 class=\"mb-0\">LAST NAME:</h6>" +
             "  </div>" +
              "<div class=\"col-sm-9 text-secondary\">" +
                user.surname +
             " </div>" +
            "</div>"+
            "<hr>" +
            "<div class=\"row\">" +
              "<div class=\"col-sm-3\">" +
                "<h6 class=\"mb-0\">USERNAME</h6>"+
              "</div>" +
              "<div class=\"col-sm-9 text-secondary\">" +
                user.username +
             "</div>"+
            "</div>"+
            "<hr>"+
            "<div class=\"row\">" +
              "<div class=\"col-sm-3\">"+
                "<h6 class=\"mb-0\">PASSWORD</h6>"+ 
              "</div>"+
              "<div class=\"col-sm-9 text-secondary\">" +
                user.password +
              "</div>"+
            "</div>"+
            "<hr>"+
            "<div class=\"row\">" +
              "<div class=\"col-sm-3\">"+
                "<h6 class=\"mb-0\">GENDER</h6>"+
              "</div>"+
              "<div class=\"col-sm-9 text-secondary\">"+
                user.gender +
              "</div>"+
            "</div>"+
            "<hr>"+
            "<div class=\"row\">"+
              "<div class=\"col-sm-3\">"+
                "<h6 class=\"mb-0\">ROLE</h6>"+
              "</div>"+
              "<div class=\"col-sm-9 text-secondary\">"+
                user.role +
              "</div>"+
            "</div>"+
            "<hr>"+
        "</div>"+
        "</div>"+

      "</div>"+
		"</div>");
		}
		
		
	});
	
}


function editUserClick(data){

	$("#userProfileDiv").hide();
	fillEditUserForm(data);
	$("#editUserForm").show();
}

function fillEditUserForm(data){
	
	let info = data.split(",");
	userId = info[0];
	$("#editUserFirstName").val(info[1]);
	$("#editUserLastName").val(info[2]);
	editUserUsername = info[3];
	$("#editUserPassword").val(info[4]);
	editUserGender=info[5];
	
	
	
}

function editUser(){
	
	event.preventDefault();
	
	let id= userId;
	let username=editUserUsername;
	let name = $("#editUserFirstName").val();
	let surname= $("#editUserLastName").val();
	let password = $("#editUserPassword").val();
	let conpassword = $("#editUserConPassword").val();
	let gender= editUserGender;
	
	
	
	if(password == "" && conpassword == ""){
		alert("Enter password...");
		
		return;
	}else{
		if(password !== conpassword){
			alert("Passwords doesn't match!");
			return;
		}
	}
	
      let data = {
    		  
    		 "id": id,
    		 "username": username,
    		 "name": name,
    		 "surname": surname,
    		 "password": password,
    		 "gender": gender
    		  
      };
	
      let u= JSON.stringify(data);
	
	$.ajax({
		
		url:"rest/users/editUserProfile",
		type: "PUT",
		data: u,
		contentType: "application/json",
		success: function(user){
		  	
			$("#editUserForm").hide();
			//getMyUser();
			window.location.replace("index.html");	
			
		}
		
		
	});
	
	
	
	
	
}

function showUsersAdmin(){
	
	$("#listOfApartments").hide();
	$("#addAmenityForm").hide();
	$("#editAmenityForm").hide();
	$("#allAmenitiesDiv").hide();
	$("#allAmenitiesTable").hide();
	$("#newApartmentForm").hide();
	$("#editUserForm").hide();
	$("#userProfileDiv").hide();
	$("#listOfApartmentsAdmin").hide();
	 getAllUsers();
	
	
	
	
}

function getAllUsers(){
	
	  $.ajax({
		    type: "GET",
		    url: "rest/users/getAllUsers",
		    contentType: "application/json",
		    success: function(users){
		    	
		    	$("#allUsersDiv").show();
		    	let i;
				$('#allUsersTable tbody').empty();
				
				for(i=0; i< users.length; i++){
					let u= users[i];
					let rbr = i +1;
					
					
				$('#allUsersTable tbody').append("<tr><th scope=\"row\">"+u.id+"</th>"+
						"<td>"+u.name+"</td>"+ "<td>"+u.surname+"</td>"+ "<td>"+u.username+"</td>"+ "<td>"+u.gender+"</td>"+ "<td>"+u.role+"</td>"+
						"<td><button type=\"button\" onclick=\"changeRole('"+u.id+","+u.role+
						"')\" class=\"btn btn-primary \">Change Role</button>" +
						"<button type=\"button\" onclick=\"removeUser('"+u.id+"')\" class=\"btn btn-primary\">Remove</button></td></tr>"		
						);
				}
				
		    
		    },
		    error: function () {
		      console.log("100% TE ZAJEBAVA NEKI #ID");
		    }
		  });
	
	
}

function changeRole(data){
	
	var info = data.split(",");
	var id = info[0];
	var role = info[1];
	

	$.ajax({
		url: "rest/users/changeRole/" + id,
		type: "PUT",
		contentType: "application/json",
		success: function(){
			alert("Role changed!");
			getAllUsers();
		}
		
		
	});
	
}

