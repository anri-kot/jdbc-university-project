
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `university`
--
CREATE DATABASE IF NOT EXISTS `university` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `university`;

-- --------------------------------------------------------

--
-- Estrutura para tabela `classes`
--

CREATE TABLE `classes` (
  `idClass` varchar(3) NOT NULL,
  `semester` int(1) DEFAULT NULL,
  `Courses_idCourse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `classes`
--

INSERT INTO `classes` (`idClass`, `semester`, `Courses_idCourse`) VALUES
('1A', 1, 1),
('1B', 1, 2),
('1C', 1, 3),
('2A', 2, 1),
('2B', 2, 2),
('2C', 2, 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `courses`
--

CREATE TABLE `courses` (
  `idCourse` int(11) NOT NULL,
  `c_name` varchar(50) NOT NULL,
  `c_duration` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `courses`
--

INSERT INTO `courses` (`idCourse`, `c_name`, `c_duration`) VALUES
(1, 'Data Science', 200),
(2, 'Computer Engineering', 250),
(3, 'Software Development', 150);

-- --------------------------------------------------------

--
-- Estrutura para tabela `enrollments`
--

CREATE TABLE `enrollments` (
  `idEnrollment` int(11) NOT NULL,
  `en_year` int(4) DEFAULT NULL,
  `Students_st_matr` int(11) NOT NULL,
  `Classes_idClass` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `enrollments`
--

INSERT INTO `enrollments` (`idEnrollment`, `en_year`, `Students_st_matr`, `Classes_idClass`) VALUES
(3, 2022, 1, '1A'),
(4, 2022, 2, '2A'),
(5, 2023, 3, '1B'),
(6, 2023, 4, '2B'),
(7, 2024, 5, '1C'),
(8, 2024, 6, '2C'),
(9, 2024, 7, '1A');

-- --------------------------------------------------------

--
-- Estrutura para tabela `grades`
--

CREATE TABLE `grades` (
  `idGrade` int(11) NOT NULL,
  `Students_st_matr` int(11) NOT NULL,
  `grade` double DEFAULT NULL,
  `_idClass` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `grades`
--

INSERT INTO `grades` (`idGrade`, `Students_st_matr`, `grade`, `_idClass`) VALUES
(1, 1, 10, '1A'),
(2, 2, 7, '2A'),
(3, 3, 4, '1B'),
(4, 4, 5.5, '2B'),
(5, 5, 6.8, '1C'),
(6, 6, 9.5, '2C'),
(7, 7, 8.5, '1A');

-- --------------------------------------------------------

--
-- Estrutura para tabela `professors`
--

CREATE TABLE `professors` (
  `pr_matr` int(11) NOT NULL,
  `pr_name` varchar(75) DEFAULT NULL,
  `pr_address` varchar(100) DEFAULT NULL,
  `pr_phone` varchar(11) DEFAULT NULL,
  `pr_ssn` varchar(11) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `Courses_idCourse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `professors`
--

INSERT INTO `professors` (`pr_matr`, `pr_name`, `pr_address`, `pr_phone`, `pr_ssn`, `salary`, `Courses_idCourse`) VALUES
(1, 'Nakabachi Makise', 'Japan', '12312312312', '11111222222', 3500, 1),
(2, 'Sherlock Holmes', 'Britain', '22111222111', '23223322121', 4000, 2),
(3, 'Machado de Assis', 'Brazil', '31231231231', '31231231231', 4199.99, 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `students`
--

CREATE TABLE `students` (
  `st_matr` int(11) NOT NULL,
  `st_name` varchar(75) DEFAULT NULL,
  `st_address` varchar(100) DEFAULT NULL,
  `st_phone` varchar(11) DEFAULT NULL,
  `st_ssn` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `students`
--

INSERT INTO `students` (`st_matr`, `st_name`, `st_address`, `st_phone`, `st_ssn`) VALUES
(1, 'Robert William', 'Texas', '11111111111', '11111111111'),
(2, 'Marcos Castro', 'Brasil', '22222222222', '22222222222'),
(3, 'Bianca Menezes', 'Mexico', '33333333333', '33333333333'),
(4, 'Lucia Takagawa', 'Japan', '41414141414', '44444444444'),
(5, 'Luke Stenford', 'Britain', '55555555555', '55115511551'),
(6, 'Eirin Loren', 'Australia', '55554444545', '55555555555'),
(7, 'Yuki Minase', 'Japan', '66666666666', '66666666666');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`idClass`),
  ADD KEY `fk_Classes_Courses_idx` (`Courses_idCourse`);

--
-- Índices de tabela `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`idCourse`);

--
-- Índices de tabela `enrollments`
--
ALTER TABLE `enrollments`
  ADD PRIMARY KEY (`idEnrollment`),
  ADD KEY `fk_Enrollments_Students1_idx` (`Students_st_matr`),
  ADD KEY `fk_Enrollments_Classes1_idx` (`Classes_idClass`);

--
-- Índices de tabela `grades`
--
ALTER TABLE `grades`
  ADD PRIMARY KEY (`idGrade`),
  ADD KEY `fk_Grades_Students1_idx` (`Students_st_matr`),
  ADD KEY `_idClass` (`_idClass`);

--
-- Índices de tabela `professors`
--
ALTER TABLE `professors`
  ADD PRIMARY KEY (`pr_matr`),
  ADD UNIQUE KEY `pr_ssn_UNIQUE` (`pr_ssn`),
  ADD KEY `fk_Professors_Courses1_idx` (`Courses_idCourse`);

--
-- Índices de tabela `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`st_matr`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `courses`
--
ALTER TABLE `courses`
  MODIFY `idCourse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `enrollments`
--
ALTER TABLE `enrollments`
  MODIFY `idEnrollment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `grades`
--
ALTER TABLE `grades`
  MODIFY `idGrade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `professors`
--
ALTER TABLE `professors`
  MODIFY `pr_matr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `students`
--
ALTER TABLE `students`
  MODIFY `st_matr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `fk_Classes_Courses` FOREIGN KEY (`Courses_idCourse`) REFERENCES `courses` (`idCourse`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `enrollments`
--
ALTER TABLE `enrollments`
  ADD CONSTRAINT `fk_Enrollments_Classes1` FOREIGN KEY (`Classes_idClass`) REFERENCES `classes` (`idClass`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Enrollments_Students1` FOREIGN KEY (`Students_st_matr`) REFERENCES `students` (`st_matr`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `grades`
--
ALTER TABLE `grades`
  ADD CONSTRAINT `fk_Grades_Students1` FOREIGN KEY (`Students_st_matr`) REFERENCES `students` (`st_matr`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`_idClass`) REFERENCES `classes` (`idClass`) ON DELETE CASCADE;

--
-- Restrições para tabelas `professors`
--
ALTER TABLE `professors`
  ADD CONSTRAINT `fk_Professors_Courses1` FOREIGN KEY (`Courses_idCourse`) REFERENCES `courses` (`idCourse`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
