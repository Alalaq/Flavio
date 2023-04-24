const filterButton = document.getElementById("filterButton");
const filterOptions = document.getElementById("filterOptions");

filterButton.addEventListener("click", () => {
    if (filterOptions.style.display === "none") {
        filterOptions.style.display = "block";
    } else {
        filterOptions.style.display = "none";
    }
});

function redirectToFiltered() {
    let price = document.getElementById("price");
    let distance = document.getElementById("distance")
    let cuisine = document.getElementById("cuisine")
    let rating = document.getElementById("rating")

    window.location.href = '/restaurants/filtered?price=' + price + '&distance=' + distance + '&cuisines=' + cuisine + '&rating=' + rating;
}

let addressDropdown = document.getElementById("address-dropdown");
let savedAddressesList = document.getElementById("saved-addresses-list");
let newAddressButton = document.getElementById("new-address-button");
let addAddressForm = document.getElementById("add-address-form");

// Function to display list of saved addresses
function displaySavedAddresses() {
    // Make an AJAX request to fetch the saved addresses from the server
    // and populate the savedAddressesList element with the results
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/saved-addresses', true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            let addresses = JSON.parse(xhr.responseText);
            let html = '';
            if (addresses.length > 0) {
                for (let i = 0; i < addresses.length; i++) {
                    html += '<li>' + addresses[i] + '</li>';
                }
            } else {
                html = '<li>No saved addresses found</li>';
            }
            savedAddressesList.innerHTML = html;
        } else {
            console.error('Error fetching saved addresses: ' + xhr.status);
        }
    };
    xhr.send();
}

// Function to display the add new address form
function displayAddAddressForm() {
    // Show the addAddressForm element as a pop-up
    addAddressForm.style.display = "block";
}

addressDropdown.addEventListener("change", function() {
    let selectedOption = addressDropdown.options[addressDropdown.selectedIndex].value;

    if (selectedOption === "saved-addresses") {
        displaySavedAddresses();
    } else if (selectedOption === "new-address") {
        displayAddAddressForm();
    }
});

// Event listener for the new address button
newAddressButton.addEventListener("click", function(event) {
    event.preventDefault();
    displayAddAddressForm();
});

// Event listener for the add address form submission
addAddressForm.addEventListener("submit", function(event) {
    event.preventDefault();
    // Make an AJAX request to add the new address to the database
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/add-address', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function() {
        if (xhr.status === 200) {
            let address = JSON.parse(xhr.responseText);
            savedAddressesList.innerHTML += '<li>' + address + '</li>';
            addAddressForm.style.display = "none";
        } else {
            console.error('Error adding new address: ' + xhr.status);
        }
    };
    let formData = new FormData(addAddressForm);
    let address = {
        street: formData.get('street'),
        city: formData.get('city'),
        state: formData.get('state'),
        zip: formData.get('zip')
    };
    xhr.send(JSON.stringify(address));
});
