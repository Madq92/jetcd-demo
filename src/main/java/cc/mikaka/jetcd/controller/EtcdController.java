package cc.mikaka.jetcd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.mikaka.jetcd.commons.Result;
import cc.mikaka.jetcd.domain.vo.KvVO;
import cc.mikaka.jetcd.etcd.EtcdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/etcd")
public class EtcdController {
    @Autowired
    private EtcdService etcdService;

    @ApiOperation("添加Key")
    @PostMapping
    public Result<Object> addKey(@RequestBody KvVO vo) {
        return Result.ok(etcdService.addKey(vo.getKey(), vo.getValue()));
    }

    @ApiOperation("获取Key")
    @GetMapping
    public Result<Object> getKey(@RequestParam String key) {
        return Result.ok(etcdService.getKey(key));
    }
}
