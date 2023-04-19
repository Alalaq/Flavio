const reviews = document.querySelectorAll('.review');
reviews.forEach(review => {
    review.addEventListener('mousemove', e => {
        const mouseX = e.clientX;
        const mouseY = e.clientY;
        const { left, top, width, height } = review.getBoundingClientRect();
        const centerX = left + width / 2;
        const centerY = top + height / 2;
        const dx = mouseX - centerX;
        const dy = mouseY - centerY;
        review.style.transform = `translate(${dx / 10}px, ${dy / 10}px)`;
    });
    review.addEventListener('mouseout', () => {
        review.style.transform = 'none';
    });
});
// Select the header element and create a new search bar element
const header = document.querySelector('header');
const searchBar = document.createElement('input');

// Set the attributes of the search bar
searchBar.type = 'text';
searchBar.placeholder = 'Search for restaurants...';
searchBar.classList.add('search-bar'); // Add a class for styling

// Select the logo div and its parent div
const logo = header.querySelector('.logo');
const parentDiv = logo.parentElement;

// Insert the search bar element after the logo div
parentDiv.insertBefore(searchBar, logo.nextSibling);

// Add an event listener to the search bar for user input
searchBar.addEventListener('input', function(event) {
    const searchText = event.target.value; // Get the text entered by the user
    console.log(searchText); // Do something with the search text, like filter the restaurant reviews
});
