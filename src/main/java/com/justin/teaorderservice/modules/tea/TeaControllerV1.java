package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.order.OrderService;
import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/order/v1/teas")
@RequiredArgsConstructor
public class TeaControllerV1 {

    private final TeaService teaService;
    private final OrderService orderService;

    @GetMapping
    public String items(Model model){
        List<Tea> teas = teaService.findAll();

        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        for(Tea tea : teas){
            ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                    .id(tea.getId())
                    .teaName(tea.getTeaName())
                    .price(tea.getPrice())
                    .quantity(tea.getQuantity())
                    .orderQuantity(Integer.valueOf(0))
                    .build();
            itemOrderFormList.add(itemOrderForm);
        }

        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .userId(UUID.randomUUID().toString())
                .itemOrderFormList(itemOrderFormList)
                .build();

        model.addAttribute("itemPurchaseForm",itemPurchaseForm);
        return "order/v1/addItems";
    }

    @GetMapping("/{teaId}")
    public String tea(@PathVariable long teaId, Model model){
        Tea tea = teaService.findById(teaId);
        model.addAttribute("tea", tea);
        return "order/v1/tea";
    }

    @GetMapping("/{orderId}/detail")
    public String orderDetail(@PathVariable long orderId, Model model){
        Order order = orderService.findById(orderId);
        model.addAttribute("order",order);
        return "order/v1/order";
    }

    @PostMapping
    public String addOrder(@Validated @ModelAttribute("itemPurchaseForm") ItemPurchaseForm itemPurchaseForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        /**
         * Validation 상의 오류가 존재할 경우
         */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }
        Long orderId = orderService.addViewOrder(bindingResult, itemPurchaseForm);

        /**
         * Process 처리 상의 오류가 존재할 경우
         */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        redirectAttributes.addAttribute("orderId", orderId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/order/v1/teas/{orderId}/detail";
    }




}