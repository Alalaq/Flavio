window.onload = function() {
    updateCartTotal();
}

function updateCartTotal() {
    var cartItems = document.getElementsByClassName('cart-item');
    var total = 0;
    for (var i = 0; i < cartItems.length; i++) {
        var priceElement = cartItems[i].getElementsByClassName('cart-item-price')[0];
        var quantityElement = cartItems[i].getElementsByClassName('cart-item-quantity')[0];
        var price = parseFloat(priceElement.innerText);
        var quantity = parseInt(quantityElement.innerText);
        total += price * quantity;
    }
    document.getElementById('cart-total-price').innerText = total.toFixed(2) + '₽';
}

function placeOrder() {
    Cookies.set("hasOrder", true);
    window.location.href = "/process-payment";
}
