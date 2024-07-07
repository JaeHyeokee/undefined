function completeRegistration() {
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const phone = document.getElementById('phone').value;

    if (nickname && password && confirmPassword && phone && password === confirmPassword) {
        window.location.href = '/Home';
    } else {
        alert('모든 입력 필드를 채우고 비밀번호를 확인해주세요.');
    }
}

// Add event listener to change border color back to normal on focus
document.getElementById('verification-code').addEventListener('focus', function() {
    this.classList.remove('red-border');
    this.style.borderColor = 'black';
});

function submitEmailForm() {
    document.getElementById("email-form").action = "/user/validate-email";
    document.getElementById("email-form").submit();
}

function submitRegistrationForm() {
    document.getElementById("registration-form").action = "/user/register";
    document.getElementById("registration-form").submit();
}