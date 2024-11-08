package com.boot.web.action;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.beans.Goods;
import com.boot.common.BaseAction;
import com.boot.web.service.GoodsService;
import com.boot.web.service.Type2Service;

import util.Constant;
import util.FieldUtil;
import util.MessageUtil;
import util.Page;

@Controller
@RequestMapping("/sys")
public class GoodsAction extends BaseAction {
	@Autowired
	private Type2Service	type2Service;
	private String				actionname	= "商品";
	private String				actionclass	= "Goods";
	@Autowired
	private GoodsService	service;

	@RequestMapping(value = "/add2Goods.do")
	public String add2() {
		putRequestValue("list", type2Service.selectAll());
		request.setAttribute("actionname", actionname);
		request.setAttribute("actionclass", actionclass);
		return "addGoods";
	}

	@RequestMapping(value = "/getGoods.do")
	public String get(int uid) {
		try {
			putRequestValue("list", type2Service.selectAll());
			Goods temp = service.get(uid);
			request.setAttribute("modifybean", temp);

			request.setAttribute("actionname", actionname);
			request.setAttribute("actionclass", actionclass);
			return "modifyGoods";
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(request, "获取信息失败.");
			return ERROR;
		}
	}

	@RequestMapping(value = "/addGoods.do")
	public String add(Goods bean) {
		try {
			bean.setScore(5d);
			bean.setViewscount(0);
			bean.setSellamount(0);
			service.addGoods(bean);

			MessageUtil.addMessage(request, "添加成功.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(request, "添加失败.");
			return ERROR;
		}

	}

	@RequestMapping(value = "/updateGoods.do")
	public String update(Goods bean) {
		try {
			service.update(bean);
//			for (int i = 0; i < 10; i++) {
//				bean.setId(null);
//				bean.setScore(5d);
//				bean.setViewscount(0);
//				bean.setSellamount(0);
//				service.addGoods(bean);
//			}
			MessageUtil.addMessage(request, "更新成功.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(request, "更新失败.");
			return ERROR;
		}
	}
//宠物货品管理咨询页8
	@RequestMapping(value = "/queryGoods.do")
	public String query() {
		try {
			// 字段名称集合
			LinkedList<String> parmnames = new LinkedList<String>();
			// 字段值集合
			LinkedList<Object> parmvalues = new LinkedList<Object>();
			Page p = FieldUtil.createPage(request, Goods.class, parmnames, parmvalues);

			Page page = service.selectPage(p, Goods.class);
			session.setAttribute(Constant.SESSION_PAGE, page);

			request.setAttribute("actionname", actionname);
			request.setAttribute("actionclass", actionclass);
			return "listGoods";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@RequestMapping(value = "/deleteGoods.do")
	public String delete(String ids) {
		try {
			service.deleteAll(ids);
			MessageUtil.addRelMessage(request, "删除信息成功.", "mainquery");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(request, "删除信息失败.");
			return ERROR;
		}
	}

}
