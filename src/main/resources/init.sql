DROP TABLE IF EXISTS burial_requests CASCADE;
DROP TABLE IF EXISTS plots           CASCADE;
DROP TABLE IF EXISTS employees       CASCADE;
DROP TABLE IF EXISTS sectors         CASCADE;
DROP TABLE IF EXISTS customers       CASCADE;

DROP TYPE IF EXISTS plot_status   CASCADE;
DROP TYPE IF EXISTS request_status CASCADE;
DROP TYPE IF EXISTS employee_role  CASCADE;

-- 1. Создание перечислений (ENUM) для статусов и ролей
CREATE TYPE plot_status AS ENUM ('FREE', 'RESERVED', 'OCCUPIED');
CREATE TYPE request_status AS ENUM ('NEW', 'PROCESSING', 'APPROVED', 'REJECTED', 'COMPLETED');
CREATE TYPE employee_role AS ENUM ('ADMIN', 'MANAGER', 'WORKER');

-- 2. Таблица: Заявители (Клиенты)
CREATE TABLE IF NOT EXISTS customers (
                           id BIGSERIAL PRIMARY KEY,
                           full_name VARCHAR(150) NOT NULL,
                           phone VARCHAR(20) NOT NULL,
                           email VARCHAR(100),
                           passport_data VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO customers(full_name, phone, passport_data) VALUES ('Владислав Поздняков', '1231233245', '1111 222222');

-- 3. Таблица: Сектора кладбища
CREATE TABLE IF NOT EXISTS sectors (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO sectors (name, description) VALUES ('1', 'нет');

-- 4. Таблица: Места захоронения
CREATE TABLE IF NOT EXISTS plots (
                       id BIGSERIAL PRIMARY KEY,
                       sector_id BIGINT NOT NULL REFERENCES sectors(id) ON DELETE RESTRICT,
                       row_number INT NOT NULL,
                       plot_number INT NOT NULL,
                       status plot_status DEFAULT 'FREE',
                       length_cm INT DEFAULT 200,     -- Стандартная длина
                       width_cm INT DEFAULT 100,      -- Стандартная ширина
                       coordinates VARCHAR(100),      -- GPS (например, "55.7558, 37.6173")
                       UNIQUE (sector_id, row_number, plot_number) -- Защита от дублей мест
);

-- 5. Таблица: Сотрудники
CREATE TABLE IF NOT EXISTS employees (
                           id BIGSERIAL PRIMARY KEY,
                           full_name VARCHAR(150) NOT NULL,
                           role employee_role DEFAULT 'MANAGER',
                           login VARCHAR(50) UNIQUE NOT NULL,
                           password_hash VARCHAR(255) NOT NULL,
                           is_active BOOLEAN DEFAULT TRUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO employees(full_name, login, password_hash) VALUES ('Гой Гоевич', 'mail@mail.mail', 'password');

-- 6. Таблица: Заявки (содержит данные об умершем)
CREATE TABLE IF NOT EXISTS burial_requests (
                                 id BIGSERIAL PRIMARY KEY,

    -- Внешние ключи
                                 customer_id BIGINT NOT NULL REFERENCES customers(id) ON DELETE RESTRICT,
                                 plot_id BIGINT NOT NULL REFERENCES plots(id) ON DELETE RESTRICT,
                                 employee_id BIGINT REFERENCES employees(id) ON DELETE SET NULL,

    -- Данные умершего (без NOT NULL для возможности прижизненной брони)
                                 deceased_full_name VARCHAR(150),
                                 deceased_birth_date DATE,
                                 deceased_death_date DATE,
                                 deceased_certificate VARCHAR(100),

    -- Метаданные заявки
                                 status request_status DEFAULT 'NEW',
                                 request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 processed_date TIMESTAMP,
                                 total_cost DECIMAL(10, 2) DEFAULT 0.00,
                                 notes TEXT
);

-- 7. Индексы для ускорения работы приложения
-- Поиск свободных мест будет очень частым
CREATE INDEX IF NOT EXISTS idx_plots_status_sector ON plots(sector_id, status);
-- Поиск заявок по статусу (для панели администратора)
CREATE INDEX IF NOT EXISTS idx_requests_status ON burial_requests(status);
-- Поиск всех заявок конкретного клиента (для личного кабинета)
CREATE INDEX IF NOT EXISTS idx_requests_customer ON burial_requests(customer_id);