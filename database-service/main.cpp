#include <iostream>
#include "cppconn/driver.h"
#include "cppconn/connection.h"
#include <libconfig.h++>
#include "mysql_driver.h"

struct database_credentials
{
  std::string host;
  std::string user;
  std::string password;
  std::string database;
};

sql::Connection *connectToDatabase(struct database_credentials credentials)
{
  sql::mysql::MySQL_Driver *driver;
  sql::Connection *con;

  try
  {
    driver = sql::mysql::get_mysql_driver_instance();
    con = driver->connect(credentials.host, credentials.user, credentials.password);
    con->setSchema(credentials.database);
    std::cout << "Successfully connected to the database." << std::endl;
    return con;
  }
  catch (sql::SQLException &e)
  {
    std::cerr << "Error connecting to the database: " << e.what() << std::endl;
    return nullptr;
  }
}

int main()
{
  // Read database credentials from config file
  libconfig::Config cfg;
  cfg.readFile("config.cfg");

  std::string host = cfg.lookup("database.host");
  std::string user = cfg.lookup("database.user");
  std::string password = cfg.lookup("database.password");
  std::string database = cfg.lookup("database.database");

  const database_credentials credentials = {host, user, password, database};

  // Connect to the database
  sql::Connection *con = connectToDatabase(credentials);

  // Check if the connection is successful
  if (!con)
  {
    return 1;
  }

  // Perform database operations here

  // Close the connection
  delete con;

  return 0;
}
