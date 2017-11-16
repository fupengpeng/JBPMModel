package com.jiudianlianxian.task.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;

public class GroupTaskTest {

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
		deployment
				.addResourceFromClasspath("com/jiudianlianxian/task/group/group.jpdl.xml");
		deployment
				.addResourceFromClasspath("com/jiudianlianxian/task/group/group.png");

		String id = deployment.deploy();
		System.out.println("id = " + id);
	}

	/**
	 * 启动一个流程实例
	 */
	@Test
	public void startProcess() {

		// 3.在启动流程时时设置。 设置流程变量
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userName", "xiaowang"); // 设置当前登录用户为流程执行者
//		ProcessInstance pi = pe.getExecutionService()
//				.startProcessInstanceByKey("组任务流程", map);
		ProcessInstance pi = pe.getExecutionService()
				.startProcessInstanceByKey("组任务流程");
		System.out.println("id = " + pi.getId());
	}

	/**
	 * 查询组任务列表
	 */
	@Test
	public void test3() {
		String userId = "zhangsan";
		List<Task> list2 = pe.getTaskService().findGroupTasks(userId);

		for (Task t : list2) {
			System.out.println(t.getId() + "----" + t.getName() + "----"
					+ t.getExecutionId());
		}
	}

	/**
	 * 拾取组任务
	 */
	@Test
	public void test1() {
		// 组任务中任何一个人都可以办理此任务，
		// 而且只能有一个人办理此任务，如果有一个人拾取了此任务，不执行退回操作，其他人是不能够再次拾取此任务的
		// 1.从组任务中拾取到个人任务重
		String taskId = "480002";  //组任务id
		String userId = "lisi";  //拾取任务给谁
		pe.getTaskService().takeTask(taskId, userId);
	}
	
	/**
	 * 退回任务到组任务
	 */
	@Test
	public void test4() {
		// 组任务中任何一个人都可以办理此任务，
		// 而且只能有一个人办理此任务，如果有一个人拾取了此任务，不执行退回操作，其他人是不能够再次拾取此任务的
		// 个人退回拾取到的组任务，以便别人可以拾取此组任务
		String taskId = "480002";  //组任务id
		pe.getTaskService().assignTask(taskId, null);
	}
	
	/**
	 * 分配任务
	 */
	@Test
	public void test5(){
		//用户拾取到组任务以后，发现不能办理此任务，则可以讲此任务分配给有资格办理的用户
		String taskId = "480002";  //组任务id
		String userId = "wangwu";  //分配任务给谁
		pe.getTaskService().assignTask(taskId, userId);
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test6(){
		
		// 4.在办理任务时设置       设置流程变量
		String taskId = "480002";
		pe.getTaskService().completeTask(taskId);
		

	}
	

	/**
	 * 直接向后跳一步
	 */
	@Test
	public void test02() {
		String executionId = "custom.410001";

		pe.getExecutionService()
				.signalExecutionById("custom.410001", "to end1");

	}

}
