----BEGIN TRANSACTION;
--drop database eventholderdb;
--create database eventholderdb;
----COMMIT;

DO
'
DECLARE
BEGIN
   IF EXISTS (SELECT FROM pg_database WHERE datname = 'eventholderdb') THEN
      RAISE NOTICE 'Database already exists';  -- optional
   ELSE
      PERFORM dblink_exec('dbname=' || current_database()  -- current db
                        , 'CREATE DATABASE eventholderdb');
   END IF;
END;
' LANGUAGE PLPGSQL;