//Encontrar toda a gente que trabalha no departamento de IT
match (e:Employee)-[:Works_In]->(d:Department)
where d.department_name = "IT"
return (e.first_name + " " + e.last_name) as Employee;

//Número de trabalhadores por departamento:
match (e:Employee)-[r:Works_In]->(d:Department)
return d.department_name As Department, count(r) as NumberOfEmployees;

//Top 5 com maiores salarios:
match (e:Employee)-[:Works_As]->(j:Job)
return (e.first_name + " " + e.last_name) as Employee, j.job_title as Job, e.salary as Salary
order by Salary desc limit 5;

//Soma de todos os salários:
match (e:Employee)
return count(e) as NumberOfEmployees, sum(e.salary) as TotalOfSalaries;

//Soma salários por região:
match (e:Employee)-[:Works_In]->(:Department)-[:Located_In]->(:Location)-[:Located_In]->(:Country)-[:Located_In]->(r:Region)
return r.region_name as Region, count(e) as NumberOfEmployees, sum(e.salary) as TotalOfSalaries;

//Salario medio por profissao
match (e:Employee)-[:Works_As]->(j:Job)
return j.job_title as Job, count(e) as NumberOfEmployees, min(e.salary) as MinimunSalary, max(e.salary) as MaximumSalary, avg(e.salary) as AverageSalary
order by AverageSalary desc;

//Numero de departamentos por pais, com funcionários a operar nestes
match (:Employee)-[:Works_In]->(d:Department)-[:Located_In]->(:Location)-[:Located_In]->(c:Country)
return c.country_name as Country, count(distinct d) as NumberOfDepartments;

//Quais as profissoes de quem trabalha no departamento de Purchasing
match (e:Employee)-[:Works_As]->(j:Job)
match (e:Employee)-[:Works_In]->(d:Department)
where d.department_name = "Purchasing"
return (e.first_name + " " + e.last_name) as Employee, j.job_title as Job;