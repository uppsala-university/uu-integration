CREATE TABLE POTENTIAL_MEMBERSHIP_EVENTS (
  id                 BIGINT NOT NULL IDENTITY PRIMARY KEY,
  date               DATETIME NOT NULL,
  pnr                VARCHAR(255) NOT NULL,
  meType             VARCHAR(255) NOT NULL,
  courseCode         VARCHAR(255),
  reportCode         VARCHAR(255),
  startSemester      VARCHAR(255),
  semester           VARCHAR(255),
  program            VARCHAR(255),
  programOrientation VARCHAR(255),
  origin             VARCHAR(255) NOT NULL,
  origin2            VARCHAR(255),
  processed          BIT NOT NULL DEFAULT 0
);
CREATE INDEX POT_MEMBERSHIP_EVENTS_ID_ORIGIN_IDX ON POTENTIAL_MEMBERSHIP_EVENTS (id, origin);

CREATE TABLE MEMBERSHIP_EVENTS (
  id                 BIGINT NOT NULL IDENTITY PRIMARY KEY,
  date               DATETIME NOT NULL,
  pnr                VARCHAR(255),
  meType             VARCHAR(255) NOT NULL,
  courseCode         VARCHAR(255),
  reportCode         VARCHAR(255),
  startSemester      VARCHAR(255),
  semester           VARCHAR(255),
  program            VARCHAR(255),
  programOrientation VARCHAR(255),
  condition1         VARCHAR(255),
  condition2         VARCHAR(255),
  condition3         VARCHAR(255),
  origin             VARCHAR(255) NOT NULL,
  origin2            VARCHAR(255),
  processed          BIT NOT NULL DEFAULT 0
);

CREATE TABLE MEMBERSHIPS (
  id                 BIGINT NOT NULL IDENTITY PRIMARY KEY,
  date               DATETIME NOT NULL,
  pnr                VARCHAR(255) NOT NULL,
  courseCode         VARCHAR(255) NOT NULL,
  reportCode         VARCHAR(255) NOT NULL,
  startSemester      VARCHAR(255) NOT NULL,
  semester           VARCHAR(255) NOT NULL,
  program            VARCHAR(255),
  programOrientation VARCHAR(255),
  origin             VARCHAR(255) NOT NULL,
  origin2            VARCHAR(255)
);
CREATE INDEX MEMBERSHIPS_PNR_IDX ON MEMBERSHIPS (pnr);
CREATE INDEX MEMBERSHIPS_PNR_COURSECODE_IDX ON MEMBERSHIPS (pnr, courseCode);
CREATE INDEX MEMBERSHIPS_REPORTCODE_STARTSEMESTER_IDX ON MEMBERSHIPS (reportCode, startSemester);
CREATE INDEX MEMBERSHIPS_PNR_REPORTCODE_STARTSEMESTER_IDX ON MEMBERSHIPS (pnr, reportCode, startSemester);
CREATE INDEX MEMBERSHIPS_PNR_COURSECODE_SEMESTER_ORIGIN_IDX ON MEMBERSHIPS (pnr, courseCode, semester, origin);

CREATE TABLE ACC_MEMBERSHIPS (
  date               DATETIME NOT NULL,
  pnr                VARCHAR(255) NOT NULL,
  courseCode         VARCHAR(255) NOT NULL,
  reportCode         VARCHAR(255) NOT NULL,
  semester           VARCHAR(255) NOT NULL,
  program            VARCHAR(255),
  programOrientation VARCHAR(255),
  response           VARCHAR(255),
  condition1         VARCHAR(255),
  condition2         VARCHAR(255),
  condition3         VARCHAR(255),
  programReportCode  VARCHAR(255)
);

CREATE TABLE SP_MEMBERSHIPS (
  pnr                VARCHAR(255) NOT NULL,
  reportCode         VARCHAR(255) NOT NULL,
  startSemester      VARCHAR(255) NOT NULL,
  origin             VARCHAR(255) NOT NULL
);
CREATE INDEX SP_MEMBERSHIPS_PNR_COURSECODE_SEMESTER_IDX ON MEMBERSHIPS (pnr, reportCode, startSemester)
