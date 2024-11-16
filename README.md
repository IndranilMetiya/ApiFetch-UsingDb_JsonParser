# ApiFetch-UsingDb_JsonParser

Here I have made a simplified way to call 3rd part API and then fetch the exact data with out any code.
Using this Structure we dont need to call each API and integrate with our code, just simply call 1 Api inside that put param like apiname, desiredvalue, queryparam and pathparam (if needed). then without any more complication we can get our desired values in key-value form.

To implemet that we have to give the details of our API in our DB, name of the query parama and path param , JSONQuery for desired value thats all.

______________________________________________________________________________________________________________________

sharing the DB table that compatible with my code.
--------------------


CREATE TABLE api_config (
    id INT PRIMARY KEY AUTO_INCREMENT,
    api_name VARCHAR(50) NOT NULL,         -- Name of the API to be called
    base_url VARCHAR(255) NOT NULL,        -- Base URL of the API
    path_params JSON,                      -- JSON array of path parameters (e.g., ["bookId"])
    query_params JSON,                     -- JSON array of query parameters (e.g., ["filter", "type"])
    desired_value VARCHAR(50) NOT NULL,    -- Label for the value to extract (e.g., "bookName", "authorName")
    json_query VARCHAR(255) NOT NULL       -- JSONPath expression to extract the desired data (e.g., "$.book.name")
);

--Then insert this--

INSERT INTO api_config (api_name, base_url, path_params, query_params, desired_value, json_query)
VALUES
('bookService', 'http://localhost:8080/api/books/{bookId}', '["bookId"]', '["type"]', 'bookName', '$.book.name'),
('bookService', 'http://localhost:8080/api/books/{bookId}', '["bookId"]', '["type"]', 'authorName', '$.book.author'),
('employeeService', 'http://localhost:8080/api/employees/{empId}', '["empId"]', '["department"]', 'employeeName', '$.employee.name'),
('employeeService', 'http://localhost:8080/api/employees/{empId}', '["empId"]', '["department"]', 'skills', '$.employee.details.skills');

INSERT INTO api_config (api_name, base_url, path_params, query_params, desired_value, json_query)
VALUES
('orderService', 'http://localhost:8080/api/Order', '[]', '[]', 'processor', '$.items[?(@.name=="Smartphone")].specifications.processor');

sharing example of postman api
--------------------
http://localhost:8082/api/data?apiName=employeeService&desiredValues=skills,employeeName&empId=1&department=IT

______________________________________________________________________________________________________________________
