LOAD CSV FROM 'file:///REGIONS_DATA_TABLE.csv' AS line
CREATE(r: Region {region_id: toInteger(line[0]), region_name: line[1]});

DROP INDEX ON :Region(region_id);
CREATE INDEX FOR (n:Region) ON (n.region_id);

LOAD CSV FROM 'file:///COUNTRIES_DATA_TABLE.csv' AS line
CREATE(c: Country {country_id: line[0], country_name: line[1], region_id: toInteger(line[2])});

DROP INDEX ON :Country(country_id);
CREATE INDEX FOR (n:Country) ON (n.country_id);

LOAD CSV FROM 'file:///LOCATIONS_DATA_TABLE.csv' AS line
CREATE(l: Location {location_id: toInteger(line[0]), street_adress: line[1], postal_code: line[2], city: line[3], state_province: line[4], country_id: line[5]});

DROP INDEX ON :Location(location_id);
CREATE INDEX FOR (n:Location) ON (n.location_id);

LOAD CSV FROM 'file:///DEPARTMENTS_DATA_TABLE.csv' AS line
CREATE(d: Department {department_id: toInteger(line[0]), department_name: line[1], manager_id: toInteger(line[2]), location_id: toInteger(line[3])});

DROP INDEX ON :Department(department_id);
CREATE INDEX FOR (n:Department) ON (n.department_id);

LOAD CSV FROM 'file:///EMPLOYEES_DATA_TABLE.csv' AS line
CREATE(e: Employee {employee_id: toInteger(line[0]), first_name: line[1], last_name: line[2], email: line[3], phone_number: line[4], hire_date: date(line[5]), job_id: line[6], salary: toInteger(line[7]), commision_pct: toFloat(line[8]), manager_id: toInteger(line[9]), department_id: toInteger(line[10])});

DROP INDEX ON :Employee(employee_id);
CREATE INDEX FOR (n:Employee) ON (n.employee_id);

LOAD CSV FROM 'file:///JOB_HISTORY_DATA_TABLE.csv' AS line
CREATE(jh: JobHistory {employee_id: toInteger(line[0]), start_date: date(line[1]), end_date: date(line[2]), job_id: line[3], department_id: toInteger(line[4])});

LOAD CSV FROM 'file:///JOBS_DATA_TABLE.csv' AS line
CREATE(j: Job {job_id: line[0], job_title: line[1], min_salary: toInteger(line[2]), max_salary: toInteger(line[3])});

DROP INDEX ON :Job(job_id);
CREATE INDEX FOR (n:Job) ON (n.job_id);