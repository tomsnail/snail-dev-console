/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.tomsnail.dev.console.config.indexpage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import cn.tomsnail.dev.console.config.indexpage.entity.JsIndex;
import cn.tomsnail.dev.console.config.indexpage.dao.JsIndexDao;

/**
 * 首页配置Service
 * @author yangsong
 * @version 2017-12-20
 */
@Service
@Transactional(readOnly = true)
public class JsIndexService extends TreeService<JsIndexDao, JsIndex> {

	public JsIndex get(String id) {
		return super.get(id);
	}
	
	public List<JsIndex> findList(JsIndex jsIndex) {
		if (StringUtils.isNotBlank(jsIndex.getParentIds())){
			jsIndex.setParentIds(","+jsIndex.getParentIds()+",");
		}
		return super.findList(jsIndex);
	}
	
	@Transactional(readOnly = false)
	public void save(JsIndex jsIndex) {
		
		if(jsIndex.getParent()==null||jsIndex.getParent().getId()==null||jsIndex.getParent().getId().equals("0")||jsIndex.getParent().getId().equals("")){
		}else{
			JsIndex group = get(jsIndex.getParent().getId());
			if(group!=null)
				jsIndex.setGroupName(group.getName());
		}
		super.save(jsIndex);
		
		JsIndex index = new JsIndex();
		index.setParent(jsIndex);
		List<JsIndex> subs = findList(index);
		if(subs!=null&&subs.size()>0){
			for(JsIndex _i:subs){
				_i.setGroupName(jsIndex.getName());
				super.save(_i);
			}
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(JsIndex jsIndex) {
		super.delete(jsIndex);
	}
	
}