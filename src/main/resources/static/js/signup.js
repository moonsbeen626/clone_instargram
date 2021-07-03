const phoneInput = document.getElementById('phone');
const pwInput = document.getElementById('password');
const emailInput = document.getElementById('email');
const nameInput = document.getElementById('name');
const signupBtn = document.getElementById('btn_signup');
const loginInput = document.getElementsByClassName('container')[0];

function emailCheck() {
    var hasAt = emailInput.value.indexOf('@');
    return hasAt !== -1 ? true : false;
}

function pwCheck() {
    return pwInput.value.length >= 5 ? true : false;
}

function phoneCheck() {
    return phoneInput.value.length >= 5 ? true : false;
}

function nameCheck() {
    return nameInput.value.length >= 1 ? true : false;
}

loginInput.addEventListener('keyup', function(event) {
    const completedInput = (emailCheck() && pwCheck() && phoneCheck() && nameCheck());
    signupBtn.disabled = completedInput ? false : true;
})

