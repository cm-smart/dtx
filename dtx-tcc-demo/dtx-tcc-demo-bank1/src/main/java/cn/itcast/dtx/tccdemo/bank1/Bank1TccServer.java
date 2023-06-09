
package cn.itcast.dtx.tccdemo.bank1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"cn.itcast.dtx.tccdemo.bank1.spring"})
@ComponentScan({"cn.itcast.dtx.tccdemo.bank1","org.dromara.hmily"})
public class Bank1TccServer {

	public static void main(String[] args) {
		SpringApplication.run(Bank1TccServer.class, args);

	}

}
