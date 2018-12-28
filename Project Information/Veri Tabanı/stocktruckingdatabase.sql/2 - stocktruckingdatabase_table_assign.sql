
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
(1, 20, '2018-12-24', 1),
(1, 2, '2018-12-21', 1),
(6, 5, '2018-12-21', 1),
(7, 15, '2018-12-24', 1),
(3, 1, '2018-12-24', 1),
(4, 10, '2018-12-24', 1);
