package org.planning.SpringBootProject.controller;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.planning.SpringBootProject.dao.AccountDAO;
import org.planning.SpringBootProject.dao.OrderDAO;
import org.planning.SpringBootProject.dao.ProductDAO;
import org.planning.SpringBootProject.entity.Account;
import org.planning.SpringBootProject.entity.Order;
import org.planning.SpringBootProject.entity.Product;
import org.planning.SpringBootProject.exception.InsufficientQuantityException;
import org.planning.SpringBootProject.form.CustomerForm;
import org.planning.SpringBootProject.model.*;
import org.planning.SpringBootProject.pagination.PaginationResult;
import org.planning.SpringBootProject.pagination.Paging;
import org.planning.SpringBootProject.repository.AccountRepository;
import org.planning.SpringBootProject.repository.OrderRepository;
import org.planning.SpringBootProject.repository.ProductRepository;
import org.planning.SpringBootProject.util.Utils;
import org.planning.SpringBootProject.validator.CustomerFormValidator;
import org.planning.SpringBootProject.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class MainController {
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CustomerFormValidator customerFormValidator;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        // Trường hợp update SL trên giỏ hàng.
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
        if (target.getClass() == CartInfo.class) {

        }

        // Trường hợp save thông tin khách hàng.
        // (@ModelAttribute @Validated CustomerInfo customerForm)
        else if (target.getClass() == CustomerForm.class) {
            dataBinder.setValidator(customerFormValidator);
        }

    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping("/")
    public String home(Model model, @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        model.addAttribute("mess", "check data");
        List<Product> products = productRepository.getAllProducts();
        List<Account> accounts = accountRepository.findAll();
        for (Account a : accounts) System.out.println(a.getId());
        model.addAttribute("products", products);
//        Account a = new Account();
//        a.setFullName("My name is Admin 2");
//        a.setGmail("admin@gmail.com");
//        a.setPhoneNumber("0123456789");
//        a.setActive(true);
//        a.setUserRole("ROLE_MANAGER");
//        a.setUserName("admin2");
//        a.setEncrytedPassword(passwordEncoder.encode("123"));
//        a.setCreatedAt(LocalDateTime.now());
//        accountRepository.save(a);
        return "index";
    }

    // Danh sách sản phẩm.
    @RequestMapping({"/productList"})
    public String listProductHandler(Model model, //
                                     @RequestParam(value = "name", defaultValue = "") String likeName,
                                     @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 6;
        final int maxNavigationPage = 10;

        Paging<ProductInfo> result = productDAO.queryProducts(page, maxResult, maxNavigationPage, likeName);

        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("likeName", likeName);
        model.addAttribute("paginationProducts", result.getData());
        return "productList";
    }

    // Xóa sản phẩm

    @RequestMapping({"/buyProduct"})
    public String listProductHandler(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "code", defaultValue = "") String code) {

        Product product = null;
        if (code != null && code.length() > 0) {
            product = productRepository.findProduct(code);
        }
        if (product != null) {

            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cartInfo.addProduct(productInfo, 1);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping({"/shoppingCartRemoveProduct"})
    public String removeProductHandler(HttpServletRequest request, Model model, //
                                       @RequestParam(value = "code", defaultValue = "") String code) {
        Product product = null;
        if (code != null && code.length() > 0) {
            product = productRepository.findProduct(code);
        }
        if (product != null) {

            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cartInfo.removeProduct(productInfo);

        }

        return "redirect:/shoppingCart";
    }

    // POST: Cập nhập số lượng cho các sản phẩm đã mua.
    @RequestMapping(value = {"/shoppingCart"}, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, //
                                        Model model, //
                                        @ModelAttribute("cartForm") CartInfo cartForm) {
        CartInfo cartInfo = Utils.getCartInSession(request);
//        Product product = productRepository.findProductByCode(id);
//        if(cartInfo.getQuantityTotal() > product.getQuanityProduct()){
//            model.addAttribute("mess", "The product you selected is not available in sufficient quantity, please try again.");
//            return "redirect:/shoppingCart";
//        }
        cartInfo.updateQuantity(cartForm);

        return "redirect:/shoppingCart";
    }

    // GET: Hiển thị giỏ hàng.
    @RequestMapping(value = {"/shoppingCart"}, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = Utils.getCartInSession(request);

        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }

    // GET: Nhập thông tin khách hàng.
    @RequestMapping(value = {"/shoppingCartCustomer"}, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);

        if (cartInfo.isEmpty()) {

            return "redirect:/shoppingCart";
        }
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();

        CustomerForm customerForm = new CustomerForm(customerInfo);

        model.addAttribute("customerForm", customerForm);

        return "shoppingCartCustomer";
    }

    // POST: Save thông tin khách hàng.
    @RequestMapping(value = {"/shoppingCartCustomer"}, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
                                           BindingResult result, //
                                           final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            customerForm.setValid(false);
            // Forward tới trang nhập lại.
            return "shoppingCartCustomer";
        }

        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);
        CustomerInfo customerInfo = new CustomerInfo(customerForm);
        cartInfo.setCustomerInfo(customerInfo);

        return "redirect:/shoppingCartConfirmation";
    }

    // GET: Xem lại thông tin để xác nhận.
    @RequestMapping(value = {"/shoppingCartConfirmation"}, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        if (cartInfo == null || cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {

            return "redirect:/shoppingCartCustomer";
        }
        model.addAttribute("myCart", cartInfo);

        return "shoppingCartConfirmation";
    }

    // POST: Gửi đơn hàng (Save).
    @RequestMapping(value = {"/shoppingCartConfirmation"}, method = RequestMethod.POST)
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model, @RequestParam("userId") String userId) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        List<CartLineInfo> lines = cartInfo.getCartLines();
        for (CartLineInfo line : lines) {
            String code = line.getProductInfo().getCode();
            Product product = this.productRepository.findProduct(code);
            if(line.getQuantity() > product.getQuanityProduct()){
                model.addAttribute("messError", "error");
                model.addAttribute("myCart", cartInfo);
                return "shoppingCartConfirmation";
            }
        }
        if (cartInfo.isEmpty()) {

            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {

            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderDAO.saveOrder(cartInfo, userId);
        } catch (Exception e) {

            return "shoppingCartConfirmation";
        }

        // Xóa giỏ hàng khỏi session.
        Utils.removeCartInSession(request);

        // Lưu thông tin đơn hàng cuối đã xác nhận mua.
        Utils.storeLastOrderedCartInSession(request, cartInfo);

        return "redirect:/shoppingCartFinalize";
    }

//    @ExceptionHandler(InsufficientQuantityException.class)
//    public String handleInsufficientQuantityException(InsufficientQuantityException ex, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("messError", ex.getMessage());
//        return "redirect:/shoppingCartConfirmation";
//    }

    @RequestMapping(value = {"/shoppingCartFinalize"}, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {

        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "shoppingCartFinalize";
    }

    @RequestMapping(value = {"/productImage"}, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("code") String code) throws IOException {
        Product product = null;
        if (code != null) {
            product = this.productRepository.findProduct(code);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg,image/jpg,image/png,image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }


    @RequestMapping(value = {"/user/orderList"}, method = RequestMethod.GET)
    public String listOrderByUser(@RequestParam(name = "userId") String userId, Model model, //
                                  @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfoByUser(userId, page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }

    @RequestMapping(value = {"/user/order"}, method = RequestMethod.GET)
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

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String search(Model model, @RequestParam("query") String query) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String signUpForm(Model model) {
        model.addAttribute("account", new Account());
        return "signup";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public String signUp(@Validated @ModelAttribute("account") Account account, BindingResult result, Model model) {
//        if(result.hasErrors()){
//            return "signup";
//        }
        List<Account> accounts = accountRepository.findAll();
        boolean check = true;
        for (Account a : accounts) {
            if (a.getUserName().equals(account.getUserName()) || a.getGmail().equals(account.getGmail())) {
                check = false;
            }
        }
        if (!check) {
            model.addAttribute("error", "Username or email is valid, please try again.");
            return "signup";
        } else {
            PasswordValidator p = new PasswordValidator();
            if (!p.isValid(account.getEncrytedPassword())) {
                model.addAttribute("error", "Password must contain at least 8 characters, including uppercase, lowercase, numbers, and special characters.");
                return "signup";
            }
            String passwordEncoded = passwordEncoder.encode(account.getEncrytedPassword());
            account.setEncrytedPassword(passwordEncoded);
            account.setCreatedAt(LocalDateTime.now());
            account.setActive(false);
            account.setDeleted(true);
            account.setUserRole("ROLE_EMPLOYEE");
            accountRepository.save(account);
            return "redirect:/login";
        }
    }



    @RequestMapping(value = {"/user/viewOrderStatus"}, method = RequestMethod.GET)
    public String listOrderByUserAndStatus(@RequestParam(name = "userId") String userId, Model model, //
                                  @RequestParam(value = "page", defaultValue = "1") String pageStr,
                                           @RequestParam(value = "status", defaultValue = "") String status) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfoByUserAndStatus(userId, page, MAX_RESULT, MAX_NAVIGATION_PAGE, status);

        model.addAttribute("paginationResult", paginationResult);
        return "orderStatus";
    }


    @RequestMapping(value = {"/user/confirmStatus"}, method = RequestMethod.POST)
    public ResponseEntity<?> changeStatus(@RequestParam("orderNum") int orderNum){
        Order order = orderRepository.findByOrderNum(orderNum);
        if(order != null){
            orderRepository.confirmOrderStatusByUser(orderNum);
            return ResponseEntity.ok("Order Status updated success!");
        }else{
            return ResponseEntity.badRequest().body("Status invalid!");
        }
    }
}