create TABLE `scores` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_a` varchar(40) NOT NULL,
  `score_a` int() NOT NULL,
  `team_b` varchar(40) NOT NULL,
  `score_b` int() NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;