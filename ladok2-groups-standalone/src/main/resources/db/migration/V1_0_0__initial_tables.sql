CREATE TABLE POTENTIAL_MEMBERSHIP_EVENTS (
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date          TIMESTAMP NOT NULL,
  pnr           VARCHAR(255) NOT NULL,
  meType        VARCHAR(255) NOT NULL,
  courseCode    VARCHAR(255),
  reportCode    VARCHAR(255),
  startSemester VARCHAR(255),
  semester      VARCHAR(255),
  origin        VARCHAR(255) NOT NULL,
  origin2       VARCHAR(255),
  processed     BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE MEMBERSHIP_EVENTS (
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date          TIMESTAMP NOT NULL,
  pnr           VARCHAR(255),
  meType        VARCHAR(255) NOT NULL,
  courseCode    VARCHAR(255) NOT NULL,
  reportCode    VARCHAR(255) NOT NULL,
  startSemester VARCHAR(255) NOT NULL,
  semester      VARCHAR(255),
  origin        VARCHAR(255) NOT NULL,
  origin2       VARCHAR(255)
);

CREATE TABLE MEMBERSHIPS (
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date          TIMESTAMP NOT NULL,
  pnr           VARCHAR(255) NOT NULL,
  courseCode    VARCHAR(255) NOT NULL,
  reportCode    VARCHAR(255) NOT NULL,
  startSemester VARCHAR(255) NOT NULL,
  semester      VARCHAR(255) NOT NULL,
  origin        VARCHAR(255) NOT NULL,
  origin2       VARCHAR(255)
);
CREATE INDEX MEMBERSHIPS_PNR_IDX ON MEMBERSHIPS (pnr);
CREATE INDEX MEMBERSHIPS_PNR_COURSECODE_IDX ON MEMBERSHIPS (pnr, courseCode);
CREATE INDEX MEMBERSHIPS_PNR_REPORTCODE_STARTSEMESTER_IDX ON MEMBERSHIPS (pnr, reportCode, startSemester);
CREATE INDEX MEMBERSHIPS_PNR_COURSECODE_SEMESTER_ORIGIN_IDX ON MEMBERSHIPS (pnr, courseCode, semester, origin);

CREATE TABLE ACC_MEMBERSHIPS (
  date           TIMESTAMP NOT NULL,
  pnr            VARCHAR(255) NOT NULL,
  courseCode     VARCHAR(255) NOT NULL,
  reportCode     VARCHAR(255) NOT NULL,
  semester       VARCHAR(255) NOT NULL,
  program        VARCHAR(255),
  orientation    VARCHAR(255),
  response       VARCHAR(255),
  cond           VARCHAR(255),
  cond2          VARCHAR(255),
  cond3          VARCHAR(255),
  progReportCode VARCHAR(255)
);
