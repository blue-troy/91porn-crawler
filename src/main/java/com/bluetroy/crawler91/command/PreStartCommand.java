package com.bluetroy.crawler91.command;

import com.bluetroy.crawler91.dao.Repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-07-09
 * Time: 下午7:49
 */
@Component
@Order(1)
public class PreStartCommand implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Repository.init();
    }
}
