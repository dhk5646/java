package com.aks.study.ehcache.service;

import com.aks.study.ehcache.constants.EhcacheConfigurationConstants;
import com.aks.study.ehcache.entity.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest
public class EhcachePersonServiceTest {

    @Autowired
    private EhcachePersonService ehcachePersonService;

    @Autowired 
    private CacheManager cacheManager;

    @AfterEach
    void afterEach() {
        // 각 메소드 마다 호출 후 실행된다.
        cacheManager.getCache(EhcacheConfigurationConstants.PERSON_CACHE).clear();
    }

    @Test
    public void selectPerson_호출시_캐싱되지않는다() {

        // given
        String id = "aks";

        // when
        ehcachePersonService.selectPerson(id);

        //then
        Cache.ValueWrapper cache = cacheManager.getCache(EhcacheConfigurationConstants.PERSON_CACHE).get(id);// 캐싱 조회
        Assertions.assertNull(cache);
    }

    @Test
    public void selectPersonFromCache_호출시_캐싱된다() {

        // given
        String id = "aks";

        // when
        ehcachePersonService.selectPersonFromCache(id);

        //then
        Cache.ValueWrapper cache = cacheManager.getCache(EhcacheConfigurationConstants.PERSON_CACHE).get(id);// 캐싱 조회
        Assertions.assertNotNull(cache);

    }

    @Test
    public void updatePersonInCache_호출시_캐시가_변경된다() {

        // given
        String id = "aks";
        String expected = "스악";
        ehcachePersonService.selectPersonFromCache(id); // 먼저 1회 호출하여 캐싱한다.


        // when
        ehcachePersonService.updatePersonInCache(id, expected); // 캐시 값을 변경한다.

        //then
        Cache.ValueWrapper actualWrapper = cacheManager.getCache(EhcacheConfigurationConstants.PERSON_CACHE).get(id);// 변경 후 캐싱 조회
        String actual = ((Person) actualWrapper.get()).getName(); // 캐시에서 변경한 name 값을 가져온다.

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void deletePersonInCache_호출시_캐시가_삭제된다() {

        // given
        String id = "aks";
        ehcachePersonService.selectPersonFromCache(id); // 먼저 1회 호출하여 캐싱한다.


        // when
        ehcachePersonService.deletePersonFromCache(id); // 캐시 값을 삭제한다.


        //then
        Cache.ValueWrapper actual = cacheManager.getCache(EhcacheConfigurationConstants.PERSON_CACHE).get(id);// 캐시 조회한다.

        Assertions.assertNull(actual);

    }
}
