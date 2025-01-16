-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : mariadb
-- Généré le : ven. 11 oct. 2024 à 08:15
-- Version du serveur : 10.4.1-MariaDB-1:10.4.1+maria~bionic
-- Version de PHP : 8.2.8

CREATE DATABASE IF NOT EXISTS projet_devops_test;
USE projet_devops_test;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projet_devops_test`
--

-- --------------------------------------------------------

--
-- Structure de la table `Compte`
--

CREATE TABLE `Compte` (
  `numeroCompte` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `solde` double NOT NULL,
  `avecDecouvert` varchar(5) NOT NULL,
  `decouvertAutorise` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Compte`
--

INSERT INTO `Compte` (`numeroCompte`, `userId`, `solde`, `avecDecouvert`, `decouvertAutorise`) VALUES
('AB7328887341', 'j.doe2', 4242, 'AVEC', 123),
('AV1011011011', 'g.descomptes', 5, 'AVEC', 100),
('BD4242424242', 'j.doe1', 100, 'SANS', NULL),
('CADNV00000', 'j.doe1', 42, 'AVEC', 42),
('CADV000000', 'j.doe1', 0, 'AVEC', 42),
('CSDNV00000', 'j.doe1', 42, 'SANS', NULL),
('CSDV000000', 'j.doe1', 0, 'SANS', NULL),
('IO1010010001', 'j.doe2', 6868, 'SANS', NULL),
('KL4589219196', 'g.descomptesvides', 0, 'AVEC', 150),
('KO7845154956', 'g.descomptesvides', 0, 'SANS', NULL),
('LA1021931215', 'j.doe1', 100, 'SANS', NULL),
('MD8694030938', 'j.doe1', 500, 'SANS', NULL),
('PP1285735733', 'a.lidell1', 37, 'SANS', NULL),
('SA1011011011', 'g.descomptes', 10, 'SANS', 0),
('TD0398455576', 'j.doe1', 23, 'AVEC', 500),
('XD1829451029', 'j.doe1', -48, 'AVEC', 100);

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `userId` varchar(50) NOT NULL,
  `nom` varchar(45) NOT NULL,
  `prenom` varchar(45) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `userPwd` varchar(255) DEFAULT NULL,
  `male` bit(1) NOT NULL,
  `type` varchar(10) NOT NULL,
  `numClient` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `Utilisateur`
--

INSERT INTO `Utilisateur` (`userId`, `nom`, `prenom`, `adresse`, `userPwd`, `male`, `type`, `numClient`) VALUES
('a.lidell1', 'Lidell', 'Alice', '789, grande rue, Metz', '$argon2i$v=19$m=65536,t=2,p=1$y779BBrZQZxcDGAH+ze+dw$mjJFNL4D8y0Wg2hvQrlm3yPInFZO3HSiuYXbQaCPXP4', b'1', 'CLIENT', '9865432100'),
('admin', 'Smith', 'Joe', '123, grande rue, Metz', '$argon2i$v=19$m=65536,t=2,p=1$39EgK07HAWaSCGzzo0WWkg$VwlJznvpW5mfRqQNmCGUryWfoMTFZctB0cQP0dLcf2c', b'1', 'MANAGER', ''),
('c.exist', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$argon2i$v=19$m=65536,t=2,p=1$+oZ9wlPOEIEhGFS07uMEug$+cfxhZR+idkILN2TlP225SYN7jb7aRnKnAvjCuTVOZA', b'1', 'CLIENT', '0101010101'),
('g.descomptes', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$argon2i$v=19$m=65536,t=2,p=1$cM7t4GXYNdpgZa4BGaFivw$616R5jL9u2QAWVeXRP6wkpVcCCIZUV4G1rADlOIGUHc', b'1', 'CLIENT', '1000000001'),
('g.descomptesvides', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$argon2i$v=19$m=65536,t=2,p=1$sG9bH+dTwrvwsQ5tTMhHjQ$dtUi5tlrCFJYfIS8YeRfSDcvJxHpc2aq1N4ceyQNU04', b'1', 'CLIENT', '0000000002'),
('g.exist', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$argon2i$v=19$m=65536,t=2,p=1$zJQTFypqjSwedg5S4iTAAg$f7ex6rAhCD7ziRIRxcMVloOSPmyqUVue+muRrxJ4vvs', b'1', 'CLIENT', '1010101010'),
('g.pasdecompte', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$argon2i$v=19$m=65536,t=2,p=1$EG0HZpnN5GTs7sJDS0QJig$FR0q9zoz+sQavgnJ6KrEy/eGq+dJebc+1HvxSlsfPpE', b'1', 'CLIENT', '5544554455'),
('j.doe1', 'Doe', 'Jane', '456, grand boulevard, Brest', '$argon2i$v=19$m=65536,t=2,p=1$hHpUBJfQ+mODezWr177N3w$zl3YnIzOa002dxbiaygq6zk3aHaWG5mIHPJicRMsSpE', b'1', 'CLIENT', '1234567890'),
('j.doe2', 'Doe', 'John', '457, grand boulevard, Perpignan', '$argon2i$v=19$m=65536,t=2,p=1$y0dB1mJCUvYJFclxRCi0iA$kz8Hk2HuxIgz2LT0B5rKVnL2PD3zZH0mUSJGNhZe5hA', b'1', 'CLIENT', '0000000001');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Compte`
--
ALTER TABLE `Compte`
  ADD PRIMARY KEY (`numeroCompte`),
  ADD KEY `index_userClient` (`userId`);

--
-- Index pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `numClient_UNIQUE` (`numClient`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Compte`
--
ALTER TABLE `Compte`
  ADD CONSTRAINT `fk_Compte_userId` FOREIGN KEY (`userId`) REFERENCES `Utilisateur` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
