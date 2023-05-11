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
    console.log("redirectToFiltered() function called");

    let price = document.getElementById("price").value;
    let distance = document.getElementById("distance").value;
    let cuisine = document.getElementById("cuisine").value;
    let rating = document.getElementById("rating").value;

    let url = '/restaurants/filtered?price=' + price + '&distance=' + distance + '&cuisine=' + cuisine + '&rating=' + rating;
    window.location.replace(url);

    return false;
}
document.addEventListener('DOMContentLoaded', function() {
    checkAuthenticationStatus(function(authenticated) {
        if (authenticated) {
            addressDropdown.addEventListener('change', function() {
                const selectedValue = this.value;
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

            displaySavedAddresses();
        }
    });
});

let addressDropdown = document.querySelector('#address-dropdown');

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
            let html = '';
            html += `
                        <option value="Select an option" disabled selected>Select an option</option>
                    `;
            if (addresses.length > 0) {
                for (let i = 0; i < addresses.length; i++) {
                    const address = addresses[i];
                      html += `
                        <option value="${address.streetName}, ${address.homeNumber}, ${address.city},">
                            ${address.city}, ${address.streetName}, ${address.homeNumber}
                        </option>
                    `;

                }
            } else {
                html += '<option value="" disabled>No saved addresses found</option>';
            }
            addressDropdown.innerHTML = html;
        } else {
            console.error('Error fetching saved addresses: ' + xhr.status);
        }
    };
    xhr.send();
}

