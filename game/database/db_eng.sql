-- MySQL Script generated by MySQL Workbench
-- Sun Jan 12 15:59:00 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema game2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema game2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `game2` DEFAULT CHARACTER SET utf8 ;
USE `game2` ;

-- -----------------------------------------------------
-- Table `game2`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `last_online` BIGINT(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Planet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Planet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NOT NULL DEFAULT 'Kolonie',
  `size` INT NOT NULL,
  `temperature` INT NOT NULL,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  `material` INT NOT NULL,
  `electronics` INT NOT NULL,
  `fuel` INT NOT NULL,
  `rocketlauncher` INT NULL DEFAULT 0,
  `lasergun` INT NULL DEFAULT 0,
  `iongun` INT NULL DEFAULT 0,
  `shockwavecannon` INT NULL DEFAULT 0,
  `plasmacannon` INT NULL DEFAULT 0,
  `antimatterradiator` INT NULL DEFAULT 0,
  `spacemines` INT NULL DEFAULT 0,
  `planetshield` INT NULL DEFAULT 0,
  `scout` INT NULL DEFAULT 0,
  `hunter` INT NULL DEFAULT 0,
  `cruiser` INT NULL DEFAULT 0,
  `destroyer` INT NULL DEFAULT 0,
  `battleship` INT NULL DEFAULT 0,
  `bomber` INT NULL DEFAULT 0,
  `mothership` INT NULL DEFAULT 0,
  `colonisationship` INT NULL DEFAULT 0,
  `mine` INT NULL DEFAULT 0,
  `factory` INT NULL DEFAULT 0,
  `oilrig` INT NULL DEFAULT 0,
  `fueltank` INT NULL DEFAULT 0,
  `materialstorage` INT NULL DEFAULT 0,
  `electronicstorage` INT NULL DEFAULT 0,
  `spaceshipyard` INT NULL DEFAULT 0,
  `cyborgfactory` INT NULL DEFAULT 0,
  `researchfacility` INT NULL DEFAULT 0,
  `powerplant` INT NULL DEFAULT 0,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Planet_Account_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_planet_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `game2`.`Account` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Fleets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Fleets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hunter` INT NULL,
  `cruiser` INT NULL,
  `destroyer` INT NULL,
  `scout` INT NULL,
  `colonisationship` INT NULL,
  `battleship` INT NULL,
  `bomber` INT NULL,
  `mothership` INT NULL,
  `destinationplanet` INT NOT NULL,
  `startplanet` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Research`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Research` (
  `industry` INT NOT NULL DEFAULT 0,
  `laser` INT NOT NULL DEFAULT 0,
  `logistics` INT NOT NULL DEFAULT 0,
  `engine` INT NOT NULL DEFAULT 0,
  `weapon` INT NOT NULL DEFAULT 0,
  `shield` INT NOT NULL DEFAULT 0,
  `armor` INT NOT NULL DEFAULT 0,
  `Account_id` INT NOT NULL,
  PRIMARY KEY (`Account_id`),
  CONSTRAINT `fk_Forschung_Account1`
    FOREIGN KEY (`Account_id`)
    REFERENCES `game2`.`Account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Events` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `time` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Researches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Researches` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Researchdata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Researchdata` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `level` INT NOT NULL,
  `bonus` INT NOT NULL,
  `material` INT NOT NULL,
  `electronics` INT NOT NULL,
  `fuel` INT NOT NULL,
  `Researches_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_forschungsdaten_forschungen_idx` (`Researches_id` ASC) VISIBLE,
  CONSTRAINT `fk_forschungsdaten_forschungen`
    FOREIGN KEY (`Researches_id`)
    REFERENCES `game2`.`Researches` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Shipdata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Shipdata` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `hitpoints` INT NOT NULL,
  `attack` INT NOT NULL,
  `firerate` INT NOT NULL,
  `shield` INT NOT NULL,
  `material` INT NOT NULL,
  `electronics` INT NOT NULL,
  `fuel` INT NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Production`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Production` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Productiondata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Productiondata` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `level` INT NOT NULL,
  `output` INT NOT NULL,
  `material` INT NOT NULL,
  `electronics` INT NOT NULL,
  `fuel` INT NOT NULL,
  `Production_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Abbaudaten_Abbau1_idx` (`Production_id` ASC) VISIBLE,
  CONSTRAINT `fk_Abbaudaten_Abbau1`
    FOREIGN KEY (`Production_id`)
    REFERENCES `game2`.`Production` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Buildings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Buildings` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Buildingsdata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Buildingsdata` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `level` INT NOT NULL,
  `material` INT NOT NULL,
  `electronics` INT NOT NULL,
  `fuel` INT NOT NULL,
  `Buildings_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Buildingsdata_Buildings1_idx` (`Buildings_id` ASC) VISIBLE,
  CONSTRAINT `fk_Buildingsdata_Buildings1`
    FOREIGN KEY (`Buildings_id`)
    REFERENCES `game2`.`Buildings` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`freeplanets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`freeplanets` (
  `id` INT NOT NULL,
  `x` VARCHAR(45) NOT NULL,
  `y` VARCHAR(45) NOT NULL,
  `free` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game2`.`Defense`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `game2`.`Defense` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `hitpoints` INT NOT NULL,
  `attack` INT NOT NULL,
  `firerate` INT NOT NULL,
  `shield` INT NOT NULL,
  `material` INT NOT NULL,
  `electronics` INT NOT NULL,
  `fuel` INT NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
