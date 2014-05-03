package com.prospectivestiles.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.ProgramOfStudy;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentAddressController {
	
	
	@Inject
	private AddressService addressService;
	
	@Autowired
	private UserEntityService userEntityService;
	
	
	// ======================================
	// =             myAccount addresses             =
	// ======================================	

	@RequestMapping(value = "/myAccount/addresses", method = RequestMethod.GET)
	public String getAddresses(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		model.addAttribute("addresses", addressService.getAddressesByUserEntityId(userEntity.getId()));
		
		/*
		 * I am loading too many entities in the model. What is the limit???
		 */
		Address address = new Address();
		System.out.println("################### StudentAddressController.getAddresses: " + address.toString());
		address.setUserEntity(userEntity);
		
		model.addAttribute("address", address);
		
		model.addAttribute("userEntity", userEntity);
		
		return "addresses";
	}
	
	@RequestMapping(value = "/myAccount/addresses", method = RequestMethod.POST)
	public String postNewAddressForm(@ModelAttribute @Valid Address address, BindingResult result) {
		
		System.out.println("################### IN StudentAddressController.postNewAddressForm.");

		//
//		System.out.println("######## postNewAddressForm() Called #####");
//		System.out.println("######## getAddress1: " + address.getAddress1());
//		System.out.println("######## getAddress2: " + address.getAddress2());
//		System.out.println("######## getCity: " + address.getCity());
//		
//		if (result != null) {
//			System.out.println("######## Error in: " + result.toString());
//		}
		
		/**
		 * There is no newAddressForm page -- check this out
		 */
		if (result.hasErrors()) {
			System.out.println("######## StudentAddressController.postNewAddressForm result.hasErrors(): true" );
			/**
			 * Work on displaying spring err msg in Modal.
			 */
			return "redirect:/myAccount/addresses";
//			return "newAddressForm";
		} else {
			System.out.println("######## StudentAddressController.postNewAddressForm result.hasErrors(): false" );
		}
		//
		

		/*
		 * get userEntity from URL >>>>> if logged in as admin
		 * get userEntity from Session >>>>>>> if logged in as student
		 */
		UserEntity userEntity = getUserEntityFromSecurityContext();
		System.out.println("######## StudentAddressController.userEntity.getId(): " + userEntity.getId());
		System.out.println("######## StudentAddressController.userEntity.toString(): " + userEntity.toString());
		address.setUserEntity(userEntity);
		System.out.println("######## StudentAddressController.address.getUserEntity(): " + address.getUserEntity());
		addressService.createAddress(address);
		/**
		 * FOR TESTING PURPOSE
		 */
		System.out.println("######## StudentAddressController.address.zipcode BEFORE zipUpdate: " + address.getZipcode());
		addressService.updateAddressZipCode(address.getId(), "22046");
		System.out.println("######## StudentAddressController.address.zipcode AFTER zipUpdate: " + address.getZipcode());
		
		// Would normally set Location header and HTTP status 201, but we're
		// using the redirect-after-post pattern, which uses the Location header
		// and status code for redirection.
		return "redirect:/myAccount/addresses";
	}
	
	
	
	// ======================================
	// =                         =
	// ======================================
	
//	I may not need this
//	-----------------------------------------------------------------------------------------
	/*@RequestMapping(value = "/myAccount/address/new", method = RequestMethod.GET)
	public String getNewAddressForm(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		Address address = new Address();
		institute.setUserEntity(userEntity);
		model.addAttribute(address);
		
		return "newAddressForm";
	}*/
	
//	If I list all the addresses in the addresses page, I don't need individual address pages
//	-----------------------------------------------------------------------------------------
	/*@RequestMapping(value = "/myAccount/address/{addressId}", method = RequestMethod.GET)
	public String getInstitute(@PathVariable("addressId") Long addressId, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();		
		model.addAttribute(getAddressValidateUserEntityId(userEntity.getId(), addressId));
		return "address";
	}*/
	
//	@RequestMapping(value = "/myAccount/address/{addressId}/edit", method = RequestMethod.GET)
	@RequestMapping(value = "/myAccount/address/{addressId}", method = RequestMethod.GET)
	public String editInstitute(@PathVariable("addressId") Long addressId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		Address address = getAddressValidateUserEntityId(userEntity.getId(), addressId);
		
		model.addAttribute("originalAddress", address);
		model.addAttribute(address);
		
		return "editAddress";
	}
	
	@RequestMapping(value = "/myAccount/address/{addressId}", method = RequestMethod.POST)
	public String editInstitute(@PathVariable("addressId") Long addressId,
			@ModelAttribute @Valid Address origAddress, 
			BindingResult result,
			Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		Address address = getAddressValidateUserEntityId(userEntity.getId(), addressId);

		if (result.hasErrors()) {
//			log.debug("Validation Error in Institute form");
			model.addAttribute("originalAddress", origAddress);
			return "editAddress";
		}

//		log.debug("Message validated; updating message subject and text");
		
		address.setAddressType(origAddress.getAddressType());
		address.setAddress1(origAddress.getAddress1());
		address.setAddress2(origAddress.getAddress2());
		address.setCity(origAddress.getCity());
		address.setState(origAddress.getState());
		address.setZipcode(origAddress.getZipcode());
		address.setCountry(origAddress.getCountry());
		
		addressService.updateAddress(address);
		
		return "redirect:/myAccount/addresses";
	}
	
	
	@RequestMapping(value = "/myAccount/address/{addressId}/delete", method = RequestMethod.POST)
	public String deleteMessage(@PathVariable("addressId") Long addressId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		addressService.delete(getAddressValidateUserEntityId(userEntity.getId(), addressId));
		return "redirect:/myAccount/addresses";
	}
	
	// ======================================
	// =                JSON        =
	// ======================================
	@RequestMapping(value = "/json/myAccount/jsonAddresses", method = RequestMethod.GET)
	public String showJsonMessages(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		Address address = new Address();
		address.setUserEntity(userEntity);
		
		model.addAttribute("address", address);
		
		return "jsonAddresses";
	}
	@RequestMapping(value = "/json/myAccount/addresses", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAddressesForJSON() {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		List<Address> addresses = addressService.getAddressesByUserEntityId(userEntity.getId());
		Address address = new Address();
		address.setUserEntity(userEntity);
		
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("number", addresses.size());
		data.put("address", address);
		data.put("addresses", addresses);
		
		return data;
		
	}

	@RequestMapping(value = "/json/myAccount/jsonAddresses", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessageJSON(@RequestBody Map<String, Object> data) {
		System.out.println("############## sendAddressJSON called....");
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
//		AddressType addressType = (AddressType) data.get("addressType");
		String address1 = (String) data.get("address1");
		String address2 = (String) data.get("address2");
		String city = (String) data.get("city");
		String state = (String) data.get("state");
		String zipcode = (String) data.get("zipcode");
		String country = (String) data.get("country");
//		Integer target = (Integer) data.get("target");
	
		System.out.println(address1 + " , " + address2 + " , " + city);
		
		Address address = new Address();
	//		address.setAddressType(addressType);
		address.setAddress1(address1);
		address.setAddress2(address2);
		address.setCity(city);
		address.setState(state);
		address.setZipcode(zipcode);
		address.setCountry(country);
		address.setUserEntity(userEntity);
		
		try {
			addressService.createAddress(address);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// a map that is going to be actual value to return, 
		// the actual json value that we return to javascript
		Map<String, Object> returnVal = new HashMap<String, Object>();
		returnVal.put("success", true);
	//		returnVal.put("target", target);
		return returnVal;
	}
		
	// ======================================
	// =                         =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity =  (UserEntity) auth.getPrincipal();
		return userEntity;
	}
	
	private Address getAddressValidateUserEntityId(Long userEntityId, Long addressId) {
		
		Address address = addressService.getAddress(addressId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		// Use userEntityId instead of userEntity.getId(),  no need to generate it when it is already passed!!
//		Assert.isTrue(userEntity.getId().equals(address.getUserEntity().getId()), "Address Id mismatch");
//		Assert.isTrue(userEntity.getId().equals(userEntityId), "Address Id mismatch");
		Assert.isTrue(userEntity.getId().equals(address.getUserEntity().getId()), "Address Id mismatch");
		return address;
	}

}
