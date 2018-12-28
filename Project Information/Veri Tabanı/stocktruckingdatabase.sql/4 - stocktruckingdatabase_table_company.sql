
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
