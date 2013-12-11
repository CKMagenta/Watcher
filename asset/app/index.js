$(document).ready(function(e) {
	


// $("form.login").submit(function(e) {});

	$("button#register").on('click', function(e) {
		var userid = $("#register_userid").val();
		var password = $('#register_password').val();
		var action = 'registerParent';
		var parentName = $("#register_parent_name").val();
		var name = $("#register_name").val();
		var phonenumber = $("#register_phonenumber").val();
		var d = new Date();
		d.setMilliseconds(0);
		
		var isValid = registerValidation(userid, password, parentName, name, phonenumber);
		if(isValid == 0) {
			$.ajax({
				url : 'HandlerServlet',
				type : "POST",
				dataType : 'json',
				data : { 
					action : action, 
					userid :userid, 
					password : password,
					parentName : parentName,
					childrenName : name, 
					phonenumber : phonenumber,
					TS : (d.getTime()/1000)
				},
				success : function(json) {
					if(json.success-0) {
						registerClear();
						$('#registerModal').modal('hide');
					} else {
						registerStatus(false, json.message);
					}
				},
				error : function(json) {
					registerStatus(false, json);
				}
			});
		} else {
			switch (isValid) {
			case 1:
				notifyInputValidity("#register_userid");
				registerStatus(false, "ID 입력값을 확인해 주세요");
				break;
			case 2:
				notifyInputValidity("#register_password");
				registerStatus(false, "Password 입력값을 확인해 주세요");
				break;
			case 3 :
				notifyInputValidity("#register_parent_name");
				registerStatus(false, "본인 이름 입력값을 확인해 주세요");
				break;
			case 4 :
				notifyInputValidity("#register_name");
				registerStatus(false, "자녀 이름 입력값을 확인해 주세요");
				break;
			case 5 :
				notifyInputValidity("#register_phonenumber");
				registerStatus(false, "전화번호 입력값을 확인해 주세요");
				break;
			default :
				registerStatus(false, "입력값에 문제가 있습니다. 입력값을 확인해 주세요");
				break;
			}
			
		}
	});
});
function registerValidation(userid, password, parentName, name, phonenumber) {
	if(isTrivialString(userid))
		return 1;
	if(isTrivialString(password))
		return 2;
	if(isTrivialString(parentName))
		return 3;
	if(isTrivialString(name))
		return 4;
	if(isTrivialString(phonenumber))
		return 5;
	
	return 0;
}

function notifyInputValidity(identifier) {
	if(!identifier) return;
	
	$("#register_userid").removeClass('has-error');
	$('#register_password').removeClass('has-error');
	$("#register_parent_name").removeClass('has-error');
	$("#register_name").removeClass('has-error');
	$("#register_phonenumber").removeClass('has-error');
	
	$(identifier).addClass('has-error');
		
}

function registerClear() {
	$("#register_userid").empty();
	$('#register_password').empty();
	$("#register_parent_name").empty();
	$("#register_name").empty();
	$("#register_phonenumber").empty();
}

function isTrivialString(str) {
	if(!str) return true;
	str = str+"";
	return (str.length == 0 || str=="");
}

function registerStatus(isSuccess, msg) {
	if(isSuccess) {
		$("#registerStatus").addClass('hidden');
	} else {
		$("#registerStatus").removeClass('hidden');
		if(!msg) msg = "등록 과정 중에 문제가 발생하였습니다. 다시 시도해 주세요!";
		$("#registerStatus").text(msg);
	}
}