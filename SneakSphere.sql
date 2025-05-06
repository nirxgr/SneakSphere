-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 06, 2025 at 08:44 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `SneakSphere`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `CartID` int(11) NOT NULL,
  `CartQuantity` int(10) NOT NULL,
  `CartTotal` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`CartID`, `CartQuantity`, `CartTotal`) VALUES
(5, 1, 29409),
(6, 1, 12000);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `OrderID` int(11) NOT NULL,
  `Quantity` int(10) NOT NULL,
  `Size` float NOT NULL,
  `OrderTotal` float NOT NULL,
  `OrderStatus` varchar(30) NOT NULL,
  `OrderDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sneaker`
--

CREATE TABLE `sneaker` (
  `SneakerID` int(11) NOT NULL,
  `SneakerName` varchar(50) NOT NULL,
  `SneakerSize` float NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Category` varchar(20) NOT NULL,
  `Brand` varchar(50) NOT NULL,
  `Price` float NOT NULL,
  `ReleasedDate` date NOT NULL,
  `AvailabilityStatus` varchar(20) NOT NULL,
  `ImageURL` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sneaker`
--

INSERT INTO `sneaker` (`SneakerID`, `SneakerName`, `SneakerSize`, `Description`, `Category`, `Brand`, `Price`, `ReleasedDate`, `AvailabilityStatus`, `ImageURL`) VALUES
(16, 'Asics Gel - 1130', 12, 'Running Shoes', 'Mens', 'ASICS', 18000, '2025-02-22', 'Available', 'ASICS GEL-1130.webp'),
(17, 'Jordan MVP', 12, 'Stylish Basketball shoes', 'Mens', 'Jordan', 29409, '2025-04-16', 'Available', 'Jordan MVP.webp'),
(19, 'NB 530', 11, 'Retro, sporty, comfortable lifestyle sneaker.', 'Mens', 'New Balance', 20567, '2025-02-26', 'Available', 'NB 530.webp'),
(20, 'Nike Red Dunk', 9, 'Bold, retro, sporty red sneaker.', 'Mens', 'Nike', 10450, '2025-02-19', 'Available', 'RED DUNK.webp'),
(21, 'Puma Nd', 13, 'Stylish, sporty, versatile sneaker.', 'Mens', 'Puma', 12390, '2025-01-17', 'Available', 'Puma Nd.webp'),
(22, 'Air Force 1', 13, 'Classic, clean, iconic sneaker.', 'Mens', 'Air Force', 12000, '2025-03-11', 'Available', 'Air Force 1.webp'),
(23, 'Adidas Samba', 8, 'Retro, sleek, timeless design.', 'Mens', 'Adidas', 24780, '2025-02-02', 'Available', 'Adidas samba.webp'),
(24, 'Adidas Speed Cat', 8, 'Low-profile, racing-inspired sneaker.', 'Mens', 'Adidas', 19750, '2025-04-09', 'Available', 'Speed Cat.webp'),
(29, 'Nike Air Max CB2 \'94', 8, 'Chunky, retro, basketball sneaker.\r\n', 'Mens', 'Nike', 26409, '2024-10-02', 'Available', 'Nike Air Max CB2 \'94.webp'),
(30, 'Jordan Luka 4', 9, 'Responsive, sleek, performance-driven.\r\n', 'Mens', 'Jordan', 18000, '2024-10-10', 'Available', 'Jordan Luka 4.webp'),
(31, 'Jordan Retro 13', 7, 'Panther-inspired, bold, cushioned sneaker.\r\n', 'Mens', 'Jordan', 18000, '2024-03-12', 'Available', 'Jordan Retro 13.webp'),
(32, 'Nike Air Force 1 \'07 LV8 NA1', 7, 'Heritage-inspired, bold, cushioned sneaker.\r\n', 'Mens', 'Nike', 53900, '2024-11-13', 'Available', 'Nike Air Force 1 \'07 LV8 NA1.webp'),
(33, 'New Balance 1906R', 6, 'Retro-tech, cushioned, stylish runner.\r\n', 'Mens', 'New Balance', 14569, '2024-11-13', 'Available', 'New Balance 1906R.webp'),
(34, 'New Balance 9060', 8, 'Futuristic, chunky, cushioned lifestyle sneaker.\r\n', 'Mens', 'New Balance', 18508, '2024-11-21', 'Available', 'New Balance 9060.webp'),
(35, 'PUMA RS-X Parisian', 7, 'Bold, vibrant, retro-inspired runner.\r\n', 'Mens', 'Puma', 16600, '2024-08-15', 'Available', 'PUMA RS-X Parisian.webp'),
(36, 'New Balance M1000', 8, 'Supportive, durable, cushioned running shoe.\r\n', 'Mens', 'New Balance', 38503, '2024-10-30', 'Available', 'New Balance M1000.webp'),
(38, 'Nike Dunk High', 7, 'Iconic, high-top, retro basketball sneaker.\r\n', 'Womens', 'Nike', 13800, '2024-10-09', 'Available', 'Nike Dunk High Black W.webp'),
(39, 'Jordan Air Jordan 4 Retro', 8, 'Iconic, versatile, comfortable, retro sneaker.\r\n', 'Womens', 'Jordan', 26870, '2024-07-16', 'Available', 'Jordan Air Jordan 4 Retro W.webp'),
(41, 'Converse Chuck Taylor All Star Lift', 7, 'Classic, elevated, comfortable, casual sneaker.\r\n', 'Womens', 'Converse', 8020, '2024-09-25', 'Available', 'Converse Chuck Taylor All Star Lift W.webp'),
(42, 'PUMA Easy Rider Vintage', 8, 'Retro, lightweight, casual, sporty sneaker.\r\n', 'Womens', 'Puma', 18000, '2025-01-15', 'Available', 'PUMA Easy Rider Vintage W.webp'),
(43, 'Nike Metcon 6', 8, 'Stable, breathable, durable training shoe.\r\n', 'Womens', 'Nike', 23870, '2024-12-12', 'Available', 'Nike Metcon 6.webp'),
(44, 'Nike SuperRep Go 3 Flyknit', 8, 'Lightweight, flexible, breathable training shoe.\r\n', 'Womens', 'Nike', 8793, '2024-11-14', 'Available', 'Nike SuperRep Go 3 Flyknit W.webp'),
(45, 'On Cloudtilt', 8, 'Sleek, cushioned, lightweight performance sneaker.', 'Womens', 'On', 9835, '2025-01-02', 'Available', 'On Cloudtilt W.webp'),
(46, 'adidas Originals Gazelle Indoor', 8, 'Retro, stylish, suede, casual sneaker.\r\n', 'Womens', 'Adidas', 14504, '2024-12-12', 'Available', 'adidas Originals Gazelle Indoor W.webp'),
(47, 'adidas Originals Ozelia', 7, 'Futuristic, bold, cushioned lifestyle sneaker.\r\n', 'Womens', 'Adidas', 16590, '2025-02-06', 'Available', 'adidas Originals Ozelia W.webp'),
(48, 'adidas Originals Tokyo', 8, 'Futuristic, bold, cushioned lifestyle sneaker.\r\n', 'Womens', 'Adidas', 15430, '2024-10-03', 'Available', 'adidas Originals Tokyo W.webp'),
(49, 'Vans Knu Skool', 8, 'Chunky, retro, padded skate sneaker.\r\n', 'Womens', 'Vans', 14390, '2024-11-06', 'Available', 'Vans Knu Skool W.webp'),
(50, 'Vans Knu Skool Punk Paste', 8, 'Bold, graphic, padded skate sneaker.\r\n', 'Womens', 'Vans', 18000, '2024-11-15', 'Available', 'Vans Knu Skool Punk Paste W.webp'),
(51, 'New Balance 1906', 8, 'Retro-tech, supportive, cushioned running sneaker.\r\n', 'Womens', 'New Balance', 19890, '2025-03-13', 'Available', 'New Balance 1906 W.webp'),
(52, 'New Balance 480', 8, 'Classic, clean, court-inspired sneaker.\r\n', 'Womens', 'New Balance', 18020, '2025-01-04', 'Available', 'New Balance 480 W.webp'),
(53, 'HOKA Rincon 4', 8, 'Normal Black Running Shoes', 'Womens', 'HOKA', 18934, '2022-02-02', 'Available', 'HOKA Rincon 4 W.webp'),
(54, 'Jordan Retro', 8, 'dsgeewt', 'Mens', 'Jordan', 18000, '2025-01-07', 'Available', 'New Balance 480 W.webp');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `UserFirstName` varchar(50) NOT NULL,
  `UserLastName` varchar(50) NOT NULL,
  `UserEmail` varchar(50) NOT NULL,
  `UserPassword` varchar(100) NOT NULL,
  `UserPhone` varchar(10) NOT NULL,
  `UserAddress` varchar(80) NOT NULL,
  `Role` varchar(10) NOT NULL,
  `UserImageURL` varchar(255) NOT NULL DEFAULT 'pp.jpg'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `UserFirstName`, `UserLastName`, `UserEmail`, `UserPassword`, `UserPhone`, `UserAddress`, `Role`, `UserImageURL`) VALUES
(1, 'Nirak', 'Gurunfj', 'grgniraj01@gmail.com', '4ALnc1p4WLctwZ+FULb8HAp/zuCmP8/G/ynS+7gWw9g7lxCb1c0ZTqNL/WubTZJI5PH7W90=', '9813909088', 'Bhotebahal,Kathmandu', 'Admin', ''),
(2, 'Niraj', 'Grg', 'grgniraj@gmail.com', 'xRMCLou1Pbfqok2RVI3c4A6xXkT1gLDYr3mbw02KuItXwU4Ypd0fH+jtQnnZhRfjVCgRjaRlOFw=', '9812345678', 'Manchester', 'Customer', ''),
(6, 'Pratha', 'Lala', 'pran122@gmail.com', 'ct9DI5NXRlmWEz3+eyr3TQLB6GpI7kGhrg0CXZP/aepTHrF6hQR7MaHLgeCVeYhad0v2Yz1p', '9811223344', 'Singapore', 'Customer', ''),
(23, 'Suraj', 'Sah', 'surajhero@gmail.com', 'L72sEa732QbX6xBdruoJJMlkm73KD556c9V0C7YUhMLG2naqem4lam7fDssMXxKfYkR+xw==', '9812345678', 'Bihar,India', 'Customer', ''),
(25, 'Niraj', 'Gurung', 'grg@gmail.com', 'eCGtdLJ/Ezcv+qOw19b6hjTbMhJkeM7xhGLqDYLcwb18sLWH5Qqfgjd/I0dIvnx5Trytiu8=', '9813909088', 'Bhotebahal,Kathmandu', 'Customer', ''),
(26, 'Pratha', 'Lalaaaaaa', 'lala@gmail.com', '9uprvutWTasZtZwwEpPC3oyy0qPlOL8Y3cJw+mTO+OLtwxZFlKoAdMs269gFLw2z41clQA==', '9813434343', 'Janakpur', 'Customer', ''),
(32, 'Nissan', 'Karki', 'nissankarki@gmail.com', 'tZVYwHH3tB0/U7f57kyJC4sPsOMdGhqlWyQc3NPQB+Jf0qax0rLAm+p6OQ2s3qCy6aHqrvToxoE=', '9823220990', 'Kathmandu', 'Customer', 'pp.jpg'),
(33, 'Bhishma', 'Karki', 'bhishmakarki@gmail.com', 'y50CNBlFzN1d4d0QZSnp+vLef+aMGG3qy//eN0f1yvbqpGj/2T5iYpsUGIUbpux1dp/e2mUkvdcR', '9823220990', 'Biratnagar', 'Customer', 'pp.jpg'),
(34, 'gfidhgeig', 'qekhgoiehg', 'pratha1@gmail.com', 'RArfXvcnJ4jJJcMUdG4x0zJVZWHmc/o7Gr8QOwoI/ugmcMy2j1+/n7QadqiPC1z4hZg37cjIkc4=', '9834569212', 'Lalalalalala', 'Customer', 'pp.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user_sneaker`
--

CREATE TABLE `user_sneaker` (
  `UserID` int(11) NOT NULL,
  `SneakerID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_sneaker_cart`
--

CREATE TABLE `user_sneaker_cart` (
  `UserID` int(11) NOT NULL,
  `SneakerID` int(11) NOT NULL,
  `CartID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_sneaker_cart`
--

INSERT INTO `user_sneaker_cart` (`UserID`, `SneakerID`, `CartID`) VALUES
(2, 17, 5),
(2, 22, 6);

-- --------------------------------------------------------

--
-- Table structure for table `user_sneaker_order`
--

CREATE TABLE `user_sneaker_order` (
  `UserID` int(11) NOT NULL,
  `SneakerID` int(11) NOT NULL,
  `OrderID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wishlist`
--

CREATE TABLE `wishlist` (
  `WishListID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `SneakerID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wishlist`
--

INSERT INTO `wishlist` (`WishListID`, `UserID`, `SneakerID`) VALUES
(6, 2, 17),
(7, 2, 21);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`CartID`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`OrderID`);

--
-- Indexes for table `sneaker`
--
ALTER TABLE `sneaker`
  ADD PRIMARY KEY (`SneakerID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `user_sneaker`
--
ALTER TABLE `user_sneaker`
  ADD PRIMARY KEY (`UserID`,`SneakerID`),
  ADD KEY `Sneaker_FK` (`SneakerID`);

--
-- Indexes for table `user_sneaker_cart`
--
ALTER TABLE `user_sneaker_cart`
  ADD PRIMARY KEY (`UserID`,`SneakerID`,`CartID`),
  ADD KEY `sneaker_fk3` (`SneakerID`),
  ADD KEY `cart_fk3` (`CartID`);

--
-- Indexes for table `user_sneaker_order`
--
ALTER TABLE `user_sneaker_order`
  ADD PRIMARY KEY (`UserID`,`SneakerID`,`OrderID`),
  ADD KEY `Sneaker_FK2` (`SneakerID`),
  ADD KEY `Order_FK2` (`OrderID`);

--
-- Indexes for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD PRIMARY KEY (`WishListID`,`UserID`,`SneakerID`),
  ADD KEY `User_FK4` (`UserID`),
  ADD KEY `Sneaker_FK4` (`SneakerID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `CartID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sneaker`
--
ALTER TABLE `sneaker`
  MODIFY `SneakerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `wishlist`
--
ALTER TABLE `wishlist`
  MODIFY `WishListID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_sneaker`
--
ALTER TABLE `user_sneaker`
  ADD CONSTRAINT `Sneaker_FK` FOREIGN KEY (`SneakerID`) REFERENCES `sneaker` (`SneakerID`),
  ADD CONSTRAINT `User_FK` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints for table `user_sneaker_cart`
--
ALTER TABLE `user_sneaker_cart`
  ADD CONSTRAINT `cart_fk3` FOREIGN KEY (`CartID`) REFERENCES `cart` (`CartID`),
  ADD CONSTRAINT `sneaker_fk3` FOREIGN KEY (`SneakerID`) REFERENCES `sneaker` (`SneakerID`),
  ADD CONSTRAINT `user_fk3` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints for table `user_sneaker_order`
--
ALTER TABLE `user_sneaker_order`
  ADD CONSTRAINT `Order_FK2` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`),
  ADD CONSTRAINT `Sneaker_FK2` FOREIGN KEY (`SneakerID`) REFERENCES `sneaker` (`SneakerID`),
  ADD CONSTRAINT `User_FK2` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);

--
-- Constraints for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD CONSTRAINT `Sneaker_FK4` FOREIGN KEY (`SneakerID`) REFERENCES `sneaker` (`SneakerID`),
  ADD CONSTRAINT `User_FK4` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
