// @author Khabibullin Alisher
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
    let form = document.getElementById("popupForm");
    form.style.display = "block";
    document.body.classList.add("dark-mode");
    document.body.style.overflow = "hidden"; // Prevent scrolling while the form is open

    // Remove any previously added tile-specific fields
    let tileFields = document.getElementById("tile-specific-fields");
    while (tileFields.firstChild) {
        tileFields.removeChild(tileFields.firstChild);
    }

// Add tile-specific fields to the form based on the specified tile
    if (tile === "basic-info") {
        let label1 = document.createElement("label");
        label1.textContent = "Avatar";
        let input1 = document.createElement("input");
        input1.setAttribute("type", "file");
        input1.setAttribute("name", "avatar");
        input1.setAttribute("id", "avatar");
        label1.appendChild(input1);

        let label2 = document.createElement("label");
        label2.textContent = "Username";
        let input2 = document.createElement("input");
        input2.setAttribute("type", "text");
        input2.setAttribute("placeholder", "Enter Username");
        input2.setAttribute("name", "username");
        input2.setAttribute("id", "username");
        label2.appendChild(input2);

        let label3 = document.createElement("label");
        label3.textContent = "Email";
        let input3 = document.createElement("input");
        input3.setAttribute("type", "email");
        input3.setAttribute("placeholder", "Enter Email");
        input3.setAttribute("name", "email");
        input3.setAttribute("id", "email");
        label3.appendChild(input3);

        let label4 = document.createElement("label");
        label4.textContent = "Birthday";
        let input4 = document.createElement("input");
        input4.setAttribute("type", "date");
        input4.setAttribute("name", "birthday");
        input4.setAttribute("id", "birthday");
        label4.appendChild(input4);

        tileFields.appendChild(label1);
        tileFields.appendChild(label2);
        tileFields.appendChild(label3);
        tileFields.appendChild(label4);
    } else if (tile === "address-info") {
        let lastId;
        (async function () {
            try {
                const tileFields = document.getElementById('tile-specific-fields');

                const response = await fetch(`/addresses`, {
                    credentials: "include"
                });
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
                            credentials: "include",
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
        addAddressButton.textContent = "Add Address";
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
                    credentials: "include",
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

            // Store the new address in a letiable
            newAddress = address;
        });

        const saveButton = document.querySelector("input[type='submit']");
        saveButton.disabled = true;
        saveButton.addEventListener("click", async () => {
            // Send a request to the server to save the form data
            const form = document.querySelector("form");
            const response = await fetch("/addresses/add", {
                method: "POST",
                credentials: "include",
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
        const labelPhoneNumber = document.createElement("label");
        labelPhoneNumber.innerText = "Phone";
        const inputPhoneNumber = document.createElement("input");
        inputPhoneNumber.type = "text";
        inputPhoneNumber.placeholder = "Enter Phone Number";
        inputPhoneNumber.name = "phoneNumber";
        inputPhoneNumber.id = "phoneNumber";

        const labelEmail = document.createElement("label");
        labelEmail.innerText = "Email";
        const inputEmail = document.createElement("input");
        inputEmail.type = "email";
        inputEmail.placeholder = "Enter Email";
        inputEmail.name = "email";
        inputEmail.id = "email";

        tileFields.appendChild(labelPhoneNumber);
        tileFields.appendChild(inputPhoneNumber);
        tileFields.appendChild(labelEmail);
        tileFields.appendChild(inputEmail);
    } else if (tile === "security-info") {
        const labelPassword = document.createElement("label");
        labelPassword.innerText = "Enter new password";
        const inputPassword = document.createElement("input");
        inputPassword.type = "password";
        inputPassword.placeholder = "Enter new password";
        inputPassword.name = "hashPassword";
        inputPassword.id = "hashPassword";

        tileFields.appendChild(labelPassword);
        tileFields.appendChild(inputPassword);
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
    const addressContainer = document.createElement("div");
    const heading = document.createElement("h2");
    heading.textContent = "Addresses";
    addressContainer.appendChild(heading);

    const response = await fetch(`/addresses`, {
        credentials: "include"
    });
    const addresses = await response.json();

    for (let i = 0; i < addresses.length; i++) {
        const address = addresses[i];
        const addressField = document.createElement("div");
        const addressText = document.createElement("p");
        addressText.textContent = `${address.city}, ${address.streetName}, ${address.homeNumber}`;
        addressField.appendChild(addressText);
        addressContainer.appendChild(addressField);
    }

    const changeInfoButton = document.createElement("button");
    changeInfoButton.classList.add("openButton");
    changeInfoButton.textContent = "Change Information";
    changeInfoButton.addEventListener("click", () => {
        openForm("address-info");
    });
    addressContainer.appendChild(changeInfoButton);

    while (addressInfoTile.firstChild) {
        addressInfoTile.removeChild(addressInfoTile.firstChild);
    }
    addressInfoTile.appendChild(addressContainer);
}

document.addEventListener('DOMContentLoaded', uploadOrdersData);

async function uploadOrdersData() {
    const orderTile = document.querySelector(".user-orders-list");
    while (orderTile.firstChild) {
        orderTile.removeChild(orderTile.firstChild);
    }
    const h2 = document.createElement("h2");
    h2.textContent = "Orders";
    orderTile.appendChild(h2);

    const response = await fetch(`/orders`, {
        credentials: "include"
    });

    const orders = await response.text();
    const ordersObjString = JSON.parse(orders);
    const ordersObj = JSON.parse(ordersObjString);

    for (const order of ordersObj) {
        const restaurantResponse = await fetch(`/restaurant/get/${order.restaurantId}`, {
            credentials: "include"
        });
        const restaurantData = await restaurantResponse.json();
        const restaurantName = restaurantData.name;

        let state = order.state;
        state = state.toString();
        state = state.charAt(0).toUpperCase() + state.substring(1).replace("_", " ").toLowerCase();

        const orderInfoField = document.createElement("div");
        orderInfoField.className = "orders-info-field";

        const restaurantP = document.createElement("p");
        restaurantP.textContent = `Restaurant: ${restaurantName}`;
        orderInfoField.appendChild(restaurantP);

        const dateP = document.createElement("p");
        dateP.textContent = `Date of order: ${order.date.toString().replace("T", " ")}`;
        orderInfoField.appendChild(dateP);

        const stateP = document.createElement("p");
        stateP.textContent = `State of order: ${state}`;
        orderInfoField.appendChild(stateP);

        const totalP = document.createElement("p");
        totalP.textContent = `Total: ${order.total}₽`;
        orderInfoField.appendChild(totalP);

        if (state === "Confirmed") {
            const deliveredButton = document.createElement("button");
            deliveredButton.className = "order-delivered-btn";
            deliveredButton.textContent = "Order is delivered";
            deliveredButton.onclick = function() {
                changeStateToDelivered(order.id);
            };
            orderInfoField.appendChild(deliveredButton);

            const cancelButton = document.createElement("button");
            cancelButton.className = "cancel-order-btn";
            cancelButton.textContent = "Cancel order";
            cancelButton.onclick = function() {
                confirmCancellation(order.id);
            };
            orderInfoField.appendChild(cancelButton);
        }  else if (state === "Delivered") {
            const makeReviewButton = document.createElement("button");
            makeReviewButton.className = "make-review-btn";
            makeReviewButton.textContent = "Make a review";
            makeReviewButton.onclick = function() {
                displayReviewForm(order.id, order.userId);
            };
            orderInfoField.appendChild(makeReviewButton);
        } else if (state === "Reviewed") {
            const reviewP = document.createElement("p");
            reviewP.textContent = "Thanks for your contribution!";
            orderInfoField.appendChild(reviewP);
        } // TODO: idk maybe make something like "order again" or pay an order or delete order idk rly
        orderTile.appendChild(orderInfoField);

        const dishesH3 = document.createElement("h3");
        dishesH3.textContent = "Dishes:";
        orderTile.appendChild(dishesH3);

        const dishesInfoField = document.createElement("div");
        dishesInfoField.className = "orders-info-field-dishes";

        for (const dish of order.dishes) {
            const dishDiv = document.createElement("div");
            dishDiv.className = "orders-dish";

            const dishNameP = document.createElement("p");
            dishNameP.textContent = `${dish.name} - ${dish.price}₽`;
            dishDiv.appendChild(dishNameP);

            const dishImg = document.createElement("img");
            dishImg.className = "orders-dish-image";
            dishImg.src = dish.imageUrl;
            dishImg.alt = "Dish Image";
            dishDiv.appendChild(dishImg);

            dishesInfoField.appendChild(dishDiv);
        }

        orderTile.appendChild(dishesInfoField);
        orderTile.appendChild(document.createElement("br"));
    }
}

async function changeStateToDelivered(orderId) {
    const response = await fetch(`/orders/changeState/${orderId}/DELIVERED`, {
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
        const response = await fetch(`/orders/changeState/${orderId}/CANCELED`, {
            credentials: "include"
        });
        if (response.ok) {
            location.reload();
        } else {
            console.error("Failed to cancel order");
        }
    }
}


function displayReviewForm(orderId, userId) {
    window.location.href = "/review/" + orderId + "/" + userId;
}