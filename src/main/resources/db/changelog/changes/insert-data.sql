-- Insert default users only if 'users' table exists
INSERT INTO usersT (username, email, password)
SELECT 'admin', 'admin@example.com', 'securepassword'
WHERE NOT EXISTS (SELECT 1 FROM usersT WHERE username = 'admin');

-- Insert default organization only if 'organizations' table exists
INSERT INTO organizationsT (name)
SELECT 'Default Organization'
WHERE NOT EXISTS (SELECT 1 FROM organizationsT WHERE name = 'Default Organization');
