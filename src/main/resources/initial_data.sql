use school_timetable;

insert into calendar_days (name) values ('Monday'),
('Tuesday'),
('Wednesday'),
('Thursday'),
('Friday'),
('Saturday'),
('Sunday');

insert into subjects (name) values ('Chemistry'),
('Informatics'),
('Algebra'),
('Geometry'),
('Belarusian language'),
('Russian language'),
('English language'),
('Belarusian literature'),
('Russian literature'),
('Drawing'),
('Physical'),
('Physics'),
('Biology'),
('History'),
('Labor training'),
('Social science'),
('Life safety');

insert into teachers (subject_id, full_name) values (1, 'Maria Ivanova'),
(1, 'Elena Petrova'),
(2, 'Каterina Larioniva'),
(2, 'Mihail Yurev'),
(3, 'Yrii Leonov'),
(3, 'Anna Ivanova'),
(4, 'Maria Petrova'),
(4, 'Elena Larioniva'),
(5, 'Ivan Yurev'),
(5, 'Vasilii Leonov'),
(6, 'Anna Rabceva'),
(6, 'Maria Volniva'),
(7, 'Elena Slovuta'),
(7, 'Alla Panina'),
(8, 'Vasilii Larionov'),
(8, 'Sofia Rabceva'),
(9, 'Olga Volniva'),
(9, 'Irina Slovuta'),
(10, 'Leonid Panin'),
(10, 'Viktor Larionov'),
(11, 'Inna Rabceva'),
(11, 'Lubov Volniva'),
(12, 'Tanya Slovuta'),
(12, 'Nina Panina'),
(13, 'Denis Larionov'),
(13, 'Sofia Goleeva'),
(14, 'Olga Krivcova'),
(14, 'Irina Titova'),
(15, 'Leonid Panin'),
(15, 'Maria Slavkina'),
(16, 'Elena Leniva'),
(16, 'Каterina Ivanova'),
(17, 'Ivan Petrov'),
(17, 'Karl Bubin');

insert into school_classes (name) values ('7A'),
('7B');
