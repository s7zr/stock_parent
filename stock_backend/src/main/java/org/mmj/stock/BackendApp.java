package org.mmj.stock;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude={DruidDataSourceAutoConfigure.class})
@MapperScan("org.mmj.stock.mapper")//扫描mapper接口，生成代理对象，并被ioc容器管理
public class BackendApp {
    public static void main(String[] args) {
        SpringApplication.run(BackendApp.class, args);
    }
}
