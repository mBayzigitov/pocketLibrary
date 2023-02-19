package dao;

import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getListOfReaders() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void saveReader(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES(?, ?)",
                person.getFull_name(), person.getYear_of_birth());
    }

    public void removePerson(int client_id) {
        jdbcTemplate.update("DELETE FROM Person WHERE client_id = ?", client_id);
    }

    public Optional<Person> findPersonById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE client_id = ?", new Object[] {id},
                new BeanPropertyRowMapper<>(Person.class))
                    .stream().findAny();
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, year_of_birth = ? WHERE client_id = ?",
                person.getFull_name(), person.getYear_of_birth(), id);
    }

}
