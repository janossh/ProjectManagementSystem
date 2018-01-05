import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Objects;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/homework11";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("The driver is connected");

            System.out.println("вывести на консоль:\n" +
                    "1. зарплату(сумму) всех разработчиков отдельного проекта;\n" +
                    "2. список разработчиков отдельного проекта;\n" +
                    "3. список всех Java разработчиков;\n" +
                    "4. список всех middle разработчиков;\n" +
                    "5. список проектов в следующем формате: дата создания - название проекта - количество разработчиков на этом проекте.\n" +
                    "6. список проектов в следующем формате: дата создания - название проекта - количество разработчиков на этом проекте.\n" +
                    "7. выход\n");

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String flEnd = "exit";
            String str;

            System.out.println("Введите номер операции и нажмите ENTER");
            try {
                while (!flEnd.equals(str = bufferedReader.readLine())) {

                    switch (str) {
                        default:
                            System.out.println("Нет такой операции");
                            break;
                        case "1":
                            getSalary(connection);
                            break;
                        case "2":
                            getDevelopersList(connection);
                            break;
                        case "3":
                            getDevelopersListBySkils(connection);
                            break;
                        case "4":
                            getDevelopersListByLevel(connection);
                            break;
                        case "5":
                            getProjectsList(connection);
                            break;
                        case "6":
                            createCustomer(connection, "Roman", "tourist");
                            break;
                        case "7":
                            str = flEnd;
                            break;

                    }
                    if (Objects.equals(str, flEnd)) {
                        System.out.println("Работа программы завершена");
                        break;
                    }
                    System.out.println("Введите номер операции и нажмите ENTER");
                }
            } catch (IOException e) {
                System.out.println("Error read from keyboard");
            }

            try {
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("Error to close stream reader");
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //зарплату(сумму) всех разработчиков отдельного проекта;
    public static void getSalary(Connection connection) throws SQLException {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.println("Введите название проэкта");

        String str = "";

        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sqlSumOfSalaryQuery = "SELECT sum(salary) AS salary FROM developers\n" +
                "WHERE id IN (SELECT id FROM developers_projects\n" +
                "\t\t\t\tWHERE project IN (SELECT id FROM projects \n" +
                "\t\t\t\t\t\t\t\t\tWHERE name = ?))";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlSumOfSalaryQuery);
        preparedStatement.setString(1, str);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println("salary " + resultSet.getInt("salary"));
        }
    }

    //список разработчиков отдельного проекта;
    private static void getDevelopersList(Connection connection) throws SQLException {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.println("Введите название проэкта");

        String str = "";

        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sqlSumOfSalaryQuery = "SELECT name AS name FROM developers\n" +
                "WHERE id IN (SELECT id FROM developers_projects\n" +
                "\t\t\t\tWHERE project IN (SELECT id FROM projects \n" +
                "\t\t\t\t\t\t\t\t\tWHERE name = ?))";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlSumOfSalaryQuery);
        preparedStatement.setString(1, str);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println("name " + resultSet.getString("name"));
        }

    }

    //    список всех Java разработчиков;
    private static void getDevelopersListBySkils(Connection connection) throws SQLException {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.println("Введите название скила");

        String str = "";

        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sqlSumOfSalaryQuery = "SELECT name AS name FROM developers\n" +
                "WHERE id IN (SELECT id FROM developers_skills\n" +
                "\t\t\t\tWHERE skill IN (SELECT id FROM skills \n" +
                "\t\t\t\t\t\t\t\t\tWHERE skill = ?))";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlSumOfSalaryQuery);
        preparedStatement.setString(1, str);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println("name " + resultSet.getString("name"));
        }

    }

    //    список всех middle разработчиков;
    private static void getDevelopersListByLevel(Connection connection) throws SQLException {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.println("Введите название уровня");

        String str = "";

        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sqlSumOfSalaryQuery = "SELECT name AS name FROM developers\n" +
                "WHERE id IN (SELECT id FROM developers_skills\n" +
                "\t\t\t\tWHERE skill IN (SELECT id FROM skills \n" +
                "\t\t\t\t\t\t\t\t\tWHERE level = ?))";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlSumOfSalaryQuery);
        preparedStatement.setString(1, str);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println("name " + resultSet.getString("name"));
        }
    }

    //    список проектов в следующем формате: дата создания - название проекта - количество разработчиков на этом проекте.
    private static void getProjectsList(Connection connection) throws SQLException {

        String sqlDropTempTableQuery = "DROP TEMPORARY TABLE IF EXISTS projects_developers;";

        String sqlCreateTempTableQuery = "CREATE TEMPORARY TABLE projects_developers AS (\n" +
                "SELECT \n" +
                "count(developer) AS countOfDeveloper,\n" +
                "project\n" +
                "FROM homework11.developers_projects\n" +
                "GROUP BY project);";

        String sqlQuery = "SELECT \n" +
                "name, \n" +
                "description, \n" +
                "projects_developers.countOfDeveloper\n" +
                "FROM \n" +
                "homework11.projects\n" +
                "LEFT JOIN projects_developers ON projects.id = projects_developers.project;";

        Statement statement = connection.createStatement();
        statement.execute(sqlDropTempTableQuery);
        statement.execute(sqlCreateTempTableQuery);
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        while (resultSet.next()) {
            System.out.println("description " + resultSet.getString("description")
                    + "\tname " + resultSet.getString("name")
                    + "\tcountOfDeveloper " + resultSet.getString("countOfDeveloper"));
        }
    }


    //Также: создать заготовки операций(закомментированные query) для создания новых проектов, разработчиков, клиентов.

    //Создание проектов
    private static void createProject(Connection connection, String name, String description, int costumer_id, int company_id, int cost) throws SQLException {

        String sqlQuery = "insert into homework11.projects (name, description, costumer_id, company_id, cost) values (?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setInt(3, costumer_id);
        preparedStatement.setInt(4, company_id);
        preparedStatement.setInt(5, cost);

        preparedStatement.executeUpdate();
    }

    //Создание разработчиков
    private static void createDeveloper(Connection connection, String name, int age, String sex, int company_id, int salary) throws SQLException {

        String sqlQuery = "insert into homework11.developers (name, age, sex, company_id, salary) values (?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setString(3, sex);
        preparedStatement.setInt(4, company_id);
        preparedStatement.setInt(5, salary);

        preparedStatement.executeUpdate();
    }

    //Создание клиентов
    private static void createCustomer(Connection connection, String name, String kindOfActivity) throws SQLException {

        String sqlQuery = "insert into `homework11`.`customers` (`name`, `kindOfActivity`) values (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, kindOfActivity);

        preparedStatement.executeUpdate();
    }

}
