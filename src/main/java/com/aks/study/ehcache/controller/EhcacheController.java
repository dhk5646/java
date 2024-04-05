package com.aks.study.ehcache.controller;

import com.aks.study.ehcache.entity.Person;
import com.aks.study.ehcache.service.EhcachePersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ehcache")
public class EhcacheController {

    private final EhcachePersonService ehcachePersonService;
    @GetMapping(value = "/no-cache-person/{name}")
    public Person getNoEhcacheCommonCode(@PathVariable String name) {
        return ehcachePersonService.selectPerson(name);
    }

    @GetMapping(value = "/cache-person/{name}")
    public String getEhcacheCommonCode(@PathVariable String name) {
        return ehcachePersonService.selectPersonFromCache(name).toString();
    }
}
