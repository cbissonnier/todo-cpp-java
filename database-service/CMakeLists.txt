cmake_minimum_required(VERSION 3.0)
project(MySQLExample)

# Set the path to MySQL Connector/C++ include directory
set(MYSQLCONNECTORCPP_INCLUDE_DIR "/usr/local/opt/mysql-connector-c++/include")

# Set the path to MySQL Connector/C++ library
set(MYSQLCONNECTORCPP_LIBRARY "/usr/local/opt/mysql-connector-c++/lib/libmysqlcppconn8.dylib")

# Set the path to libconfig++ include directory
set(LIBCONFIG_INCLUDE_DIR "/usr/local/opt/libconfig/include")

# Set the path to libconfig++ library
set(LIBCONFIG_LIBRARY "/usr/local/opt/libconfig/lib/libconfig++.dylib")

# Set the path to boost library
set(BOOST_LIBRARY "/usr/local/opt/boost/lib")

# Set the path to boost include directory
set(BOOST_INCLUDE_DIR "/usr/local/opt/boost/include")

# Include directories for MySQL Connector/C++ and libconfig++
include_directories(${MYSQLCONNECTORCPP_INCLUDE_DIR} ${LIBCONFIG_INCLUDE_DIR} ${BOOST_INCLUDE_DIR})

# Add the executable
add_executable(main main.cpp)

# Link against MySQL Connector/C++
target_link_libraries(main ${MYSQLCONNECTORCPP_LIBRARY})

# Link against libconfig++
target_link_libraries(main ${LIBCONFIG_LIBRARY})