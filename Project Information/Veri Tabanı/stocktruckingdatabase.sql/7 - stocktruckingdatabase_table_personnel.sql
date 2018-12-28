
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
(2, 'alihan', '1234', 'Alihan', 'Gürbüz', 2, 2, 1),
(3, 'sebnem', '1234', 'Şebnem', 'Ferah', 4, 4, 1),
(4, 'salih', '123456', 'Salih', 'Darağaç', 4, 3, 1),
(5, 'yasin', 'yasin45', 'Yasin', 'Erkoç', 7, 5, 1),
(6, 'mehmet45', '1246', 'Mehmet', 'Renklidağ', 4, 4, 1),
(7, 'yilmaz', '9874', 'Yılmaz', 'Yıldırım', 3, 4, 1);
