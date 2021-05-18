package com.xuecheng.manageCms;

import com.xuecheng.manageCms.dao.CmsPageRepository;
import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMongodbRepository {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void testFindPage() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = new PageRequest(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageRequest);
        System.out.println(all.getContent());
    }

}
