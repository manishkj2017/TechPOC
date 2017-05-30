package shop.gui.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.core.domain.PetSaleSummaryData;
import shop.gui.models.PetSaleSummary;
import shop.gui.services.PetSaleSummaryService;

@Controller
public class PetController {
	
	@Autowired
	private PetSaleSummaryService saleSummaryService;
	
	@RequestMapping(value="/petsalesumm")
	public String getPetSaleSummary(@ModelAttribute("petsalesummary") PetSaleSummary petSaleSummary, Model model) {
		return "PetSummary";
	}
	
	
	@RequestMapping(value = "/salesumm", method= RequestMethod.GET)
	public @ResponseBody List<PetSaleSummaryData> getSaleSummary(){
		List<PetSaleSummaryData> data = saleSummaryService.getPetSaleSummary();
		
		return data;
	}
	
	@RequestMapping(value = "/shopclosed", method= RequestMethod.GET)
	public @ResponseBody Boolean isShopClosed() {
		return saleSummaryService.isShopClosed();
		
		
	}
}
