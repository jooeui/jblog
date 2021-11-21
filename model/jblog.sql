desc blog;
desc category;
desc post;
desc user;

select * from user;
select * from blog;
select * from category;
select * from post;

select c.no, c.name, ifnull(p.post_count, 0) as postCount, if(c.`desc`='' || c.`desc`=null, '-', c.`desc`) as `desc`, c.blog_id as blogId 
from category c left join
	(select category_no, count(*) as post_count
	from post
    group by category_no) p
on c.no = p.category_no
where c.blog_id='kje0727'
order by c.no;

select no, name, `desc`, blog_id as blogId 
from category
where blog_id='kje0727';

select c.no, c.name, p.post_count, `desc`, c.blog_id
from category c left join
	(select category_no, count(*) as post_count
	from post
    group by category_no) p
on c.no = p.category_no
where c.blog_id='kje0727';


select category_no, count(*) as post_count
	from post
    group by category_no;
delete from category where blog_id='kje0727' and no=5;

select category_no, count(*) as post_count
	from post
    group by category_no;

select id, title, logo from blog where id='';

insert into category values(null, '카테고리1', '', 'kje0727');
update blog set title='안녕하세용' where id='kje0727';

insert into post values(null, '잠온당', '피카츄 라이츄 파이리 꼬부기 버터플 야도란 피죤투 또가스~', now(), 19);
insert into post values(null, '이브이 진화', '부스터, 샤미드, 쥬피썬더', now(), 21);
insert into post values(null, '이브이 진화2', '에브이, 블래키, 리피아, 글레이시아, 님피아', now(), 21);
delete from post where category_no = 1;
select no from category where blog_id='kje0727' and name='미분류';


update post
set category_no = (select no from category where blog_id='kje0727' and name='미분류')
where category_no = 3;

select max(reg_date)
from post;

select no, title, contents, reg_date as regDate, category_no as cotegoryNo
from post
order by reg_date desc;
-- where category_no = 1;

select max(p.no) as no, p.title, p.contents, p.reg_date as regDate, p.category_no as cotegoryNo
from post p, category c
where p.category_no = c.no
	and c.blog_id = 'kje0727';

select p.no, p.title, p.contents, p.reg_date as regDate, p.category_no as cotegoryNo, c.blog_id
from post p, category c
where p.category_no = c.no
	and c.blog_id = 'kje0727'
	and p.category_no = 21
		and p.reg_date = (select max(reg_date)
							from post
						where category_no = 21);
                        
select no, title, contents, reg_date as regDate, category_no as categoryNo
from post
where no = 3;