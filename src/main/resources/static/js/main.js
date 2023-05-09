// @author Khabibullin Alisher
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

const header = document.querySelector('header');



