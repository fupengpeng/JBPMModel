package com.jiudianlianxian.helloworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.junit.Test;


/**
 * 
 * @Title: HelloWorld
 * @Description: 描述
 * @Company: 山东九点连线信息技术有限公司
 * @ProjectName: JBPMModel
 * @author fupengpeng
 * @date 2017年11月14日 上午10:18:34
 */
public class HelloWorld {
	ProcessEngine processEngine = Configuration.getProcessEngine();
	/**
	 * 生成18张表
	 */
	@Test
	public void test1(){
		Configuration conf = new Configuration();//获取配置对象
		conf.setResource("jbpm.cfg.xml");//加载配置文件
		ProcessEngine processEngine = conf.buildProcessEngine();//创建流程引擎对象
	}
	
	/**
	 * 创建流程引擎对象的方法
	 */
	@Test
	public void test2(){
		//方式一
		/*Configuration conf = new Configuration();
		conf.setResource("jbpm.cfg.xml");
		ProcessEngine processEngine = conf.buildProcessEngine();*/
		
		//方式二  获得的是单例对象
		ProcessEngine processEngine = Configuration.getProcessEngine();
	}
	
	/**
	 * 部署流程定义
	 * @throws Exception 
	 */
	@Test
	public void test3() throws Exception{
		// 方式一----从类路径下读取文件
		NewDeployment deployment = processEngine.getRepositoryService().createDeployment();//获取部署对象;
		deployment.addResourceFromClasspath("helloworld2.jpdl.xml");//读取xml配置文件
		deployment.addResourceFromClasspath("helloworld2.png");//读取图片文件
		String id = deployment.deploy();//完成部署
		System.out.println(id);
		//  方式二----读取压缩文件流
//		NewDeployment deployment = processEngine.getRepositoryService().createDeployment();//获取部署对象;
//		deployment.addResourcesFromZipInputStream(new ZipInputStream(new FileInputStream(new File("E:\\MyEclipse\\workspacejavawebtestproject\\JBPMModel\\process\\hello.zip"))));
//		String id = deployment.deploy();
//		System.out.println("id = " + id );
	}
	
	/**
	 * 查询流程定义
	 */
	@Test
	public void test5(){
		//获得流程定义查询对象
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		long count = query.count();
		System.out.println("当前系统流程定义的数量  == " + count);
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition processDefinition : list) {
			String id = processDefinition.getId();
			String name = processDefinition.getName();
			String key = processDefinition.getKey();
			String deploymentId = processDefinition.getDeploymentId();
			System.out.println("id = " + id + "   name = " + name + "   key = " + key + "    deploymentId = " + deploymentId);
		}
		
	}
	
	/**
	 * 删除流程定义
	 */
	@Test
	public void test4(){
		String deploymentId = "1";
		processEngine.getRepositoryService().deleteDeployment(deploymentId);
	}

	
	/**
	 * 获取流程定义文件
	 * @throws IOException 
	 */
	@Test
	public void test6() throws IOException{
		String deploymentId = "10001";
		//获得一次部署对应的文件名
		Set<String> names = processEngine.getRepositoryService().getResourceNames(deploymentId);
		for (String string : names) {
			System.out.println("string = " + string);
		}
		
		//获得一次部署对应的文件输入流
		String resourceName = "helloworld.jpdl.xml";
		String resourceNameXml = "helloworld.jpdl.xml";
		InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		//通过输出流将文件保存到本地磁盘
		OutputStream out = new FileOutputStream(new File("E:\\test\\" + resourceName));
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			out.write(b,0,len);
		}
		out.close();
		in.close();
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void test7(){
//		String processDefinitionId = "请假流程-2";
//		ProcessInstance processInstance = processEngine.getExecutionService().startProcessInstanceById(processDefinitionId);
		
		String key = "请假流程";
		//启动流程后，产生流程实例对象
		ProcessInstance processInstance = processEngine.getExecutionService().startProcessInstanceByKey(key);
		String id = processInstance.getId();
		String name = processInstance.getName();
		System.out.println("id = " + id + "   name = " + name + "   key = " + key);
		
	}
	
	/**
	 * 查询我的任务列表
	 */
	@Test
	public void test8(){
		String userId = "李四";
		//启动流程后，产生流程实例对象
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.assignee(userId);  // 添加查询的过滤条件
		List<Task> list = query.list();
		for (Task task : list) {
			String executionId = task.getExecutionId();  //用于特殊情况，不需要李四审批时，查到他的所要执行的任务id，传递给test10，根据传入的id，越过此审批。
			System.out.println("id = " + task.getId() + "   name = " + task.getName()+"  executionId = " + executionId );
			//id = 30002   name = 提交申请
		}
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test9(){
		
		String taskId = "90002";
		processEngine.getTaskService().completeTask(taskId);
		
	}
	
	/**
	 * 直接跳转到下一步，越过此步
	 */
	@Test
	public void test10(){
		String executionId = "请假流程.90001";
		processEngine.getExecutionService().signalExecutionById(executionId,"to task3");
		
		//流程实例查询对象
//		ProcessInstanceQuery query = processEngine.getExecutionService().createProcessInstanceQuery();
//		List<ProcessInstance> list = query.list();
		
		
		//部署查询对象
//		DeploymentQuery query = processEngine.getRepositoryService().createDeploymentQuery();
//		List<Deployment> list = query.list();
//		for (Deployment d : list) {
//			System.out.println("d.getId = " + d.getId()   + "    " + d.getName());
//		}
		
	}
	
	/**
	 * 查询对象的使用方式
	 */
	@Test
	public void test11(){
		//创建流程定义查询对象
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		//添加过滤条件
		query.processDefinitionKey("请假流程");
		//添加排序条件
		query.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION);
		//添加分页条件
		query.page(0, 10);
//		query.page(10, 20);
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			System.out.println("pd.getId = " + pd.getId()   + "  name = " + pd.getName() + "   key = " + pd.getKey());
		}
		
	}
	
	/**
	 * 获取最新版本的流程定义
	 */
	@Test
	public void test12(){
		//获取所有的流程定义
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION);
		List<ProcessDefinition> list = query.list();
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : list) {
			String id = pd.getId();
			String key = pd.getKey();
			String name = pd.getName();
			int version = pd.getVersion();
//			System.out.println("Id = " + id   + "  version = " + version + "   key = " + key);
		    map.put(key, pd);
		}
		
		List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
		for (ProcessDefinition pd : pdList) {
			String id = pd.getId();
			String key = pd.getKey();
			String name = pd.getName();
			int version = pd.getVersion();
			System.out.println("Id = " + id   + "  version = " + version + "   key = " + key);
		   
		}
		
	}
	
}
