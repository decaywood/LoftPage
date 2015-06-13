// Custom validation messages
// Set msg on 'data-validation-msg' input attribute
// -------------------------------------------------
$(function () {

    $('button#commitButton').click(function () {
        var userName = $('input#userName');
        var password = $('input#password');
        var nickName = $('input#userNickName');
        var email = $('input#userEmail');
        var phone = $('input#userPhone');

        var userNamePattern = /^[a-zA-Z0-9-_\.]{1,20}$/;
        var passwordPattern = /^[a-zA-Z0-9-_\.]{1,20}$/;
        var nickNamePattern = /^[a-zA-Z0-9-_\.]{1,20}$/;
        var emailPattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        var phonePattern = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;

        var leagalUserName = userNamePattern.test(userName.val());
        var leagalPassword = passwordPattern.test(password.val());
        var leagalNickName = nickNamePattern.test(nickName.val());
        var leagalEmail = emailPattern.test(email.val());
        var leagalphone = phonePattern.test(phone.val());


        if(!leagalUserName) {
            smoke.signal(userName.attr('data-validation-msg'), function (e) {}, { duration:3000});
        }
        if (!leagalPassword) {
            smoke.signal(password.attr('data-validation-msg'), function (e) {}, { duration:3000});
        }
        if (!leagalNickName) {
            smoke.signal(nickName.attr('data-validation-msg'), function (e) {}, { duration:3000})
        }
        if(!leagalEmail) {
            smoke.signal(email.attr('data-validation-msg'), function (e) {}, { duration:3000})
        }
        if(!leagalphone) {
            smoke.signal(phone.attr('data-validation-msg'), function (e) {}, { duration:3000})
        }

        //if(leagalUserName && leagalPassword && leagalNickName && leagalEmail && leagalphone){
            register(userName.val(), password.val(), nickName.val(), email.val(), phone.val());
        //}
    });



});

function register(userName, password, nickName, email, phone) {
    alert(userName + " " +password + " " +nickName + " " +email + " " +phone);
    $.ajax({
        url:'saveUser.do',
        data:{
            userName:userName,
            password:password,
            nickName:nickName,
            email:email,
            phone:phone
        },
        async:false,
        cache:false,
        type:'POST',
        dataType:'json',
        success: function (errorInfo) {
            if(errorInfo == ""){
                document.location = "mainPage.do";
            }
            else {
                smoke.signal(errorInfo, function (e) {}, { duration:3000});
            }
        }

    });
}

