const categories = document.querySelectorAll(".categories ul li a");
const contents = document.querySelectorAll(".content .profile-info");

for (let i = 0; i < categories.length; i++) {
    categories[i].addEventListener("click", function () {
        for (let j = 0; j < categories.length; j++) {
            categories[j].classList.remove("active");
        }
        categories[i].classList.add("active");
        for (let j = 0; j < contents.length; j++) {
            contents[j].style.display = "none";
        }
        let selectedContent = contents[i];
        selectedContent.style.display = "block";
    });
}

function openForm(tile) {
    var form = document.getElementById("popupForm");
    form.style.display = "block";
    document.body.classList.add("dark-mode");
    document.body.style.overflow = "hidden"; // Prevent scrolling while the form is open

    // Remove any previously added tile-specific fields
    var tileFields = document.getElementById("tile-specific-fields");
    tileFields.innerHTML = "";

    // Add tile-specific fields to the form based on the specified tile
    if (tile === "basic-info") {
        tileFields.innerHTML = `
            <label for="avatar"><b>Avatar</b></label>
            <input type="file" name="avatar" id="avatar">
            <label for="username"><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="username" id="username">
            <label for="email"><b>Email</b></label>
            <input type="email" placeholder="Enter Email" name="email" id="email">
            <label for="birthday"><b>Birthday</b></label>
            <input type="date" name="birthday" id="birthday">
        `;
    } else if (tile === "address-info") {
        let lastId;
        (async function () {
            try {
                const tileFields = document.getElementById('tile-specific-fields');

                const response = await fetch(`/addresses`);
                const addresses = await response.json();

                lastId = addresses.length;
                // Create new address fields in the form with the fetched data
                for (let i = 0; i < addresses.length; i++) {
                    // Create the address field
                    let addressParts = addresses[i];
                    const address = {
                        id: addressParts.id,
                        streetName: addressParts.streetName,
                        homeNumber: addressParts.homeNumber,
                        city: addressParts.city,
                        country: addressParts.length > 3 ? addressParts.country : null,
                    };
                    const newAddressField = document.createElement("div");
                    newAddressField.className = "address-field";
                    newAddressField.appendChild(document.createTextNode(address.city + ", " + address.streetName + ", " + address.homeNumber));

                    const deleteButton = document.createElement("button");
                    deleteButton.className = "delete-button";
                    deleteButton.innerText = "Delete";
                    deleteButton.addEventListener("click", async () => {
                        // Send a request to the server to delete the address
                        const response = await fetch(`/addresses/delete?address_id=` + address.id, {
                            method: "DELETE",
                        });

                        if (response.ok) {
                            newAddressField.remove();
                        } else {
                            console.log("Error deleting address from server");
                        }
                    });
                    newAddressField.appendChild(deleteButton);
                    tileFields.appendChild(newAddressField);
                }
            } catch (error) {
                console.log(error);
            }

        })();

        let newAddress;
        const addAddressButton = document.createElement("button");
        addAddressButton.innerHTML = "Add Address";
        addAddressButton.className = "add-address-button";
        addAddressButton.addEventListener("click", function (event) {
            event.preventDefault();

            // Prompt the user to enter the new address
            const addressString = prompt(
                "Enter the new address in the format 'street_name, house_number, city, county (optional)':"
            );
            if (!addressString) {
                return; // If the user cancels the prompt or enters an empty string, do nothing
            }

            // Parse the address string into an object
            const addressParts = addressString.split(",");
            if (addressParts.length < 3) {
                alert("Invalid address format"); // If the address is missing any required fields, show an error message and do nothing
                return;
            }
            const address = {
                id: lastId + 1,
                streetName: addressParts[0].trim(),
                homeNumber: addressParts[1].trim(),
                city: addressParts[2].trim(),
                country: addressParts.length > 3 ? addressParts[3].trim() : null,
            };

            // Add the new address field to the form
            const tileFields = document.getElementById("tile-specific-fields");
            const newAddressField = document.createElement("div");
            newAddressField.className = "address-field";

            const addressText = document.createTextNode(address.streetName + ", " + address.homeNumber);

            newAddressField.appendChild(addressText);

            const deleteButton = document.createElement("button");
            deleteButton.className = "delete-button";
            deleteButton.innerText = "Delete";
            deleteButton.addEventListener("click", async () => {
                // Send a request to the server to delete the address
                const response = await fetch(`/addresses/delete?address_id=`, {
                    method: "DELETE",
                });

                if (response.ok) {
                    newAddressField.remove();
                } else {
                    console.log("Error deleting address from server");
                }
            });

            newAddressField.appendChild(deleteButton);
            tileFields.appendChild(newAddressField);

            // Clear the input field
            event.target.previousElementSibling && (event.target.previousElementSibling.value = "");

            // Disable the "Add Address" button and enable the "Save" button
            addAddressButton.disabled = true;
            saveButton.disabled = false;

            // Store the new address in a variable
            newAddress = address;
        });

        const saveButton = document.querySelector("input[type='submit']");
        saveButton.disabled = true;
        saveButton.addEventListener("click", async () => {
            // Send a request to the server to save the form data
            const form = document.querySelector("form");
            const response = await fetch("/addresses/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newAddress)
            });
            if (response.ok) {
                // If the form was saved successfully, show a success message and clear the form fields
                alert("Information saved successfully");
                form.reset();
            } else {
                console.log("Error saving information to server");
            }
        });

// Add the "Add Address" button to the form
        const tileFields = document.getElementById("tile-specific-fields");
        tileFields.appendChild(addAddressButton);
    } else if (tile === "contact-info") {
        tileFields.innerHTML = `
            <label for="phoneNumber"><b>Phone</b></label>
            <input type="text" placeholder="Enter Phone Number" name="phoneNumber" id="phoneNumber">
            <label for="email"><b>Email</b></label>
            <input type="email" placeholder="Enter Email" name="email" id="email">
        `;
    } else if (tile === "security-info") {
        tileFields.innerHTML = `
            <label for="hashPassword"><b>Enter new password</b></label> 
                    <input type="password" placeholder="Enter new password" name="hashPassword" id="hashPassword">`
    }
}

function closeForm() {
    document.getElementById("popupForm").style.display = "none";
    document.body.classList.remove("dark-mode");
    document.body.style.overflow = "auto";
}

document.addEventListener('DOMContentLoaded', uploadAddressData);

async function uploadAddressData() {
    const addressInfoTile = document.querySelector(".address-info-tile");
    let addressHTML = "<h2>Addresses</h2>";

    const response = await fetch(`/addresses`);
    const addresses = await response.json();

    for (let i = 0; i < addresses.length; i++) {
        const address = addresses[i];
        addressHTML += `
    <div class="address-field">
      <p> ${address.city}, ${address.streetName}, ${address.homeNumber}</p>
    </div>
  `;
    }
    addressHTML += ' <button class="openButton" onclick="openForm(\'address-info\')"><strong>Change Information</strong></button>';
    addressInfoTile.innerHTML = addressHTML;
}

document.addEventListener('DOMContentLoaded', uploadOrdersData);

async function uploadOrdersData() {
    const orderTile = document.querySelector(".user-orders-list");
    let ordersHTML = "<h2>Orders</h2>";

    const response = await fetch(`/orders`);
    const orders = await response.text();
    const ordersObjString = JSON.parse(orders);

    const ordersObj = JSON.parse(ordersObjString);


    for (const order of ordersObj) {
        // Make an API call to get the restaurant name, including cookies in the request
        const restaurantResponse = await fetch(`/restaurant/get/${order.restaurantId}`, {
            credentials: "include"
        });
        const restaurantData = await restaurantResponse.json();
        const restaurantName = restaurantData.name;

        ;

        ordersHTML += `
        <div class="orders-info-field">
            <p>Restaurant: ${restaurantName}</p>
            <p>Date of order: ${order.date}</p>
            <p>State of order: ${order.state}</p>
            <p>Total: ${order.total}₽</p>
    `;

        if (order.state === "Confirmed") {
            ordersHTML += `
            <button class="order-delivered-btn" onclick="changeStateToDelivered(${order.id})">Order is delivered</button>
            <button class="cancel-order-btn" onclick="confirmCancellation(${order.id})">Cancel order</button>
        `;
        } else if (order.state === "Delivered") {
            ordersHTML += `
            <button class="make-review-btn" onclick="displayReviewForm(${order.id})">Make a review</button>
        `;
        }

        ordersHTML += `
        </div>
        <h3>Dishes:</h3>
        <div class="orders-info-field-dishes">
    `;
        for (const dish of order.dishes) {
            ordersHTML += `
        <div class="orders-dish">
          <p>${dish.name} - ${dish.price}₽</p>
          <img class="orders-dish-image" src="${dish.imageUrl}" alt="Dish Image">
        </div>
      `;
        }
        ordersHTML += `
      </div>
      <br>
    `;
    }

    orderTile.innerHTML = ordersHTML;
}

async function changeStateToDelivered(orderId) {
    const response = await fetch(`/orders/changeState/${orderId}/Delivered`, {
        credentials: "include"
    });
    if (response.ok) {
        location.reload();
    } else {
        console.error("Failed to change the order state to Delivered");
    }
}

async function confirmCancellation(orderId) {
    const isConfirmed = confirm("Are you sure you want to cancel this order?");
    if (isConfirmed) {
        const response = await fetch(`/orders/changeState/${orderId}/Canceled`, {
            credentials: "include"
        });
        if (response.ok) {
            location.reload();
        } else {
            console.error("Failed to cancel order");
        }
    }
}

//TODO: Display the review form for the selected order
function displayReviewForm(orderId) {
}