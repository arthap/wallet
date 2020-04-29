
create table clients
(
  id           uuid not null
    constraint clients_pkey
      primary key,
  email        varchar(255)
    constraint uk_srv16ica2c1csub334bxjjb59
      unique,
  first_name   varchar(255),
  last_name    varchar(255),
  phone        varchar(255)
    constraint uk_e3it7h0veoeergtkfqxhos5qj
      unique,
  created_time timestamp
);

alter table clients
  owner to postgres;

create table balance_histories
(
  id                     uuid not null
    constraint balance_histories_pkey
      primary key,
  amount                 numeric(19, 2),
  description            varchar(255),
  post_balance           numeric(19, 2),
  timestamp              timestamp,
  transaction_state_type varchar(255),
  transaction_type       varchar(255),
  wallet_id              uuid
);

alter table balance_histories
  owner to postgres;

create table wallets
(
  id           uuid not null
    constraint wallets_pkey
      primary key,
  balance      numeric(19, 2),
  name         varchar(255),
  created_time timestamp,
  client_id    uuid
    constraint fkrog16b0y1jucy75kpvij66b80
      references clients
);

alter table wallets
  owner to postgres;
