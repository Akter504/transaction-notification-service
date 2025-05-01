--Users
CREATE TABLE IF NOT EXISTS Users
(
    id bigserial PRIMARY KEY,
    email varchar(40) NOT NULL UNIQUE,
    phone_number varchar(20) NOT NULL UNIQUE,
    name_user varchar(20) NOT NULL,
    surname_user varchar(20) NOT NULL,
    password_hash varchar(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_mail ON Users(email);

--Accounts
CREATE TYPE currency_type AS ENUM ('USD', 'EUR', 'RUB');
CREATE TYPE account_type AS ENUM ('DEBIT', 'CREDIT', 'SAVINGS');

CREATE TABLE IF NOT EXISTS Accounts (
    id bigserial PRIMARY KEY,
    balance bigint DEFAULT 0 CHECK (balance >= 0),
    currency currency_type NOT NULL,
    type account_type NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users ON DELETE CASCADE
);

--Transactions
CREATE TYPE transaction_status AS ENUM ('PENDING', 'SUCCESS', 'FAILED');

CREATE TABLE IF NOT EXISTS Transactions (
    id uuid PRIMARY KEY,
    from_account_id bigint NOT NULL,
    to_account_id bigint NOT NULL,
    amount bigint NOT NULL CHECK (amount > 0),
    status transaction_status NOT NULL DEFAULT 'PENDING',
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comment text,
    FOREIGN KEY (from_account_id) REFERENCES Accounts,
    FOREIGN KEY (to_account_id) REFERENCES Accounts,
    CONSTRAINT no_self_transaction CHECK ( from_account_id != to_account_id )
);

CREATE INDEX idx_transactions_status ON Transactions(status);
CREATE INDEX idx_transactions_created_at ON Transactions(created_at);
CREATE INDEX idx_transactions_from_account ON Transactions(from_account_id);
CREATE INDEX idx_transactions_to_account ON Transactions(to_account_id);

--Receipts
CREATE TABLE IF NOT EXISTS Receipts (
    id uuid PRIMARY KEY,
    transaction_id uuid NOT NULL,
    filename text NOT NULL,
    file_extension varchar(10) NOT NULL,
    file_size bigint NOT NULL,
    mime_type varchar(100) NOT NULL,
    s3_bucket varchar(255) NOT NULL,
    s3_key text NOT NULL,
    uploaded_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES Transactions,
    CONSTRAINT unique_s3_location UNIQUE (s3_bucket, s3_key)
);

CREATE INDEX idx_receipts_transaction ON Receipts(transaction_id);
CREATE INDEX idx_receipts_uploaded_at ON Receipts(uploaded_at);
