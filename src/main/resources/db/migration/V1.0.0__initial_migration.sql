CREATE TABLE IF NOT EXISTS companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    api_key VARCHAR(255) NOT NULL UNIQUE,
) ENGINE=InnoDB;

-- 2. Tabela employees (depende de companies)
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    company_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Inserir dados iniciais
INSERT INTO companies (id, name, api_key)
VALUES (1, 'Driven', 'zadKLNx.DzvOVjQH01TumGl2urPjPQSxUbf67vs0');

INSERT INTO employees (id, full_name, cpf, email, company_id)
VALUES
    (1, 'Fulano Rubens da Silva', '47100935741', 'fulano.silva@gmail.com', 1),
    (2, 'Ciclana Maria Madeira', '08434681895', 'ciclaninha@gmail.com', 1);



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

    -- Chave estrangeira para employee
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,

    -- Chave estrangeira para original_card_id (cartões virtuais)
    FOREIGN KEY (original_card_id) REFERENCES cards(id) ON DELETE SET NULL,
);

CREATE INDEX idx_cards_type ON cards(type);

engine=InnoDB