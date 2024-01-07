CREATE TABLE member_data
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    surname     VARCHAR(255) NOT NULL,
    email       VARCHAR(400) NOT NULL,
    id_card_key VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX user_data_email ON member_data (email);
CREATE UNIQUE INDEX user_data_id_card_ket ON member_data (id_card_key);
CREATE INDEX user_data_surname_name ON member_data (surname, name);

CREATE TABLE item
(
    id               UUID PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    author_name      VARCHAR(255) NOT NULL,
    author_surname   VARCHAR(255) NOT NULL,
    publication_year INT
);

CREATE UNIQUE INDEX library_item_name_author ON item (name, author_surname, author_name);
CREATE INDEX library_item_name ON item (name);

CREATE TABLE item_borrow
(
    id         UUID PRIMARY KEY,
    member_id  UUID NOT NULL,
    item_id    UUID NOT NULL,
    start_date DATE,
    end_date   DATE,

    FOREIGN KEY (member_id) REFERENCES member_data (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
);

CREATE INDEX item_borrow_start_end_date ON item_borrow (start_date, end_date);

CREATE TABLE item_stock
(
    item_id              UUID PRIMARY KEY,
    item_count           INT NOT NULL,
    available_item_count INT NOT NULL,

    FOREIGN KEY (item_id) REFERENCES item (id)
)
