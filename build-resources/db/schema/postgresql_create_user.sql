-- Create OSRMT User on PostgreSQL 8.1
-- Script created: 2006/08/26
-- ===============================

-- Role: "osrmt"
-- ===============================
-- DROP ROLE osrmt;
CREATE ROLE osrmt 
  LOGIN
  PASSWORD 'osrmt'
  NOSUPERUSER NOINHERIT CREATEDB NOCREATEROLE
;

-- ===============================
-- END OF SCRIPT
-- ===============================
