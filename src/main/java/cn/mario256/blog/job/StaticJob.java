/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.job;

import cn.mario256.blog.service.StaticService;
import cn.mario256.blog.service.StaticService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Job - 静态化
 *
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Lazy(false)
@Component("staticJob")
public class StaticJob {

    @Resource(name = "staticServiceImpl")
    private StaticService staticService;

    /**
     * 生成首页静态
     */
    @Scheduled(fixedDelayString = "${job.static_generate_index.delay}")
    public void generateIndex() {
        staticService.generateIndex();
    }

    /**
     * 生成所有静态
     */
    @Scheduled(cron = "${job.static_generate_all.cron}")
    public void generateAll() {
        staticService.generateAll();
    }

}