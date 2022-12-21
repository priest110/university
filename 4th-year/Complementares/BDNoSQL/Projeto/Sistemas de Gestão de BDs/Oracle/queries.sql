-- Encontrar todos os empregados que trabalham no departamento IT

select e.first_name "Primeiro Nome", e.last_name "Último Nome" from employees e
join departments d on e.department_id = d.department_id
where d.department_name = 'IT';

-- Obter o número de trabalhadores por departamento

select count(e.employee_id) "Número de Empregados" ,d.department_name "Departamento"  from employees e
join departments d on e.department_id = d.department_id
group by d.department_name;

-- Top 5 trabalhadores com maior salário

select e.first_name "Primeiro Nome", e.last_name "Último Nome",e.salary "Salário" from employees e
order by e.salary DESC
FETCH FIRST 5 ROWS ONLY;

-- Soma de todos os salários mensais

select sum(e.salary) Total from employees e;

-- Soma de todos os salários por região

select sum(e.salary) "Total Salário", r.region_name "Região" from employees e
join departments d on e.department_id = d.department_id
join locations l on l.location_id = d.location_id
left join countries c on c.country_id = l.country_id
left join regions r on r.region_id = c.region_id
group by r.region_name;

-- Salário médio em cada cargo

select avg(e.salary) "Salário Médio", j.job_title "Cargo" from employees e
join jobs j on j.job_id = e.job_id
group by j.job_title;

-- Número de departamentos por país com empregados a operar nestes

select count(distinct(d.department_id)) "Número de departamentos", c.country_name "País" from employees e
join departments d on e.department_id = d.department_id
join locations l on l.location_id = d.location_id
left join countries c on c.country_id = l.country_id
group by c.country_name;

-- Encontrar os empregados que trabalham no departamento de Purchasing e quais os seus cargos
select e.first_name "Primeiro Nome", e.last_name "Último Nome", j.job_title "Cargo" from employees e
join departments d on e.department_id = d.department_id
join jobs j on j.job_id = e.job_id
where d.department_name = 'Purchasing';
