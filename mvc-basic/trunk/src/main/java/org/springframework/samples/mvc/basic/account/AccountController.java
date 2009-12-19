package org.springframework.samples.mvc.basic.account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	private AtomicLong idSequence = new AtomicLong();
	
	private Map<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();
	
	@RequestMapping(method=RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute(new Account());
		return "account/createForm";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String create(@Valid Account account, BindingResult result) {
		if (result.hasErrors()) {
			// re-render form with errors
			return "account/createForm";
		}
		account.setId(nextId());
		this.accounts.put(account.getId(), account);
		return "redirect:/account/" + account.getId();
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String get(@PathVariable("id") Long id, Model model) {
		Account account = this.accounts.get(id);
		model.addAttribute(account);
		return "account/view";
	}
	
	private Long nextId() {
		return this.idSequence.incrementAndGet();
	}

}
