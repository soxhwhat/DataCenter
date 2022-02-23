create table t_account
(
      id   INTEGER,
      account INTEGER
);

CREATE INDEX index_t_account_account
ON t_account (account);

