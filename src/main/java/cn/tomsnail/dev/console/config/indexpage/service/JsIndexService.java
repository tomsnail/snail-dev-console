/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.indexpage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import cn.tomsnail.dev.console.config.indexpage.entity.JsIndex;
import cn.tomsnail.dev.console.config.indexpage.dao.JsIndexDao;

/**
 * 首页配置Service
 * @author yangsong
 * @version 2017-12-14
 */
@Service
@Transactional(readOnly = true)
public class JsIndexService extends CrudService<JsIndexDao, JsIndex> {

	public JsIndex get(String id) {
		return super.get(id);
	}
	
	public List<JsIndex> findList(JsIndex jsIndex) {
		return super.findList(jsIndex);
	}
	
	public Page<JsIndex> findPage(Page<JsIndex> page, JsIndex jsIndex) {
		return super.findPage(page, jsIndex);
	}
	
	@Transactional(readOnly = false)
	public void save(JsIndex jsIndex) {
		super.save(jsIndex);
	}
	
	@Transactional(readOnly = false)
	public void delete(JsIndex jsIndex) {
		super.delete(jsIndex);
	}
	
}