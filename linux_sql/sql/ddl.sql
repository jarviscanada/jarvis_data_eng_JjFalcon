-- automation script to create 2 tables

\c host_agent;

-- create the host information table
CREATE TABLE [IF NOT EXISTS]PUBLIC.host_info
(
host_info_id		SERIAL NOT NULL,
hostname		VARCHAR(75) UNIQUE NOT NULL,
cpu_number		INT NOT NULL,
cpu_architecture	VARCHAR(10) NOT NULL,
cpu_model		VARCHAR(75) NOT NULL,
cpu_mhz		FLOAT(5) NOT NULL,
L2_cache		INT NOT NULL,
total_mem		INT NOT NULL,
time_capture		TIMESTAMPTZ NOT NULL,
PRIMARY KEY(host_info_id)
);

-- create the host usage table
CREATE TABLE [IF NOT EXISTS] PUBLIC.host_usage
(
host_usage_id		SERIAL NOT NULL,
host_info_id		INT NOT NULL,
memory_free		INT NOT NULL,
cpu_idle		NUMERIC(3,2),
cpu_kernel		NUMERIC(3,2),
disk_io		INT NOT NULL,
disk_available		INT NOT NULL,
time_capture		TIMESTAMPTZ NOT NULL,
PRIMARY KEY(host_usage_id),
CONSTRAINT fk_host_info
	FOREIGN KEY(host_info_id)
		REFERENCES PUBLIC.host_info(host_info_id)
);
