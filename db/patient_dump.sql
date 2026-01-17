-- ================================
-- Patient table definition
-- ================================

CREATE TABLE IF NOT EXISTS patient (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(20)
);

-- ================================
-- Sample patient data
-- ================================

INSERT INTO patient (first_name, last_name, date_of_birth, gender) VALUES
('John',  'Doe',     '1985-01-15', 'Male'),
('Jane',  'Doe',     '1990-06-22', 'Female'),
('Alice', 'Brown',   '1978-11-03', 'Female'),
('Bob',   'Smith',   '1982-04-10', 'Male'),
('Mary',  'Johnson', '1995-09-27', 'Female'),
('Peter', 'Williams','1970-12-05', 'Male'),
('Grace', 'Miller',  '1988-03-18', 'Female'),
('James', 'Wilson',  '2000-07-01', 'Male');

