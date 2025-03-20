-- Cities table insertion
INSERT INTO cities(attraction_city, city) values 
('CPH', 'Copenhagen'),
('ROS', 'Roskilde'),
('BIL', 'Billund'),
('AAH', 'Aarhus'),
('HEL', 'Helsingør'),
('RIB', 'Ribe'),
('SKA', 'Skagen');

-- Tags insertion
INSERT INTO tags (tag_name) VALUES
    ('CHILD_FRIENDLY'),
    ('GRATIS'),
    ('ART'),
    ('MUSEUM'),
    ('NATURE'),
    ('HISTORY'),
    ('CULTURAL INHERITANCE'),
    ('ROYAL FAMILY');
    
-- Attraction insertion
INSERT INTO attractions(attraction_name, attraction_description, attraction_city) VALUES
	('Tivoli', 'Amusement park in Copenhagen', 'CPH'),
    ('Amalienborg', 'Where the king and queen live', 'CPH'),
    ('Viking Ship Museum', 'A museum about the Vikings and their ships', 'ROS'),
    ('Legoland Billund', 'A theme park based on Lego bricks', 'BIL'),
    ('Aarhus Art Museum (ARoS)', 'A modern art museum with the famous rainbow panorama', 'AAH'),
    ('Kronborg Castle', 'The castle that inspired Shakespeare\'s Hamlet', 'HEL'),
    ('Ribe Viking Center', 'An open-air museum showcasing Viking life', 'RIB'),
    ('Skagen Grenen', 'The northernmost point of Denmark where two seas meet', 'SKA'),
    ('Den Gamle By', 'An open-air museum depicting Danish urban history', 'AAH');
    
INSERT INTO tags_for_attractions (attraction_id, tag_id) VALUES
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Tivoli'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CHILD_FRIENDLY')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Amalienborg'),
     (SELECT tag_id FROM tags WHERE tag_name = 'ROYAL FAMILY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Amalienborg'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CULTURAL INHERITANCE')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Amalienborg'),
     (SELECT tag_id FROM tags WHERE tag_name = 'HISTORY')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Viking Ship Museum'),
     (SELECT tag_id FROM tags WHERE tag_name = 'HISTORY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Viking Ship Museum'),
     (SELECT tag_id FROM tags WHERE tag_name = 'MUSEUM')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Viking Ship Museum'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CHILD_FRIENDLY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Viking Ship Museum'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CULTURAL INHERITANCE')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Legoland Billund'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CHILD_FRIENDLY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Legoland Billund'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CULTURAL INHERITANCE')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Aarhus Art Museum (ARoS)'),
     (SELECT tag_id FROM tags WHERE tag_name = 'ART')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Aarhus Art Museum (ARoS)'),
     (SELECT tag_id FROM tags WHERE tag_name = 'MUSEUM')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Kronborg Castle'),
     (SELECT tag_id FROM tags WHERE tag_name = 'HISTORY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Kronborg Castle'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CULTURAL INHERITANCE')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Kronborg Castle'),
     (SELECT tag_id FROM tags WHERE tag_name = 'ROYAL FAMILY')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Ribe Viking Center'),
     (SELECT tag_id FROM tags WHERE tag_name = 'HISTORY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Ribe Viking Center'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CHILD_FRIENDLY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Ribe Viking Center'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CULTURAL INHERITANCE')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Skagen Grenen'),
     (SELECT tag_id FROM tags WHERE tag_name = 'NATURE')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Skagen Grenen'),
     (SELECT tag_id FROM tags WHERE tag_name = 'GRATIS')),

    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Den Gamle By'),
     (SELECT tag_id FROM tags WHERE tag_name = 'HISTORY')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Den Gamle By'),
     (SELECT tag_id FROM tags WHERE tag_name = 'MUSEUM')),
    ((SELECT attraction_id FROM attractions WHERE attraction_name = 'Den Gamle By'),
     (SELECT tag_id FROM tags WHERE tag_name = 'CULTURAL INHERITANCE'));


		