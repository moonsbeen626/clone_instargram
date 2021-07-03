const idInput = document.getElementById('username');
const pwInput = document.getElementById('password');
const loginInput = document.getElementsByClassName('container')[0];
const loginBtn = document.getElementById('btn_login');

function idCheck() {
    var hasAt = idInput.value.indexOf('@');
    return hasAt !== -1 ? true : false;
}

function pwCheck() {
    return pwInput.value.length >= 5 ? true : false;
}

loginInput.addEventListener('keyup', function(event) {
    const completedInput = (idCheck() && pwCheck());
    loginBtn.disabled = completedInput ? false : true;
})

