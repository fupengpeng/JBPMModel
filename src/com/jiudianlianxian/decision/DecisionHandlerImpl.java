package com.jiudianlianxian.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;


/**
 * 
 * @Title: DecisionHandlerImpl
 * @Description: 判断
 * @Company: 山东九点连线信息技术有限公司
 * @ProjectName: JBPMModel
 * @author fupengpeng
 * @date 2017年11月15日 下午4:42:42
 */
public class DecisionHandlerImpl implements DecisionHandler {

	@Override
	public String decide(OpenExecution execution) {
		//
		String name = "to task2";
		Double money = (Double) execution.getVariable("money");
		if (money > 500) {
			name = "to task3";
		}
		return name;
	}

}
