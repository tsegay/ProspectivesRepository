package com.prospectivestiles.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.prospectivestiles.domain.AddressType;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.UserEntityService;

/**
 * The url to get the form and post the form are the same. 
 * eg. "/accounts/{userEntityId}/xxx/new"
 * Advantage: when a user submit a form with error the post url will be displayed in the url,
 * if the session expires, and post url used to post the form will be displayed in the url,
 * if post and get url are the same then when the user login and refresh the page, 
 * the get method will be called. or the page will crash.
 * @author danielanenia
 *
 */
@Controller
public class AdminAddressController {
	
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private AddressService addressService;
	
	private static final Logger log = LoggerFactory.getLogger(AdminAddressController.class);
	
	// ======================================
	// =             addresses             =
	// ======================================
	
	/*
	 * I am going to merge the personal info and addresses page together.
	 * Then i am not going to have addresses page. this method will be removed.
	 */
//	@RequestMapping(value = "/accounts/{userEntityId}/addresses", method = RequestMethod.GET)
//	public String getAddresses(@PathVariable("userEntityId") Long userEntityId,
//			Model model) {
//		
//		log.debug(" viewing /accounts/userEntityId/addresses on ");
//		
//		/**
//		 * load all addresses for a user
//		 */
//		model.addAttribute("addresses", addressService.getAddressesByUserEntityId(userEntityId));
//		
//		/**
//		 * The modelAttribute "address" for the form to add new address
//		 */
//		Address address = new Address();
//		model.addAttribute("address", address);
//		
//		/**
//		 * Do I really need to add the userEntity? 
//		 * Maybe, I just need the Full Name of the user or userId
//		 */
//		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
//		
//		return "addresses";
//	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/new", method = RequestMethod.GET)
	public String getNewAddressForm(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		log.debug("################# debug address 1");
		System.out.println("################# address 1");
		Address address = new Address();
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		address.setUserEntity(userEntity);
		
		model.addAttribute(address);
		model.addAttribute(userEntity);
		
		return "newAddressForm";
	}
	
	/**
	 * CreatedBy is set to the currently logged in Admission User
	 * dateCreated is set in the abstractHbn Class
	 * @param userEntityId
	 * @param address
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/address/new", method = RequestMethod.POST)
	public String postNewAddressForm(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute @Valid Address address, BindingResult result, Model model) {
		
		log.debug("################# debug address 2");
		System.out.println("################# address 2");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "newAddressForm";
//			return "addresses";
		}

		address.setUserEntity(userEntity);
		address.setCreatedBy(currentAdmissionUser);
		
		addressService.createAddress(address);
		
		// I am no longer using the addresses page!
//		return "redirect:/accounts/{userEntityId}/addresses";
		return "redirect:/accounts/{userEntityId}";
	}


	
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}/edit", method = RequestMethod.GET)
	public String editAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId, Model model) {
		
		log.debug("################# debug address 3");
		System.out.println("################# address 3");
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		Address address = getAddressValidateUserEntityId(userEntityId, addressId);
		
		model.addAttribute("originalAddress", address);
		model.addAttribute(address);
		model.addAttribute(userEntity);
		
		return "editAddress";
	}
	
	/**
	 * lastModifiedBy - currentAdmissionUser is the Admission Office who is creating the address
	 * dateLastModified is set in the serviceImp class to the current time
	 * @param userEntityId
	 * @param addressId
	 * @param origAddress
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}/edit", method = RequestMethod.POST)
	public String editAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId,
			@ModelAttribute @Valid Address origAddress, 
			BindingResult result,
			Model model) {
		log.debug("################# debug address 4");
		System.out.println("################# address 4");
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		Address address = getAddressValidateUserEntityId(userEntityId, addressId);

		if (result.hasErrors()) {
			origAddress.setId(addressId);
			origAddress.setUserEntity(userEntityService.getUserEntity(userEntityId));
//			model.addAttribute("originalAddress", origAddress);
			model.addAttribute("address", origAddress);
			model.addAttribute(userEntity);
//			model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
			return "editAddress";
		}

		address.setAddressType(origAddress.getAddressType());
		address.setAddress1(origAddress.getAddress1());
		address.setAddress2(origAddress.getAddress2());
		address.setCity(origAddress.getCity());
		address.setState(origAddress.getState());
		address.setZipcode(origAddress.getZipcode());
		address.setCountry(origAddress.getCountry());
		address.setLastModifiedBy(currentAdmissionUser);
		
		addressService.updateAddress(address);
		
		// I am no longer using the addresses page!
//		return "redirect:/accounts/{userEntityId}/addresses";
		return "redirect:/accounts/{userEntityId}";
	}
	
	/*
	 * Using a Modal to delete Address.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}/delete", method = RequestMethod.GET)
	public String getDeleteAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId, Model model) {
		log.debug("################# debug address 5");
		System.out.println("################# address 5");
		
		Address address = getAddressValidateUserEntityId(userEntityId, addressId);
		
		model.addAttribute("originalAddress", address);
		model.addAttribute(address);
		
		return "deleteAddress";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/address/{addressId}/delete", method = RequestMethod.POST)
	public String deleteAddress(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("addressId") Long addressId, Model model)
			throws IOException {
		log.debug("################# debug address 6");
		System.out.println("################# address 6");
		
		addressService.delete(getAddressValidateUserEntityId(userEntityId, addressId));
		// I am no longer using the addresses page!
//		return "redirect:/accounts/{userEntityId}/addresses";
		return "redirect:/accounts/{userEntityId}";
	}
	
	// ======================================
	// =                JSON        =
	// ======================================
	
	/*@RequestMapping(value = "/json/accounts/{userEntityId}/addresses", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAddressesForJSON(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		List<Address> addresses = null;
		if (userEntityId == null) {
			addresses = new ArrayList<Address>();
		} else {
			addresses = addressService.getAddressesByUserEntityId(userEntityId);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("addresses", addresses);
		data.put("number", addresses.size());
		return data;
		
	}*/
	/**
	 * @requestBody - this enables you to get data in the appropriate data type
	 * going to receive the @RequestBody data from javascript or jquery post
	 * The map data is what the jquery sendMessage sent
	 */
	/*@RequestMapping(value = "/json/accounts/{userEntityId}/addresses", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendAddressJSON(@PathVariable("userEntityId") Long userEntityId,
			@RequestBody Map<String, Object> data) {
		System.out.println("############## sendAddressJSON called....");
		
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
	}*/
	
	// ======================================
	// =                        =
	// ======================================
	
	private Address getAddressValidateUserEntityId(Long userEntityId, Long addressId) {
		Address address = addressService.getAddress(addressId);
		
		Assert.isTrue(userEntityId.equals(address.getUserEntity().getId()), "Address Id mismatch");
		return address;
	}
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
}
