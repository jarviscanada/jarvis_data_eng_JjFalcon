-- automation script to create 2 tables and insert sample values for query testing

\c host_agent;

-- create the host information table
CREATE TABLE [IF NOT EXISTS] PUBLIC.host_info
(
host_info_id		SERIAL NOT NULL,
host_number		INT NOT NULL,
host_name		VARCHAR(75) UNIQUE NOT NULL,
cpu_number		INT NOT NULL,
cpu_architecture	VARCHAR(10) NOT NULL,
cpu_model		VARCHAR(75) NOT NULL,
cpu_mhz			FLOAT(5) NOT NULL,
L2_cache		INT NOT NULL,
total_mem		INT NOT NULL,
time_capture		TIMESTAMPTZ NOT NULL,
PRIMARY KEY(id)
);

-- create the host usage table
CREATE TABLE [IF NOT EXISTS] PUBLIC.host_usage
(
host_usage_id		SERIAL NOT NULL,
host_info_id		INT NOT NULL,
memory_free		INT NOT NULL,
cpu_idle		NUMERIC(3,2),
cpu_kernel		NUMERIC(3,2),
disk_io			INT NOT NULL,
disk_available		INT NOT NULL,
PRIMARY KEY(host_usage_id),
CONSTRAINT fk_host_info
	FOREIGN KEY(host_info_id)
		REFERENCES PUBLIC.host_info(host_info_id)
);

-- created duplicate sample tables [sample_info/usage] for testing purposes
-- add sample data into host_information table for query testing
INSERT INTO sample_info
	(host_info_id, host_number, host_name, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_capture)
VALUES
	(DEFAULT, 1, 'node1A.jrvs.ca', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 256, 750000, '2019-05-29 15:00:00.000'),
	(DEFAULT, 1, 'node1B.jrvs.ca', 2, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 512, 600000, '2019-05-29 15:01:00.000'),
	(DEFAULT, 1, 'node1C.jrvs.ca', 1, 'x86_128', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 256, 900000, '2019-05-29 15:02:00.000'),
	(DEFAULT, 1, 'node1D.jrvs.ca', 2, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 512, 900000, '2019-05-29 15:04:00.000'),
	(DEFAULT, 1, 'node1E.jrvs.ca', 2, 'x86_128', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 512, 500000, '2019-05-29 15:10:00.000'),
	(DEFAULT, 2, 'node2A.jrvs.ca', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 256, 300000, '2019-05-29 15:11:00.000'),
	(DEFAULT, 2, 'node2B.jrvs.ca', 2, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 512, 600000, '2019-05-29 15:12:00.000'),
	(DEFAULT, 2, 'node2C.jrvs.ca', 1, 'x86_128', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 256, 500000, '2019-05-29 15:13:00.000'),
	(DEFAULT, 2, 'node2D.jrvs.ca', 2, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 512, 900000, '2019-05-29 15:14:00.000'),
	(DEFAULT, 2, 'node2E.jrvs.ca', 2, 'x86_128', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 512, 500000, '2019-05-29 15:20:00.000');

-- add sample data into host_usage table for query testing
INSERT INTO sample_usage
	(host_usage_id, host_info_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES
	(1, 1, 300000, 0.9, 0.04, 2, 3),
	(2, 2, 100000, 0.8, 0.04, 1, 3),
	(3, 3, 400000, 0.6, 0.03, 1, 2),
	(4, 4, 350000, 0.7, 0.02, 3, 4),
	(5, 5, 200000, 0.8, 0.03, 2, 2),
	(6, 6, 300000, 0.9, 0.05, 2, 1),
	(7, 7, 250000, 0.9, 0.04, 2, 2),
	(8, 8, 100000, 0.9, 0.04, 2, 1),
	(9, 9, 300000, 0.5, 0.04, 2, 1),
	(10, 10, 300000, 0.5, 0.04, 2, 2);

