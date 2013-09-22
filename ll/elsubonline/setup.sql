insert into Role(admin, name) values(0, "User");
insert into Role(admin, name) values(1, "Administrator");

insert into Parameter(name, description, value) values("SITE_NAME", 		"Nombre del Sitio" "Freematador");
insert into Parameter(name, description, value) values("SITE_URL", 			"URL", "http://localhost:8080");

insert into Parameter(name, description, value) values("MAIL_ADDRESS", 		"Mail Address", "elsubonline@gmail.com");
insert into Parameter(name, description, value) values("MAIL_SMTP_HOST", 	"Mail SMTP Host", "smtp.gmail.com");
insert into Parameter(name, description, value) values("MAIL_SMTP_AUTH", 	"Mail SMTP Auth", "true");
insert into Parameter(name, description, value) values("MAIL_SMTP_TLS", 	"Mail SMTP Start TLS", "true");
insert into Parameter(name, description, value) values("MAIL_SMTP_AUTH", 	"Mail SMTP Port", "587");
 
insert into Parameter(name, description, value) values("MAIL_SMTP_USER", 	"Mail SMTP User", "elsubonline");
insert into Parameter(name, description, value) values("MAIL_SMTP_PASS", 	"Mail SMTP Password", "freematador");

insert into Parameter(name, value, description) values("IMAGE_PATH", 	   "/home/leonardo/images", "Repositorio de imgs");
insert into Parameter(name, value, description) values("AUCTION_CONTROLLER_ENABLED", "true", "Proceso de control de remates");
insert into Parameter(name, value, description) values("BILLING_ENABLED", "true", "Proceso de facturación");
insert into Parameter(name, value, description) values("COMMISSION", 	   "0.05", "Porcentaje de comisión, decimal");

commit;
