/**
 * 
 */
const images = [
    "${pageContext.request.contextPath}/resources/images/system/image1.webp",
    "${pageContext.request.contextPath}/resources/images/system/image2.webp",
    "${pageContext.request.contextPath}/resources/images/system/image3.webp"
];

let currentIndex = 0;
const sliderImage = document.getElementById("slider-image");

setInterval(() => {
    currentIndex = (currentIndex + 1) % images.length;
    sliderImage.src = images[currentIndex];
}, 3000); // Change image every 3 seconds




// Second slider (bottom banner)
const imagesForBottomSlider = [
    "${pageContext.request.contextPath}/resources/images/system/changer1.webp",
    "${pageContext.request.contextPath}/resources/images/system/changer2.webp"
];

let currentBottomIndex = 0;
const bottomSliderImage = document.getElementById("slider-image2");

setInterval(() => {
    currentBottomIndex = (currentBottomIndex + 1) % imagesForBottomSlider.length;
    bottomSliderImage.src = imagesForBottomSlider[currentBottomIndex];
}, 3000);