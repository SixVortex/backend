-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 06, 2019 at 11:36 AM
-- Server version: 5.7.23-0ubuntu0.16.04.1
-- PHP Version: 7.0.32-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL COMMENT 'Comment ID',
  `image_id` int(11) NOT NULL COMMENT 'ID of image that was commented on',
  `user_id` int(11) NOT NULL COMMENT 'ID of user who made the comment',
  `text` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT 'Comment Text',
  `date` datetime NOT NULL COMMENT 'Date when comment was made'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Stand-in structure for view `fame_image`
--
CREATE TABLE `fame_image` (
`image_id` int(11)
,`user_id` int(11)
,`user` varchar(64)
,`date` datetime
,`title` varchar(64)
,`location` varchar(256)
,`image` text
,`rating` decimal(32,0)
,`fame_count` bigint(21)
,`shame_count` bigint(21)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `full_image_info`
--
CREATE TABLE `full_image_info` (
`image_id` int(11)
,`user_id` int(11)
,`user` varchar(64)
,`date` datetime
,`title` varchar(64)
,`location` varchar(256)
,`image` text
,`rating` decimal(32,0)
,`fame_count` bigint(21)
,`shame_count` bigint(21)
);

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `id` int(11) NOT NULL COMMENT 'Image ID',
  `user_id` int(11) NOT NULL COMMENT 'The user who uploded the image',
  `date` datetime NOT NULL COMMENT 'Date of picture when it was taken',
  `title` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Title for picture',
  `location` varchar(256) COLLATE utf8_bin NOT NULL COMMENT 'Location of where the picture was taken',
  `image` text COLLATE utf8_bin NOT NULL COMMENT 'Image name on the filesystem'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `image_user_rating`
--

CREATE TABLE `image_user_rating` (
  `user_id` int(11) NOT NULL COMMENT 'User who rated',
  `image_id` int(11) NOT NULL COMMENT 'Image that was rated',
  `score` int(11) NOT NULL COMMENT 'The rating (-1 = shame / 1 = fame)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Triggers `image_user_rating`
--
DELIMITER $$
CREATE TRIGGER `check_rating` BEFORE INSERT ON `image_user_rating` FOR EACH ROW BEGIN
	IF (NEW.score > -1) THEN
		SET NEW.score = 1;	
	ELSEIF (NEW.score < -1) THEN
    	SET NEW.score = -1;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `shame_image`
--
CREATE TABLE `shame_image` (
`image_id` int(11)
,`user_id` int(11)
,`user` varchar(64)
,`date` datetime
,`title` varchar(64)
,`location` varchar(256)
,`image` text
,`rating` decimal(32,0)
,`fame_count` bigint(21)
,`shame_count` bigint(21)
);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL COMMENT 'ID of user',
  `username` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Username',
  `password` varchar(256) COLLATE utf8_bin NOT NULL COMMENT 'hashed version of password'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure for view `fame_image`
--
DROP TABLE IF EXISTS `fame_image`;

CREATE  VIEW `fame_image`  AS  select `full_image_info`.`image_id` AS `image_id`,`full_image_info`.`user_id` AS `user_id`,`full_image_info`.`user` AS `user`,`full_image_info`.`date` AS `date`,`full_image_info`.`title` AS `title`,`full_image_info`.`location` AS `location`,`full_image_info`.`image` AS `image`,`full_image_info`.`rating` AS `rating`,`full_image_info`.`fame_count` AS `fame_count`,`full_image_info`.`shame_count` AS `shame_count` from (`image` join `full_image_info`) where ((`full_image_info`.`rating` > -(1)) and (`full_image_info`.`image_id` = `image`.`id`)) ;

-- --------------------------------------------------------

--
-- Structure for view `full_image_info`
--
DROP TABLE IF EXISTS `full_image_info`;

CREATE  VIEW `full_image_info`  AS  select `image`.`id` AS `image_id`,`image`.`user_id` AS `user_id`,(select `user`.`username` from `user` where (`image`.`user_id` = `user`.`id`)) AS `user`,`image`.`date` AS `date`,`image`.`title` AS `title`,`image`.`location` AS `location`,`image`.`image` AS `image`,sum(`image_user_rating`.`score`) AS `rating`,(select count(`image_user_rating`.`score`) AS `count` from `image_user_rating` where ((`image_user_rating`.`score` > 0) and (`image_user_rating`.`image_id` = `image`.`id`))) AS `fame_count`,(select count(`image_user_rating`.`score`) AS `count` from `image_user_rating` where ((`image_user_rating`.`score` < 0) and (`image_user_rating`.`image_id` = `image`.`id`))) AS `shame_count` from (`image` join `image_user_rating`) where (`image_user_rating`.`image_id` = `image`.`id`) group by `image`.`id` ;

-- --------------------------------------------------------

--
-- Structure for view `shame_image`
--
DROP TABLE IF EXISTS `shame_image`;

CREATE  VIEW `shame_image`  AS  select `full_image_info`.`image_id` AS `image_id`,`full_image_info`.`user_id` AS `user_id`,`full_image_info`.`user` AS `user`,`full_image_info`.`date` AS `date`,`full_image_info`.`title` AS `title`,`full_image_info`.`location` AS `location`,`full_image_info`.`image` AS `image`,`full_image_info`.`rating` AS `rating`,`full_image_info`.`fame_count` AS `fame_count`,`full_image_info`.`shame_count` AS `shame_count` from (`image` join `full_image_info`) where ((`full_image_info`.`rating` < 0) and (`full_image_info`.`image_id` = `image`.`id`)) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `image_id` (`image_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `image_user_rating`
--
ALTER TABLE `image_user_rating`
  ADD KEY `image_id` (`image_id`),
  ADD KEY `user_id` (`user_id`,`image_id`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Comment ID', AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Image ID', AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID of user', AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`);

--
-- Constraints for table `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `image_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `image_user_rating`
--
ALTER TABLE `image_user_rating`
  ADD CONSTRAINT `image_user_rating_ibfk_1` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  ADD CONSTRAINT `image_user_rating_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
