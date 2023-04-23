const form = document.querySelector('#signup-form');
const emailInput = document.querySelector('#email');
const passwordInput = document.querySelector('#password');

form.addEventListener('submit', (e) => {
    e.preventDefault();
    const emailValue = emailInput.value.trim();
    const passwordValue = passwordInput.value.trim();

    if(emailValue === '' || passwordValue === '') {
        alert('Please enter valid values for both fields');
    } else {
        alert(`Success! Email: ${emailValue}, Password: ${passwordValue}`);
        form.reset();
    }
});