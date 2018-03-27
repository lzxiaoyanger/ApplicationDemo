package com.zz.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

//@RestController
//@SpringBootApplication
//@MapperScan("com.zz.personal.dao.mapper")
//@SpringBootApplication
@SpringBootApplication
//@MapperScan("com.zz.personal.dao.mapper")
public class PersonalApplication implements EmbeddedServletContainerCustomizer {

	public static void main(String[] args) {
		SpringApplication.run(PersonalApplication.class, args);
	}
    /**
     * 修改启动端口号
     * @param configurableEmbeddedServletContainer
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8088);
    }

}
