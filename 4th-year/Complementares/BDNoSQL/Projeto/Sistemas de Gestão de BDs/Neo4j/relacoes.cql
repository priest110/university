MATCH (c:Country), (r:Region) 
WHERE c.region_id = r.region_id 
CREATE (c)-[:Located_In]->(r);

MATCH (l:Location), (c:Country)
WHERE l.country_id = c.country_id 
CREATE (l)-[:Located_In]->(c);

MATCH (d:Department), (e:Employee)
WHERE d.manager_id = e.employee_id 
CREATE (d)-[:Managed_By]->(e);

MATCH (d:Department), (l:Location)
WHERE d.location_id = l.location_id 
CREATE (d)-[:Located_In]->(l);

MATCH (e:Employee), (j:Job)
WHERE e.job_id = j.job_id
CREATE (e)-[:Works_As]->(j);

MATCH (e:Employee), (m:Employee)
WHERE e.manager_id = m.employee_id
CREATE (e)-[:Works_To]->(m);

MATCH (e:Employee), (d:Department)
WHERE e.department_id = d.department_id
CREATE (e)-[:Works_In]->(d);

MATCH (jh:JobHistory), (e:Employee)
WHERE jh.employee_id = e.employee_id
CREATE (e)-[:Worked_As]->(jh);

MATCH (jh:JobHistory), (j:Job)
WHERE jh.job_id = j.job_id
CREATE (jh)-[:Profession]->(j);

MATCH (jh:JobHistory), (d:Department)
WHERE jh.department_id = d.department_id
CREATE (jh)-[:Place]->(d);

match (x:Region)
remove x.region_id;

match (x:Country)
remove x.country_id, x.region_id;

match (x:Location)
remove x.location_id, x.country_id;

match (x:Department)
remove x.department_id, x.manager_id, x.location_id;

match (x:Employee)
remove x.employee_id, x.job_id, x.manager_id, x.department_id;

match (x:JobHistory)
remove x.employee_id, x.job_id, x.department_id;

match (x:Job)
remove x.job_id;