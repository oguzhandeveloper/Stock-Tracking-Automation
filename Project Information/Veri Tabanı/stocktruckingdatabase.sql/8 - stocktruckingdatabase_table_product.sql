
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
(7, 'Intel', 'Intel Core i7-8700K 3.7 GHz Box 12 MB Smart Cache BX80684I78700K İşlemci', 3166, 7, 0, 1, '2018-12-02'),
(8, 'Intel', 'Intel Core i7-8700 4.60 GHz İşlemci', 2695, 4, 0, 1, '2018-12-12'),
(9, 'Intel', 'Intel Core i9-9900K 3.60GHz 16MB Soket 1151 14nm İşlemci (Fansız) - BX80684I99900K', 3687, 9, 0, 0, '2018-12-17'),
(10, 'Intel', 'Intel Core i9-9900K 3.6 GHz Box 16 MB Smart Cache BX80684I99900K İşlemci', 4472, 4, 1, 0, '2018-12-17'),
(19, 'HP', '4GT27EA CORE İ7 8700T 2.4 GHZ 16 GB 2TB+256GB 2 GB AMD RADEON 530 WIN10 27\"', 7999, 1, 0, 0, '2018-12-24'),
(17, 'HP ', '4GS27EA INTEL CORE İ5 8250U 1.6 GHZ 4 GB 1 TB 2 GB NVIDIA MX110 WIN10 21.5\'', 4239, 1, 0, 0, '2018-12-24'),
(15, 'Monster', 'Abra A5 V13.1.1 15.6\" ', 4699, 1, 1, 0, '2018-12-24'),
(14, 'Asus', 'X542UR-GQ434 Intel Core i5 8250U 4GB 1TB GT930MX Freedos 15.6\" ', 3499, 9, 0, 0, '2018-12-24'),
(20, 'LENOVO', 'AIO 520 CORE İ5 8400T 1.7 GHZ 4 GB 1 TB 2 GB AMD R...', 4999, 4, 1, 0, '2018-12-24'),
(37, 'Asus ', '27\" MX279H Geniş Ekran AH-IPS LED Monitor', 2599, 5, 0, 0, '2018-12-24'),
(44, 'Monster', 'Laptop FULL HD 8GB Ekran Kartı, 16GB RAM DDR4', 9780, 5, 0, 0, '2018-12-25');
