package com.Logan50miles.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Mailconfiguration;
import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Entity.SuperAdmin;
import com.Logan50miles.Repository.MailConfigurationRepository;
import com.Logan50miles.Service.DashboarService;
import com.Logan50miles.Service.ShopAdminService;
import com.Logan50miles.Service.SuperAdminService;
import com.Logan50miles.Util.Mailer;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ophelia")
public class SuperAdminController {
	
	@Autowired
	private SuperAdminService superAdminService;
	@Autowired
	private ShopAdminService shopAdminService;
	@Autowired
	private DashboarService dashboarService;
	@Autowired
	MailConfigurationRepository mailConfigurationRepository;

	@PostMapping("superadmin/regiter")
	public ResponseEntity<String> superAdminRegistration(SuperAdmin superAdmin, MultipartFile file) throws IOException {
		return superAdminService.superRegisteration(superAdmin, file);
	}

	@PostMapping("superadmin/login")
	public ResponseEntity<String> superLogin(String userName, String password) {
		return superAdminService.superAdminLogin(userName, password);
	}

	@GetMapping("superadmin/shop/orders")
	public List<Orders> getListOrdersByShop(String shopId) {
		return shopAdminService.listOrdersByShop(shopId);
	}

	@GetMapping("superadmin/orders/customers")
	public List<Orders> getOrdersByCusId(String cusId) {
		return dashboarService.getOrdersByCusId(cusId);
	}

	@GetMapping("superadmin/orders/statics")
	public List<Orders> getOrdersStatics(String fd, String td) throws ParseException {
		return shopAdminService.getStaticsWhole(fd, td);
	}

	@PostMapping("add/mail")
	public Mailconfiguration addMailconfiguration(Mailconfiguration mailconfiguration) {
		return superAdminService.addMailconfiguration(mailconfiguration);
	}

	@PostMapping("delete/mail")
	public String deleteMail(int id) {
		return superAdminService.deleteMail(id);
	}

	@GetMapping("get/mail")
	public List<Mailconfiguration> getMAilMailconfigurations() {
		return superAdminService.getAllMails();
	}

	@PostMapping("newsletter")
	public String sendNewsLetter(String email) {
		Mailconfiguration mc = mailConfigurationRepository.findAll().stream().filter(x -> x.getType().equals("general"))
				.findAny().orElse(null);
		Mailer m = new Mailer();
		m.sendMail("NewsLetter", "Nazca Newsletter has been subscribed By: " + email, email, mc.getEmail(),
				mc.getPassword());
		return "sent";
	}
}
