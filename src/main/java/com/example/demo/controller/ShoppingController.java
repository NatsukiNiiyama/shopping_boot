package com.example.demo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

	@RequestMapping(value = { "/", "/login" }, method = { GET, POST })
	public String login(Model model) {

		LoginUserDBAccessJDBC dba = new LoginUserDBAccessJDBC();
		List<LoginUserEntity> list = dba.findAll();
		
		LoginForm form = new LoginForm();
		model.addAttribute(form);

		return "login";
	}

	@RequestMapping(value = "/menu", method = { GET, POST })
	public String toMenu(@Valid LoginForm form, BindingResult result) {

		LoginUserDBAccessJDBC dba = new LoginUserDBAccessJDBC();
		if (result.hasErrors() || dba.findOne(form.getUserId()) == null) {
			return "login";
		}

		return "menu";
	}

	@RequestMapping(value = "/backmenu", method = { GET, POST })
	public String backMenu(LoginForm form, BindingResult result) {
		return "menu";
	}

	@RequestMapping(value = "/list", method = { GET, POST })
	public String toList(HttpServletRequest request, Model model) {

		ItemDBAccessJDBC dba = new ItemDBAccessJDBC();
		request.setAttribute("itemList", dba.findAll());
		//request.setAttribute("itemList", getItemList());

		DetailForm form = new DetailForm();
		model.addAttribute(form);

		return "list";
	}

	@RequestMapping(value = "/detail", method = { GET, POST })
	public String toDetail(DetailForm detailForm, HttpServletRequest request, Model model) {

		ItemDBAccessJDBC dba = new ItemDBAccessJDBC();
		request.setAttribute("dispItem", dba.findOne(detailForm.getItemId()));
		//request.setAttribute("dispItem", getItemFromId(detailForm.getItemId()));

		PurchaseForm purchaseForm = new PurchaseForm();
		model.addAttribute(purchaseForm);

		return "detail";
	}

	@RequestMapping(value = "/confirm", method = { GET, POST })
	public String toConFirm(@Valid PurchaseForm form, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "detail";
		}

		ItemDBAccessJDBC dba = new ItemDBAccessJDBC();
		request.setAttribute("dispItem", dba.findOne(form.getItemId()));
		//request.setAttribute("dispItem", getItemFromId(form.getItemId()));

		return "confirm";
	}

	private List<ItemEntity> getItemList() {
		List<ItemEntity> list = new ArrayList<>();
		list.add(new ItemEntity(1, "参考書", "images/book_law_roppouzensyo.png", 1000));
		list.add(new ItemEntity(2, "CD", "images/entertainment_music.png", 2000));
		list.add(new ItemEntity(3, "Tシャツ", "images/fashion_sutajan.png", 3000));
		return list;
	}

	private ItemEntity getItemFromId(int id) {

		for (ItemEntity item : getItemList()) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}
}