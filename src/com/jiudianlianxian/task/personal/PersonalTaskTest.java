package com.jiudianlianxian.task.personal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;

public class PersonalTaskTest {
	
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
				deployment.addResourceFromClasspath("com/jiudianlianxian/task/personal/personal.jpdl.xml");
				deployment.addResourceFromClasspath("com/jiudianlianxian/task/personal/personal.png");

				String id = deployment.deploy();
				System.out.println("" + id);
			}

			/**
			 * 启动一个流程实例
			 */
			@Test
			public void startProcess() {
			
				// 3.在启动流程时时设置。  设置流程变量
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userName", "xiaowang"); //设置当前登录用户为流程执行者
				ProcessInstance pi = pe.getExecutionService().startProcessInstanceByKey("个人任务流程", map);
				System.out.println("id = " + pi.getId());
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
					System.out.println(t.getId() + "----" + t.getName() + "----"
							+ t.getExecutionId());
				}
			}
			/**
			 * 办理任务
			 */
			@Test
			public void test1(){
				
				// 4.在办理任务时设置       设置流程变量
				String taskId = "460003";
				pe.getTaskService().completeTask(taskId);
				

			}
			
			/**
			 * 直接向后跳一步
			 */
			@Test
			public void test02(){
				String executionId = "custom.410001";
				
				pe.getExecutionService().signalExecutionById("custom.410001","to end1");
				
			}
	
}
