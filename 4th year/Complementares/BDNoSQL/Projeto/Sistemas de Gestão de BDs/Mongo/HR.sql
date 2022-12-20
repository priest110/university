set feedback off
set echo off
set heading off
set trimspool on
set newpage 0
set verify off
spool hr.json
select json_object(
    'id' VALUE e.employee_id,
    'first_name' VALUE e.first_name,
    'last_name' VALUE e.last_name,
    'email' VALUE e.email,
    'phone_number' VALUE e.phone_number,
    'hire_date' VALUE e.hire_date,
    'job' VALUE json_object('title' VALUE j.job_title, 'min_salary' VALUE j.min_salary, 'max_salary' VALUE j.max_salary FORMAT JSON ABSENT ON NULL),
    'salary' VALUE e.salary,
    'commission_pct' VALUE e.commission_pct,
    'manager' VALUE case when (m.employee_id IS NULL) then null
    else json_object('manager_id' VALUE m.employee_id, 'manager_first_name' VALUE  m.first_name, 'manager_last_name' VALUE m.last_name , 'manager_email' VALUE m.email, 'manager_phone_number' VALUE m.phone_number)
    end FORMAT JSON,
    'department' VALUE case when (d.department_id IS NULL) THEN NULL
    else json_object('name' VALUE d.department_name, 'manager_id' VALUE d.manager_id,
        'location' VALUE  json_object('street_address' VALUE l.street_address,
                            'postal_code' VALUE l.postal_code,
                            'city' VALUE l.city,
                            'state_province' VALUE l.state_province,
                            'country' VALUE c.country_name,
                            'region' VALUE r.region_name
                            ABSENT ON NULL)
        FORMAT JSON ABSENT ON NULL)
    end FORMAT JSON,
    'job_history' VALUE (select json_arrayagg(
        json_object('start_date' VALUE jha.start_date, 'end_date' VALUE jha.end_date, 
                'job' VALUE json_object('title' VALUE ja.job_title, 'min_salary' VALUE ja.min_salary, 'max_salary' VALUE ja.max_salary FORMAT JSON ABSENT ON NULL),
                'department' VALUE case when (da.department_id IS NULL) THEN NULL
                            else json_object('name' VALUE da.department_name, 'manager_id' VALUE da.manager_id,
                                'location' VALUE  json_object('street_address' VALUE la.street_address,
                                'postal_code' VALUE la.postal_code,
                                'city' VALUE la.city,
                                'state_province' VALUE la.state_province,
                                'country' VALUE ca.country_name,
                                'region' VALUE ra.region_name
                                ABSENT ON NULL)
                FORMAT JSON ABSENT ON NULL)
                end FORMAT JSON)) from job_history jha 
                Inner Join jobs ja on jha.job_id = ja.job_id
                LEFT Join departments da on jha.department_id = da.department_id
                LEFT Join locations la on da.location_id = la.location_id
                LEFT Join countries ca on la.country_id = ca.country_id
                LEFT Join regions ra on ca.region_id = ra.region_id
                where e.employee_id = jha.employee_id)
    FORMAT JSON
    ABSENT ON NULL
) from employees e
Inner Join jobs j on e.job_id = j.job_id
LEFT Join employees m on e.manager_id = m.employee_id
LEFT Join departments d on e.department_id = d.department_id
LEFT Join locations l on d.location_id = l.location_id
LEFT Join countries c on l.country_id = c.country_id
LEFT Join regions r on c.region_id = r.region_id;
spool off;
set feedback on
set echo on
set heading on
set trimspool off
set newpage none
set verify on
