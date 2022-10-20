package rikkei.academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rikkei.academy.model.Customer;
import rikkei.academy.service.ICustomerService;

@Controller
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ModelAndView listCustomer() {
        ModelAndView modelAndView = new ModelAndView("customer/list");
        modelAndView.addObject("customers", customerService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public String formCreate(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customerForm", customer);
        return "customer/add";
    }

    @PostMapping("/save")
    public String create(@ModelAttribute("customerForm") Customer customer) {
        customerService.save(customer);
        return "redirect:/";
    }

    @GetMapping("detail/{id}")
    public String formDetail(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/detail";
    }

    @GetMapping("edit/{id}")
    public String formUpdate(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customerEdit", customer);
        return "customer/edit";
    }

    @PostMapping("update")
    public String update(Customer customer) {
        System.out.println(customer);
        customerService.save(customer);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String formDelete(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customerDelete", customer);
        return "customer/delete";
    }

    @PostMapping("remove")
    public String remove(@RequestParam Long id) {
        customerService.deleteById(id);
        return "redirect:/";
    }

}
