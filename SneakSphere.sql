-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 21, 2025 at 11:17 PM
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
(25, 1, 13800),
(30, 1, 16590),
(31, 1, 18000),
(32, 1, 18020),
(38, 1, 18000),
(39, 1, 18000),
(43, 1, 19890),
(44, 1, 18000),
(45, 1, 18508);

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

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`OrderID`, `Quantity`, `Size`, `OrderTotal`, `OrderStatus`, `OrderDate`) VALUES
(6, 4, 11, 73159, 'Returned', '2025-05-16'),
(7, 2, 10.5, 30390, 'Cancelled', '2025-05-16'),
(8, 2, 8, 36000, 'Returned', '2025-05-16'),
(9, 4, 9, 68230, 'Returned', '2025-05-16'),
(10, 6, 9.6, 109957, 'Pending', '2025-05-16'),
(11, 2, 9.5, 38567, 'Pending', '2025-05-16'),
(12, 2, 10, 32504, 'Pending', '2025-05-18'),
(13, 1, 8, 19890, 'Pending', '2025-05-18'),
(14, 1, 8, 18020, 'Pending', '2025-05-18'),
(15, 3, 9.33333, 47488, 'Pending', '2025-05-20'),
(16, 1, 12, 18000, 'Pending', '2025-05-20'),
(17, 1, 9, 10450, 'Pending', '2025-05-20'),
(18, 3, 8, 38900, 'Pending', '2025-05-20'),
(19, 1, 11, 20567, 'Pending', '2025-05-20'),
(20, 1, 11, 20567, 'Pending', '2025-05-20'),
(21, 1, 12, 29409, 'Pending', '2025-05-22'),
(22, 3, 7.66667, 54340, 'Pending', '2025-05-22'),
(23, 1, 8, 9835, 'Pending', '2025-05-22');

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
(1, 'Niraj', 'Gurung', 'grgniraj01@gmail.com', '4ALnc1p4WLctwZ+FULb8HAp/zuCmP8/G/ynS+7gWw9g7lxCb1c0ZTqNL/WubTZJI5PH7W90=', '9812345678', 'Bhotebahal,Kathmandu', 'Admin', 'hagen-indra-vaYxtGpgzfU-unsplash.jpg'),
(2, 'Customer', 'Gurung', 'grgniraj@gmail.com', 'xRMCLou1Pbfqok2RVI3c4A6xXkT1gLDYr3mbw02KuItXwU4Ypd0fH+jtQnnZhRfjVCgRjaRlOFw=', '9812345679', 'Manchester', 'Customer', '3845387.jpg'),
(6, 'Pratha', 'Lala', 'pran122@gmail.com', 'ct9DI5NXRlmWEz3+eyr3TQLB6GpI7kGhrg0CXZP/aepTHrF6hQR7MaHLgeCVeYhad0v2Yz1p', '9811223344', 'Singapore', 'Customer', 'photo.jpeg'),
(23, 'Suraj', 'Sah', 'surajhero@gmail.com', 'L72sEa732QbX6xBdruoJJMlkm73KD556c9V0C7YUhMLG2naqem4lam7fDssMXxKfYkR+xw==', '9812345678', 'Bihar,India', 'Customer', 'photo.jpeg'),
(25, 'Nb', 'Gurung', 'grg@gmail.com', 'eCGtdLJ/Ezcv+qOw19b6hjTbMhJkeM7xhGLqDYLcwb18sLWH5Qqfgjd/I0dIvnx5Trytiu8=', '9813909088', 'Bhotebahal,Kathmandu', 'Customer', 'photo.jpeg'),
(26, 'Pratha', 'Lalaaaaaa', 'lala@gmail.com', '9uprvutWTasZtZwwEpPC3oyy0qPlOL8Y3cJw+mTO+OLtwxZFlKoAdMs269gFLw2z41clQA==', '9813434343', 'Janakpur', 'Customer', 'photo.jpeg'),
(32, 'Nissan', 'Karki', 'nissankarki@gmail.com', 'tZVYwHH3tB0/U7f57kyJC4sPsOMdGhqlWyQc3NPQB+Jf0qax0rLAm+p6OQ2s3qCy6aHqrvToxoE=', '9823220990', 'Kathmandu', 'Customer', 'pp.jpg'),
(47, 'Rejika', 'Maharjan', 'rejy@gmail.com', 'e7wn8YAzm1yI8tWi3rWQtraT73lwFGFedfDI1fXb6POoHsl9GchDky0b/UfPH5vzOdeOovm9', '9813908080', 'Samakhusi', 'Customer', 'att.xdEepbgIm4J6Dy8j7MarDGHvl-HKDUxCRaQ6v_1klm0.jpg'),
(48, 'Shashi', 'Gurung', 'sas@gmail.com', 'GZeRMlnr5Bbzj6zityHHBh2Mt6lg8Lc3fi41e57ut3H6lBA6M2MTFrZ874Qqzn+jBDdnlttk', '9821212121', 'New York', 'Customer', 'photo.jpeg'),
(49, 'Shiksha', 'Gurung', 'sikshack@gmail.com', 'lIs9LBlrZhNj5tKxd3M/bYgVMuvQQh2U9v6MoxGxjOQmjnWmGV3jUbPx9QVCtGBaRZ65HwWW2g==', '9821212112', 'New York', 'Customer', 'Screenshot 2025-05-11 at 11.28.19 PM.png'),
(50, 'Ojaswi', 'Gurung', 'nn@gmail.com', 'RYzabywowVWcilfX2d6dJUaj5JccuHGjWoWvbDyAoH2GaqS6BqzOaHp3wlM9AX4i0jKA3RA=', '9813909051', 'Bhotebahal,Kathmandu', 'Customer', 'Screenshot 2024-08-19 at 10.34.07 AM.png'),
(51, 'Shristhi', 'Gurung', 'em@gmail.com', 'FOy13TDJ4sRn/k5RB3xWlYBTHM/yGhyo/i/ZoznWxuHrU2183SdvqYv3Tu0nsweDgoGj0AY=', '9813909055', 'Bhotebahal,Kathmandu', 'Customer', '3845387.jpg'),
(52, 'E', 'Gurung', 'egrg@gmail.com', 'b/1OfXVY3bZD0DnEHvm1HROWF5frO9acD4XOHY/lT0+Nd3pbbIF+DfGDguu6BvuXKql5qPQ=', '9813909038', 'Bhotebahal,Kathmandu', 'Customer', '');

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
(47, 38, 25),
(48, 31, 31),
(48, 47, 30),
(48, 52, 32),
(49, 42, 38),
(49, 50, 39),
(50, 31, 44),
(50, 34, 45),
(50, 51, 43);

-- --------------------------------------------------------

--
-- Table structure for table `user_sneaker_order`
--

CREATE TABLE `user_sneaker_order` (
  `UserID` int(11) NOT NULL,
  `SneakerID` int(11) NOT NULL,
  `OrderID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_sneaker_order`
--

INSERT INTO `user_sneaker_order` (`UserID`, `SneakerID`, `OrderID`) VALUES
(2, 17, 21),
(2, 42, 7),
(2, 42, 22),
(2, 45, 23),
(2, 54, 11),
(47, 17, 6),
(47, 22, 6),
(47, 24, 6),
(47, 50, 8),
(47, 54, 8),
(48, 22, 9),
(48, 24, 9),
(48, 47, 9),
(48, 51, 9),
(49, 19, 10),
(49, 22, 10),
(49, 24, 10),
(49, 50, 10),
(49, 51, 10),
(50, 16, 12),
(50, 46, 12),
(50, 51, 13),
(50, 52, 14),
(52, 16, 16),
(52, 19, 19),
(52, 19, 20),
(52, 20, 17),
(52, 20, 18),
(52, 21, 15),
(52, 31, 18),
(52, 34, 15),
(52, 47, 15);

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
(14, 2, 31),
(15, 2, 29),
(16, 47, 51),
(17, 47, 42),
(18, 48, 45),
(19, 48, 17),
(20, 49, 43),
(21, 49, 17),
(22, 49, 47),
(23, 49, 50),
(24, 50, 47),
(25, 52, 17),
(26, 52, 47),
(27, 52, 23);

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
  MODIFY `CartID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `OrderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `sneaker`
--
ALTER TABLE `sneaker`
  MODIFY `SneakerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `wishlist`
--
ALTER TABLE `wishlist`
  MODIFY `WishListID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

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
