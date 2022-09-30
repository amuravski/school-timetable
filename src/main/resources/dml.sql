use school_timetable;
#inserts into DB

insert into days(name) values('Monday'), ('Tuesday'), ('Wednesday'), ('Thursday'), ('Friday'), ('Saturday'), ('Sunday');                                     
			
insert into subjects(name) values ('chemistry'), ('computing'), ('drawing'), ('economy'), ('geometry'), ('language_by'),
								  ('language_de'), ('language_en'), ('literature_by'), ('literature_de'), ('literature_en'),
                                  ('music'), ('physical'), ('physics'), ('biology'), ('history'), ('labor');
                                  
insert into teachers(subject_id, full_name) values (1, 'Maria Ivanova'), (1, 'Elena Petrova'), (2,'Каterina Larioniva'), (2, 'Mihail Yurev'), (3, 'Yrii Leonov'),
									   (3, 'Anna Ivanova'), (4, 'Maria Petrova'), (4, 'Elena Larioniva'), (5, 'Ivan Yurev'), (5, 'Vasilii Leonov'),
                                       (6, 'Anna Rabceva'), (6, 'Maria Volniva'), (7, 'Elena Slovuta'), (7, 'Alla Panina'), (13, 'Vasilii Larionov'),
                                       (8, 'Sofia Rabceva'), (9, 'Olga Volniva'), (9, 'Irina Slovuta'), (10, 'Leonid Panin'), (10, 'Viktor Larionov'),
                                       (11, 'Inna Rabceva'), (11, 'Lubov Volniva'), (12, 'Tanya Slovuta'), (12, 'Nina Panina'), (13, 'Denis Larionov'),
                                       (8, 'Sofia Goleeva'), (14, 'Olga Krivcova'), (14, 'Irina Titova'), (15, 'Leonid Panin'), (13, 'Viktor Kozlov'),
                                       (15, 'Maria Slavkina'), (16, 'Elena Leniva'), (16,'Каterina Ivanova'), (17, 'Ivan Petrov');    
insert into school_timetables(hashcode) values (1), (2);
insert into school_classes (name) values ('7A'), ('8B'), ('1A'), ('2B'), ('3A'), ('5B'), ('6A'), ('10B');
insert into class_timetables(class_id, school_timetable_id) values (1, 2), (1,1), (2, 2), (2,1);
insert into school_days (timetable_id, day_id) values (1, 2), (2, 2), (3, 2), (4, 2);
insert into lessons (teacher_id,school_day_id,lesson_number) values (2, 1, 1), (3, 2, 2), (4, 3, 3), (5, 4, 4), (6, 4, 5),
(17, 1, 1), (7, 2, 2), (8, 3, 3), (9, 4, 4), (10, 2, 5), (11, 1, 1), (13, 2, 2), (14, 3, 3), (15, 4, 4), (16, 1, 5); 
                                      
#selects

select ct.class_id as class_id, ct.school_timetable_id as school_id from class_timetables ct where ct.id = 1; 
select sc.name as school_class from school_classes sc where sc.id = 1;
select sc.name as school_class from school_classes sc where sc.id in (1, 2);
select dn.name as day from days dn where dn.id = 1;
select dn.id, dn.name as day from days dn where dn.id in (1,2,3,4,5);
#select for teachers
select t.id as teacher_id, t.full_name as teachers_name from teachers t where t.id = 34;
select t.id as teacher_id, t.full_name as teachers_name from teachers t where t.subject_id = 1;
#find all techers and their subjects if we know subject (eg economy)
select t.id as teacher_id, t.full_name as teachers_name, s.name from teachers t left join subjects s on s.id = t.subject_id where s.name  = 'economy';
#find all teachers and their subjects if we know some letters of subject (eg biology)
select t.id as teacher_id, t.full_name as teachers_name, s.name from teachers t left join subjects s on s.id = t.subject_id where s.name  like  '%bio%';
#find subjects for some teachers using id
select s.id as subject_id, s.name as subject_name from subjects s left join teachers t on s.id = t.subject_id where t.id in (3,17,30);
#find subjects for teacher using name without teacher_name
select s.id as subject_id, s.name as subject_name from subjects s left join teachers t on s.id = t.subject_id where t.full_name in ('Ivan Yurev');
#find subjects for some teachers with teacher_name
select s.id as subject_id, s.name as subject_name, t.full_name as teachers_name from subjects s left join teachers t on s.id = t.subject_id where t.id in (3,17,30);
#select for lessons
select l.id as lesson_id, l.teacher_id as teacher_id, l.school_day_id as school_day_id, l.lesson_number as lesson_number from lessons l where l.id = 8;
select l.id as lesson_id, l.teacher_id as teacher_id, l.school_day_id as school_day_id, l.lesson_number as lesson_number from lessons l where l.teacher_id = 1;
select l.id as lesson_id, l.teacher_id as teacher_id, l.school_day_id as school_day_id, l.lesson_number as lesson_number from lessons l where l.school_day_id = 1;
select l.id as lesson_id, l.teacher_id as teacher_id, l.school_day_id as school_day_id, l.lesson_number as lesson_number from lessons l where l.lesson_number = 5;
#select teacher and subject for lesson
select l.id as lesson_id, l.lesson_number as lesson_number, t.full_name as teachers_name, s.name as subject_name from lessons l left join
teachers t on l.teacher_id = t.id left join subjects s on s.id = t.subject_id;
#select for class_timetables
select ct.id as class_timetable_id, ct.class_id as class_id, ct.school_timetable_id as school_timetable_id from class_timetables ct where ct.id = 2;
select ct.id as class_timetable_id, ct.class_id as class_id, ct.school_timetable_id as school_timetable_id from class_timetables ct where ct.class_id = 2;
select ct.id as class_timetable_id, ct.class_id as class_id, ct.school_timetable_id as school_timetable_id from class_timetables ct where ct.school_timetable_id = 2;
#select lesson, teacher and subject for school_classes
select l.id as lesson_id, l.lesson_number as lesson_number, t.full_name as teachers_name, s.name as subject_name, sd.id as school_day_id,
ct.id as class_timetable_id, sc.name as school_class from school_classes sc left join class_timetables ct on ct.class_id = sc.id left join school_days sd
on sd.timetable_id = ct.id left join lessons l on sd.id = l.school_day_id left join teachers t on l.teacher_id = t.id left join subjects s 
on s.id = t.subject_id;
#select lesson, teacher and subject for school_timetable
select st.id, sc.name as school_class, ct.id as class_timetable_id, sd.id as school_day_id, l.id as lesson_id, l.lesson_number as lesson_number,
 t.full_name as teachers_name, s.name as subject_name from school_timetables st left join class_timetables ct on st.id = ct.school_timetable_id
 join school_classes sc on sc.id = ct.class_id left join school_days sd on sd.timetable_id = ct.id left join lessons l on sd.id = l.school_day_id left join 
 teachers t on l.teacher_id = t.id left join subjects s on s.id = t.subject_id;

 #updates
 
update days set name = 'Monday' where id = 1;
update subjects set name = 'chemistry' where id = 1;
update teachers set full_name = 'Regina Lapina' where subject_id = 1; 
update teachers set full_name = 'Ioanna Lavrova' where id = 1;
update class_timetables set class_id = 1, school_timetable_id = 2 where id = 1;
update school_classes set name = '1B' where id = 1;
update school_timetables set hashcode = 2 where id = 1;
update school_days set timetable_id = 4, day_id = 5 where id = 6;
#update lesson's number and scool_day for any teacher
update lessons set lesson_number = 7, school_day_id = 2 where teacher_id = 34;
#update teacher for lesson in any scool_day
update lessons set teacher_id = 7, school_day_id = 2 where  id = 1;
#update teacher and lesson number for lesson
update lessons set teacher_id = 7, lesson_number = 2 where  id = 1;

 #deletes
 
delete from class_timetables where id = 1; 
delete from school_classes where id = 3;    
delete from days where id = 1;
delete from subjects where id = 1;  
delete from teachers where id = 1;
delete from school_timetables where id = 1; 
delete from lessons where scool_day_id = 1; 
delete from lessons where teacher_id = 1;
delete from lessons where id = 2;
delete from school_days where id = 1;                         
delete from school_days where timetable_id = 1; 
delete from school_days where day_id = 1; 
                                      