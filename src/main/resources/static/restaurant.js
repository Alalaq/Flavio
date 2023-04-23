const filterButton = document.getElementById("filterButton");
const filterOptions = document.getElementById("filterOptions");

filterButton.addEventListener("click", () => {
    if (filterOptions.style.display === "none") {
        filterOptions.style.display = "block";
    } else {
        filterOptions.style.display = "none";
    }
});

