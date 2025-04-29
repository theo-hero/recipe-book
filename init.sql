CREATE TABLE Recipe (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instruction TEXT NOT NULL,
    img_url VARCHAR(255),
    date_created DATE
);

CREATE TABLE Ingredient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Tag (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Recipe_tags (
    recipe_id INTEGER NOT NULL REFERENCES Recipe(id) ON DELETE CASCADE,
    tag_id INTEGER NOT NULL REFERENCES Tag(id),
    PRIMARY KEY (recipe_id, tag_id)
);

CREATE TABLE Unit (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Recipe_ingredients (
    recipe_id INTEGER NOT NULL REFERENCES Recipe(id) ON DELETE CASCADE,
    ingredient_id INTEGER NOT NULL REFERENCES Ingredient(id),
    quantity DECIMAL(10, 2) NOT NULL,
    unit_id INTEGER REFERENCES Unit(id),
    PRIMARY KEY (recipe_id, ingredient_id)
);

CREATE OR REPLACE VIEW recipe_view AS
SELECT 
    r.id,
    r.title,
    r.description,
    r.instruction,
    r.img_url,
    COALESCE(string_agg(DISTINCT i.name || '*' || ri.quantity || '*' || u.name, ':'), '') as ingredients,
    COALESCE(string_agg(DISTINCT t.name, '*'), '') AS tags,
    r.date_created
FROM 
    recipe r
LEFT JOIN 
    recipe_ingredients ri ON r.id = ri.recipe_id
LEFT JOIN 
    ingredient i ON ri.ingredient_id = i.id
LEFT JOIN 
    unit u ON ri.unit_id = u.id
LEFT JOIN 
    recipe_tags rt ON r.id = rt.recipe_id
LEFT JOIN 
    tag t ON rt.tag_id = t.id
GROUP BY 
    r.id, r.title, r.description, r.instruction, r.img_url;
    
INSERT INTO Unit (name) VALUES
('килограмм'),
('штука'),
('чайная ложка'),
('столовая ложка'),
('литр'),
('миллилитр'),
('грамм');

INSERT INTO Ingredient (name) VALUES
('рис'),
('курица'),
('лук'),
('чеснок'),
('оливковое масло'),
('томатная паста'),
('соль'),
('перец черный'),
('базилик'),
('спагетти'),
('сыр пармезан'),
('мука'),
('яйцо'),
('молоко'),
('сахар'),
('куриный бульон'),
('помидоры'),
('красное вино'),
('соевый соус'),
('имбирь'),
('рисовая лапша');

INSERT INTO Tag (name) VALUES
('итальянская кухня'),
('азиатская кухня'),
('быстрое приготовление'),
('вегетарианское'),
('основное блюдо'),
('десерт'),
('закуска');

INSERT INTO Recipe (title, description, instruction, img_url, date_created) VALUES
('Паста карбонара', 'Классическое итальянское блюдо из спагетти с яйцом и сыром.', '1. Отварите спагетти до состояния "аль денте". 2. В отдельной миске смешайте яйцо, сыр пармезан и немного соли. 3. Смешайте горячие спагетти с яичной смесью, чтобы яйцо слегка загустело. 4. Подавайте с дополнительным сыром по вкусу.', 'https://example.com/pasta-carbonara.jpg', '2023-10-01'),
('Жареный рис с курицей', 'Азиатский рецепт жареного риса с курицей и овощами.', '1. Обжарьте нарезанную курицу до готовности. 2. Добавьте лук и чеснок, обжарьте еще 2 минуты. 3. Введите рис и соевый соус, перемешайте. 4. Готовьте до полного прогрева.', 'https://example.com/chicken-fried-rice.jpg', '2023-10-01'),
('Шоколадный торт', 'Простой рецепт шоколадного торта для сладкоежек.', '1. Смешайте муку, сахар, какао и другие ингредиенты. 2. Выпекайте в духовке 30 минут. 3. Охладите и украсьте по желанию.', 'https://example.com/chocolate-cake.jpg', '2023-10-01');

INSERT INTO Recipe_tags (recipe_id, tag_id) VALUES
(1, (SELECT id FROM Tag WHERE name = 'итальянская кухня')),
(1, (SELECT id FROM Tag WHERE name = 'основное блюдо')),
(2, (SELECT id FROM Tag WHERE name = 'азиатская кухня')),
(2, (SELECT id FROM Tag WHERE name = 'основное блюдо')),
(3, (SELECT id FROM Tag WHERE name = 'десерт'));

INSERT INTO Recipe_ingredients (recipe_id, ingredient_id, quantity, unit_id) VALUES
(1, (SELECT id FROM Ingredient WHERE name = 'спагетти'), 200, (SELECT id FROM Unit WHERE name = 'грамм')),
(1, (SELECT id FROM Ingredient WHERE name = 'яйцо'), 2, (SELECT id FROM Unit WHERE name = 'штука')),
(1, (SELECT id FROM Ingredient WHERE name = 'сыр пармезан'), 50, (SELECT id FROM Unit WHERE name = 'грамм')),
(2, (SELECT id FROM Ingredient WHERE name = 'рис'), 300, (SELECT id FROM Unit WHERE name = 'грамм')),
(2, (SELECT id FROM Ingredient WHERE name = 'курица'), 200, (SELECT id FROM Unit WHERE name = 'грамм')),
(2, (SELECT id FROM Ingredient WHERE name = 'соевый соус'), 2, (SELECT id FROM Unit WHERE name = 'столовая ложка')),
(3, (SELECT id FROM Ingredient WHERE name = 'мука'), 200, (SELECT id FROM Unit WHERE name = 'грамм')),
(3, (SELECT id FROM Ingredient WHERE name = 'сахар'), 150, (SELECT id FROM Unit WHERE name = 'грамм')),
(3, (SELECT id FROM Ingredient WHERE name = 'молоко'), 100, (SELECT id FROM Unit WHERE name = 'миллилитр'));