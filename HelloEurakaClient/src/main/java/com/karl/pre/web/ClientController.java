package com.karl.pre.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientController
{
    @GetMapping("/hello")
    public ResponseEntity<String> hello()
    {
        return new ResponseEntity<>("get hello", HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<String> hello1()
    {
        return new ResponseEntity<>("post hello", HttpStatus.OK);
    }

    @PostMapping("/world/{path}")
    public ResponseEntity<Map<String, String>> world(@PathVariable("path") String path,
                                            @RequestParam("wor") String wor,
                                            @RequestBody String hello)
    {
        Map<String, String> ret = new HashMap<>();
        ret.put("path", path);
        ret.put("wor", wor);
        ret.put("hello", hello);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    /** test the canary */
    @GetMapping("/v1")
    public ResponseEntity<String> v1()
    {
        return new ResponseEntity<>("get v1", HttpStatus.OK);
    }

    @GetMapping("/v2")
    public ResponseEntity<String> v2()
    {
        return new ResponseEntity<>("get v2", HttpStatus.OK);
    }
}
