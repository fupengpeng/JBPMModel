package com.jiudianlianxian.forkjoin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;



/**
 * 
 * @Title: ForkJoinTest
 * @Description: 分支合并活动测试
 * @Company: 山东九点连线信息技术有限公司
 * @ProjectName: JBPMModel
 * @author fupengpeng
 * @date 2017年11月15日 下午4:43:36
 */
public class ForkJoinTest {
	// 读取核心配置文件，并创建表
	ProcessEngine pe = Configuration.getProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy() {
		// 获得部署对象
		NewDeployment deployment = pe.getRepositoryService().createDeployment();

		// 加载流程定义文档
		deployment.addResourceFromClasspath("com/jiudianlianxian/forkjoin/forkjoin.jpdl.xml");
		deployment.addResourceFromClasspath("com/jiudianlianxian/forkjoin/forkjoin.png");

		String id = deployment.deploy();
		System.out.println("" + id);
	}

	/**
	 * 启动一个流程实例
	 */
	@Test
	public void startProcess() {
		ProcessInstance pi = pe.getExecutionService()
				.startProcessInstanceByKey("报销流程");
		System.out.println("启动一个流程实例  " + pi.getId());
	}
	
	
	/**
	 * 查询我的任务列表
	 */
	@Test
	public void test3() {
		/*
		 * TaskQuery query = processEngine.getTaskService().createTaskQuery();
		 * query.assignee("张三"); List<Task> list = query.list();
		 */

		String userId = "张三";
		List<Task> list2 = pe.getTaskService().findPersonalTasks(userId);

		for (Task t : list2) {
			System.out.println(t.getId() + " " + t.getName() + " "
					+ t.getExecutionId());
		}
	}
	/**
	 * 办理任务，同时设置流程变量
	 */
	@Test
	public void test1(){
		
		// 4.在办理任务时设置       设置流程变量
		String taskId = "220002";
		String outcome = "to exclusive1";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("money", new Double(600));
		pe.getTaskService().completeTask(taskId,outcome,map);
		

	}
	
}
