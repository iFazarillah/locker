package com.lockerz.locker.service.impl;

import com.lockerz.locker.dto.LockerOrderRequest;
import com.lockerz.locker.dto.LockerOrderResponse;
import com.lockerz.locker.dto.ReqRes;
import com.lockerz.locker.exception.ValidationException;
import com.lockerz.locker.model.LockerOrderDetails;
import com.lockerz.locker.model.LockerOrders;
import com.lockerz.locker.model.Lockers;
import com.lockerz.locker.model.Users;
import com.lockerz.locker.repository.LockerOrderDetailsRepository;
import com.lockerz.locker.repository.LockerOrdersRepository;
import com.lockerz.locker.service.EmailService;
import com.lockerz.locker.service.LockerOderService;
import com.lockerz.locker.service.LockerService;
import com.lockerz.locker.service.UsersService;
import com.lockerz.locker.utils.DateUtils;
import com.lockerz.locker.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LockerOrderServiceImpl implements LockerOderService {

    @Autowired
    private LockerOrdersRepository lockerOrdersRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private LockerService lockerService;

    @Autowired
    private LockerOrderDetailsRepository lockerOrderDetailsRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public LockerOrderResponse orderLocker(LockerOrderRequest orderRequest) throws Exception {

        validateOrder(orderRequest);

        LockerOrderResponse responses = new LockerOrderResponse();
        List<ReqRes> lockerList = new ArrayList<>();
        double totalDepositNeeded = 0.0;

        Users user = usersService.getUserByEmail(orderRequest.getEmail());

        for (ReqRes request : orderRequest.getDataOrder()) {
            Lockers locker = lockerService.findByLockerCode(request.getLockerCode());
            int counter = 1;
            while (counter <= request.getQuantity()) {
                LockerOrders lockerOrders = new LockerOrders();
                ReqRes lockerDto = new ReqRes();

                lockerOrders.setLocker(locker);
                lockerOrders.setUser(user);
                lockerOrders.setStatus("WAITING FOR DEPOSIT");
                lockerOrdersRepository.saveAndFlush(lockerOrders);
                totalDepositNeeded += locker.getDeposit();
                lockerDto.setOrderId(lockerOrders.getId());
                lockerList.add(lockerDto);

                counter++;
            }
        }

        responses.setLockerList(lockerList);
        responses.setTotalBilling(totalDepositNeeded);
        responses.setEmail(orderRequest.getEmail());

        return responses;
    }

    @Override
    public LockerOrderResponse depositMoney(LockerOrderRequest orderRequest) throws ValidationException {

        LockerOrderResponse lockerOrderResponse = new LockerOrderResponse();
        lockerOrderResponse.setEmail(orderRequest.getEmail());
        List<ReqRes> resOrderList = new ArrayList<>();

        Double depositedMoney = orderRequest.getTotalDeposit();

        for (ReqRes order : orderRequest.getDataOrder()) {
            LockerOrders lockerOrder = lockerOrdersRepository.findByIdAndStatus(order.getOrderId(), "WAITING FOR DEPOSIT").orElseThrow(() -> new ValidationException("Locker Orders Data Not Found"));

            Double depositNeeded = lockerOrder.getLocker().getDeposit();
            if (depositedMoney >= depositNeeded) {

//                Save deposited needed to order details
                LockerOrderDetails lockerOrderDetails = lockerOrder.getLockerOrderDetail() != null ? lockerOrder.getLockerOrderDetail() : new LockerOrderDetails();
                lockerOrderDetails.setTotalDeposit(depositNeeded);
                lockerOrderDetailsRepository.saveAndFlush(lockerOrderDetails);
                depositedMoney -= depositNeeded;

//                GENERATE 8 CHARACTERS PASSWORD
                String password = StringUtils.randomStringSimple(8);

                lockerOrder.setLockerOrderDetail(lockerOrderDetails);
                lockerOrder.setStatus("ACTIVE");
                lockerOrder.setPassword(password);

                emailService.sendPasswordLocker(orderRequest.getEmail(), order.getOrderId(), password);
                lockerOrdersRepository.saveAndFlush(lockerOrder);
                order.setMessage("Deposit success! Check your email for locker password");

            } else {
                order.setMessage("Amount insufficient");
            }

            resOrderList.add(order);

        }

        lockerOrderResponse.setLockerList(resOrderList);
        lockerOrderResponse.setRefund(depositedMoney);

        return lockerOrderResponse;
    }

    @Override
    public ReqRes lockLocker(ReqRes request) throws ValidationException {
        ReqRes response = new ReqRes(request.getOrderId());
        LockerOrders lockerOrder = lockerOrdersRepository.findByIdAndStatus(request.getOrderId(), "ACTIVE").orElseThrow(() -> new ValidationException("Locker Orders Data Not Found"));

        if (lockerOrder.getPassword().equals(request.getPassword())) {

            if (!lockerOrder.isLocked()) {
                lockerOrder.setLocked(true);
                lockerOrder.setEligibleToUnlock(true);
                lockerOrder.setRentalStartTime(LocalDateTime.now());
                lockerOrder.setRentalFinishTime(LocalDateTime.now().plusDays(1));

                lockerOrdersRepository.saveAndFlush(lockerOrder);
                response.setMessage("Locker is locked");

            } else {
                response.setMessage("Locker is already locked");
            }
        } else {
            response.setMessage("Invalid Password");
        }


        return response;
    }

    @Override
    public List<ReqRes> bulkLockLocker(List<ReqRes> requestList) throws Exception {

        List<ReqRes> responseList = new ArrayList<>();

        for (ReqRes request : requestList) {
            ReqRes resp = lockLocker(request);
            responseList.add(resp);
        }

        return responseList;

    }

    @Override
    public ReqRes unlockLocker(ReqRes request) throws Exception {

        ReqRes response = new ReqRes(request.getOrderId());
        LockerOrders lockerOrder = lockerOrdersRepository.findByIdAndStatus(request.getOrderId(), "ACTIVE").orElseThrow(() -> new ValidationException("Locker Orders Data Not Found"));

//        Check if locked and eligible (no fine dues)
        if (lockerOrder.isLocked() && lockerOrder.isEligibleToUnlock()) {


            if (lockerOrder.getPassword().equals(request.getPassword())) {

//                check if on time
                if (LocalDateTime.now().isBefore(lockerOrder.getRentalFinishTime())) {

                    Double refund = lockerOrder.getLockerOrderDetail().getTotalDeposit();

                    completeOrder(lockerOrder);
                    response.setMessage("Success! Locker Unlocked");
                    response.setRefund(refund);
                    return response;
                } else {

//                    Count days late
                    long daysLate = DateUtils.countDaysFromNow(lockerOrder.getRentalFinishTime());
                    Double totalFineDue = lockerOrder.getLocker().getDailyLateFee() * daysLate;
                    Double deposit = lockerOrder.getLockerOrderDetail().getTotalDeposit();
                    lockerOrder.getLockerOrderDetail().setDaysLate((int) daysLate);

                    if (deposit >= totalFineDue){
                        deposit -= totalFineDue;

                        completeOrder(lockerOrder);
                        response.setMessage("Success! Fines paid from deposit. Locker is unlocked");
                        response.setRefund(deposit);
                        return response;
                    } else {

//                        User has fines due
                        totalFineDue -= deposit;
                        lockerOrder.getLockerOrderDetail().setTotalFineDue(totalFineDue);
                        lockerOrdersRepository.saveAndFlush(lockerOrder);

                        response.setMessage("Failed! You have fines due. Deposit amount to unlock locker");
                        response.setFinesDue(totalFineDue);
                        return response;
                    }

                }

            } else {

                int counterWrongPassword = lockerOrder.getWrongPasswordCounter();
                counterWrongPassword++;

//                check if password retry more than 3
                if (counterWrongPassword >= 3) {

                    lockerOrder.setWrongPasswordCounter(counterWrongPassword);
                    lockerOrder.setEligibleToUnlock(false);
                    response.setTotalPassAttemps(counterWrongPassword);
                    response.setMessage("Failed! You have fines due. Deposit amount to unlock locker");

                    Double unlockFee = lockerOrder.getLocker().getUnlockFee();
                    //                        use deposit to paid part of fines
                    unlockFee -= lockerOrder.getLockerOrderDetail().getTotalDeposit();
                    lockerOrder.getLockerOrderDetail().setTotalFineDue(unlockFee);
                    lockerOrder.getLockerOrderDetail().setTotalDeposit(0.0);

//                    Check if on time
                    if (LocalDateTime.now().isBefore(lockerOrder.getRentalFinishTime())) {

                        lockerOrdersRepository.saveAndFlush(lockerOrder);

                        response.setFinesDue(unlockFee);
                        return response;
                    } else {

                        //                    Count days late
                        long daysLate = DateUtils.countDaysFromNow(lockerOrder.getRentalFinishTime());

                        Double totalFineDue = lockerOrder.getLocker().getDailyLateFee() * daysLate;
                        unlockFee += totalFineDue;
                        lockerOrder.getLockerOrderDetail().setDaysLate((int) daysLate);
                        lockerOrder.getLockerOrderDetail().setTotalFineDue(unlockFee);
                        lockerOrdersRepository.saveAndFlush(lockerOrder);

                        response.setFinesDue(unlockFee);
                        return response;
                    }


                } else {
                    lockerOrder.setWrongPasswordCounter(counterWrongPassword);
                    lockerOrdersRepository.saveAndFlush(lockerOrder);

                    response.setTotalPassAttemps(counterWrongPassword);
                    response.setMessage("Wrong Password! You have up to 3 attempts");
                    return response;
                }
            }
        } else if (!lockerOrder.isEligibleToUnlock() && lockerOrder.getLockerOrderDetail().getTotalFineDue() != null) {
            response.setMessage("Failed! You have fines due. Deposit amount to unlock locker");
            response.setFinesDue(lockerOrder.getLockerOrderDetail().getTotalFineDue());
            return response;
        } else {
            response.setMessage("Locker already unlocked");
            return response;
        }

    }

    @Override
    public ReqRes unlockLockerByDeposit(ReqRes request) throws Exception {
        ReqRes response = new ReqRes(request.getOrderId());
        LockerOrders lockerOrder = lockerOrdersRepository.findByIdAndStatus(request.getOrderId(), "ACTIVE").orElseThrow(() -> new ValidationException("Locker Orders Data Not Found"));
        Double finesDue = lockerOrder.getLockerOrderDetail().getTotalFineDue();

//        Check if deposited money sufficient with fines due
        if (request.getTotalDeposit() >= finesDue){
            response.setMessage("Success! locker is unlocked");
            response.setRefund(request.getTotalDeposit() - finesDue);

            completeOrder(lockerOrder);

        } else {

            response.setMessage("Failed! Total deposit is insufficient");
        }


        return response;
    }

    private void completeOrder(LockerOrders lockerOrder) {
        lockerOrder.setLocked(false);
        lockerOrder.setStatus("COMPLETED");
        lockerOrder.setRentalCompletedTime(LocalDateTime.now());
        lockerOrder.getLockerOrderDetail().setTotalDeposit(0.0);
        lockerOrdersRepository.saveAndFlush(lockerOrder);
    }

    private void sendEmail(String email, String orderId, String password) {

        System.out.println("Email sent");
    }

    private void validateOrder(LockerOrderRequest orderRequest) throws Exception {

//        Order request can't be null or empty
        if (orderRequest.getDataOrder() == null || orderRequest.getDataOrder().isEmpty())
            throw new ValidationException("Order list can't be empty");

//        Cant Order more than 3 lockers
        if (orderRequest.getTotalOrder() > 3) throw new ValidationException("Order has exceeding limit of 3 rentals");

//        Check active + total order can't be more than 3
        List<LockerOrders> activeOrders = getActiveOrders(orderRequest.getEmail());

        if (activeOrders != null && !activeOrders.isEmpty()) {
            int totalActiveOrders = activeOrders.size();
            if (totalActiveOrders == 3) throw new ValidationException("User already has 3 active rentals");
            if (totalActiveOrders + orderRequest.getTotalOrder() > 3)
                throw new ValidationException("Total active orders and current order exceeding limit of 3 rentals");
        }

    }

    private List<LockerOrders> getActiveOrders(String email) {

        return lockerOrdersRepository.findByUser_EmailIgnoreCaseAndStatusIgnoreCase(email, "ACTIVE");
    }
}
