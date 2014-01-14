-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 12, 2014 at 11:39 PM
-- Server version: 5.5.33a-MariaDB-log
-- PHP Version: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `RTBM`
--

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE IF NOT EXISTS `movies` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(250) NOT NULL,
  `type` smallint(3) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `camera` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `m_n_domain`
--

CREATE TABLE IF NOT EXISTS `m_n_domain` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `N` float NOT NULL,
  `M` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

-- --------------------------------------------------------

--
-- Table structure for table `parameters`
--

CREATE TABLE IF NOT EXISTS `parameters` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `abbreviation` varchar(30) NOT NULL,
  `unit` varchar(20) NOT NULL,
  `constant` tinyint(1) NOT NULL,
  `category` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `paramter` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=114 ;

-- --------------------------------------------------------

--
-- Table structure for table `parameter_data`
--

CREATE TABLE IF NOT EXISTS `parameter_data` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `parameters_id` int(11) NOT NULL,
  `value` float NOT NULL,
  `user_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=841 ;

-- --------------------------------------------------------

--
-- Table structure for table `parsed_input_files`
--

CREATE TABLE IF NOT EXISTS `parsed_input_files` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `stored_path` varchar(200) NOT NULL,
  `successfully_parsed` tinyint(1) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `name` (`name`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=33667 ;

-- --------------------------------------------------------

--
-- Table structure for table `pictures`
--

CREATE TABLE IF NOT EXISTS `pictures` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(500) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `camera` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `sensor_data_1_day`
--

CREATE TABLE IF NOT EXISTS `sensor_data_1_day` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `wind_speed` float NOT NULL,
  `wind_direction` float NOT NULL,
  `wind_speed_max` float NOT NULL,
  `wind_direction_max` float NOT NULL,
  `hydrometer` float NOT NULL,
  `hydrometer_variance` float DEFAULT NULL,
  `sonar` float DEFAULT NULL,
  `sonar_variance` float DEFAULT NULL,
  `sonar_perc_correct` float DEFAULT NULL,
  `sonar_perc_wrong` float DEFAULT NULL,
  `sonar_perc_outOfWater` float DEFAULT NULL,
  `sonar_perc_error` float DEFAULT NULL,
  `sonar_perc_uncertain` float DEFAULT NULL,
  `safety_factor_00` float DEFAULT NULL,
  `safety_factor_01` float DEFAULT NULL,
  `safety_factor_10` float DEFAULT NULL,
  `safety_factor_11` float DEFAULT NULL,
  `water_speed` float DEFAULT NULL,
  `water_flow_rate` float DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1379 ;

-- --------------------------------------------------------

--
-- Table structure for table `sensor_data_1_hour`
--

CREATE TABLE IF NOT EXISTS `sensor_data_1_hour` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `wind_speed` float DEFAULT NULL,
  `wind_direction` float DEFAULT NULL,
  `wind_speed_max` float DEFAULT NULL,
  `wind_direction_max` float DEFAULT NULL,
  `hydrometer` float DEFAULT NULL,
  `hydrometer_variance` float DEFAULT NULL,
  `sonar` float DEFAULT NULL,
  `sonar_variance` float DEFAULT NULL,
  `sonar_perc_correct` float DEFAULT NULL,
  `sonar_perc_wrong` float DEFAULT NULL,
  `sonar_perc_outOfWater` float DEFAULT NULL,
  `sonar_perc_error` float DEFAULT NULL,
  `sonar_perc_uncertain` float DEFAULT NULL,
  `safety_factor_00` float DEFAULT NULL,
  `safety_factor_01` float DEFAULT NULL,
  `safety_factor_10` float DEFAULT NULL,
  `safety_factor_11` float DEFAULT NULL,
  `water_speed` float DEFAULT NULL,
  `water_flow_rate` float DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=44674 ;

-- --------------------------------------------------------

--
-- Table structure for table `sensor_data_10_min`
--

CREATE TABLE IF NOT EXISTS `sensor_data_10_min` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `wind_speed` float DEFAULT NULL,
  `wind_direction` float DEFAULT NULL,
  `wind_speed_max` float DEFAULT NULL,
  `wind_direction_max` float DEFAULT NULL,
  `hydrometer` float DEFAULT NULL,
  `hydrometer_variance` float DEFAULT NULL,
  `sonar` float DEFAULT NULL,
  `sonar_variance` float DEFAULT NULL,
  `sonar_perc_correct` float DEFAULT NULL,
  `sonar_perc_wrong` float DEFAULT NULL,
  `sonar_perc_outOfWater` float DEFAULT NULL,
  `sonar_perc_error` float DEFAULT NULL,
  `sonar_perc_uncertain` float DEFAULT NULL,
  `safety_factor_00` float DEFAULT NULL,
  `safety_factor_01` float DEFAULT NULL,
  `safety_factor_10` float DEFAULT NULL,
  `safety_factor_11` float DEFAULT NULL,
  `water_speed` float DEFAULT NULL,
  `water_flow_rate` float DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=130324 ;

-- --------------------------------------------------------

--
-- Table structure for table `sensor_data_raw`
--

CREATE TABLE IF NOT EXISTS `sensor_data_raw` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `wind_speed` float NOT NULL,
  `wind_direction` float NOT NULL,
  `hydrometer` float NOT NULL,
  `sonar` float NOT NULL,
  `sonar_type` int(2) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26283590 ;

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE IF NOT EXISTS `settings` (
  `ID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `surename` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `password` varchar(250) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Table structure for table `users_roles`
--

CREATE TABLE IF NOT EXISTS `users_roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `userID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Table structure for table `worst_case_00`
--

CREATE TABLE IF NOT EXISTS `worst_case_00` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `pylon_number` int(11) NOT NULL,
  `N` float NOT NULL,
  `Tx` float NOT NULL,
  `Ty` float NOT NULL,
  `Mx` float NOT NULL,
  `My` float NOT NULL,
  `M` float NOT NULL,
  `cs` float NOT NULL,
  `comb_number` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=410305 ;

-- --------------------------------------------------------

--
-- Table structure for table `worst_case_01`
--

CREATE TABLE IF NOT EXISTS `worst_case_01` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `pylon_number` int(11) NOT NULL,
  `N` float NOT NULL,
  `Tx` float NOT NULL,
  `Ty` float NOT NULL,
  `Mx` float NOT NULL,
  `My` float NOT NULL,
  `M` float NOT NULL,
  `cs` float NOT NULL,
  `comb_number` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=410293 ;

-- --------------------------------------------------------

--
-- Table structure for table `worst_case_10`
--

CREATE TABLE IF NOT EXISTS `worst_case_10` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `pylon_number` int(11) NOT NULL,
  `N` float NOT NULL,
  `Tx` float NOT NULL,
  `Ty` float NOT NULL,
  `Mx` float NOT NULL,
  `My` float NOT NULL,
  `M` float NOT NULL,
  `cs` float NOT NULL,
  `comb_number` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=410275 ;

-- --------------------------------------------------------

--
-- Table structure for table `worst_case_11`
--

CREATE TABLE IF NOT EXISTS `worst_case_11` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `pylon_number` int(11) NOT NULL,
  `N` float NOT NULL,
  `Tx` float NOT NULL,
  `Ty` float NOT NULL,
  `Mx` float NOT NULL,
  `My` float NOT NULL,
  `M` float NOT NULL,
  `cs` float NOT NULL,
  `comb_number` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `timestamp_index` (`timestamp`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=410263 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
