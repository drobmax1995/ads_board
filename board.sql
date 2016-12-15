-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 16, 2016 at 12:27 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `board`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `ID` int(11) NOT NULL,
  `CONTENT` varchar(100) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `POST_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`ID`, `CONTENT`, `CREATED_AT`, `POST_ID`, `USER_ID`) VALUES
(1, 'qwrcdsc', '2016-12-16 02:20:48', 9, 7),
(2, 'xzczcvsdf', '2016-12-16 02:20:50', 9, 7),
(3, 'xcsdvcsdcs', '2016-12-16 02:20:52', 9, 7),
(4, 'sdfdsfdsfsdf', '2016-12-16 02:21:21', 9, 7),
(5, 'fsdfsdfdsf', '2016-12-16 02:21:23', 9, 7),
(6, 'sdfsdf', '2016-12-16 02:21:25', 9, 7),
(7, 'dsfsdfsd', '2016-12-16 02:21:29', 9, 7);

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `ID` int(11) NOT NULL,
  `CONTENT` varchar(500) NOT NULL,
  `TITLE` varchar(150) NOT NULL,
  `CREATED_AT` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`ID`, `CONTENT`, `TITLE`, `CREATED_AT`) VALUES
(9, 'ewer', 'post123', '2016-12-16 02:18:19');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `USERNAME` varchar(45) NOT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `ROLE` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `USERNAME`, `PASSWORD`, `ROLE`, `email`) VALUES
(2, 'admin', '$2a$10$.l.vtzd76LsmfBItSomcxO9kJNGvWJf5.mATTGU3nIFbbSTo4jcPK', 'admin', ''),
(4, 'test1', '$2a$10$iIJlaX8kUra/T1zYmHa2Gu35NzhLjuPb6VN66OsgczARfknA8WYuy', 'user', ''),
(5, 'test2', '$2a$10$XT.5dzLxFHe7ULLIXqPeTufC2oBjI4aY6Tn8Q.JNvWtw0Ps1N8MCi', 'user', ''),
(6, 'test3', '$2a$10$plmY4jpIRQqDSE21vussuO9ERRzQk3s8yhkgpd3yCdmTVe4IafMOK', 'user', ''),
(7, 'test6', '$2a$10$ZwSwSbEZBdqys5wPhfRCb.mBtWS2EGDY4BIrtd1IizlBu9F2oTC1G', 'user', ''),
(8, 'err', '$2a$10$L247iDCCP1Dn836/y4OmEOeVce5aSmjoqm04l03HttmLCt8bwWjK6', 'user', ''),
(9, 'qwerty', '$2a$10$HotxWTraJlKKuK2WcsXv9.M3ljx/jsM32JGUZD5K7z4vVnEnflwmG', 'user', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_COMMENT_POST_idx` (`POST_ID`),
  ADD KEY `fk_COMMENT_USER1_idx` (`USER_ID`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `fk_COMMENT_POST` FOREIGN KEY (`POST_ID`) REFERENCES `post` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_COMMENT_USER1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
