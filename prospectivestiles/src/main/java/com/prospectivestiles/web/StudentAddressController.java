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
import com.prospectivestiles.domain.Country;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.ProgramOfStudy;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.CountryService;
import com.prospectivestiles.service.UserEntityService;

/*
 * Work on displaying spring err msg in Modal.
 * 
 * ! I am loading too many entities in the model. What is the limit???
 * If i am not using Modal, I don't need to load new address to model
 */
@Controller
public class StudentAddressController {
	
	
	@Inject
	private AddressService addressService;
	
	@Autowired
	private UserEntityService userEntityService;
	@Inject
	private CountryService countryService;
	
	// ======================================
	// =             myAccount addresses             =
	// ======================================	

	/**
	 * For Modal: add new address to the model. as modal uses the current page to load the new address page in modal.
	 */
	/*
	 * I am going to merge the personal info and addresses page together.
	 * Then i am not going to have addresses page. this method will be removed.
	 */
	/*@RequestMapping(value = "/myAccount/addresses", method = RequestMethod.GET)
	public String getAddresses(Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		model.addAttribute("addresses", addressService.getAddressesByUserEntityId(userEntity.getId()));
		
		Address address = new Address();
		address.setUserEntity(userEntity);
		
		model.addAttribute("address", address);
		
		model.addAttribute("userEntity", userEntity);
		
		return "addresses";
	}*/
	
//	Used a new page to add new address
//	-----------------------------------------------------------------------------------------
	@RequestMapping(value = "/myAccount/address/new", method = RequestMethod.GET)
	public String getNewAddressForm(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		Address address = new Address();
		address.setUserEntity(userEntity);
		model.addAttribute(address);
		model.addAttribute(userEntity);
		model.addAttribute("countries", countryService.getAllCountries());
		
		return "newAddressForm";
	}
	
	/**
	 * For Modal use and new page use for adding new address
	 */
	@RequestMapping(value = "/myAccount/address/new", method = RequestMethod.POST)
	public String postNewAddressForm(@ModelAttribute @Valid Address address, BindingResult result, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		if (result.hasErrors()) {
			System.out.println("######## StudentAddressController.postNewAddressForm result.hasErrors(): true" );
			model.addAttribute(userEntity);
			model.addAttribute("countries", countryService.getAllCountries());
//			this is for modal
//			return "redirect:/myAccount/addresses";
			return "newAddressForm";
		} 
//		/*
//		 * get userEntity from URL >>>>> if logged in as admin
//		 * get userEntity from Session >>>>>>> if logged in as student
//		 */
		address.setUserEntity(userEntity);
		address.setCreatedBy(userEntity);
		addressService.createAddress(address);
		
		// Would normally set Location header and HTTP status 201, but we're
		// using the redirect-after-post pattern, which uses the Location header
		// and status code for redirection.
		
		// I am no longer using the addresses page!
//		return "redirect:/myAccount/addresses";
		return "redirect:/myAccount";
	}
	
	
//	If I list all the addresses in the addresses page, I don't need individual address pages
//	-----------------------------------------------------------------------------------------
	/*@RequestMapping(value = "/myAccount/address/{addressId}", method = RequestMethod.GET)
	public String getAddress(@PathVariable("addressId") Long addressId, Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();		
		model.addAttribute(getAddressValidateUserEntityId(userEntity.getId(), addressId));
		return "address";
	}*/
	
//	@RequestMapping(value = "/myAccount/address/{addressId}", method = RequestMethod.GET)
	@RequestMapping(value = "/myAccount/address/{addressId}/edit", method = RequestMethod.GET)
	public String editAddress(@PathVariable("addressId") Long addressId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		Address address = getAddressValidateUserEntityId(userEntity.getId(), addressId);
		
		model.addAttribute("originalAddress", address);
		model.addAttribute(address);
		model.addAttribute("countries", countryService.getAllCountries());
		
		return "editAddress";
	}

	@RequestMapping(value = "/myAccount/address/{addressId}/edit", method = RequestMethod.POST)
	public String editAddress(@PathVariable("addressId") Long addressId,
			@ModelAttribute @Valid Address origAddress, 
			BindingResult result,
			Model model) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		Address address = getAddressValidateUserEntityId(userEntity.getId(), addressId);

		if (result.hasErrors()) {
//			log.debug("Validation Error in Institute form");
			origAddress.setId(addressId);
			origAddress.setUserEntity(userEntity);
			model.addAttribute("address", origAddress);
			model.addAttribute(userEntity);
			model.addAttribute("countries", countryService.getAllCountries());
//			model.addAttribute("originalAddress", origAddress);
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
		address.setLastModifiedBy(userEntity);
		
		addressService.updateAddress(address);
		
		// I am no longer using the addresses page!
//		return "redirect:/myAccount/addresses";
		return "redirect:/myAccount";
	}
	
	/*
	 * Using a Modal to delete Address.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/myAccount/address/{addressId}/delete", method = RequestMethod.GET)
	public String getDeleteAddress(@PathVariable("addressId") Long addressId, Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();	
		
		Address address = getAddressValidateUserEntityId(userEntity.getId(), addressId);
		
		model.addAttribute("originalAddress", address);
		model.addAttribute(address);
		
		return "deleteAddress";
	}
	
	@RequestMapping(value = "/myAccount/address/{addressId}/delete", method = RequestMethod.POST)
	public String deleteAddress(@PathVariable("addressId") Long addressId)
			throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		addressService.delete(getAddressValidateUserEntityId(userEntity.getId(), addressId));
		// I am no longer using the addresses page!
//		return "redirect:/myAccount/addresses";
		return "redirect:/myAccount";
	}
	
	// ======================================
	// =                JSON        =
	// ======================================
	/*@RequestMapping(value = "/json/myAccount/jsonAddresses", method = RequestMethod.GET)
	public String showJsonMessages(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		Address address = new Address();
		address.setUserEntity(userEntity);
		
		model.addAttribute("address", address);
		
		return "jsonAddresses";
	}*/
	/*@RequestMapping(value = "/json/myAccount/addresses", method = RequestMethod.GET, produces = "application/json")
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
		
	}*/

	/*@RequestMapping(value = "/json/myAccount/jsonAddresses", method = RequestMethod.POST, produces="application/json")
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
	}*/
		
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
//		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		
		// Use userEntityId instead of userEntity.getId(),  no need to generate it when it is already passed!!
		Assert.isTrue(userEntityId.equals(address.getUserEntity().getId()), "Address Id mismatch");
		return address;
	}

}
