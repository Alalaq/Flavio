// @author Khabibullin Alisher
const params = new URLSearchParams(window.location.search);
const amount = params.get('amount');
if (amount) {
    const amountInput = document.createElement('input');
    amountInput.type = 'hidden';
    amountInput.name = 'amount';
    amountInput.value = amount;
    document.querySelector('form').appendChild(amountInput);
}

document.getElementById('cvv').type = 'password';