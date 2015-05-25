// Custom validation messages
// Set msg on 'data-validation-msg' input attribute
// -------------------------------------------------
$(function () {
  var inputs = document.getElementsByTagName('input'),
      inputsLen = inputs.length,
      input,
      inputMsg,
      inputValidationMsg,
      label,
      button = document.getElementsByTagName('button')[0],
      form = document.getElementsByTagName('form')[0];

  // Let's hide the default validation msg as
  // -webkit-validation-bubble no longer works in Chrome 28+. Booooooo!
  form.addEventListener('invalid', function (e) {
    e.preventDefault();
  }, true);

  // Validate form on submit - display tooltip if input has no value
  button.onclick = function () {
    inputsLen = inputs.length;
    
    while (inputsLen--) {
      if (inputs[inputsLen].value.length > 0) {
        return true;
      }     
    next(inputs[inputsLen]).nextSibling.style.display = "block";
    }
  };
  
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
  
// Credit to John Resig for this function taken from Pro JavaScript techniques 
function next(elem) {
  do {
    elem = elem.nextSibling;
  }
  while (elem && elem.nodeType !== 1);
  return elem;        
}