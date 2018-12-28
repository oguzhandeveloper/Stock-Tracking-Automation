
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
