create table PlayerBlocks (
    uuid   varchar(36)   not null primary key
,   blocks bigint not null
);


create table Plots (
    id         bigint       not null primary key,
    server     varchar(128) not null,
    home_world varchar(128) not null,
    home_x     double       not null,
    home_y     double       not null,
    home_z     double       not null,
    home_yaw   double       not null,
    home_pitch double       not null
);


create table Areas (
    id       bigint       not null primary key,
    plot_id  bigint       not null,
    world    varchar(128) not null,
    bottom_x integer      not null,
    bottom_z integer      not null,
    top_x    integer      not null,
    top_z    integer      not null,
    foreign key(plot_id) references Plots(id)
);