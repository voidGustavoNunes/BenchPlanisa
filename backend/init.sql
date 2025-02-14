CREATE USER IF NOT EXISTS 'benchuser'@'%' IDENTIFIED BY 'benchpass';
GRANT ALL PRIVILEGES ON benchdb.* TO 'benchuser'@'%';
FLUSH PRIVILEGES;