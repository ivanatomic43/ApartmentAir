function searchUsersAdmin(){
	
	
	let username = $("#usernameAdminSearch").val();
	let gender = $("#gender option:selected").val();
	let role = $("#role option:selected").val();
	
	
	
	
	let data = { "username": username,
				"gender": gender,
				"role": role
				
			
			
				};
	
	let u = JSON.stringify(data);
	
	
	$.ajax({
		url: "rest/search/searchUsersAdmin",
		type: "POST",
		data: u,
		contentType: "application/json",
		success: function(users){
			
			if(users == null){
				alert("There is no match!");
				return;
			}
			
			
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
			
			
			
		}
		
		
	});
	
	
}

function cancelSearch(){
	
	
    document.getElementById("usernameAdminSearch").value = "";
    $('#gender option:first').prop('selected',true);
    $('#role option:first').prop('selected',true);
    getAllUsers();
	
}
