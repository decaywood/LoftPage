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

        //alert($('input#userName').value());
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

function changeVerifyingCode() {
    $('img#codeImg').attr('src', 'loginValidate.do?t=' + new Date().getTime());
}

// Credit to John Resig for this function taken from Pro JavaScript techniques 
function next(elem) {
    do {
        elem = elem.nextSibling;
    }
    while (elem && elem.nodeType !== 1);
    return elem;
}