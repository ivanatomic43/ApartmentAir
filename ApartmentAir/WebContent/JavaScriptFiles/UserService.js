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
			
			$("#userProfileDiv2").append("<div class=\"row gutters-sm\"  style=\"background-color: #F2F0DD\">" +
      "<div class=\"col-md-4 mb-3\">" +
        "<div class=\"card-for-profile\">" +
          "<div class=\"card-body-for-profile\"> " +
            "<div class=\"d-flex flex-column align-items-center text-center\">"+
              "<img src=\"https://c8.alamy.com/comp/T98NFR/avatar-user-basic-abstract-circle-background-flat-color-icon-T98NFR.jpg\" alt=\"Admin\" class=\"rounded-circle\" width=\"150\">" +
              "<div class=\"mt-3\">" +
                "<h4>" + user.name + "</h4>" +
                "<p class=\"text-secondary mb-1\">" + user.surname + "</p>" +
                
               "<button class=\"btn btn-primary\" (click)=\"editProfile()\">Edit profile</button>" +

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