package com.springboot.redis.demo;

import com.springboot.redis.demo.controller.RedisController;
import com.springboot.redis.demo.model.TestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    RedisController redisController;

    @Autowired
    RedisTemplate<String, Object> objectRedisTemplate;

    @Autowired
    RedisTemplate<Object, TestModel> testRedisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
//        System.out.println(redisController.testObject());
//        System.out.println(redisController.testHashOptions());
//        HashOperations<String, String, String> operations = redisTemplate.opsForHash();
//        operations.put("h", "1", "a");
//        redisTemplate.expire("h", 1, TimeUnit.SECONDS);
//        System.out.println(redisTemplate.opsForHash().values("h"));
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        operations.put("h", "2", "b");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(redisTemplate.opsForHash().values("h"));
//        operations.put("h", "3", "c");
//        System.out.println(redisTemplate.opsForHash().values("h"));
//        System.out.println(redisTemplate.opsForValue().get("model"));
//        Set<String> t = new HashSet<>();
//        Set<Object> s = redisTemplate.opsForHash().keys("hashValues");
//        for (Object o : s) {
//            t.add(o.toString());
//        }
////        Set<String> sortSet = new TreeSet<>(Comparator.reverseOrder());
////        sortSet.addAll(t);
//        Set<Object> sortSet = new TreeSet<>((o1, o2) -> ((String) o2).compareTo((String) o1));
//        sortSet.addAll(s);
//        System.out.println(sortSet.iterator().next());
//        TestModel model = (TestModel) objectRedisTemplate.opsForValue().get("model3");
//        System.out.println(model);
    }

    @Test
    public void test01() {
        testRedisTemplate.opsForValue().set("testModel", (TestModel) objectRedisTemplate.opsForValue().get("model3"));
    }

}
