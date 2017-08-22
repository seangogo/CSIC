//查询最近几周的订单
	SELECT COUNT(DISTINCT id) as OrderCount, CASE
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now()) THEN weekofyear(curdate()) \n" +
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-1 THEN weekofyear(curdate())-1\n" +
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-2 THEN weekofyear(curdate())-2\n" +
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-3 THEN weekofyear(curdate())-3\n" +
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-4 THEN weekofyear(curdate())-4\n" +
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-5 THEN weekofyear(curdate())-5\n" +
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-6 THEN weekofyear(curdate())-6\n" +
			"    WHEN YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-7 THEN weekofyear(curdate())-7\n" +
			"    ELSE 'unknown' END AS orderDate FROM t_repair_order r WHERE r.repair_type=0\n" +
			"GROUP BY orderDate ORDER BY create_time