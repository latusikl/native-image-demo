package pl.latusikl.demo.nativeimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestNativeImageApplication {

	public static void main(String[] args) {
		SpringApplication.from(NativeImageApplication::main).with(TestNativeImageApplication.class).run(args);
	}

}
