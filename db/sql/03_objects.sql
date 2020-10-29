ALTER SESSION SET CONTAINER = ORCLPDB1;
ALTER SESSION SET CURRENT_SCHEMA = test_user;

create table chachkies (
                           ID INTEGER
    , CNAME VARCHAR2(30)
    , UPD_DATE DATE
);

CREATE OR REPLACE PROCEDURE insert_chachkie (
    ID NUMBER
                                            , CNAME VARCHAR2
                                            , UPD_DATE DATE) IS
BEGIN
    INSERT INTO chachkies (
        "ID", "CNAME", "UPD_DATE"
    )
    VALUES (
               ID, CNAME, UPD_DATE
           );
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE all_chachkies (C_CURSOR OUT SYS_REFCURSOR)
    IS
BEGIN
    OPEN C_CURSOR FOR
        select * from chachkies;
END;
/

CREATE OR REPLACE PROCEDURE delete_chachkies
    IS
BEGIN
    delete from chachkies;
    COMMIT;
END;
/