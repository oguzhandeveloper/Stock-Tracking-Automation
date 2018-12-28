
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
