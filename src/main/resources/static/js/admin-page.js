// @author Khabibullin Alisher
const tableBody = document.getElementById("user-table-body");
const table = document.getElementById("user-table");
const noUsersMsg = document.getElementById("no-users");

// Function to create a row for a user
function createUserRow(user) {
    const row = document.createElement("tr");

    const idCell = document.createElement("td");
    idCell.textContent = user.id;
    row.appendChild(idCell);

    const nameCell = document.createElement("td");
    nameCell.textContent = user.name;
    row.appendChild(nameCell);

    const emailCell = document.createElement("td");
    emailCell.textContent = user.email;
    row.appendChild(emailCell);

    const stateCell = document.createElement("td");
    stateCell.textContent = user.state;
    row.appendChild(stateCell);

    const actionCell = document.createElement("td");

    const deleteButton = document.createElement("button");
    deleteButton.className = "delete-btn";
    deleteButton.textContent = "Delete";
    deleteButton.addEventListener("click", () => deleteUser(user.id, row));
    actionCell.appendChild(deleteButton);

    // Add a button to ban/unban the user based on their state
    const banButton = document.createElement("button");
    banButton.className = "ban-btn";
    banButton.textContent = user.state === 'BANNED' ? "Unban" : "Ban";
    banButton.addEventListener("click", () => {
        if (user.state === "BANNED") {
            unbanUser(user.id);
        } else {
            banUser(user.id);
        }
    });
    actionCell.appendChild(banButton);

    // Add a button to make the user an admin if they are not already an admin
    if (user.role !== "ADMIN") {
        const makeAdminButton = document.createElement("button");
        makeAdminButton.className = "make-admin-btn";
        makeAdminButton.textContent = "Make Admin";
        makeAdminButton.addEventListener("click", () => {
            makeUserAdmin(user.id);
        });
        actionCell.appendChild(makeAdminButton);
    }

    row.appendChild(actionCell);

    return row;
}


// Function to reload the table
function reloadTable() {
    // Clear the table
    tableBody.innerHTML = "";

    // Make an AJAX request to get the list of users
    fetch("/api/users/", {
        credentials: "include"
    })
        .then(response => response.json())
        .then(users => {
            // Check if there are any users
            if (users.length === 0) {
                // If there are no users, hide the table and show the "no users" message
                table.style.display = "none";
                noUsersMsg.style.display = "block";
            } else {
                // If there are users, show the table and hide the "no users" message
                table.style.display = "table";
                noUsersMsg.style.display = "none";
                // Iterate over the list of users and create HTML elements for each user
                users.forEach(user => {
                    const row = createUserRow(user);
                    tableBody.appendChild(row);
                });
            }
        });
}


// Function to delete a user
function deleteUser(id, row) {
    // Remove the row from the table
    row.remove();

    // Make an AJAX request to delete the user
    fetch(`/api/users/${id}`, {
        credentials: "include",
        method: "DELETE"
    })
        .then(() => {
            // Reload the table
            reloadTable();
        });
}

function updateUserState(id, state) {
    let newState = state.toUpperCase();
    // Make an AJAX request to update the user's state
    fetch(`/api/users/${id}?updateField=state`, {
        credentials: "include",
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: (newState)
    }).then(() => {
        // Reload the table
        reloadTable();
    });
}

function banUser(id) {
    updateUserState(id, 'BANNED');
}

function unbanUser(id) {
    updateUserState(id, 'CONFIRMED');
}


// Function to make a user an admin
function makeUserAdmin(id) {
    // Make an AJAX request to update the user's role to ADMIN
    fetch(`/api/users/${id}?updateField=role`, {
        credentials: "include",
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: "ADMIN"
    }).then(() => {
        // Reload the table
        reloadTable();
    });
}

// Reload the table when the page is loaded
reloadTable();
