// Custom validation messages
// Set msg on 'data-validation-msg' input attribute
// -------------------------------------------------
$(function () {

    changeVerifyingCode();
    $('img#codeImg').click(changeVerifyingCode);

    $('button#register').click(function () {
        document.location = "register.do";
    });

    $('button#login').click(function () {
        var userName = $('input#userName');
        var password = $('input#password');
        var legalVerifyingCode = $('input#validate');

        var userNamePattern = /^[a-zA-Z0-9-_\.]{1,20}$/;
        var passwordPattern = /^[a-zA-Z0-9-_\.]{1,20}$/;

        var leagalUserName = userNamePattern.test(userName.val());
        var leagalPassword = passwordPattern.test(password.val());
        if(!leagalUserName) {
            smoke.signal(userName.attr('data-validation-msg'), function (e) {}, { duration:3000});
        }
        if (!leagalPassword) {
            smoke.signal(password.attr('data-validation-msg'), function (e) {}, { duration:3000});
        }

        if(leagalUserName && leagalPassword){
            validate($(legalVerifyingCode).val());
        }
    });



});

function register() {
    $.ajax({
        url:'register.do',
        async:false,
        cache:false,
        type:'POST',
        dataType:'json',
        success: function (errorInfo) {
            if(errorInfo == ""){
                var userName = $('input#userName').val();
                var password = $('input#password').val();
                auth(userName, password);
            }
            else {
                smoke.signal(errorInfo, function (e) {}, { duration:3000});
            }
        }

    });
}

function validate(validateCode) {

    $.ajax({
        url:'validate.do',
        data:{
            validateCode:validateCode
        },
        async:false,
        cache:false,
        type:'POST',
        dataType:'json',
        success: function (errorInfo) {
            if(errorInfo == ""){
                var userName = $('input#userName').val();
                var password = $('input#password').val();
                auth(userName, password);
            }
            else {
                smoke.signal(errorInfo, function (e) {}, { duration:3000});
            }
        }

    });
}

function auth(userName, password) {

    $.ajax({
        url:'authenticate.do',
        data:{
            userName:userName,
            password:password
        },
        async:false,
        cache:false,
        type:'POST',
        dataType:'json',
        success: function (errorInfo) {
            if(errorInfo == ""){
                $('form#loginForm').submit();
            }
            else {
                smoke.signal(errorInfo, function (e) {}, { duration:3000});
            }
        }

    });
}

function changeVerifyingCode() {
    $('img#codeImg').attr('src', 'validateCode.do?t=' + new Date().getTime());
}

