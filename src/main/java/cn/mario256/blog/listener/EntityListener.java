/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import cn.mario256.blog.entity.BaseEntity;

/**
 * Listener - 创建日期、修改日期、版本处理
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public class EntityListener {

	/**
	 * 保存前处理
	 * 
	 * @param entity
	 *            实体对象
	 */
	@PrePersist
	public void prePersist(BaseEntity<?> entity) {
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setVersion(null);
	}

	/**
	 * 更新前处理
	 * 
	 * @param entity
	 *            实体对象
	 */
	@PreUpdate
	public void preUpdate(BaseEntity<?> entity) {
		entity.setModifyDate(new Date());
	}

}