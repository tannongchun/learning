package com.example.demo;


import com.example.demo.controller.RedisqpsController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QpsredisApplicationTests {
	//SpringMVC提供的Controller测试类
	private MockMvc mockMvc;
		@Autowired
		private RedisqpsController redisqps;

	@Before
	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
//		mvc = MockMvcBuilders.standaloneSetup(redisqps).build();
		 mockMvc = MockMvcBuilders.standaloneSetup(redisqps).build();
	}

	@Test
	public  void testRequestToAllUser()  {
		int i =0 ;
		while (i<10) {
			MockHttpServletRequestBuilder builders = MockMvcRequestBuilders.get("http://localhost:8080/api/qps/allusers")
					.accept(MediaType.APPLICATION_JSON);
			builders.contentType(MediaType.APPLICATION_JSON_UTF8);
			// 请求参数
			try {
				MvcResult result = mockMvc
						.perform(builders)
						.andDo(print()) // 输出处理结果
						.andExpect(status().isOk())
						.andReturn();
			} catch (Exception e) {
				e.printStackTrace();
			}

			i ++ ;
		}

	}



}
