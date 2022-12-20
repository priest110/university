-- Encontrar todos os empregados que trabalham no departamento IT

select e.first_name "Primeiro Nome", e.last_name "�ltimo Nome" from employees e
join departments d on e.department_id = d.department_id
where d.department_name = 'IT';

-- Obter o n�mero de trabalhadores por departamento

select count(e.employee_id) "N�mero de Empregados" ,d.department_name "Departamento"  from employees e
join departments d on e.department_id = d.department_id
group by d.department_name;

-- Top 5 trabalhadores com maior sal�rio

select e.first_name "Primeiro Nome", e.last_name "�ltimo Nome",e.salary "Sal�rio" from employees e
order by e.salary DESC
FETCH FIRST 5 ROWS ONLY;

-- Soma de todos os sal�rios mensais

select sum(e.salary) Total from employees e;

-- Soma de todos os sal�rios por regi�o

select sum(e.salary) "Total Sal�rio", r.region_name "Regi�o" from employees e
join departments d on e.department_id = d.department_id
join locations l on l.location_id = d.location_id
left join countries c on c.country_id = l.country_id
left join regions r on r.region_id = c.region_id
group by r.region_name;

-- Sal�rio m�dio em cada cargo

select avg(e.salary) "Sal�rio M�dio", j.job_title "Cargo" from employees e
join jobs j on j.job_id = e.job_id
group by j.job_title;

-- N�mero de departamentos por pa�s com empregados a operar nestes

select count(distinct(d.department_id)) "N�mero de departamentos", c.country_name "Pa�s" from employees e
join departments d on e.department_id = d.department_id
join locations l on l.location_id = d.location_id
left join countries c on c.country_id = l.country_id
group by c.country_name;

-- Encontrar os empregados que trabalham no departamento de Purchasing e quais os seus cargos
select e.first_name "Primeiro Nome", e.last_name "�ltimo Nome", j.job_title "Cargo" from employees e
join departments d on e.department_id = d.department_id
join jobs j on j.job_id = e.job_id
where d.department_name = 'Purchasing';
