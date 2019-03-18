package com.example.demo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.example.demo.form.LoginForm;
import com.example.demo.form.ShoppingForm;

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
		return "menu";
	}

	@RequestMapping(value = "/backmenu", method = { GET, POST })
	public String backMenu(ShoppingForm form, Model model) {

		LoginUserEntity entity = loginUserDba.findOne(form.getUserId());

		model.addAttribute("loginName", entity.getUserName());
		model.addAttribute("loginId", entity.getUserId());
		return "menu";
	}

	@RequestMapping(value = "/list", method = { GET, POST })
	public String toList(ShoppingForm form, Model model) {

		List<ItemEntity> list = itemDba.findAll();
		model.addAttribute("itemList", list);

		model.addAttribute("loginId", form.getUserId());

		return "list";
	}

	@RequestMapping(value = "/detail", method = { GET, POST })
	public String toDetail(ShoppingForm form, Model model) {

		model.addAttribute("dispItem", itemDba.findOne(form.getItemId()));

		model.addAttribute("loginId", form.getUserId());

		return "detail";
	}

	@RequestMapping(value = "/confirm", method = { GET, POST })
	public String toConFirm(@Valid ShoppingForm form, BindingResult result, Model model) {

		model.addAttribute("dispItem", itemDba.findOne(form.getItemId()));

		model.addAttribute("loginId", form.getUserId());

		if (result.hasErrors() || !isNumber(form.getCount())) {
			return "detail";
		}
		model.addAttribute("count", Integer.parseInt(form.getCount()));

		return "confirm";
	}

	@RequestMapping(value = "/finish", method = { GET, POST })
	public String toFinish(ShoppingForm form, Model model) {

		historyDba.save(
				new BuyHistoryEntity(null, form.getUserId(), form.getItemId(), Integer.parseInt(form.getCount())));

		model.addAttribute("loginId", form.getUserId());

		return "finish";
	}

	@RequestMapping(value = "/history", method = { GET, POST })
	public String toHistory(ShoppingForm form, Model model) {

		model.addAttribute("userHistoryList", historyDba.findByUserId(form.getUserId()));
		model.addAttribute("itemMap", convertListToMap(itemDba.findAll()));
		model.addAttribute("loginId", form.getUserId());

		return "history";
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

	private Map<Integer, ItemEntity> convertListToMap(List<ItemEntity> itemList) {
		Map<Integer, ItemEntity> itemMap = new HashMap<>();
		for (ItemEntity item : itemList) {
			itemMap.put(item.getId(), item);
		}
		return itemMap;
	}
}