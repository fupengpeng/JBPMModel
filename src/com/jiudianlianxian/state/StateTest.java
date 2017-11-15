package com.jiudianlianxian.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;


/**
 * 
 * @Title: StateTest
 * @Description: 状态活动
 * @Company: 山东九点连线信息技术有限公司
 * @ProjectName: JBPMModel
 * @author fupengpeng
 * @date 2017年11月15日 下午2:46:26
 */
public class StateTest {
	// 读取核心配置文件，并创建表
	ProcessEngine pe = Configuration.getProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy() {
		// 读取核心配置文件并创建表

		// 获得部署对象
		NewDeployment deployment = pe.getRepositoryService().createDeployment();

		// 加载流程定义文档
		deployment.addResourceFromClasspath("com/jiudianlianxian/state/state.jpdl.xml");
		deployment.addResourceFromClasspath("com/jiudianlianxian/state/state.png");

		String id = deployment.deploy();
		System.out.println(id);

	}

	/**
	 * 启动一个流程实例
	 */
	@Test
	public void startProcess() {
		ProcessInstance pi = pe.getExecutionService()
				.startProcessInstanceByKey("状态活动流程");
		System.out.println("启动一个流程实例  " + pi.getId());
	}


	/**
	 * 执行状态活动节点（直接跳转到下一个节点）
	 */
	@Test
	public void test2() {
		
		String executionId = "状态活动流程.160001";
		pe.getExecutionService().signalExecutionById(executionId, "to end1");
		
	}


}
