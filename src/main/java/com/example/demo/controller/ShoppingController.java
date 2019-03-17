package com.example.demo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dba.BuyHistoryDBAccessJDBC;
import com.example.demo.dba.ItemDBAccessJDBC;
import com.example.demo.dba.LoginUserDBAccessJDBC;
import com.example.demo.entity.BuyHistoryEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.LoginUserEntity;
import com.example.demo.form.DetailForm;
import com.example.demo.form.LoginForm;
import com.example.demo.form.PurchaseForm;
import com.example.demo.form.UserIdForm;

@Controller
public class ShoppingController {

	@Autowired
	LoginUserDBAccessJDBC loginUserDba;

	@Autowired
	ItemDBAccessJDBC itemDba;
	
	@Autowired
	BuyHistoryDBAccessJDBC historyDba;

	@RequestMapping(value = { "/", "/login" }, method = { GET, POST })
	public String login(Model model) {

		LoginForm form = new LoginForm();
		model.addAttribute(form);

		return "login";
	}

	@RequestMapping(value = "/menu", method = { GET, POST })
	public String toMenu(@Valid LoginForm form, BindingResult result, Model model) {

		LoginUserEntity entity = loginUserDba.findOne(form.getUserId());
		if (result.hasErrors() || entity == null || !entity.getPassword().equals(form.getPassword())) {
			return "login";
		}

		model.addAttribute("loginName", entity.getUserName());
		model.addAttribute("loginId", entity.getUserId());
		model.addAttribute(new UserIdForm());
		return "menu";
	}

	@RequestMapping(value = "/backmenu", method = { GET, POST })
	public String backMenu(UserIdForm form, Model model) {

		LoginUserEntity entity = loginUserDba.findOne(form.getUserId());

		model.addAttribute("loginName", entity.getUserName());
		model.addAttribute("loginId", entity.getUserId());
		model.addAttribute(new UserIdForm());
		return "menu";
	}

	@RequestMapping(value = "/list", method = { GET, POST })
	public String toList(UserIdForm userIdForm, Model model) {

		List<ItemEntity> list = itemDba.findAll();
		model.addAttribute("itemList", list);

		model.addAttribute(new DetailForm());

		model.addAttribute("loginId", userIdForm.getUserId());
		model.addAttribute(new UserIdForm());

		return "list";
	}

	@RequestMapping(value = "/detail", method = { GET, POST })
	public String toDetail(UserIdForm userIdForm, DetailForm detailForm, Model model) {

		model.addAttribute("dispItem", itemDba.findOne(detailForm.getItemId()));
		model.addAttribute(new PurchaseForm());

		model.addAttribute("loginId", userIdForm.getUserId());
		model.addAttribute(new UserIdForm());

		return "detail";
	}

	@RequestMapping(value = "/confirm", method = { GET, POST })
	public String toConFirm(UserIdForm userIdForm, @Valid PurchaseForm form, BindingResult result, Model model) {

		model.addAttribute("dispItem", itemDba.findOne(form.getItemId()));
		model.addAttribute(new PurchaseForm());

		model.addAttribute("loginId", userIdForm.getUserId());
		model.addAttribute(new UserIdForm());
		
		if (result.hasErrors() || !isNumber(form.getCount())) {
			return "detail";
		}
		model.addAttribute("count", Integer.parseInt(form.getCount()));
		
		return "confirm";
	}

	@RequestMapping(value = "/finish", method = { GET, POST })
	public String toFinish(UserIdForm userIdForm, PurchaseForm form, Model model) {

		historyDba.save(new BuyHistoryEntity(null, userIdForm.getUserId(), form.getItemId(), Integer.parseInt(form.getCount())));
		
		model.addAttribute("loginId", userIdForm.getUserId());
		
		return "finish";
	}
	
	private boolean isNumber(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
}