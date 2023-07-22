-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Lug 22, 2023 alle 12:26
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `provafinale`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `categorie`
--

CREATE TABLE `categorie` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `descrizione` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `categorie`
--

INSERT INTO `categorie` (`id`, `nome`, `descrizione`) VALUES
(2, 'cancelleria', 'cose come libri, quaderni, penne, etc.'),
(3, 'macelleria', 'carni e salumi direttamente dal banco'),
(4, 'ortofrutta', 'frutta e verdura, fresche di stagione'),
(5, 'surgelati', 'conservare tassativamente a -18 gradi'),
(6, 'igiene e cura del corpo', NULL),
(9, 'pasticceria, gelati e dolci', 'conservare a bassa temperatura, e occhio alle calorie!');

-- --------------------------------------------------------

--
-- Struttura della tabella `clienti`
--

CREATE TABLE `clienti` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `codice_cliente` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `clienti`
--

INSERT INTO `clienti` (`id`, `nome`, `cognome`, `email`, `codice_cliente`) VALUES
(6, 'Valeria', 'Capua', 'vc99@gmail.com', 'C001206806531'),
(7, 'Marco', 'Catapano', 'mc78@gmail.com', 'C006064828111'),
(8, 'Valeria', 'Rizzi', NULL, 'C0095569938X1'),
(9, 'Valentina', 'Catrone', 'vc01@gmail.com', 'C0093842172X1');

-- --------------------------------------------------------

--
-- Struttura della tabella `dettaglivendite`
--

CREATE TABLE `dettaglivendite` (
  `id` bigint(20) NOT NULL,
  `id_vendita` bigint(20) NOT NULL,
  `id_prodotto` bigint(20) NOT NULL,
  `quantita` int(11) NOT NULL CHECK (`quantita` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `dettaglivendite`
--

INSERT INTO `dettaglivendite` (`id`, `id_vendita`, `id_prodotto`, `quantita`) VALUES
(33, 38, 4, 1),
(35, 40, 1, 2),
(46, 41, 2, 1),
(47, 41, 1, 2),
(49, 42, 5, 1),
(50, 42, 1, 2),
(52, 44, 3, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `dipendenti`
--

CREATE TABLE `dipendenti` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `id_mansione` bigint(20) NOT NULL,
  `codice_dipendente` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `dipendenti`
--

INSERT INTO `dipendenti` (`id`, `nome`, `cognome`, `email`, `id_mansione`, `codice_dipendente`) VALUES
(14, 'Andrea', 'Imparato', 'ai89@gmail.com', 2, 'D001670667471'),
(15, 'Andrea', 'Postiglione', 'ap79@gmail.com', 6, 'D001094103171'),
(16, 'Alessandro', 'Caiazza', 'ac01@gmail.com', 6, 'D001421873801'),
(17, 'Roxana', 'Larissa', 'rl69@gmail.com', 1, 'D003618063451'),
(18, 'Francesco', 'Catapone', 'fc87@gmail.com', 2, 'D009125996231');

-- --------------------------------------------------------

--
-- Struttura della tabella `mansioni`
--

CREATE TABLE `mansioni` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `descrizione` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `mansioni`
--

INSERT INTO `mansioni` (`id`, `nome`, `descrizione`) VALUES
(1, 'receptionist', 'accoglie i clienti e li aiuta per le questioni più generiche'),
(2, 'magazziniere', 'gestisce la logistica di magazzino'),
(6, 'commesso', 'sistema i prodotti nelle corsie e aiuta i clienti a trovarli'),
(8, 'addetto pulizie', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `prodotti`
--

CREATE TABLE `prodotti` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `id_categoria` bigint(20) NOT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  `costo` bigint(20) NOT NULL CHECK (`costo` >= 0),
  `codice_prodotto` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `prodotti`
--

INSERT INTO `prodotti` (`id`, `nome`, `id_categoria`, `descrizione`, `costo`, `codice_prodotto`) VALUES
(1, 'Matite 1x', 2, 'singola matita', 100, 'P001765330381'),
(2, 'Matite 3x', 2, 'matite in confezione da 3', 250, 'P003660262741'),
(3, 'Prosciutto cotto', 3, 'Variante cotta del prosciutto', 400, 'P007109587661'),
(4, 'Anguria', 4, 'L\'ideale per quando fa caldo', 500, 'P005502958791'),
(5, 'Prosciutto crudo', 3, 'Variante cruda del prosciutto', 450, 'P001132755391'),
(8, 'ghiacciolo al limone', 9, 'rinfrescante e a basso contenuto calorico', 150, 'P001099310421');

-- --------------------------------------------------------

--
-- Struttura della tabella `vendite`
--

CREATE TABLE `vendite` (
  `id` bigint(20) NOT NULL,
  `codice_vendita` varchar(255) NOT NULL,
  `id_cliente` bigint(20) NOT NULL,
  `data` datetime(6) DEFAULT NULL,
  `pagato` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `vendite`
--

INSERT INTO `vendite` (`id`, `codice_vendita`, `id_cliente`, `data`, `pagato`) VALUES
(38, 'V002084374401', 6, '2023-07-21 11:58:11.000000', 0),
(40, 'V002553950501', 6, '2023-07-21 12:00:17.000000', 1),
(41, 'V005453031581', 6, NULL, 1),
(42, 'V008468426451', 8, '2023-07-21 12:11:19.000000', 0),
(44, 'V001156179241', 8, '2023-07-21 13:04:18.000000', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `clienti`
--
ALTER TABLE `clienti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codice_cliente` (`codice_cliente`);

--
-- Indici per le tabelle `dettaglivendite`
--
ALTER TABLE `dettaglivendite`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_vendita` (`id_vendita`),
  ADD KEY `id_prodotto` (`id_prodotto`);

--
-- Indici per le tabelle `dipendenti`
--
ALTER TABLE `dipendenti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codice_dipendente` (`codice_dipendente`),
  ADD UNIQUE KEY `codice_dipendente_2` (`codice_dipendente`),
  ADD KEY `id_mansione` (`id_mansione`);

--
-- Indici per le tabelle `mansioni`
--
ALTER TABLE `mansioni`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `prodotti`
--
ALTER TABLE `prodotti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codice_prodotto` (`codice_prodotto`),
  ADD KEY `chiaveEsterna` (`id_categoria`);

--
-- Indici per le tabelle `vendite`
--
ALTER TABLE `vendite`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codice_vendita` (`codice_vendita`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `clienti`
--
ALTER TABLE `clienti`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `dettaglivendite`
--
ALTER TABLE `dettaglivendite`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT per la tabella `dipendenti`
--
ALTER TABLE `dipendenti`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT per la tabella `mansioni`
--
ALTER TABLE `mansioni`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `prodotti`
--
ALTER TABLE `prodotti`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `vendite`
--
ALTER TABLE `vendite`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `dettaglivendite`
--
ALTER TABLE `dettaglivendite`
  ADD CONSTRAINT `dettaglivendite_ibfk_1` FOREIGN KEY (`id_vendita`) REFERENCES `vendite` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `dettaglivendite_ibfk_2` FOREIGN KEY (`id_prodotto`) REFERENCES `prodotti` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `dipendenti`
--
ALTER TABLE `dipendenti`
  ADD CONSTRAINT `dipendenti_ibfk_1` FOREIGN KEY (`id_mansione`) REFERENCES `mansioni` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `prodotti`
--
ALTER TABLE `prodotti`
  ADD CONSTRAINT `chiaveEsterna` FOREIGN KEY (`id_categoria`) REFERENCES `categorie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `vendite`
--
ALTER TABLE `vendite`
  ADD CONSTRAINT `vendite_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clienti` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
