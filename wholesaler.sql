-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : lun. 27 mars 2023 à 20:24
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `wholesaler`
--

-- --------------------------------------------------------

--
-- Structure de la table `bills`
--

DROP TABLE IF EXISTS `bills`;
CREATE TABLE IF NOT EXISTS `bills` (
  `billId` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `totalPrice` float NOT NULL,
  `fk_customerId` int(11) NOT NULL,
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`billId`),
  KEY `fk_customer_id` (`fk_customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bills`
--

INSERT INTO `bills` (`billId`, `date`, `totalPrice`, `fk_customerId`, `isDelete`) VALUES
(7, '2023-03-20', 36.1, 4, 1),
(8, '2023-03-20', 10.83, 4, 1),
(9, '2023-03-20', 480, 5, 1),
(10, '2023-03-20', 750, 3, 1),
(11, '2023-03-20', 450, 3, 1),
(12, '2023-03-26', 14.44, 4, 0),
(13, '2023-03-25', 269.85, 3, 0),
(14, '2023-03-26', 154.7, 7, 0),
(15, '2023-03-26', 154.7, 7, 1),
(16, '2023-03-26', 154.7, 7, 1),
(17, '2023-03-26', 154.7, 7, 1);

-- --------------------------------------------------------

--
-- Structure de la table `bills_items`
--

DROP TABLE IF EXISTS `bills_items`;
CREATE TABLE IF NOT EXISTS `bills_items` (
  `bills_itemsId` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `fk_billId` int(11) NOT NULL,
  `fk_itemId` int(11) NOT NULL,
  PRIMARY KEY (`bills_itemsId`),
  KEY `fk_item_id` (`fk_itemId`),
  KEY `fk_bill_id` (`fk_billId`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bills_items`
--

INSERT INTO `bills_items` (`bills_itemsId`, `quantity`, `fk_billId`, `fk_itemId`) VALUES
(14, 10, 7, 2),
(15, 3, 8, 2),
(16, 32, 9, 1),
(17, 50, 10, 1),
(18, 30, 11, 1),
(20, 10, 13, 1),
(21, 15, 13, 3),
(22, 1, 13, 2),
(23, 3, 13, 3),
(24, 1, 13, 3),
(34, 4, 12, 2),
(38, 15, 14, 2),
(39, 3, 14, 6),
(40, 1, 14, 7),
(41, 4, 14, 4),
(42, 15, 15, 2),
(43, 3, 15, 6),
(44, 1, 15, 7),
(45, 4, 15, 4),
(46, 15, 16, 2),
(47, 3, 16, 6),
(48, 1, 16, 7),
(49, 4, 16, 4),
(50, 15, 17, 2),
(51, 3, 17, 6),
(52, 1, 17, 7),
(53, 4, 17, 4);

-- --------------------------------------------------------

--
-- Structure de la table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`customerId`),
  UNIQUE KEY `name` (`firstname`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `customers`
--

INSERT INTO `customers` (`customerId`, `firstname`, `lastname`, `address`, `phoneNumber`, `isDelete`) VALUES
(1, 'Plan-it', 'Bricooo', 'Rue des joncs', '0476908176', 1),
(2, 'Antoine', 'Patel', '1 rue de sébastopol 91700 Sainte-Geneviève-Des-Bois', '01.70.09.11.67', 0),
(3, 'Albertine', 'René', '41 avenue de Provence 75000 Paris', '03.69.14.05.86', 0),
(4, 'Théophile', 'Dionne', '44 cours Franklin Roosevelt 13010 Marseille', '04.00.69.03.55', 0),
(5, 'Morgana', 'Parizeau', '13 rue Reine Elizabeth 5700 Metz', '03.11.15.88.91', 0),
(6, 'Aya', 'Cyr', '17', 'dsds', 1),
(7, 'Susan', 'Dupuy', '24 rue de Groussay 93110 Rosny-Sous-Bois', '01.93.86.84.87', 0),
(8, 'Jean', 'Jacques', '17 rue Borneau 6230 Pont-à-Celles', '0476908169', 1),
(9, 'df', '-10', '0394', '3131', 1);

-- --------------------------------------------------------

--
-- Structure de la table `items`
--

DROP TABLE IF EXISTS `items`;
CREATE TABLE IF NOT EXISTS `items` (
  `itemId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `stock` int(11) NOT NULL,
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `items`
--

INSERT INTO `items` (`itemId`, `name`, `brand`, `price`, `stock`, `isDelete`) VALUES
(1, 'Blocs en béton', 'Big Mat', 15, 150, 0),
(2, 'Fibre de verre', 'Fibatape', 3.61, 40, 0),
(3, 'Ciment 25kg', 'Brico', 8.99, 84, 0),
(4, 'Sac de graviers 20kg', 'Yellow', 5.14, 17, 0),
(5, 'Sac de sable', 'Knauf', 9.19, 41, 0),
(6, 'Sac de sable', 'GAMMA', 15, 17, 1),
(7, 'Outillages', 'Brico Plant-it', 34.99, 9, 0),
(8, 'Test', 'test', 34, 43, 1);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(55) NOT NULL,
  `lastname` varchar(55) NOT NULL,
  `username` varchar(55) NOT NULL,
  `password` varchar(256) NOT NULL,
  `email` varchar(55) NOT NULL,
  `isDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`idUser`, `firstname`, `lastname`, `username`, `password`, `email`, `isDelete`) VALUES
(1, 'Théo', 'Mansy', 'tmansy', 'ddcea25344240815025b06bd0505fca6', 'theo.mansy@gmail.com', 0),
(2, 'Elisa', 'Génicot', 'Elou1605', 'b44bd53fc97237194e5ef54ab320610', 'elisa.genicot2@gmail.com', 0),
(3, 'Zoé', 'Mansy', 'zmansy', '', 'zoe.mansy@gmail.com', 0),
(5, 'Isabelle', 'Franquet', 'Isafranq', '12aafc145069ea7c1aa1917bd1187301', 'isabelle.franquet@gmail.com', 0),
(8, 'Oliver', 'Mansy', 'omansy', '', 'olivier.mansy@gmail.com', 0),
(11, 'Justyne', 'Harmel', 'JHarmel', 'ddcea25344240815025b06bd0505fca6', 'justine.harmel@gmail.com', 0),
(19, 'Luc', 'Dubois', 'LDubois', '47bce5c74f589f4867dbd57e9ca9f808', 'luc.dubois@gmail.com', 1),
(20, 'Jacques', 'Jean', 'JJ', '5c7d0c90cf9e0ce560956179e8e82e7d', 'jean.jacques@gmail.com', 1),
(21, 'Glenn', 'Crompton', 'Glennichouffe', 'ddcea25344240815025b06bd0505fca6', 'moi.moi.miam@hotmail.com', 0),
(22, 'Del Tedesco', 'Pierre', 'Yamisky', 'c07e301690e1dfe4f61b84f11ba0fdbe', 'pierre.dl@gmail.com', 0),
(23, 'Loïc', 'Brugneaux', 'Neutroture', 'a31851770dae6ee96fc886f261c211e7', 'lolodu91@gmail.com', 0),
(27, 'Jean', 'Dujardin', 'JDujardin', 'ddcea25344240815025b06bd0505fca6', 'jean.dujardin@gmail.com', 1),
(28, 'Pallow', 'Sébastian', 'SPallow', 'ddcea25344240815025b06bd0505fca6', 'seb.pal@hotmail.be', 1),
(29, 'Lénoss', 'Koval', 'LKoval', 'ddcea25344240815025b06bd0505fca6', 'll.koval@gmail.com', 1);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `fk_customer_id` FOREIGN KEY (`fk_customerId`) REFERENCES `customers` (`customerId`);

--
-- Contraintes pour la table `bills_items`
--
ALTER TABLE `bills_items`
  ADD CONSTRAINT `fk_bill_id` FOREIGN KEY (`fk_billId`) REFERENCES `bills` (`billId`),
  ADD CONSTRAINT `fk_item_id` FOREIGN KEY (`fk_itemId`) REFERENCES `items` (`itemId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
