document.addEventListener("DOMContentLoaded", function () {
    const sliderContainer = document.querySelector(".slider-container");
    const slides = document.querySelectorAll(".slide");
    let currentIndex = 0;
    let slideInterval;
  
    // Function to show a specific slide
    function showSlide(index) {
        // Hide all slides
        slides.forEach(slide => {
            slide.classList.remove("active");
        });
        
        // Show the selected slide
        slides[index].classList.add("active");
    }
  
    // Function to go to next slide
    function nextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        showSlide(currentIndex);
    }
  
    // Initialize the slider
    function initSlider() {
        // Show first slide
        showSlide(currentIndex);
        
        // Start automatic sliding
        slideInterval = setInterval(nextSlide, 3000);
    }
  
    // Start the slider
    initSlider();
	});