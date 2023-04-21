package com.eoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author e_oa
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EoaApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(EoaApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  易办公启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
