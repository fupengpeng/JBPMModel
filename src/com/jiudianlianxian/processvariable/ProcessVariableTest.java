package com.jiudianlianxian.processvariable;

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

public class ProcessVariableTest {
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
		deployment
				.addResourceFromClasspath("com/jiudianlianxian/processvariable/testjpdl.jpdl.xml");
		deployment
				.addResourceFromClasspath("com/jiudianlianxian/processvariable/testjpdl.png");

		String id = deployment.deploy();
		System.out.println(id);

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
	 * 设置流程变量
	 */
	@Test
	public void test1() {
//		// 1.使用ExdutionService  设置流程变量
//		String name = "报销金额";
//		Double value = 100d;
//		// 一次设置一个流程变量
//		pe.getExecutionService().setVariable("报销流程.70001", name, value);		
//		
//		//一次设置多个流程变量
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("key1", "value1");
//		map.put("key2", "value2");
//		pe.getExecutionService().setVariables("报销流程.70001", map);
		
//		// 2.使用TaskService  设置流程变量
//		String taskId = "70002";
//		Map<String,Object> variables = new HashMap<String, Object>();
//		variables.put("key3", "value3");
//		pe.getTaskService().setVariables(taskId, variables);
		
//		// 3.在启动流程时时设置。  设置流程变量
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("key4", "value4");
//		map.put("key5", "value5");
//		pe.getExecutionService().startProcessInstanceByKey("报销流程", map);
		
		// 4.在办理任务时设置       设置流程变量
		String taskId = "110004";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("key6", "value6");
		map.put("key7", "value7");
		pe.getTaskService().completeTask(taskId,"to task2",map);

		
	}
	
	/**
	 * 获取流程变量
	 */
	@Test
	public void test2() {
//		// 1.使用ExdutionService  获取流程变量
//		Set<String> names = pe.getExecutionService().getVariableNames("报销流程.70001");
//		for (String name : names) {
//			System.out.println(name);
//			Object value = pe.getExecutionService().getVariable("报销流程.70001", name);
//			System.out.println("报销金额 = " + value.toString());
//		}
		// 2.使用TaskService  获取流程变量
		String taskId = "70002";
		Set<String> names = pe.getTaskService().getVariableNames(taskId);
		Map<String, Object> map = pe.getTaskService().getVariables(taskId, names);
		for (String name : names) {
			Object value = pe.getExecutionService().getVariable("报销流程.70001", name);
			System.out.println("报销金额 = " + value.toString());
		}
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



}
