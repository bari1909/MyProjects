drop table pais cascade constraints;
drop table estil  cascade constraints;
drop table artista cascade constraints;
drop table art_grup cascade constraints;
drop table art_ind cascade constraints;
drop table h_grup cascade constraints;
drop table producte cascade constraints;
drop table canco cascade constraints;
drop table autoria cascade constraints;
drop table album cascade constraints;
drop table album_contingut cascade constraints;
drop table llista cascade constraints;
drop table llista_contingut cascade constraints;
drop table client cascade constraints;
drop table reproduccio cascade constraints;

create table pais 
(
    pai_iso varchar2(3),
    pai_nom varchar2(100) not null,

    constraint PK_pais primary key (pai_iso),
    constraint un_pais_nom unique (pai_nom)
);

create table estil(
    est_id number GENERATED ALWAYS AS IDENTITY,
    est_nom varchar2(32) NOT NULL,

    constraint pk_estil primary key (est_id),
    constraint un_nom_estil unique (est_nom)
);

create table artista(
    art_id number GENERATED ALWAYS AS IDENTITY,
    art_nom varchar2(32) NOT NULL, 
    art_tipus char(1),

    constraint pk_artista primary key (art_id),
    constraint un_nom_artista unique (art_nom),
    constraint ck_tipus_artista check (art_tipus = 'I' or art_tipus ='G')
);

create table art_grup(
    grp_id number,
    grp_data_creacio date NOT NULL,

    constraint pk_art_grup primary key (grp_id),
    constraint fk_art_grup_artista foreign key (grp_id) references artista(art_id) ON DELETE CASCADE  
    --constraint ck_data_creacio check (grp_data_creacio < sysdate)
);


create table art_ind(
    ind_id number,
    ind_data_naix date NOT NULL,
    ind_nacionalitat varchar2(3) NOT NULL,

    constraint pk_art_ind primary key (ind_id),
    constraint fk_artista foreign key (ind_id) references artista(art_id) ON DELETE CASCADE  ,
    constraint fk_nacionalitat_pais foreign key (ind_nacionalitat) references pais(pai_iso) ON DELETE CASCADE  
    --constraint ck_data_naix check (ind_data_naix < sysdate)  
);


create table h_grup(
    id_artgrup number,
    id_artind number,
    data_inici date,
    data_final date,

    constraint pk_id_art_grup primary key (id_artgrup,id_artind, data_inici),
    constraint fk_art_grup foreign key (id_artgrup) references art_grup(grp_id) ON DELETE CASCADE  ,
    constraint fk_art_ind foreign key (id_artind) references art_ind(ind_id) ON DELETE CASCADE  
    --constraint ck_data_inici check (data_inici < sysdate),
    --constraint ck_data_final check (data_final > data_inici )
);

--Disparador data inici no pot ser futura 
create or replace trigger trg_data_inici_nofutura
before insert or update of data_inici
on h_grup
for each row
declare
begin
    if :new.data_inici > sysdate then
        raise_application_error(-20002, 'La data d inici no pot ser futura');
    end if;
    null;
end;
/

--Disparador de la data_final que no sigui futura a d'inici
create or replace trigger trg_data_final
before insert or update of data_final
on h_grup
for each row 
declare 
begin
    if inserting then
      if :new.data_final < :new.data_inici then
        raise_application_error(-20003, 'La data final no pot ser anterior a la data d inici');
      end if;
    elsif updating then
      if :new.data_final > :old.data_inici then
        raise_application_error(-20004, 'La data final no pot ser anterior a la data d inici');
      end if;
    end if;
    null;
end;
/

create table producte(
    prod_id number GENERATED ALWAYS AS IDENTITY,
    prod_titol varchar2(32) NOt NULL,
    prod_actiu char(1) NOt NULL,
    prod_estil number,
    prod_tipus char(1) NOT NULL,

    constraint PK_PRODUCTE primary key (prod_id),
    constraint ck_actiu check (prod_actiu = 'S' or prod_actiu = 'N'),
    constraint ck_tipus check (prod_tipus = 'C' or prod_tipus = 'A' or prod_tipus = 'L' ),
   
    constraint fk_estil foreign key (prod_estil) references estil(est_id) ON DELETE CASCADE  

);

create index IDX_producte_titol 
on producte(prod_titol);

create table canco(
    can_id number,
    can_any_creacio number not null,
    can_interpret number,
    can_durada number not null,

    constraint pk_canco primary key (can_id),
    constraint fk_canco_id foreign key (can_id) references producte(prod_id) ON DELETE CASCADE  ,
    constraint ck_canco_any_creacio check ( can_any_creacio >=0),
    constraint FK_canco_interpret foreign key (can_interpret) references artista(art_id) ON DELETE CASCADE  ,
    constraint ck_canco_durada check (can_durada > 0)
);

--Disparador any creacio no pot ser futur a l'any actual 
create or replace trigger trg_any_creacio_nofutur
before insert or update of can_any_creacio
on canco
for each row
declare
begin
    if :new.can_any_creacio > to_char(sysdate,'yyyy') then
        raise_application_error(-20005, 'Any creació no pot ser futur de la data actual.');
    end if;
    null;
end;
/

create table autoria(
    id_canco number,
    id_artind number,

    constraint pk_canco_id_canco primary key (id_canco,id_artind),
    constraint fk_canco_id_canco foreign key (id_canco) references canco(can_id) ON DELETE CASCADE  ,
    constraint fk_canco_id_artind foreign key (id_artind) references art_ind(ind_id) ON DELETE CASCADE  
);

create table album(
    alb_id number,
    any_creacio number(4) NOT NULL,
    alb_durada number not null,

    constraint pk_album_alb_id primary key (alb_id),
    constraint fk_album_alb_id foreign key (alb_id) references producte(prod_id) ON DELETE CASCADE  ,
    constraint ck_album_any_creacio check (any_creacio > 0),
    constraint ck_album_alb_durada check (alb_durada >= 0)
);

--Disparador any creacio d'album que no sigui futura 
create or replace trigger trg_alb_anycreacio_nofutur
before insert or update of any_creacio
on album 
for each row
begin 
    if :new.any_creacio > to_char(sysdate, 'yyyy') then
        raise_application_error(-20006, 'Any creacio d album no pot ser futur de la data actual.');
    end if;
    null;
end;
/

create table album_contingut(
    id_album number,
    id_canco number,
    pos number,

    constraint pk_album_contingut primary key (id_album,id_canco),
    constraint fk_album_contingut_id_album foreign key (id_album) references album(alb_id) ON DELETE CASCADE  ,
    constraint fk_album_contingut_id_canco foreign key (id_canco) references canco(can_id) ON DELETE CASCADE  ,
    constraint ck_album_pos check (pos > 0),
    constraint ck_album_pos_id_album unique (id_album,pos)
);

create table llista(
    lli_id number,
    lli_durada number NOT NULL,

    constraint pk_llista primary key (lli_id),
    constraint fk_llista_lli_id foreign key (lli_id) references producte(prod_id) ON DELETE CASCADE,
    constraint ck_album_durada check (lli_durada >= 0)
);

create table llista_contingut(
    id_llista number,
    id_producte number,
    pos number NOT NULL,

    constraint pk_llista_contingut primary key (id_llista,id_producte),
    constraint fk_llista_contingut_id_llsita foreign key (id_llista) references llista(lli_id) ON DELETE CASCADE,
    constraint un_llista_contingut_id_producte_pos unique (id_producte,pos),
    constraint ck_llista_contingut_pos check (pos >0)
);

create table client(
    cli_id number GENERATED BY DEFAULT AS IDENTITY,
    cli_email varchar2(32),
    cli_nom varchar2(32) NOT NULL,
    cli_cognoms varchar2(32),
    cli_data_naix date, 
    cli_cp number(5),
    cli_dom1 varchar2(50),
    cli_dom2 varchar2(50), 
    cli_poblacio varchar2(32),
    cli_pais varchar2(3),

    constraint pk_client primary key (cli_id),
    constraint un_client_cli_email unique (cli_email),
    --constraint ck_client_data_naix check (cli_data_naix < sysdate),
    constraint fk_client_pais foreign key (cli_pais) references pais(pai_iso)
);

--Disparador data naixement de client no futura 
create or replace trigger trg_data_naix_client 
before insert or update of cli_data_naix
on client
for each row 
begin
    if :new.cli_data_naix > sysdate then
        raise_application_error(-20007, 'La data naixement de client no pot ser futura.');
    end if;
    null;

end;
/

create index IDX_client_nom_cognoms 
on client(cli_nom,cli_cognoms);

create table reproduccio (
    id_client number ,
    moment_temporal date, 
    id_producte number,

    constraint pk_reproduccio primary key (id_client, moment_temporal),
    constraint fk_reproduccio_id_client foreign key (id_client) references client(cli_id) ON DELETE CASCADE,
    constraint fk_reproduccio_id_producte foreign key (id_producte) references producte(prod_id) ON DELETE CASCADE
);



--Assignar les reproduccions del client eliminat al virtual 
-- Y PROHIBIR L'ELIMINACIO DEL CLIENT VIRTUAL
create or replace trigger trg_elim_client 
before delete 
on client
for each row  
declare 
    v_id_client_eliminat reproduccio.id_client%type;
    v_moment_client_eliminat reproduccio.moment_temporal%type;
    v_prod_client_eliminat reproduccio.id_producte%type;
begin

    if :old.cli_id = 0 then
        raise_application_error(-20016, 'NO ES POT ELIMINAR EL CLIENT VIRTUAL');
    end if;

    -- select moment_temporal,id_producte
    --   into v_moment_client_eliminat,v_prod_client_eliminat
    --   from reproduccio
    -- where id_client = :old.cli_id;

    -- insert into reproduccio 
    -- values (0,v_moment_client_eliminat,v_prod_client_eliminat);

    update reproduccio set id_client = 0 where id_client = :old.cli_id;
end;
/



