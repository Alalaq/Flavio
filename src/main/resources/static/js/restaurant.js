// @author Khabibullin Alisher
const restaurantName = document.getElementById("restaurant-name").textContent;

async function getRestaurant() {
    const response = await fetch('/restaurant/' + restaurantName, {
        credentials: "include"
    });
    return await response.json();
}

async function main() {
    const restaurant = await getRestaurant();

    const cuisines = [];

    Object.keys(restaurant.cuisineRatings).forEach(key => {
        cuisines.push(key);
    });

    document.getElementById("restaurant-image").src = `/static/images/${restaurant.id}.jpg`;
    document.getElementById("restaurant-name-h2").textContent = restaurant.name;
    document.getElementById("restaurant-description").textContent = restaurant.description;
    document.getElementById("restaurant-cuisine").textContent = cuisines;
    document.getElementById("restaurant-price").textContent = restaurant.price;
    document.getElementById("restaurant-rating").textContent = '★ ' + restaurant.generalRating;
    const restaurantDistanceButtonTime = document.getElementById("restaurant-distance-button-time");
    const restaurantDeliveryTime = document.getElementById("restaurant-delivery-time");

    const timeNode = document.createTextNode('🕒' + restaurantDeliveryTime.textContent);
    restaurantDistanceButtonTime.textContent = '';
    restaurantDistanceButtonTime.appendChild(timeNode);
    document.getElementById("restaurant-address").textContent = restaurant.address;

    const cuisinesMenu = document.getElementById('cuisines-menu');
    const dishesContainer = document.getElementById('dishes-container');

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const dishesByCuisine = {};
                const dishes = JSON.parse(xhr.responseText);
                dishes.forEach(function (dish) {
                    const cuisine = dish.cuisine;
                    if (!dishesByCuisine[cuisine]) {
                        dishesByCuisine[cuisine] = [];
                    }
                    dishesByCuisine[cuisine].push(dish);
                });
                if (Object.keys(dishesByCuisine).length === 0) {
                    const noDishesMessage = document.createElement('p');
                    noDishesMessage.textContent = 'No dishes available';
                    dishesContainer.appendChild(noDishesMessage);
                } else {
                    Object.keys(dishesByCuisine).forEach(function (cuisine) {
                        const cuisineContainer = document.createElement('div');
                        cuisineContainer.classList.add('cuisine-category');

                        const cuisineAnchor = document.createElement('a');
                        cuisineAnchor.id = cuisine;
                        cuisineAnchor.name = cuisine;
                        dishesContainer.appendChild(cuisineAnchor);

                        const cuisineHeading = document.createElement('h2');
                        cuisineHeading.textContent = cuisine;
                        cuisineHeading.classList.add('cuisine-category-h2');
                        dishesContainer.appendChild(cuisineHeading);

                        const dishesList = document.createElement('ul');
                        dishesList.classList.add('dishes-card-list')
                        dishesByCuisine[cuisine].forEach(function (dish) {
                            const dishItem = document.createElement('li');
                            dishItem.classList.add('dishes-card-list-item');

                            const dishCard = document.createElement('div');
                            dishCard.classList.add('dishes-card');

                            const dishName = document.createElement('h3');
                            dishName.textContent = dish.name;

                            const dishImage = document.createElement('img');
                            dishImage.classList.add('dish-image');
                            dishImage.src = dish.imageUrl;
                            dishImage.alt = dish.name;

                            const dishPrice = document.createElement('p');
                            dishPrice.style.fontSize = '25px';
                            dishPrice.textContent = `${dish.price} ₽`;

                            const dishDescription = document.createElement('p');
                            dishDescription.style.fontSize = '13px';
                            dishDescription.textContent = dish.description;

                            const addToCartButton = document.createElement('button');
                            addToCartButton.classList.add('add-to-cart-button');
                            addToCartButton.textContent = 'Add to cart';

                            dishCard.appendChild(dishName);
                            dishCard.appendChild(dishImage);
                            dishCard.appendChild(dishPrice);
                            dishCard.appendChild(dishDescription);
                            dishCard.appendChild(addToCartButton);

                            dishItem.appendChild(dishCard);
                            dishesList.appendChild(dishItem);
                        });

                        cuisineContainer.appendChild(dishesList);
                        dishesContainer.appendChild(cuisineContainer);
                        const cuisineLink = document.createElement('li');
                        const cuisineLinkAnchor = document.createElement('a');
                        cuisineLinkAnchor.textContent = cuisine;
                        cuisineLinkAnchor.href = `#${cuisine}`;
                        cuisineLink.appendChild(cuisineLinkAnchor);
                        cuisinesMenu.appendChild(cuisineLink);
                    });
                }
            } else {
                console.error('Failed to fetch dishes');
            }
        }
    };
    xhr.open('GET', '/dishes/' + restaurant.id);
    xhr.send();

}

main()


function toggleForm() {
    const form = document.getElementById("restaurant-form");
    if (form.style.display === "none") {
        // Show the form
        form.style.display = "block";
    } else {
        // Hide the form
        form.style.display = "none";
    }
}

window.onclick = function (event) {
    const form = document.getElementById("restaurant-form");
    if (event.target !== form && event.target.parentNode !== form && event.target.tagName !== "BUTTON") {
        form.style.display = "none";
    }
}

const dishesContainer = document.getElementById('dishes-container');
const cartItems = document.getElementById('cart-items');

const noItemsMessage = document.createElement("p");
noItemsMessage.classList.add('cart-no-items-message')
noItemsMessage.textContent = "No items in cart";

const totalPrice = document.createElement("p");
totalPrice.classList.add('cart-total-price')

const cart = document.getElementById('cart');
const paymentButton = document.createElement('button')
paymentButton.textContent = 'Go to payment';
paymentButton.id = 'payment';

paymentButton.addEventListener('click', () => {
    const cartItems = Array.from(document.querySelectorAll('.cart-item'));
    const dishDtos = cartItems.map(cartItem => {
        const dishName = cartItem.querySelector('.cart-p:first-child').textContent;
        const dishPrice = cartItem.querySelector('.cart-p.price').textContent;
        const dishQuantity = cartItem.querySelector('.quantity').textContent;
        return {
            name: dishName,
            price: dishPrice,
            quantity: dishQuantity
        };
    });
    goToCart(dishDtos);
});

cartItems.appendChild(noItemsMessage);


dishesContainer.addEventListener('click', event => {
    if (cartItems.contains(noItemsMessage)) {
        cartItems.removeChild(noItemsMessage);
    }
    if (event.target.classList.contains('add-to-cart-button')) {
        const dishItem = event.target.closest('.dishes-card');
        const dishName = dishItem.querySelector('h3').textContent;
        const dishPrice = dishItem.querySelector('p').textContent.replace(/[^0-9.-]+/g,"");


        // Check if the item already exists in the cart
        const existingCartItem = Array.from(cartItems.children).find(cartItem => {
            const nameElement = cartItem.querySelector('.cart-p:first-child');
            const priceElement = cartItem.querySelector('.cart-p.price');
            return nameElement.textContent === dishName && priceElement.textContent === dishPrice;
        });

        if (existingCartItem) {
            // If the item already exists, increase its quantity instead of adding a new row
            const quantityElement = existingCartItem.querySelector('.quantity');
            quantityElement.textContent = parseInt(quantityElement.textContent) + 1;
        } else {
            const cartItem = document.createElement('li');
            cartItem.classList.add('cart-item');

            const dishNameDiv = document.createElement('div');
            dishNameDiv.classList.add('cart-p');
            dishNameDiv.textContent = dishName;

            const dishPriceDiv = document.createElement('div');
            dishPriceDiv.classList.add('cart-p', 'price');
            dishPriceDiv.textContent = dishPrice;

            const decreaseButton = document.createElement('button');
            decreaseButton.classList.add('decrease-cart-item-button');
            decreaseButton.textContent = '-';

            const quantityDiv = document.createElement('div');
            quantityDiv.classList.add('cart-p', 'quantity');
            quantityDiv.textContent = '1';

            const increaseButton = document.createElement('button');
            increaseButton.classList.add('increase-cart-item-button');
            increaseButton.textContent = '+';

            cartItem.appendChild(dishNameDiv);
            cartItem.appendChild(dishPriceDiv);
            cartItem.appendChild(decreaseButton);
            cartItem.appendChild(quantityDiv);
            cartItem.appendChild(increaseButton);
            cartItems.appendChild(cartItem);
            cart.appendChild(totalPrice)
            cart.appendChild(paymentButton)
            localStorage.setItem("cart-item-name", dishName);
            localStorage.setItem("cart-item-price", dishPrice);
        }
        totalPrice.textContent = "Total price: " + calculateCartTotal().toString() + "₽";
    }
});

cartItems.addEventListener('click', event => {
    if (event.target.classList.contains('increase-cart-item-button')) {
        const cartItem = event.target.closest('.cart-item');
        const quantityElement = cartItem.querySelector('.quantity');
        quantityElement.textContent = parseInt(quantityElement.textContent) + 1;
    } else if (event.target.classList.contains('decrease-cart-item-button')) {
        const cartItem = event.target.closest('.cart-item');
        const quantityElement = cartItem.querySelector('.quantity');
        const quantity = parseInt(quantityElement.textContent);
        if (quantity > 1) {
            quantityElement.textContent = quantity - 1;
        } else {
            cartItem.remove();
        }
    }
    if (cartItems.children.length === 0) {
        cartItems.appendChild(noItemsMessage);
        cart.removeChild(paymentButton)
        cart.removeChild(totalPrice)
    } else {
        totalPrice.textContent = "Total price: " + calculateCartTotal().toString() + "₽";
    }
});

function calculateCartTotal() {
    const cartItems = document.querySelector('#cart-items');
    let totalPrice = 0;
    for (let i = 0; i < cartItems.children.length; i++) {
        const item = cartItems.children[i];
        const price = parseFloat(item.querySelector('.price').textContent);
        const quantity = parseInt(item.querySelector('.quantity').textContent);
        totalPrice += price * quantity;
    }
    return totalPrice;
}


function goToCart(cartData) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/cart', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = '/cart';
        }
    };
    xhr.send(JSON.stringify(cartData));
}

const reviewsButton = document.getElementById('restaurant-rating');
const restId = document.getElementById('restaurantId').textContent;
reviewsButton.addEventListener('click', () => {
    toReviews(restId);
})

function toReviews(id){
    window.location.href = "/reviews/" + id;
}
