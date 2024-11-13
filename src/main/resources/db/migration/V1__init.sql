CREATE TABLE "user" (
                      id SERIAL PRIMARY KEY,
                      uuid UUID DEFAULT gen_random_uuid(),
                      name VARCHAR(255) NOT NULL,
                      email VARCHAR(50) UNIQUE NOT NULL,
                      password VARCHAR(64) NOT NULL,
                      user_role VARCHAR(255) NOT NULL,

                      status boolean NOT NULL
);

CREATE TABLE truck (
                       id SERIAL PRIMARY KEY,
                       uuid UUID DEFAULT gen_random_uuid(),
                       license_plate VARCHAR(7) UNIQUE NOT NULL,
                       brand VARCHAR(50),
                       model VARCHAR(50),
                       year INT NOT NULL,
                       capacity NUMERIC(10,2) NOT NULL,
                       driver_percentage numeric(5,2) NOT NULL ,
                       truck_status VARCHAR(255) NOT NULL,

                       status boolean NOT NULL
);

CREATE TABLE driver (
                        id SERIAL PRIMARY KEY,
                        uuid UUID DEFAULT gen_random_uuid(),
                        name VARCHAR(255) NOT NULL,
                        primary_phone VARCHAR(15) NOT NULL,
                        secondary_phone VARCHAR(15),
                        email VARCHAR(50),
                        license_number VARCHAR(11) UNIQUE NOT NULL,
                        license_expiration_date DATE NOT NULL,
                        driver_status VARCHAR(255) NOT NULL,

                        status boolean NOT NULL
);

CREATE TABLE driver_truck (
                              id SERIAL PRIMARY KEY,
                              uuid UUID DEFAULT gen_random_uuid(),
                              start_date DATE NOT NULL,
                              end_date DATE,
                              driver_id BIGINT NOT NULL,
                              truck_id BIGINT NOT NULL,
                              FOREIGN KEY (driver_id) REFERENCES driver(id),
                              FOREIGN KEY (truck_id) REFERENCES truck(id)
);

CREATE TABLE delivery (
                          id SERIAL PRIMARY KEY,
                          uuid UUID DEFAULT gen_random_uuid(),
                          origin VARCHAR(255) NOT NULL,
                          destination VARCHAR(255) NOT NULL,
                          value_per_ton numeric(12,2) NOT NULL,
                          driver_share numeric(12,2) NOT NULL,
                          weight numeric(12,2) NOT NULL,
                          observation VARCHAR(255),
                          net_value numeric(12,2) not null,
                          gross_value numeric(12,2) not null,
                          driver_truck_id BIGINT NOT NULL,
                          FOREIGN KEY (driver_truck_id) REFERENCES driver_truck(id),
                          delivery_status VARCHAR(255) NOT NULL,

                          status boolean NOT NULL
);