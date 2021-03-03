-- sample queries

-- list the host information by their memory size in descending order for each cpu number
SELECT	cpu_number, host_info_id, total_mem, avg(total_mem) OVER (PARTITION BY cpu_number) as avg_mem
FROM	sample_info ORDER BY cpu_number ASC, total_mem DESC;

-- create function that rounds the time stamp to every 5 minutes
CREATE FUNCTION round5s(ts timestamptz) RETURNS timestamptz AS
$$
	BEGIN
	RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
	END;
$$
LANGUAGE PLPGSQL;

-- list the host information by its average memory size in %
SELECT	host_number, per5, used_memory, AVG(used_memory) OVER (PARTITION BY per5)
FROM
	(SELECT host_number, round5s(info.time_capture) as per5, (total_mem - memory_free)/total_mem::float as used_memory
	FROM 		sample_info info
	INNER JOIN 	sample_usage
	USING 		(host_info_id)) AS temp;

-- list the host number of the hosts that failed to insert at least 3 entries to the host_usage table every 5 mintues
SELECT 	host_number, count from (select host_number, count(per5) as count
FROM
	(SELECT host_number, round5s(time_capture) as per5
	FROM 		sample_info) as temp
	GROUP BY 	host_number, per5
	ORDER BY 	host_number) as newTemp
	WHERE 		count < 3;
