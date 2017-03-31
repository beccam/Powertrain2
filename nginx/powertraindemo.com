server {
	listen 80;

	index index.html;

	server_name www.powertraindemo.com;

	location / {
		rewrite ^/$ http://www.powertraindemo.com/game/index.html redirect;
		proxy_pass http://127.0.0.1:9000/;    
	}
}
