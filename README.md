# simpleservlet  
  
Simple servlet, designed to show a table with DB data with filtering and sorting.  
Filtering is done via SQL, sorting via JS.  

# Deploy instructions  
  
1. Import the project with git
2. Adjust PostgreSQL connection settings in web.xml (dbHostname, dbPort, dbDatabase, dbUser, dbPassword)
3. Build with **mvn clean package**
4. Since specification says *"no interfaces to edit or add Part are needed"* execute the resources/db.sql file and fill the table with sufficient amount of data.  
5. Drop the built .war file to tomcat/webapps folder.
6. Wait a minute for tomcat to deploy the servlet and visit it on <tomcat_url>/parts
  
# Specification  
  
**Use Case Specification**  
**Write servlet that outputs list of Parts in HTML format.**  
       List of Parts is a table with the columns corresponding to Part fields.  
       Before the table on page there is a filter which allows to filter output Part records.  
       If none of the filter fields is specified then all Parts should be shown.  
       The table can be sorted by any of the columns by clicking on their header (one click  ASC order, next click  DESC order).  
       Sorting performed only by one column at a time.  
       When user uses sorting  filtering is still applied.  
       Output and input format for dates is “MMM dd, yyyy”  
       Parts are stored in database.  
       No interfaces to edit or add Part are needed.  
   
**Filter notes:**  
       Text fields are filtered using “like” criteria.  
       Integer fields are filtered using “not less” criteria.  
       Date fields are filtered using range (after, before) criteria.  
  
**Part Entity Specification**  
**Part entity consists of following fields:**  
       Part Name (String)  
       Part Number (String)  
       Vendor (String)  
       Qty (Integer)  
       Shipped (Date)  
       Receive (Date)  
  
**Tools to use**  
Tomcat, Postgresql, Maven, no ORM, no Spring  
**Additional info**  
       Application should be provided with clear deployment instructions written in English.  
       Please provide actual time spent for this test.  
