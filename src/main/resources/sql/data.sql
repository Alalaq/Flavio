INSERT INTO restaurants (name, general_rating, price, address, description)
VALUES
    ('The Hungry Bear', null, '$$', 'Moscow, Tverskaya st. 1', 'The Hungry Bear is a cozy restaurant with a wide range of dishes. It is famous for its burgers and delicious desserts.'),
    ('La Bella Vita', null, '$$$',  'St. Petersburg, Nevsky Prospekt 2', 'La Bella Vita is an upscale Italian restaurant with a romantic atmosphere. The menu includes a variety of traditional Italian dishes, and the wine list is extensive.'),
    ('Golden Dragon', null, '$$', 'Kazan, Bauman st. 10', 'Golden Dragon is a Chinese restaurant that also serves Japanese and Korean cuisine. The menu includes a variety of noodles, dumplings, and stir-fries.'),
    ('Le Chat Noir', null, '$$$', 'Nizhny Novgorod, Gorky st. 1', 'Le Chat Noir is a French restaurant that also serves Italian and Russian cuisine. The atmosphere is elegant and romantic, and the menu includes a variety of classic French dishes.'),
    ('The Red Dragon', null, '$', 'Ekaterinburg, Lenin st. 5', 'The Red Dragon is a casual Chinese restaurant with a laid-back atmosphere. The menu includes a variety of rice and noodle dishes, as well as soups and appetizers.'),
    ('Mamma Mia', null, '$$', 'Novosibirsk, Lenina st. 2', 'Mamma Mia is an Italian restaurant that also serves American and Mexican cuisine. The menu includes a variety of pizzas, pastas, and burgers.'),
    ('The Golden Phoenix', null, '$$$', 'Samara, Leningradskaya st. 10', 'The Golden Phoenix is a Chinese restaurant that also serves Japanese and Korean cuisine. The menu includes a variety of dim sum, sushi, and Korean barbecue.');



CREATE OR REPLACE FUNCTION update_restaurant_rating()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE restaurants SET general_rating =
                               (SELECT AVG((rating))
                                FROM restaurant_cuisine_rating
                                WHERE restaurant_cuisine_rating.restaurant_id = NEW.restaurant_id)
    WHERE restaurants.restaraunt_id = NEW.restaurant_id;
    RETURN NEW;
END;
$$LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_restaurant_rating_trigger
    AFTER INSERT OR UPDATE OR DELETE ON restaurant_cuisine_rating
    FOR EACH ROW
EXECUTE FUNCTION update_restaurant_rating();

INSERT INTO restaurant_cuisine_rating (restaurant_id, cuisine, rating)
VALUES
    (1, 'AMERICAN', 4.3),
    (1, 'ITALIAN', 4.5),
    (1, 'MEXICAN', 3.8),
    (1, 'JAPANESE', 3.9),
    (1, 'KOREAN', 4.2),
    (2, 'ITALIAN', 4.6),
    (2, 'FRENCH', 4.8),
    (2, 'AMERICAN', 4.3),
    (2, 'CHINESE', 3.9),
    (2, 'JAPANESE', 4.1),
    (2, 'KOREAN', 4.4),
    (3, 'CHINESE', 4.0),
    (3, 'JAPANESE', 4.1),
    (3, 'KOREAN', 4.2),
    (4, 'FRENCH', 4.7),
    (4, 'ITALIAN', 4.6),
    (4, 'RUSSIAN', 4.3),
    (5, 'CHINESE', 3.8),
    (5, 'JAPANESE', 3.9),
    (5, 'KOREAN', 4.1),
    (6, 'AMERICAN', 4.2),
    (6, 'ITALIAN', 4.4),
    (6, 'MEXICAN', 3.9),
    (7, 'CHINESE', 4.1),
    (7, 'JAPANESE', 4.2),
    (7, 'KOREAN', 4.3);

