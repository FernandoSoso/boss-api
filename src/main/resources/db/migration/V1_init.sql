CREATE TYPE user_role AS ENUM (
    'ADMIN',
    'USER'
    );

CREATE TYPE delivery_status AS ENUM (
    'EM_PROGRESSO',
    'CONCLUIDO',
    'CANCELADO'
    );

CREATE TYPE situation_status AS ENUM (
    'DISPONIVEL',
    'EM_TRANSITO',
    'INDISPONIVEL'
    );

CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      uuid VARCHAR(128) NOT NULL,
                      name VARCHAR(50) NOT NULL,
                      email VARCHAR(50) NOT NULL UNIQUE,
                      password VARCHAR(64) NOT NULL,
                      role user_role NOT NULL,

                      status boolean NOT NULL
);

CREATE TABLE truck (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       uuid VARCHAR(128) NOT NULL,
                       license_plate VARCHAR(7) UNIQUE NOT NULL,
                       brand VARCHAR(50),
                       model VARCHAR(50),
                       year INT,
                       capacity DOUBLE,
                       driver_percentage DOUBLE,
                       truck_status situation_status NOT NULL,

                       status boolean NOT NULL
);

CREATE TABLE driver (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        uuid VARCHAR(128) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        primary_phone VARCHAR(15) NOT NULL,
                        secondary_phone VARCHAR(15),
                        email VARCHAR(50) UNIQUE,
                        license_number VARCHAR(11) NOT NULL UNIQUE,
                        license_expiration_date DATE NOT NULL,
                        driver_status situation_status NOT NULL,

                        status boolean NOT NULL
);

CREATE TABLE driver_truck (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              start_date DATE NOT NULL,
                              end_date DATE,
                              driver_id BIGINT NOT NULL,
                              truck_id BIGINT NOT NULL,
                              FOREIGN KEY (driver_id) REFERENCES driver(id),
                              FOREIGN KEY (truck_id) REFERENCES truck(id)
);

CREATE TABLE delivery (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          uuid VARCHAR(128) NOT NULL,
                          origin VARCHAR(255) NOT NULL,
                          destination VARCHAR(255) NOT NULL,
                          value_per_ton DOUBLE NOT NULL,
                          driver_share DOUBLE NOT NULL,
                          weight DOUBLE NOT NULL,
                          observation VARCHAR(255),
                          net_value DOUBLE not null,
                          gross_value DOUBLE not null,
                          driver_truck_id BIGINT NOT NULL,
                          FOREIGN KEY (driver_truck_id) REFERENCES driver_truck(id),
                          delivery_status delivery_status NOT NULL,

                          status boolean NOT NULL
);