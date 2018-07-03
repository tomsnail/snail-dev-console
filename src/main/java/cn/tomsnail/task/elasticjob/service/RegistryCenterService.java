package cn.tomsnail.task.elasticjob.service;



import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.lite.console.domain.RegistryCenterConfiguration;
import com.dangdang.ddframe.job.lite.console.util.SessionRegistryCenterConfiguration;
import com.dangdang.ddframe.job.lite.lifecycle.internal.reg.RegistryCenterFactory;
import com.google.common.base.Optional;

@Service
public class RegistryCenterService {
	
	
	
	
	
	 /**
     * 从当前会话范围获取注册中心配置.
     *
     * @return 事件追踪数据源配置
     */
    public static boolean getRegistryCenterConfiguration(String zkAddress,String namespace) {
    	RegistryCenterFactory.createCoordinatorRegistryCenter(zkAddress, namespace, Optional.fromNullable(""));
    	RegistryCenterConfiguration registryCenterConfiguration = new RegistryCenterConfiguration();
    	registryCenterConfiguration.setZkAddressList(zkAddress);
    	registryCenterConfiguration.setNamespace(namespace);
    	registryCenterConfiguration.setActivated(true);
    	registryCenterConfiguration.setDigest(null);
    	SessionRegistryCenterConfiguration.setRegistryCenterConfiguration(registryCenterConfiguration);
    	return true;
    }

}
