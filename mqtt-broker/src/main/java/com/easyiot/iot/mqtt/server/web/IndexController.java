package com.easyiot.iot.mqtt.server.web;

import com.alibaba.fastjson.JSONObject;
import com.easyiot.iot.mqtt.server.store.client.ChannelStoreService;
import com.easyiot.iot.mqtt.server.store.client.TopicStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class IndexController {
    @Autowired
    ChannelStoreService channelStoreService;
    @Autowired
    TopicStoreService topicStoreService;

    @GetMapping("/")
    public Object index() {
        return "Run Success!<br> /topics 查看Topic <br> /clients 查看客户端";

    }

    @GetMapping("/topics/{page}/{size}")
    public Object topics(@PathVariable int page, @PathVariable int size) {
        return JSONObject.toJSONString(topicStoreService.listAll(page, size));

    }

    @GetMapping("/clients/{page}/{size}")

    public Object clients(@PathVariable int page, @PathVariable int size) {
        return JSONObject.toJSONString(channelStoreService.listAll(page, size));

    }

    @GetMapping("/total")
    public Object total() {
        return JSONObject.toJSONString(channelStoreService.clientCount());

    }

    /**
     * 根据Token获取用户数据
     *
     * @param request
     * @return
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    private Map<String, Object> getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            return null;
        }
        try {
            return jdbcTemplate.queryForMap("SELECT * FROM admin WHERE token=? ", token);
        } catch (Exception e) {
            return null;
        }
    }
}