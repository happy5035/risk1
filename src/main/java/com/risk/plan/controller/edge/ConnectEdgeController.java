package com.risk.plan.controller.edge;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risk.plan.entity.Emergency;
import com.risk.plan.entity.GoodsType;
import com.risk.plan.entity.TranModel;
import com.risk.plan.entity.Users;
import com.risk.plan.service.box.emer.EmerTypeService;
import com.risk.plan.service.box.emer.EmergencyService;

@Controller
public class ConnectEdgeController {
	//service
	@Autowired
	EmerTypeService emerTypeService;
	@Autowired
	EmergencyService emergencyService;
	@Autowired
	HttpServletResponse response;
	//httpServlet
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/gosearchDisaster")
	public String gosearchDisaster(ModelMap modelmap){
		//加载风险类型和事件
		List<String>emertypenames=emerTypeService.selectAllEmerTypeNames();
		Users user=(Users)request.getSession().getAttribute("user");
		String userid=user.getUserid();
		String usertype=user.getUsertype();
		Map<String, Object> params1=new HashMap<String, Object>();
		params1.put("userid", userid);
		params1.put("emertypename", emertypenames.get(0));
		params1.put("usertype", usertype);
		List<Emergency> emergencylist=emergencyService.selectByEmerTypeName(params1);
		if(emertypenames.size() >0)
		{
			modelmap.put("emergencylist", emergencylist);
		}else {
			String listStrng="No Option";
			modelmap.put("emergencylist", listStrng);
		}
		modelmap.put("emertypename", emertypenames);
		return "WebRoot/Edge/searchDisaster";
	}
	@RequestMapping("/gotoAddEdge")
	public String gotoAddEdge(ModelMap modelmap){
		List<String>emername=emergencyService.selectAllEmerNames();
		Users user=(Users)request.getSession().getAttribute("user");
		String userid=user.getUserid();
		String usertype=user.getUsertype();
		Map<String, Object> params1=new HashMap<String, Object>();
		params1.put("userid", userid);
		params1.put("emername", emername.get(0));
		params1.put("usertype", usertype);
		List<Emergency> emergencylist=emergencyService.selectAll();
		modelmap.put("emergencylist", emergencylist);
		List<Emergency> emergencylist1=emergencyService.selectByEmerName(params1);
		if(emername.size() >0)
		{
			modelmap.put("emersublist", emergencylist1);
		}else {
			String listStrng="No Option";
			modelmap.put("emersublist", listStrng);
		}
		//modelmap.put("emername", emername);
		
		
		return "WebRoot/Edge/addEdge";
	}

	@ResponseBody
	@RequestMapping("/findSubNum")
	public void findSubNum(String emername) throws UnsupportedEncodingException{
		
		Users user=(Users)request.getSession().getAttribute("user");
		String userid=user.getUserid();
		String usertype=user.getUsertype();
		emername=URLDecoder.decode(emername, "UTF-8");
		String cSelect = "";
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("userid", userid);
			
			params.put("usertype", usertype);
			params.put("emername", emername);
			List<Emergency> emersublist=emergencyService.selectByEmerName(params);
			
			if (emersublist != null && emersublist.size() > 0) {
				for (int i = 0; i < emersublist.size(); i++) {
					Emergency type = emersublist.get(i);
					cSelect += "<option value=" + type.getEmerid()
							+ ">" + type.getSubnum()+ "</option>";
				}
			} else {
				cSelect += "<option value=\"0\">该项目无子项目</option>";
			}			
		response.getWriter().write(cSelect);
		} catch (Exception e) {
		}
	}
}