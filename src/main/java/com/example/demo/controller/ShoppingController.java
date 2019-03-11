package com.example.demo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dba.ItemDBAccessJDBC;
import com.example.demo.dba.LoginUserDBAccessJDBC;
import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.LoginUserEntity;
import com.example.demo.form.DetailForm;
import com.example.demo.form.LoginForm;
import com.example.demo.form.PurchaseForm;

@Controller
public class ShoppingController {

	@Autowired
	LoginUserDBAccessJDBC loginUserDba;

	@Autowired
	ItemDBAccessJDBC itemDba;

	@RequestMapping(value = { "/", "/login" }, method = { GET, POST })
	public String login(Model model) {

		LoginForm form = new LoginForm();
		model.addAttribute(form);

		return "login";
	}

	@RequestMapping(value = "/menu", method = { GET, POST })
	public String toMenu(@Valid LoginForm form, BindingResult result, HttpServletRequest request) {

		LoginUserEntity entity = loginUserDba.findOne(form.getUserId());
		if (result.hasErrors() || entity == null || !entity.getPassword().equals(form.getPassword())) {
			return "login";
		}

		request.setAttribute("loginUser", entity);
		return "menu";
	}

	@RequestMapping(value = "/backmenu", method = { GET, POST })
	public String backMenu(LoginForm form, BindingResult result) {
		return "menu";
	}

	@RequestMapping(value = "/list", method = { GET, POST })
	public String toList(HttpServletRequest request, Model model) {

		List<ItemEntity> list = itemDba.findAll();
		request.setAttribute("itemList", list);

		DetailForm form = new DetailForm();
		model.addAttribute(form);

		return "list";
	}

	@RequestMapping(value = "/detail", method = { GET, POST })
	public String toDetail(DetailForm detailForm, HttpServletRequest request, Model model) {

		request.setAttribute("dispItem", itemDba.findOne(detailForm.getItemId()));

		PurchaseForm purchaseForm = new PurchaseForm();
		model.addAttribute(purchaseForm);

		return "detail";
	}

	@RequestMapping(value = "/confirm", method = { GET, POST })
	public String toConFirm(@Valid PurchaseForm form, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "detail";
		}

		request.setAttribute("dispItem", itemDba.findOne(form.getItemId()));

		return "confirm";
	}
}