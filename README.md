---
## Init

- The application requires the PostgreSQL database to be running locally on port **5432** named **note**
  
-  [Postgre](https://postgresapp.com) can be downloaded at https://postgresapp.com

- After downloading, you need to turn on the database with the **initialize** button 

- After turning on the database in the terminal, run the following code

`sudo mkdir -p /etc/paths.d &&`
`echo /Applications/Postgres.app/Contents/Versions/latest/bin | sudo tee /etc/paths.d/postgresapp`

- Then we can switch to postgres with the `psql` command

- Create a database with the command `CREATE DATABASE note;`

- Use the `\du` command to find out the role names

- Then we ll assign privileges to the accounts with the command `GRANT ALL PRIVILEGES ON DATABASE "note" TO roleName;` where **role name** is the name of the role from the previous command. **Run this command for all roles**

- The project can now be started and **Postman** can be used to run API calls.
---
