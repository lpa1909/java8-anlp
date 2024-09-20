package org.planning.SpringBootProject.controller;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.planning.SpringBootProject.config.WebSecurityConfig;
import org.planning.SpringBootProject.dao.AccountDAO;
import org.planning.SpringBootProject.dao.OrderDAO;
import org.planning.SpringBootProject.dao.ProductDAO;
import org.planning.SpringBootProject.entity.Account;
import org.planning.SpringBootProject.entity.Product;
import org.planning.SpringBootProject.form.ProductForm;
import org.planning.SpringBootProject.model.OrderDetailInfo;
import org.planning.SpringBootProject.model.OrderInfo;
import org.planning.SpringBootProject.model.ProductInfo;
import org.planning.SpringBootProject.pagination.PaginationResult;
import org.planning.SpringBootProject.pagination.Paging;
import org.planning.SpringBootProject.repository.AccountRepository;
import org.planning.SpringBootProject.repository.ProductRepository;
import org.planning.SpringBootProject.validator.PasswordValidator;
import org.planning.SpringBootProject.validator.ProductFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class AdminController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductFormValidator productFormValidator;
    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HttpSession session;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productFormValidator);
        }
    }

    // GET: Hiển thị trang login
    @RequestMapping(value = {"/admin/login"}, method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request, HttpSession session) {
        List<Product> list = productDAO.findProductByName("Air Jordan ");
        System.out.println(list.size());
        model.addAttribute("session", session);
        model.addAttribute("request", request);
        return "login";
    }

    @RequestMapping(value = {"/admin/accountInfo"}, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account a = accountRepository.findByUsername(userDetails.getUsername());
        System.out.println("id :  " + a.getId());
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());
        model.addAttribute("name", userDetails.getUsername());
        session.setAttribute("id", a.getId());
        model.addAttribute("account", a);
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = {"/admin/changePassword"}, method = RequestMethod.GET)
    public String changePassword(Model model, @RequestParam(value = "userName") String userName,
                                 @RequestParam(value = "currentPassword") String currentPassword,
                                 @RequestParam(value = "newPassword") String newPassword,
                                 @RequestParam(value = "confirmPassword") String confirmPassword) {
        PasswordValidator p = new PasswordValidator();
        if (!p.isValid(newPassword)) {
            model.addAttribute("errorMessage", "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("name", userDetails.getUsername());
            model.addAttribute("userDetails", userDetails);
            return "accountInfo";
        }
        Account a = accountRepository.findByUsername(userName);
        System.out.println(a.getEncrytedPassword());
        if (passwordEncoder.matches(currentPassword, a.getEncrytedPassword())) {
            if (newPassword.equals(confirmPassword)) {
                String encodedConfirmPassword = passwordEncoder.encode(confirmPassword);
                accountRepository.changePasswordAccount(encodedConfirmPassword, userName);
                model.addAttribute("mess", "Change password successfully");
            } else {
                model.addAttribute("errorMessage", "New password not match confirmation password");
            }
        } else {
            model.addAttribute("errorMessage", "Current password not match");
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", userDetails.getUsername());
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = {"/admin/orderList"}, method = RequestMethod.GET)
    public String orderList(Model model, //
                            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }

    // GET: Hiển thị product
    @RequestMapping(value = {"/admin/product"}, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductForm productForm = null;

        if (code != null && code.length() > 0) {
            Product product = productRepository.findProduct(code);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("productForm", productForm);
        return "product";
    }

    // POST: Save product
    @RequestMapping(value = {"/admin/product"}, method = RequestMethod.POST)
    public String productSave(Model model, //
                              @ModelAttribute("productForm") @Validated ProductForm productForm, //
                              BindingResult result, //
                              final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "product";
        }
        List<Product> products = productRepository.findAll();
        Product p2 = productRepository.findProduct(productForm.getCode());
        boolean check = true;
        for(Product p : products){
            if(productForm.getCode().equals(p.getCode()) && !productForm.getCode().equals(p2.getCode())){
                check = false;
            }
        }
        if(!check){
            model.addAttribute("errorId", "Product code already exists");
            return "product";
        }
        try {
            productDAO.save(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "product";
        }

        return "redirect:/productList";
    }

    @RequestMapping(value = {"/admin/order"}, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/admin/orderList";
        }
        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);

        model.addAttribute("orderInfo", orderInfo);

        return "order";
    }

    @RequestMapping(value = {"admin/deleteProduct"}, method = RequestMethod.GET)
    public String handleDeleteProduct(Model model, @RequestParam("code") String code, @RequestParam(value = "name", defaultValue = "") String likeName,
                                      @RequestParam(value = "page", defaultValue = "1") int page) {
        productRepository.softDeleteProduct(code);
        return "redirect:/productList";
    }

    @RequestMapping(value = {"/admin/manageAccount"}, method = RequestMethod.GET)
    public String manageAccount(Model model) {
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "manageAccount";
    }

    @RequestMapping(value = {"/admin/loadData"}, method = RequestMethod.GET)
    public ResponseEntity<Account> loadData(Model model, @RequestParam("accountId") String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found")); // Xử lý trường hợp không tìm thấy tài khoản

        // Trả về dữ liệu tài khoản dưới dạng JSON
        return ResponseEntity.ok(account);
    }

    @RequestMapping(value = {"/admin/editAccount"}, method = RequestMethod.POST)
    public String editAccount(@Validated @ModelAttribute("account") Account account, BindingResult result, Model model) {
//        if(result.hasErrors()){
//            return "manageAccount";
//        }
        List<Account> accounts = accountRepository.findAll();
        Account a2 = accountRepository.findByUserId(account.getId());
        boolean check = true;
        for (Account a : accounts) {
                if ((a.getUserName().equals(account.getUserName()) && !a.getUserName().equals(a2.getUserName())) || (a.getGmail().equals(account.getGmail()) && !a.getGmail().equals(a2.getGmail()))) {
                    check = false;
                }
        }
        if (!check) {
            model.addAttribute("errorMessage", "Username or email is valid, please try again.");
            model.addAttribute("accounts", accounts);
            return "manageAccount";
        }
        System.out.println(account.getUserRole());

        a2.setUpdatedAt(LocalDateTime.now());
        a2.setFullName(account.getFullName());
        a2.setGmail(account.getGmail());
        a2.setPhoneNumber(account.getPhoneNumber());
        a2.setUserName(account.getUserName());
        a2.setUserRole(account.getUserRole());
        accountRepository.save(a2);
        model.addAttribute("successMessage", "Edit account successfully");
        model.addAttribute("accounts", accounts);
        return "manageAccount";


    }

    @RequestMapping(value = {"/admin/deleteAccount"}, method = RequestMethod.GET)
    public String deleteAccount(@RequestParam("id") String id, Model model) {
        accountRepository.softDeleteAccount(id);
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "manageAccount";
    }

    @RequestMapping(value = {"/admin/activeAccount"}, method = RequestMethod.GET)
    public String activeAccount(@RequestParam("id") String id, Model model) {
        accountRepository.activeAccount(id);
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "manageAccount";
    }

    @RequestMapping(value = {"/admin/addAccount"}, method = RequestMethod.POST)
    public String addAccount(@ModelAttribute("account") Account account, BindingResult result, Model model) {
        List<Account> accounts = accountRepository.findAll();
        boolean check = true;
        for (Account a : accounts) {
            if (a.getUserName().equals(account.getUserName()) || a.getGmail().equals(account.getGmail())) {
                check = false;
            }
        }
        if (!check) {
            model.addAttribute("errorMessage", "Username or Email is valid, please try again.");
            model.addAttribute("accounts", accounts);
            return "manageAccount";
        } else {
            String passwordEncoded = passwordEncoder.encode(account.getEncrytedPassword());
            account.setEncrytedPassword(passwordEncoded);
            account.setCreatedAt(LocalDateTime.now());
            account.setActive(true);
            account.setDeleted(false);
            accountRepository.save(account);
            model.addAttribute("accounts", accounts);
            model.addAttribute("successMessage", "Add success");
            return "manageAccount";
        }
    }

    @RequestMapping(value = {"/admin/manageProduct"}, method = RequestMethod.GET)
    public String loadProduct(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "manageProduct";
    }

    @RequestMapping(value = "/admin/unlockProduct", method = RequestMethod.GET)
    public String unlockProduct(@RequestParam("code") String code, Model model) {
        productRepository.unlockProduct(code);
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "manageProduct";
    }

    @RequestMapping(value = {"/admin/searchInManageProduct"}, method = RequestMethod.GET)
    public String search(Model model, @RequestParam("query") String query){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        model.addAttribute("products", products);
        return "manageProduct";
    }

    @RequestMapping(value = {"/admin/searchAccountByAdmin"}, method = RequestMethod.GET)
    public String searchAccountByAdmin(Model model, @RequestParam("query") String query){
        List<Account> accounts = accountRepository.searchAccount(query);
        model.addAttribute("accounts", accounts);
        return "manageAccount";
    }

}