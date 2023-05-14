// @author Khabibullin Alisher
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
    let price = document.getElementById("price").value;
    let distance = document.getElementById("distance").value;
    let cuisine = document.getElementById("cuisine").value;
    let rating = document.getElementById("rating").value;

    let url = '/restaurants/filtered?price=' + price + '&distance=' + distance + '&cuisine=' + cuisine + '&rating=' + rating;
    window.location.replace(url);

    return false;
}
let addressDropdown = document.getElementById('address-dropdown');

document.addEventListener('DOMContentLoaded', function() {
    checkAuthenticationStatus(function(authenticated) {
        if (authenticated) {
            displaySavedAddresses();

            addressDropdown.addEventListener('change', function() {
                const selectedValue = this.value;
                console.log(selectedValue)
                const xhr = new XMLHttpRequest();
                const url = '/restaurants?userAddress=' + (selectedValue);
                xhr.open('POST', url, true);
                xhr.onload = function() {
                    if (xhr.status === 200) {
                    } else {
                        console.error('Error fetching filtered restaurants: ' + xhr.status);
                    }
                };
                xhr.send();
            });

        }
    });
});


function checkAuthenticationStatus(callback) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/auth/check-authentication', true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            callback(true);
        } else {
            callback(false);
        }
    };

    xhr.send();
}

function displaySavedAddresses() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/addresses', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            let addresses = JSON.parse(xhr.responseText);
            addressDropdown.setAttribute('id', 'address-dropdown')
            addressDropdown.setAttribute('name', 'address-dropdown')
            addressDropdown.setAttribute('class', 'address-dropdown')
            let defaultOption = document.createElement('option');
            defaultOption.setAttribute('value', 'Select an option');
            defaultOption.setAttribute('disabled', '');
            defaultOption.textContent = 'Select an option';
            addressDropdown.appendChild(defaultOption);
            if (addresses.length > 0) {
                for (let i = 0; i < addresses.length; i++) {
                    const address = addresses[i];
                    let option = document.createElement('option');
                    option.setAttribute('value', `${address.streetName}, ${address.homeNumber}, ${address.city}`);
                    option.textContent = `${address.city}, ${address.streetName}, ${address.homeNumber}`;
                    addressDropdown.appendChild(option);
                }
            } else {
                let noOption = document.createElement('option');
                noOption.setAttribute('value', '');
                noOption.setAttribute('disabled', '');
                noOption.textContent = 'No saved addresses found';
                addressDropdown.appendChild(noOption);
            }
        } else {
            console.error('Error fetching saved addresses: ' + xhr.status);
        }
    };
    xhr.send();
}

