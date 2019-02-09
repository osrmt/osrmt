-- Create OSRMT Database on PostgreSQL 8.1
-- Script created: 2006/08/26
-- ===============================

-- Database: osrmt
-- ===============================
-- DROP DATABASE osrmt;
CREATE DATABASE osrmt
  WITH OWNER = osrmt
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
;

ALTER DATABASE osrmt 
  OWNER TO osrmt
; 

-- ===============================
-- END OF SCRIPT
-- ===============================
