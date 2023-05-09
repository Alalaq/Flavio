document.addEventListener('DOMContentLoaded', function () {
    const API_KEY = document.querySelector('#api-key').value;
    // Create a Stripe client.
    const stripe = Stripe(API_KEY);

    // Create an instance of Elements.
    const elements = stripe.elements();

    // Create an instance of the card Element.
    const card = elements.create('card');

    // Add an instance of the card Element into the `card-element` <div>.
    card.mount('#card-element');

    // Handle real-time validation errors from the card Element.
    card.addEventListener('change', function (event) {
        const displayError = document.getElementById('card-errors');
        if (event.error) {
            displayError.textContent = event.error.message;
        } else {
            displayError.textContent = '';
        }
    });

    // Handle form submission.
    const form = document.getElementById('payment-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        // handle payment
        handlePayments();
    });

    //handle card submission
    function handlePayments() {
        stripe.createToken(card).then(function (result) {
            if (result.error) {
                // Inform the user if there was an error.
                const errorElement = document.getElementById('card-errors');
                errorElement.textContent = result.error.message;
            } else {
                // Send the token to your server.
                const token = result.token.id;
                const email = document.getElementById('email').value;

                const data = `email=${encodeURIComponent(email)}&token=${encodeURIComponent(token)}`;

                fetch('/process-payment', {
                    credentials: "include",
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: data
                })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = '/payment-success';
                        } else {
                            alert('An error occurred while trying to create a charge.');
                        }
                    })
                    .catch(error => console.error(error));
            }
        });
    }
});
