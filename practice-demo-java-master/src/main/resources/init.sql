CREATE TABLE IF NOT EXISTS products_and_services
(
    name text PRIMARY KEY,
    category text,
    price integer
);

CREATE TABLE IF NOT EXISTS members
(
    full_name text PRIMARY KEY,
    birthday date
);

CREATE TABLE IF NOT EXISTS jobs
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    full_name text REFERENCES members(full_name),
    profession text,
    organization text,
    salary integer,
    start_work_at date
);

CREATE TABLE IF NOT EXISTS purchases
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    purchase_at date,
    full_name text REFERENCES members(full_name),
    product_or_service_name text REFERENCES products_and_services(name),
    quantity integer
);