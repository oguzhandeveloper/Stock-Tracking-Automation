-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1:3306
-- Üretim Zamanı: 22 Ara 2018, 20:57:56
-- Sunucu sürümü: 5.7.23
-- PHP Sürümü: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `stocktrackingdatabase`
--
CREATE DATABASE IF NOT EXISTS `stocktrackingdatabase` DEFAULT CHARACTER SET utf8 COLLATE utf8_turkish_ci;
USE `stocktrackingdatabase`;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `assign`
--

DROP TABLE IF EXISTS `assign`;
CREATE TABLE IF NOT EXISTS `assign` (
  `personnelID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `assignDate` date NOT NULL,
  `active` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `assign`
--

INSERT INTO `assign` (`personnelID`, `productID`, `assignDate`, `active`) VALUES
(1, 2, '2018-12-14', 0),
(1, 3, '2018-12-18', 0),
(4, 7, '2018-12-18', 0),
(1, 6, '2018-12-21', 0),
(2, 6, '2018-12-18', 0),
(6, 9, '2018-12-18', 0),
(1, 4, '2018-12-19', 0),
(1, 8, '2018-12-19', 0),
(3, 5, '2018-12-21', 0),
(1, 3, '2018-12-21', 0),
(1, 9, '2018-12-21', 0),
(2, 5, '2018-12-21', 0),
(4, 8, '2018-12-21', 0),
(4, 3, '2018-12-21', 0),
(5, 8, '2018-12-21', 0),
(6, 1, '2018-12-21', 0),
(2, 7, '2018-12-21', 1),
(7, 5, '2018-12-21', 0),
(7, 5, '2018-12-21', 0),
(1, 2, '2018-12-21', 1),
(6, 5, '2018-12-21', 1),
(2, 9, '2018-12-21', 0),
(1, 1, '2018-12-22', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `buystock`
--

DROP TABLE IF EXISTS `buystock`;
CREATE TABLE IF NOT EXISTS `buystock` (
  `productID` int(11) NOT NULL AUTO_INCREMENT,
  `brand` text COLLATE utf8_turkish_ci NOT NULL,
  `definition` text COLLATE utf8_turkish_ci NOT NULL,
  `price` double NOT NULL,
  `companyID` int(11) NOT NULL,
  PRIMARY KEY (`productID`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `companyID` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`companyID`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `company`
--

INSERT INTO `company` (`companyID`, `name`) VALUES
(1, 'Vatan Bilgisayar'),
(2, 'Media Mark'),
(3, 'Teknosa'),
(4, 'Amazon'),
(5, 'Bimeks'),
(6, 'Casper'),
(7, 'Ali Express'),
(8, 'Asus'),
(9, 'Hepsiburada');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE IF NOT EXISTS `department` (
  `departmentID` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`departmentID`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `department`
--

INSERT INTO `department` (`departmentID`, `name`) VALUES
(1, 'Management'),
(2, 'Sales'),
(3, 'Software'),
(4, 'Accountancy'),
(5, 'Services'),
(6, 'Technical Support');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `jobID` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`jobID`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `job`
--

INSERT INTO `job` (`jobID`, `name`) VALUES
(1, 'Admin'),
(2, 'Sales Responsible'),
(3, 'Department Manager'),
(4, 'Employee'),
(5, 'Project Leader'),
(6, 'Test Developer'),
(7, 'Security'),
(8, 'Consultant');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `personnel`
--

DROP TABLE IF EXISTS `personnel`;
CREATE TABLE IF NOT EXISTS `personnel` (
  `personnelID` int(11) NOT NULL AUTO_INCREMENT,
  `username` text COLLATE utf8_turkish_ci NOT NULL,
  `password` text COLLATE utf8_turkish_ci NOT NULL,
  `name` text COLLATE utf8_turkish_ci NOT NULL,
  `lastname` text COLLATE utf8_turkish_ci NOT NULL,
  `jobID` int(11) NOT NULL,
  `departmentID` int(11) NOT NULL,
  `active` int(11) NOT NULL,
  PRIMARY KEY (`personnelID`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `personnel`
--

INSERT INTO `personnel` (`personnelID`, `username`, `password`, `name`, `lastname`, `jobID`, `departmentID`, `active`) VALUES
(1, 'oguzhan', '1234', 'Oğuzhan', 'Çetinkaya', 1, 1, 1),
(2, 'null', '1234', 'Alihan', 'Gürbüz', 2, 2, 1),
(3, 'sebnem', '1234', 'Şebnem', 'Ferah', 4, 4, 0),
(4, 'salih', '123456', 'Salih', 'Darağaç', 4, 3, 1),
(5, 'null', 'yasin45', 'Yasin', 'Erkoç', 7, 5, 1),
(6, 'mehmet45', '1246', 'Mehmet', 'Renklidağ', 4, 4, 1),
(7, 'yilmaz', '9874', 'Yılmaz', 'Yıldırım', 3, 4, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `productID` int(11) NOT NULL,
  `brand` text COLLATE utf8_turkish_ci NOT NULL,
  `definition` text COLLATE utf8_turkish_ci NOT NULL,
  `price` double(10,0) NOT NULL,
  `companyID` int(11) NOT NULL,
  `isBelong` int(11) NOT NULL DEFAULT '0',
  `isWaste` int(11) NOT NULL DEFAULT '0',
  `purchaseDate` date NOT NULL,
  PRIMARY KEY (`productID`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `product`
--

INSERT INTO `product` (`productID`, `brand`, `definition`, `price`, `companyID`, `isBelong`, `isWaste`, `purchaseDate`) VALUES
(1, 'Apple', 'Apple MacBook Air 13\" Dizüstü Bilgisayar', 5699, 1, 1, 0, '2018-12-15'),
(2, 'Apple', 'Apple 13\" MacBook Pro with Touch Bar', 10515, 4, 1, 0, '2018-12-14'),
(3, 'Apple', 'Apple Macbook Pro 15\", Intel Core i7 2.2 GHz İşlemci', 15997, 3, 0, 1, '2018-12-13'),
(4, 'Apple', 'Intel Xeon W 32GB 1TB SSD Radeon Pro Vega 56 MacOS 27\" 5K Retina All In One Bilgisayar MQ2Y2TU/A', 32690, 4, 0, 1, '2018-12-12'),
(5, 'MSI', 'Gaming27 6QE-001EU Core i7 6700 16GB 2TB+256GB SSD GTX980M', 11700, 5, 1, 0, '2018-11-13'),
(6, 'Lenovo', 'Ideacentre 520-27IKL Intel Core i7 7700T 16GB 2TB + 16GB Intel® Optane™', 8499, 7, 0, 1, '2018-12-06'),
(7, 'Intel', 'Intel Core i7-8700K 3.7 GHz Box 12 MB Smart Cache BX80684I78700K İşlemci', 3166, 7, 1, 0, '2018-12-02'),
(8, 'Intel', 'Intel Core i7-8700 4.60 GHz İşlemci', 2695, 4, 0, 0, '2018-12-12'),
(9, 'Intel', 'Intel Core i9-9900K 3.60GHz 16MB Soket 1151 14nm İşlemci (Fansız) - BX80684I99900K', 3687, 9, 0, 0, '2018-12-17'),
(10, 'Intel', 'Intel Core i9-9900K 3.6 GHz Box 16 MB Smart Cache BX80684I99900K İşlemci', 4472, 4, 0, 0, '2018-12-17');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
