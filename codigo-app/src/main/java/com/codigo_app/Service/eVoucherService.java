package com.codigo_app.Service;

import com.codigo_app.Enum.VourcherStatus;
import com.codigo_app.Model.eVoucher;
import org.springframework.stereotype.Service;
import com.codigo_app.Repository.eVoucherRepository;

import java.util.List;

@Service
public class eVoucherService {

    private final eVoucherRepository eVoucherRepository;


    public eVoucherService(eVoucherRepository eVoucherRepository) {
        this.eVoucherRepository = eVoucherRepository;

    }

    public eVoucher create(eVoucher voucher) {
        return eVoucherRepository.save(voucher);
    }

    public eVoucher updateVoucher(Long id, eVoucher updatedVoucher) {eVoucher existingVoucher = eVoucherRepository.findById(id).orElseThrow(() -> new NotFoundException("eVoucher not found"));
        existingVoucher.setTitle(updatedVoucher.getTitle());
        existingVoucher.setDescription(updatedVoucher.getDescription());
        existingVoucher.setExpiryDate(updatedVoucher.getExpiryDate());
        return eVoucherRepository.save(existingVoucher);
    }

    public eVoucher inactivateVoucher(Long id) {eVoucher existingVoucher = eVoucherRepository.findById(id).orElseThrow(() -> new NotFoundException("eVoucher not found"));

        existingVoucher.setStatus(VourcherStatus.INACTIVE);
        return eVoucherRepository.save(existingVoucher);
    }

    public List<eVoucher> getAllVouchers() {
        return eVoucherRepository.findAll();
    }

    public eVoucher getVoucherById(Long id) {
        return eVoucherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("eVoucher not found"));
    }

    private boolean isValidCard(String cardNumber, String cardExpiry, String cardCvv) {

        if (cardNumber.startsWith("4") && cardCvv.length() == 3) {
            return true;
        } else if (cardNumber.startsWith("5") && cardCvv.length() == 3) {
            return true;
        } else {
            return false;
        }
    }


    public eVoucher makePayment(Long voucherId, String buyerFirstName, String buyerLastName, String buyerPhoneNumber, String cardNumber, String cardExpiry, String cardCvv) {
        eVoucher voucher = eVoucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException("eVoucher not found"));

        if (!isValidCard(cardNumber, cardExpiry, cardCvv)) {
            throw new PaymentException("Invalid card details.");
        }
        String paymentMethod = determinePaymentMethod(cardNumber);

        voucher.setPaymentMethod(paymentMethod);

        return eVoucherRepository.save(voucher);
    }

    private String determinePaymentMethod(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "VISA";
        } else if (cardNumber.startsWith("5")) {
            return "MASTER";
        } else {
            return "UNKNOWN";
        }
    }


}