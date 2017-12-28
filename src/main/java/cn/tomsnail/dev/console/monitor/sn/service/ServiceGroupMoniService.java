package cn.tomsnail.dev.console.monitor.sn.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.I0Itec.zkclient.IZkChildListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tomsnail.dev.console.config.server.dao.TsServerDao;
import cn.tomsnail.dev.console.config.server.entity.TsServer;
import cn.tomsnail.dev.console.monitor.sn.dao.TsServiceGroupDao;
import cn.tomsnail.dev.console.monitor.sn.dao.TsServiceNodeDao;
import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceGroup;
import cn.tomsnail.dev.console.monitor.sn.entity.TsServiceNode;
import cn.tomsnail.dev.console.util.ZkHelper;
import cn.tomsnail.http.register.core.JsonServerRegisterObject;

@Service
public class ServiceGroupMoniService {
	
	/**
	 * zk根
	 */
	private static final String ROOT = "/http/rest";
	
	
	private static final String PROVIDERS_STR = "providers";
	
	private static final String CUSTOMERS_STR = "customers";
	
	private static final String GROUP_STR = "group";
	
	private static final Map<String,ZkHelper> ZK_SERVER_MAP = new HashMap<String, ZkHelper>();
	
	@Autowired
	private TsServerDao tsServerDao;
	
	
	@Autowired
	private TsServiceGroupDao tsServiceGroupDao;
	
	@Autowired
	private TsServiceNodeDao tsServiceNodeDao;
	


	public void init(){
		TsServer tsServer = new TsServer();
		tsServer.setServiceType("ZOOKEEPER");
		tsServer.setIsMaster("1");
		tsServer.setServerType("02");
		List<TsServer> tsServers = tsServerDao.findList(tsServer);
		if(tsServers!=null&&tsServers.size()>0){
			for(TsServer server:tsServers){
				if(ZK_SERVER_MAP.containsKey(server.getServerIp()+":"+server.getServerPort())){
					
				}else{
					ZkHelper zkHelper = ZkHelper.get(server.getServerIp()+":"+server.getServerPort());
					ZK_SERVER_MAP.put(server.getServerIp()+":"+server.getServerPort(), zkHelper);
				}
				
			}
		}
		syncData();
	}
	
	private void syncData(){
		if(!ZK_SERVER_MAP.isEmpty()){
			Iterator<String> it = ZK_SERVER_MAP.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				ZkHelper zkHelper = ZK_SERVER_MAP.get(key);
				_syncData(zkHelper);
			}
		}
		
	}
	
	private void syncData(String key){
		ZkHelper zkHelper = ZK_SERVER_MAP.get(key);
		_syncData(zkHelper);
	}
	
	private void initNodeStatus(String p){
		TsServiceGroup _gGroup = new TsServiceGroup();
		_gGroup.setNameKey(p);
		List<TsServiceGroup> _groups =  tsServiceGroupDao.findList(_gGroup);
		if(_groups!=null&&_groups.size()>0){
			for(TsServiceGroup g:_groups){
				TsServiceNode tsServiceNode = new TsServiceNode();
				tsServiceNode.setServiceId(g.getId());
				List<TsServiceNode> nodes = tsServiceNodeDao.findList(tsServiceNode);
				if(nodes!=null&&nodes.size()>0){
					for(TsServiceNode _node:nodes){
						_node.setNodeStatus("1");
						_node.preUpdate();
						tsServiceNodeDao.update(_node);
					}
				}
			}
		}
	}
	
	private void _syncData(ZkHelper zkHelper){
		List<String> c_root = zkHelper.getPath(ROOT);
		if(c_root!=null&&c_root.size()>0){
			for(String p:c_root){
				List<String> c_p = null;
				try {
					c_p = zkHelper.getPath(ROOT+"/"+p+"/"+PROVIDERS_STR);
				} catch (Exception e) {
					continue;
				}
				initNodeStatus(p);
				if(c_p!=null&&c_p.size()>0){
					for(String _p:c_p){
						String pp = ROOT+"/"+p+"/"+PROVIDERS_STR+"/"+_p;
						Object data = null;
						try {
							data = zkHelper.readNode(pp);
						} catch (Exception e) {
							continue;
						}
						JsonServerRegisterObject jsonServerRegisterObject = JsonServerRegisterObject.newRegisterObject(data.toString());
						
						TsServiceGroup gGroup = new TsServiceGroup();
						gGroup.setNameKey(jsonServerRegisterObject.getGroup());
						List<TsServiceGroup> groups =  tsServiceGroupDao.findList(gGroup);
						if(groups!=null&&groups.size()>0){
							gGroup = groups.get(0);
						}else{
							gGroup.setGroupName(jsonServerRegisterObject.getGroup());
							gGroup.setName(jsonServerRegisterObject.getGroup());
							gGroup.preInsert();
							tsServiceGroupDao.insert(gGroup);
						}
						
						TsServiceGroup tsServiceGroup = new TsServiceGroup();
						tsServiceGroup.setNameKey(jsonServerRegisterObject.getService());
						groups =  tsServiceGroupDao.findList(tsServiceGroup);
						if(groups!=null&&groups.size()>0){
							tsServiceGroup = groups.get(0);
							tsServiceGroup.setParent(gGroup);
							tsServiceGroup.setParentIds(gGroup.getParentIds()+","+gGroup.getId());
							tsServiceGroup.preUpdate();
							tsServiceGroupDao.update(tsServiceGroup);
						}else{
							tsServiceGroup.setGroupName(jsonServerRegisterObject.getGroup());
							tsServiceGroup.setName(jsonServerRegisterObject.getService());
							tsServiceGroup.setParent(gGroup);
							tsServiceGroup.setParentIds(gGroup.getParentIds()+","+gGroup.getId());
							tsServiceGroup.preInsert();
							tsServiceGroupDao.insert(tsServiceGroup);
						}
						TsServiceNode tsServiceNode = new TsServiceNode();
						tsServiceNode.setServiceId(tsServiceGroup.getId());
						tsServiceNode.setAddressUrl(jsonServerRegisterObject.getAddUrl());
						tsServiceNode.setModule(jsonServerRegisterObject.getAppName());
						List<TsServiceNode> nodes = tsServiceNodeDao.findList(tsServiceNode);
						if(nodes!=null&&nodes.size()>0){
							tsServiceNode = nodes.get(0);
							tsServiceNode.setFailType(jsonServerRegisterObject.getFailType());
							tsServiceNode.setLbType(jsonServerRegisterObject.getLbType());
							tsServiceNode.setModule(jsonServerRegisterObject.getAppName());
							tsServiceNode.setNodeStatus(jsonServerRegisterObject.getStatus()+"");
							tsServiceNode.setRetryCount(jsonServerRegisterObject.getRetryCount()+"");
							tsServiceNode.setTimeout(jsonServerRegisterObject.getTimeout()+"");
							tsServiceNode.setVersion(jsonServerRegisterObject.getVersion());
							tsServiceNode.setWeight(jsonServerRegisterObject.getWeight()+"");
							tsServiceNode.preUpdate();
							tsServiceNodeDao.update(tsServiceNode);
						}else{
							tsServiceNode.setFailType(jsonServerRegisterObject.getFailType());
							tsServiceNode.setLbType(jsonServerRegisterObject.getLbType());
							tsServiceNode.setModule(jsonServerRegisterObject.getAppName());
							tsServiceNode.setNodeStatus(jsonServerRegisterObject.getStatus()+"");
							tsServiceNode.setRetryCount(jsonServerRegisterObject.getRetryCount()+"");
							tsServiceNode.setTimeout(jsonServerRegisterObject.getTimeout()+"");
							tsServiceNode.setVersion(jsonServerRegisterObject.getVersion());
							tsServiceNode.setWeight(jsonServerRegisterObject.getWeight()+"");
							tsServiceNode.preInsert();
							tsServiceNodeDao.insert(tsServiceNode);
						}
												
					}
				}
			}
		}
	}
	
	private void changeListener(){
		if(!ZK_SERVER_MAP.isEmpty()){
			Iterator<String> it = ZK_SERVER_MAP.keySet().iterator();
			while(it.hasNext()){
				final String key = it.next();
				ZkHelper zkHelper = ZK_SERVER_MAP.get(key);
				zkHelper.getZkClient().subscribeChildChanges(ROOT, new IZkChildListener() {
					@Override
					public void handleChildChange(String arg0, List<String> arg1)throws Exception {
						syncData(key);
					}
				});
			}
		}
	}
	
	
}
