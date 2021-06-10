-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT count(*) FROM retail;

-- number of clients (e.g. unique client ID)
SELECT count(distinct customer_id) FROM retail;

-- invoice date range (e.g. max/min dates)
SELECT max(invoice_date) as max, min(invoice_date) as min FROM retail;

-- unique stock code
SELECT count(distinct stock_code) FROM retail;

-- Calculate average invoice amount excluding invoices with a negative amount
SELECT avg(invTotal.totalAmt) as avg
from (
    -- this can also be created as a view and referenced!
    SELECT sum(unit_price*quantity) as totalAmt FROM retail group by invoice_no having sum(unit_price*quantity) > 0
) as invTotal;

-- Calculate total revenue
SELECT sum(invTotal.totalAmt) as avg
from (
    SELECT sum(unit_price*quantity) as totalAmt FROM retail group by invoice_no
) as invTotal;

-- Calculate total revenue by YYYYMM
-- Run 1st
CREATE VIEW monthlyRevenue AS
SELECT
	cast(extract(year FROM invoice_date) AS integer) yyyy,
	cast(extract(month FROM invoice_date) AS integer) mm, quantity * unit_price AS amount
FROM retail;

-- Run 2nd
SELECT cast(yyyy * 100 + mm AS text) yyyymm , sum(amount)
FROM monthlyRevenue
GROUP BY yyyy, mm
ORDER BY yyyymm ASC;



