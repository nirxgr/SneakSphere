document.addEventListener("DOMContentLoaded", function() {
    const dynamicSlider = document.querySelector(".dynamic-slider");
    const dynamicImages = dynamicSlider.querySelectorAll("img");
    let currentDynamicIndex = 0;
    let dynamicInterval;
    
    // Function to show a specific image
    function showDynamicImage(index) {
        // Hide all images
        dynamicImages.forEach(img => {
            img.classList.remove("active");
        });
        
        // Show the selected image
        dynamicImages[index].classList.add("active");
    }
    
    // Function to go to next image
    function nextDynamicImage() {
        currentDynamicIndex = (currentDynamicIndex + 1) % dynamicImages.length;
        showDynamicImage(currentDynamicIndex);
    }
    
    // Initialize the slider
    function initDynamicSlider() {
        // Show first image
        showDynamicImage(currentDynamicIndex);
        
        // Start automatic sliding
        dynamicInterval = setInterval(nextDynamicImage, 3000);
    }
    
    // Start the slider
    initDynamicSlider();
   
});