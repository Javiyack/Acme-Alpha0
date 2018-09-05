DROP DATABASE IF EXISTS `Acme-Alpha0`;
CREATE DATABASE `Acme-Alpha0`;

USE `Acme-Alpha0`;

GRANT SELECT, INSERT, UPDATE, DELETE 
  ON `Acme-Alpha0`.* TO 'acme-user'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, 
    CREATE TEMPORARY TABLES, LOCK TABLES, CREATE VIEW, CREATE ROUTINE, 
    ALTER ROUTINE, EXECUTE, TRIGGER, SHOW VIEW
  ON `Acme-Alpha0`.* TO 'acme-manager'@'%';