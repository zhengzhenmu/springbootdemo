package com.example;

import com.example.domain.JuheSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.core.env.Environment;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@SpringBootApplication
public class DemoApplication {

	//@Autowired
	//Environment ev;

	@Autowired
	JuheSettings juheSettings;

	@RequestMapping("/")
	String home(){
		return "Hello World!";
	}

	@RequestMapping(value = "/stock")
	public Map getAll(){

		//return ev.toString();
		//从application.properties中获取
		//return ev.getProperty("juhe.url") + "finance/stock/shall?key="+ev.getProperty("juhe.appkey") +"&stock=a";

		String url = juheSettings.getUrl()+"finance/stock/shall?key=" +juheSettings.getAppkey()+"&stock=a";


		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map map = jsonParser.parseMap(result);
		if(map!=null & "SUCCESSED!".equals(map.get("reason").toString())){
			return (Map)map.get("result");
		}
		return null;
	}




	public static void main(String[] args) {

	    //System.setProperty("","");
	    /*SpringApplication app = new SpringApplication(DemoApplication.class);
	    app.setBannerMode(Banner.Mode.OFF);
	    app.run(args);*/
		SpringApplication.run(DemoApplication.class, args);
	}
}
