// Custom validation messages
// Set msg on 'data-validation-msg' input attribute
// -------------------------------------------------
$(function () {
    var inputs = $('input'),
        inputsLen = inputs.length,
        input,
        inputMsg,
        inputValidationMsg,
        label,
        button = $('button')[0],
        form = $('form')[0];

    $('div#loginComponent').animate({
        width: '300px',
        height: '300px',
        position: 'absolute',
        left: '40%',
        top: '35%'
    }, 500, function(){
        $('input#username').focus();
    });
    changeVerifyingCode();
    $('img#codeImg').click(changeVerifyingCode);

    // Let's hide the default validation msg as
    // -webkit-validation-bubble no longer works in Chrome 28+. Booooooo!
    form.addEventListener('invalid', function (e) {
        e.preventDefault();
    }, true);

    // Validate form on submit - display tooltip if input has no value
    $('button#login').click(function () {
        inputsLen = inputs.length;

        while (inputsLen--) {
            if (inputs[inputsLen].value.length > 0) {
                return true;
            }
            next(inputs[inputsLen]).nextSibling.style.display = "block";
        }
    });


    $('button#login').click(function () {

        var legalUserName = $('input#userName').css("display") != "block";
        var legalPassword = $('input#password').css("display") != "block";
        var legalVerifyingCode = $('input#validate').css("display") != "block";

        var leagal = legalUserName && legalPassword && legalVerifyingCode;
        if(leagal) {
            validate($('input#validate').val());
        }
    });

    while (inputsLen--) {
        input = inputs[inputsLen];
        label = next(input);

        if (input.hasAttribute('data-validation-msg')) {
            // Create span element for our validation msg
            inputValidationMsg = input.getAttribute('data-validation-msg');
            inputMsg = document.createElement('span');
            inputMsg.innerHTML = inputValidationMsg;

            // Add our own validation msg element so we can style it
            label.parentNode.insertBefore(inputMsg, label.nextSibling);

            input.onblur = function (e) {
                // If value does not exist or is invalid, toggle validation msg
                e.target.classList.add('blur');
                next(e.target).nextSibling.style.display = (!this.value || this.validity.valid === false) ? 'block' : 'none';
            }
        }
    }




});


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

// Credit to John Resig for this function taken from Pro JavaScript techniques 
function next(elem) {
    do {
        elem = elem.nextSibling;
    }
    while (elem && elem.nodeType !== 1);
    return elem;
}