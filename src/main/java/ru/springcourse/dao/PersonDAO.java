package ru.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
//убрать в properties
private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
private static final String USERNAME = "postgres";
private static final String PASSWORD = "postgres";
private static Connection connection;

static {
    try {
        Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    try {
        connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
}


    public List<Person> index() {
    //добавляет людей по умолчанию
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultset = statement.executeQuery(SQL);

            while(resultset.next()) {
            Person person = new Person();
            person.setEmail(resultset.getString("email"));
            person.setAge(resultset.getInt("age"));
            person.setId(resultset.getInt("id"));
            person.setName(resultset.getString("name"));

            people.add(person);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

public Person show(int id) {
    Person person = null;
    // в случае если случится SQLException, то метод выплюнет null.
    //если создавать объект в блоке try, он не будет виден ретерну.
    try {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Person where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        //сдвигаем указатель один раз и он будет показывать первую строчку из БД
        person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setEmail(resultSet.getString("email"));
        person.setAge(resultSet.getInt("age"));
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    return person;
    }

  public void save(Person person) throws SQLException {
      //пока что по умолчанию всегда id=1
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Person VALUES(1,?,?,?)");

        preparedStatement.setString(1,person.getName());
        preparedStatement.setInt(2,person.getAge());
        preparedStatement.setString(3,person.getEmail());
        preparedStatement.executeUpdate();
    }

    public void update(int id, Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Person SET name=?,age=?,email=? WHERE id=?");
//где id=? мы обновляем колонки
            preparedStatement.setInt(4,id);
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

public void delete(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE from Person WHERE id=?");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }
}
