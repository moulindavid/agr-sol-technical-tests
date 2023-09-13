CREATE TABLE energy_park
(
    id          UUID        NOT NULL,
    park_key    VARCHAR(255) UNIQUE,
    type        VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_energy_park PRIMARY KEY (id)
);

CREATE TABLE offer
(
    id             UUID        NOT NULL,
    market         VARCHAR(255) NOT NULL,
    energy         DECIMAL NOT NULL,
    price          DECIMAL NOT NULL,
    block_date     TIMESTAMP NOT NULL,
    block_number    INT NOT NULL,
    energy_park_id UUID        NOT NULL,
    CONSTRAINT pk_offer PRIMARY KEY (id)
);

ALTER TABLE offer
    ADD CONSTRAINT FK_OFFER_ON_ENERGYPARKID FOREIGN KEY (energy_park_id) REFERENCES energy_park (id);