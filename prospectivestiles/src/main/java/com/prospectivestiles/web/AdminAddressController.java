package com.prospectivestiles.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
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
import com.prospectivestiles.domain.AddressType;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminAddressController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AddressService addressService;
	
	// ======================================
	// =             addresses             =
	// ======================================
	
	/*
	 * I am going to merge the personal info and addresses page together.
	 * Then i am not going to have addresses page. this method will be removed.
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/addresses", method = RequestMethod.GET)
	public String getAddresses(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load all addresses for a user
		 */
		model.addAttribute("addresses", addressService.getAddressesByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "address" for the form to add new address
		 */
		Address address = new Address();
		model.addAttribute("address", address);
		
		/**
		 * Do I really need to add the userEntity? 
		 * Maybe, I just need the Full Name of the user or userId
		 */
		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "addresses";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/new", method = RequestMethod.GET)
	public String getNewAddressForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		Address address = new Address();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		address.setUserEntity(userEntity);
		
		model.addAttribute(address);
		model.addAttribute(userEntity);
		
		return "newAddressForm";
	}
	
	/**
	 * @param userEntityId
	 * @param address
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/addresses", method = RequestMethod.POST)
	public String postNewAddressForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Address address, BindingResult result, Model model) {
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newAddressForm";
//			return "addresses";
		}

		address.setUserEntity(userEntity);
		addressService.createAddress(address);
		
		// I am no longer using the addresses page!
//		return "redirect:/accounts/{userEntityId}/addresses";
		return "redirect:/accounts/{userEntityId}";
	}


	
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}/edit", method = RequestMethod.GET)
	public String editAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId, Model model) {
		
		Address address = getAddressValidateUserEntityId(userEntityId, addressId);
		
		model.addAttribute("originalAddress", address);
		model.addAttribute(address);
		
		return "editAddress";
	}
	
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}", method = RequestMethod.POST)
	public String editAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId,
			@ModelAttribute @Valid Address origAddress, 
			BindingResult result,
			Model model) {
		
		Address address = getAddressValidateUserEntityId(userEntityId, addressId);

		if (result.hasErrors()) {
			model.addAttribute("originalAddress", origAddress);
			return "editAddress";
		}

		address.setAddressType(origAddress.getAddressType());
		address.setAddress1(origAddress.getAddress1());
		address.setAddress2(origAddress.getAddress2());
		address.setCity(origAddress.getCity());
		address.setState(origAddress.getState());
		address.setZipcode(origAddress.getZipcode());
		address.setCountry(origAddress.getCountry());
		
		addressService.updateAddress(address);
		
		// I am no longer using the addresses page!
//		return "redirect:/accounts/{userEntityId}/addresses";
		return "redirect:/accounts/{userEntityId}";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}/delete", method = RequestMethod.GET)
	public String getDeleteAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId, Model model) {
		
		Address address = getAddressValidateUserEntityId(userEntityId, addressId);
		
		model.addAttribute("originalAddress", address);
		model.addAttribute(address);
		
		return "deleteAddress";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}/delete", method = RequestMethod.POST)
	public String deleteAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId, Model model)
			throws IOException {
		addressService.delete(getAddressValidateUserEntityId(userEntityId, addressId));
		// I am no longer using the addresses page!
//		return "redirect:/accounts/{userEntityId}/addresses";
		return "redirect:/accounts/{userEntityId}";
	}
	
	// ======================================
	// =                JSON        =
	// ======================================
	
	@RequestMapping(value = "/json/accounts/{userEntityId}/addresses", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAddressesForJSON(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		List<Address> addresses = null;
		if (userEntityId == null) {
			addresses = new ArrayList<Address>();
		} else {
			addresses = addressService.getAddressesByUserEntityId(userEntityId);
					//messageService.getMessagesByUserEntityId(userEntityId);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("addresses", addresses);
		data.put("number", addresses.size());
		return data;
		
	}
	/**
	 * @requestBody - this enables you to get data in the appropriate data type
	 * going to receive the @RequestBody data from javascript or jquery post
	 * The map data is what the jquery sendMessage sent
	 */
	@RequestMapping(value = "/json/accounts/{userEntityId}/addresses", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendAddressJSON(@PathVariable("userEntityId") Long userEntityId,
			@RequestBody Map<String, Object> data) {
		System.out.println("############## sendAddressJSON called....");
//		@ModelAttribute @Valid Address address, BindingResult result) {
//			
//	if (result.hasErrors()) {
//		return "addresses";
//	}
		
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
		address.setUserEntity(userEntityService.getUserEntity(userEntityId));
		
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
	// =                        =
	// ======================================
	
	private Address getAddressValidateUserEntityId(Long userEntityId, Long highschoolId) {
		Address address = addressService.getAddress(highschoolId);
		
		Assert.isTrue(userEntityId.equals(address.getUserEntity().getId()), "Address Id mismatch");
		return address;
	}
}
