--INSERT INTO users (id,user_name, email, password, enabled,first_name,last_name)
--VALUES (1,'ade', 'ade@gmail.com', 'ade12345',true,'ade1','ade2');
--INSERT INTO users (id,user_name, email, password, enabled,first_name,last_name)
--VALUES (2,'seun', 'seun@gmail.com', 'seun1234',true,'seun1','seun2');
--INSERT INTO users (id,user_name, email, password, enabled,first_name,last_name)
--VALUES (3,'adam', 'adam@yhaoo.com', 'adam1234',false,'adam1','adam2');
----inserting into roles
--INSERT INTO roles(role) VALUES ('admin');
--INSERT INTO roles(role) VALUES ('user');
--INSERT INTO user_roles (role_user_id,role_id)
--VALUES (1,1);
--INSERT INTO user_roles (role_user_id,role_id)
--VALUES (2,2);
--INSERT INTO user_roles (role_user_id,role_id)
--VALUES (3,3);
INSERT INTO users (id, username, email, password, enabled, first_name, last_name)
VALUES
(1, 'ade', 'ade@gmail.com', 'ade12345', 1, 'ade1', 'ade2'),
(2, 'seun', 'seun@gmail.com', 'seun1234', 1, 'seun1', 'seun2'),
(3, 'adam', 'adam@yahoo.com', 'adam1234', 0, 'adam1', 'adam2');

INSERT INTO roles (id, role) VALUES (1, 'admin');
INSERT INTO roles (id, role) VALUES (2, 'user');

-- Assigning roles to users
INSERT INTO user_roles (role_user_id, role_id)
VALUES
(1, 1),
(2, 2);









































































































































