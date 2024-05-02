-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 02, 2024 at 07:53 PM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `products`
--

-- --------------------------------------------------------

--
-- Table structure for table `approval_queue`
--

DROP TABLE IF EXISTS `approval_queue`;
CREATE TABLE IF NOT EXISTS `approval_queue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `action` varchar(20) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `price`, `status`, `created_at`, `updated_at`) VALUES
(1, 'Laptop', 800.00, 0, '2024-05-01 06:30:00', '2024-05-02 11:59:02'),
(2, 'Smartphone', 1200.00, 1, '2024-05-02 04:00:00', '2024-05-02 11:59:02'),
(3, 'Headphones', 50.00, 0, '2024-05-03 04:30:00', '2024-05-02 13:04:13'),
(4, 'TV', 4000.00, 1, '2024-05-04 05:30:00', '2024-05-02 13:04:26'),
(5, 'Camera', 8000.00, 1, '2024-05-05 06:30:00', '2024-05-02 13:04:39'),
(6, 'Smartwatch', 300.00, 1, '2024-05-02 07:30:00', '2024-05-02 11:59:02'),
(7, 'test product', 100.00, 1, '2024-05-02 16:55:20', '2024-05-02 16:55:20'),
(8, 'refrigrator', 100.00, 1, '2024-05-02 17:12:41', '2024-05-02 17:12:41'),
(9, 'toys', 5001.00, 1, '2024-05-02 18:06:01', '2024-05-02 18:06:01'),
(10, 'test product', 5001.00, 1, '2024-05-02 19:41:44', '2024-05-02 19:41:44');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
