package org.gourd.hu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动类
 *
 * @author gour.hu
 */
@Slf4j
@SpringBootApplication
public class AdminWebApplication {

    public static void main(String[] args) throws UnknownHostException {
        /**
         * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
         * 解决netty冲突后初始化client时还会抛出异常
         * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
         */
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        // new application
        ConfigurableApplicationContext application = new SpringApplicationBuilder()
                .sources(AdminWebApplication.class)
                // default properties
                .properties("--spring.profiles.active=local")
                .web(WebApplicationType.SERVLET)
                .run(args);
        log.warn(">o< admin服务启动成功！温馨提示：代码千万行，注释第一行，命名不规范，同事泪两行 >o<");
        Environment env = application.getEnvironment();
        // 是否启用https
        boolean httpsFlag = Boolean.valueOf(env.getProperty("server.ssl.enabled"));
        log.info("Swagger文档: \t\t{}://{}:{}/doc.html",httpsFlag?"https":"http",InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}
