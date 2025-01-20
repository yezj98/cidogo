package com.codigo_app.Controller;


import com.codigo_app.Model.eVoucher;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import com.codigo_app.Service.eVoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/vouchers")
public class eVoucherController {

    private final eVoucherService eVoucherService;


    public eVoucherController(eVoucherService eVoucherService) {
        this.eVoucherService = eVoucherService;
    }

    /*Create eVoucher (CMS API)*/
    @PostMapping
    public ResponseEntity<eVoucher> createVoucher(@RequestBody eVoucher voucher) {
        eVoucher createdVoucher = eVoucherService.create(voucher);
        return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
    }

    /*Edit eVoucher (CMS API)*/
    @PutMapping("/{id}")
    public ResponseEntity<eVoucher> updateVoucher(@PathVariable Long id, @RequestBody eVoucher updatedVoucher) throws ChangeSetPersister.NotFoundException {
        eVoucher updated = eVoucherService.updateVoucher(id, updatedVoucher);
        return ResponseEntity.ok(updated);
    }

    /*Set eVoucher as InActive (CMS API)*/
    @PutMapping("/{id}/inactivate")
    public ResponseEntity<eVoucher> inactivateVoucher(@PathVariable Long id) {
        eVoucher inactivatedVoucher = eVoucherService.inactivateVoucher(id);
        return ResponseEntity.ok(inactivatedVoucher);
    }

    /*eStore API S/N 1:  Returning eVoucher list*/
    @GetMapping
    public ResponseEntity<List<eVoucher>> getAllVouchers() {
        List<eVoucher> vouchers = eVoucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    /*eStore API S/N 2:  RReturning eVoucher Detail*/
    @GetMapping("/{id}")
    public ResponseEntity<eVoucher> getVoucherById(@PathVariable Long id) {
        eVoucher voucher = eVoucherService.getVoucherById(id);
        return ResponseEntity.ok(voucher);
    }


    /*eStore API S/N 2: Checkout*/
    @PutMapping("/{id}/checkout")
    public ResponseEntity<eVoucher> purchaseVoucher(@PathVariable Long id,
                                                    @RequestParam("buyerFirstName") String buyerFirstName,
                                                    @RequestParam("buyerLastName") String buyerLastName,
                                                    @RequestParam("buyerPhoneNumber") String buyerPhoneNumber,
                                                    @RequestParam("cardNumber") String cardNumber,
                                                    @RequestParam("cardExpiry") String cardExpiry,
                                                    @RequestParam("cardCvv") String cardCvv) {
        eVoucher purchasedVoucher = eVoucherService.makePayment(id, buyerFirstName, buyerLastName, buyerPhoneNumber, cardNumber, cardExpiry, cardCvv);
        return ResponseEntity.ok(purchasedVoucher);
    }
}
