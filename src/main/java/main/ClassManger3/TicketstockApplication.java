package main.ClassManger3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TicketstockApplication {

    // @Autowired
    // private JdbcTemplate jdbcTemplate;


    public static void main(String[] args) {
        SpringApplication.run(TicketstockApplication.class, args);
    }
 
    // @Override
    // public void run(String... args) throws Exception {
    //     String sql = "SELECT * FROM CurrentShows";
    //     List<ShowEntity> customers = jdbcTemplate.query(sql,
    //             BeanPropertyRowMapper.newInstance(ShowEntity.class));
         
    //     customers.forEach(System.out :: println);
    // }
}