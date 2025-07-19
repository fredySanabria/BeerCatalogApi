
INSERT INTO Manufacturer (name,country) VALUES ('Carlsberg & Heineken group', 'Belgium');
INSERT INTO Manufacturer (name,country) VALUES ('Anheuser-Busch InBev', 'Mexico');
INSERT INTO Manufacturer (name,country) VALUES ('Erdinger Weißbräu', 'Germany');
INSERT INTO Manufacturer (name,country) VALUES ('Moritz', 'España');

CREATE TABLE Beer (id IDENTITY NOT NULL PRIMARY KEY,name VARCHAR(30),ABV DECIMAL(4,2),type VARCHAR(20), description TEXT, manufacturer_id INT,
CONSTRAINT FK_ManufacturerBeer FOREIGN KEY (manufacturer_id) REFERENCES Manufacturer(id));

INSERT INTO Beer VALUES (1,'Erdinger Weissbier',5.3, 'Wheat', 'Wheat beer, brewed following the classic Bavarian tradition, with fine yeast and achieving a harmonious balance of different aromas, gently spiced with wheat and slightly bitter hops.',3);
INSERT INTO Beer VALUES (2,'Mort Subite',4, 'Lambic', 'Beer made from young lambic beer, with dark beers that are completely dissolved within five months. Fruity taste and bitter aroma. The flavor combines the sweetness of cherries with a slight bitterness.',1);
INSERT INTO Beer VALUES (3,'Moritz 7',5.5, 'Lager', 'A bottom-fermented lager beer made with barley malt, water, and hops. It is characterized by its aged golden color, abundant white foam, and an intense flavor with notes of toasted grains and a balanced bitterness.',4);
INSERT INTO Beer VALUES (4,'Modelo especial',4.5, 'Lager', 'Modelo Especial represents the pride and tradition of Mexico. It is an American Lager perfect for sharing with family and friends thanks to its perfect balance of malt and hops.',2);
