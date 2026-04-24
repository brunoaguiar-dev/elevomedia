UPDATE tb_cliente SET quantidade_posts_mes = 12 WHERE quantidade_posts_mes IS NULL;

ALTER TABLE tb_cliente ALTER COLUMN quantidade_posts_mes SET NOT NULL;
