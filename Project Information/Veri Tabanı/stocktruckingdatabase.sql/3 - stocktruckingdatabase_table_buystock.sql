
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
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `buystock`
--

INSERT INTO `buystock` (`productID`, `brand`, `definition`, `price`, `companyID`) VALUES
(16, 'EXPER ', 'ACTION DEX777 INTEL CORE İ7 7700 3.6GHZ 8GB 1TB INTEL HD 630 WIN10+OFFICE', 4980, 1),
(28, 'Asus ', 'Amd Radeon R7 240 OC 4GB 128Bit DDR3 (DX12) PCI-E 3.0 Ekran Kartı ', 585, 9),
(27, 'Apple ', 'MacBook Air 13\" Dizüstü Bilgisayar, Intel Core i5, 8 GB RAM, 128 GB SSD, macOS', 5588, 4),
(18, 'DELL ', 'INSPIRON 13 7380 INTEL® CORE™ i7 IŞLEMCI 1.8GHZ-8GB-256GB SSD-INT-13.3\"W10', 6952, 1),
(29, 'MSI ', 'NVIDIA GeForce GTX 1050 TI Gaming 4G 4GB 128 bit GDDR5 DX(12) PCI-E 3.0 Ekran Kartı', 1302, 9),
(26, 'Lenovo ', 'V330 81AX00DQTX 15.6\" Dizüstü Bilgisayar Intel Core i5-8250U, 8GB RAM, 1TB HDD, AMD Radeon 530 2GB, FreeDOS', 3498, 4),
(21, 'ASUS ', 'GL12CP-TR011T İ5 8400 2.8 GHZ 16 GB 1TB+128SSD 6 GB NVIDIA GTX1060 WIN10', 8986, 1),
(22, 'ASUS ', 'GL12CP-TR008T İ7 8700 3.2 GHZ 16 GB 1TB+256SSD 6 GB NVIDIA GTX1060 WIN10', 10826, 1),
(23, 'MSI ', 'AEGIS 3 CORE İ7 8700 3.2 GHZ 8 GB 2TB+128GB 6 GB NVIDIA GTX1060 WIN10', 8769, 1),
(24, 'HP', 'OMEN by HP INTEL CORE İ7 8700K 3.7GHZ 32GB 2TB+16GB 8GB NVIDIA GTX1070 WIN10', 11019, 1),
(25, 'MSI ', 'AEGIS 3 CORE İ7 8700 3.2 GHZ 16 GB 2TB+256GB 8 GB NVIDIA GTX1070Ti WIN10', 10989, 1),
(30, 'SanDisk ', 'Ultra 3D 500GB 560MB-530MB/s Sata 3 2.5\" SSD SDSSDH3-500G-G25', 572, 9),
(31, 'Samsung ', '970 Evo 250GB NVME M.2 SSD (3400/1500MB/S) MZ-V7E250BW', 572, 9),
(32, 'Seagate', '4Tb 5400Rpm Sata3 256Mb St4000Dm004', 689.9, 9),
(33, 'Samsung', '970 Evo 250GB NVME M.2 SSD (3400/1500MB/S) MZ-V7E250BW', 572.9, 9),
(34, 'AMD ', 'Ryzen 3 1300X 3.5 Ghz 8 MB AM4+ 65W İşlemci', 887.9, 9),
(35, 'AMD ', 'Ryzen 3 1300X 3.5 Ghz 8 MB AM4+ 65W İşlemci', 887.9, 9),
(36, 'Asus ', '27\" MX279H Geniş Ekran AH-IPS LED Monitor', 2599, 5),
(43, 'Apple', 'MacBook', 5151.9, 7),
(38, 'LENOVO ', 'ideapad 120S Intel® Celeron® N3350-4GB DDR4 Ram-128 GB SSD-14.0\" FHD-Win10-Mavi 81A5007WTX', 2033, 2),
(39, 'ACER ', 'SF314-52-32PC i3-7130U 4GB 128 SSD 14\'\' FHD Swift 3 Ultrabook', 2999, 2),
(40, 'ACER ', 'SF314-52-32PC i3-7130U 4GB 128 SSD 14\'\' FHD Swift 3 Ultrabook', 2999, 2),
(41, 'CASPER ', 'F650.8250-8145T-G i5 8250 18 1TB+128SDD 2-940MX Laptop', 4199, 2),
(42, 'APPLE ', 'MRE82TU/A Intel Core i5-128GB-13\" MacBook Air Uzay Grisi', 9199, 2);
