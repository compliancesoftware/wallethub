use wallethub;
SELECT ip, count(id) FROM blocked_ips WHERE date >= '2017-01-01 00:00:00' and date <= '2017-01-01 15:30:00' GROUP BY ip HAVING COUNT(id) > 100;