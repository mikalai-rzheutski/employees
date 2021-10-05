DO
' BEGIN
CREATE TYPE gender_type AS ENUM (''MALE'', ''FEMALE'');
EXCEPTION
WHEN duplicate_object THEN null;
END ';
CREATE TABLE IF NOT EXISTS employee (
  id  serial  PRIMARY  KEY,
  first_name  VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  department_id INT NOT NULL,
  job_title VARCHAR(100) NOT NULL,
  gender gender_type NOT NULL,
  date_of_birth DATE NOT NULL );

-- Logback: the reliable, generic, fast and flexible logging framework.
-- Copyright (C) 1999-2010, QOS.ch. All rights reserved.
--
-- See http://logback.qos.ch/license.html for the applicable licensing
-- conditions.

-- This SQL script creates the required tables by ch.qos.logback.classic.db.DBAppender
--
-- It is intended for PostgreSQL databases.

CREATE SEQUENCE IF NOT EXISTS logging_event_id_seq MINVALUE 1 START 1;

CREATE TABLE IF NOT EXISTS logging_event
(
  timestmp         BIGINT NOT NULL,
  formatted_message  TEXT NOT NULL,
  logger_name       VARCHAR(254) NOT NULL,
  level_string      VARCHAR(254) NOT NULL,
  thread_name       VARCHAR(254),
  reference_flag    SMALLINT,
  arg0              VARCHAR(254),
  arg1              VARCHAR(254),
  arg2              VARCHAR(254),
  arg3              VARCHAR(254),
  caller_filename   VARCHAR(254) NOT NULL,
  caller_class      VARCHAR(254) NOT NULL,
  caller_method     VARCHAR(254) NOT NULL,
  caller_line       CHAR(4) NOT NULL,
  event_id          BIGINT DEFAULT nextval('logging_event_id_seq') PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS logging_event_property
(
  event_id	      BIGINT NOT NULL,
  mapped_key        VARCHAR(254) NOT NULL,
  mapped_value      VARCHAR(1024),
  PRIMARY KEY(event_id, mapped_key),
  FOREIGN KEY (event_id) REFERENCES logging_event(event_id)
);

CREATE TABLE IF NOT EXISTS logging_event_exception
(
  event_id         BIGINT NOT NULL,
  i                SMALLINT NOT NULL,
  trace_line       VARCHAR(254) NOT NULL,
  PRIMARY KEY(event_id, i),
  FOREIGN KEY (event_id) REFERENCES logging_event(event_id)
);
