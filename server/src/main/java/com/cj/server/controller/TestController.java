package com.cj.server.controller;


import com.cj.common.utils.ResultUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/test")
@ApiIgnore
public class TestController {

    RestUtil restUtil = new RestUtil();

    @GetMapping("/11")
    public ApiResult test(){

        String s = "{\"modulevkey\":{\"method\":\"CgiGetDownUrl\",\"module\":\"vkey.GetDownUrlServer\",\"param\":{\"songtype\":[1],\"downloadfrom\":1,\"uin\":\"675542126\",\"filename\":[\"M500003aAYrm3GE0Ac.mp3\"],\"ctx\":1,\"guid\":\"joe\",\"referer\":\"y.qq.com\",\"songmid\":[\"003aAYrm3GE0Ac\"],\"scene\":1}},\"comm\":{\"authst\":\"00015d70aa810058c488672040ca2460e01952c5f2fb16097d5a0cde33461cb7b6846e39e03ab8170a84b17b91519912f6d48b21d70e86ae7f3b29cc444ca801e68e4a3995c9e73735eef45f0feb09220bb4997c185ff59a7341c93863505c99\",\"chid\":\"10034015\",\"ct\":\"3\",\"cv\":\"4120104\",\"qq\":\"675542126\",\"uid\":\"1480744241\",\"v\":\"4120104\"}}";
        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg";


        return ResultUtil.result(restUtil.postJson2(url,s));
    }

}
