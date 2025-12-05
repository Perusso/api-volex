CREATE TABLE cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL UNIQUE,
    cardholder_name VARCHAR(255) NOT NULL,
    security_code VARCHAR(255) NOT NULL,
    expiration_date VARCHAR(5) NOT NULL,
    password VARCHAR(255),
    is_virtual BOOLEAN NOT NULL DEFAULT FALSE,
    original_card_id BIGINT,
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE,
    type VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    employee_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

CREATE INDEX idx_cards_type ON cards(type);

engine=InnoDB