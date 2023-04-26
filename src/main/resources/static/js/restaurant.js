const filterButton = document.getElementById("filterButton");
const filterOptions = document.getElementById("filterOptions");

filterButton.addEventListener("click", () => {
    if (filterOptions.style.display === "none") {
        filterOptions.style.display = "block";
    } else {
        filterOptions.style.display = "none";
    }
});

window.onload = function() {
    displaySavedAddresses();
}

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

let addressDropdown = document.getElementById("address-dropdown");// Function to display list of saved addresses
function displaySavedAddresses() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/addresses', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            let addresses = JSON.parse(xhr.responseText);
            let html = '';
            if (addresses.length > 0) {
                for (let i = 0; i < addresses.length; i++) {
                    const address = addresses[i];
                    html += `
                        <option value="${address.city}, ${address.streetName}, ${address.homeNumber}">
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


