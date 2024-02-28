package br.com.diego.conexaomultitenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ConexaoMultitenancyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConexaoMultitenancyApplication.class, args);
    }

}
