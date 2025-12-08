-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 03, 2025 at 08:42 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_login_system_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_info`
--

CREATE TABLE `admin_info` (
  `id` int(11) NOT NULL,
  `admin_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_info`
--

INSERT INTO `admin_info` (`id`, `admin_id`) VALUES
(1, 'ADMIN123');

-- --------------------------------------------------------

--
-- Table structure for table `login_logs`
--

CREATE TABLE `login_logs` (
  `log_id` int(11) NOT NULL,
  `student_id` varchar(50) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `course` varchar(50) DEFAULT NULL,
  `year_level` varchar(10) DEFAULT NULL,
  `login_datetime` datetime DEFAULT NULL,
  `semester` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_logs`
--

INSERT INTO `login_logs` (`log_id`, `student_id`, `student_name`, `course`, `year_level`, `login_datetime`, `semester`) VALUES
(1, '2024-11223', 'Luffy D. Monkey', 'BSIT', '2', '2023-12-03 14:30:00', '1st'),
(2, '2023-00194', 'Zita Miro M. Valerio', 'BSIT', '2', '2025-12-03 15:01:24', '1st'),
(3, '2023-00194', 'Zita Miro M. Valerio', 'BSIT', '2', '2025-12-03 15:03:08', '1st'),
(4, '2023-00194', 'Zita Miro M. Valerio', 'BSIT', '2', '2025-12-03 15:12:32', '1st'),
(6, '2025-12323', 'Ichigo Q. Kurosaki', 'BSCrim', '3', '2025-12-03 15:28:10', '1st'),
(7, '2024-00987', 'Mark Cyrell D. Quinio', 'BSIT', '3', '2025-12-03 15:29:36', '1st');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` varchar(50) NOT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `course` varchar(50) DEFAULT NULL,
  `year_level` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `student_name`, `course`, `year_level`) VALUES
('2023-00194', 'Zita Miro M. Valerio', 'BSIT', '2'),
('2024-00987', 'Mark Cyrell D. Quinio', 'BSIT', '3'),
('2024-11223', 'Luffy D. Monkey', 'BSIT', '2'),
('2025-12323', 'Ichigo Q. Kurosaki', 'BSCrim', '3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_info`
--
ALTER TABLE `admin_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login_logs`
--
ALTER TABLE `login_logs`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_info`
--
ALTER TABLE `admin_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `login_logs`
--
ALTER TABLE `login_logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
