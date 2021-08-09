create table actor (
  actor_id serial not null
  , first_name character varying(45) not null
  , last_name character varying(45) not null
  , last_update timestamp(6) without time zone default now() not null
  , constraint actor_PKC primary key (actor_id)
) ;

create index idx_actor_last_name
  on actor(last_name);

comment on table actor is 'actor';
comment on column actor.actor_id is 'actor_id';
comment on column actor.first_name is 'first_name';
comment on column actor.last_name is 'last_name';
comment on column actor.last_update is 'last_update';
