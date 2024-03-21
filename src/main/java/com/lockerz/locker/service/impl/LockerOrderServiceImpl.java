package com.lockerz.locker.service.impl;

import com.lockerz.locker.dto.LockerDto;
import com.lockerz.locker.dto.LockerOrderRequest;
import com.lockerz.locker.dto.LockerOrderResponse;
import com.lockerz.locker.dto.ReqRes;
import com.lockerz.locker.exception.ValidationException;
import com.lockerz.locker.model.LockerOrders;
import com.lockerz.locker.model.Lockers;
import com.lockerz.locker.model.Users;
import com.lockerz.locker.repository.LockerOrdersRepository;
import com.lockerz.locker.repository.UsersRepository;
import com.lockerz.locker.service.LockerOderService;
import com.lockerz.locker.service.LockerService;
import com.lockerz.locker.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public LockerOrderResponse orderLocker(LockerOrderRequest orderRequest) throws Exception {

        validateOrder(orderRequest);

        LockerOrderResponse responses = new LockerOrderResponse();
        List<LockerDto> lockerList = new ArrayList<>();
        double totalDepositNeeded = 0.0;

        Users user = usersService.getUserByEmail(orderRequest.getEmail());

        for (ReqRes request : orderRequest.getDataOrder()){
            Lockers locker = lockerService.findByLockerCode(request.getLockers());
            int counter = 1;
            while (counter <= request.getQuantity() ){
                LockerOrders lockerOrders = new LockerOrders();
                LockerDto lockerDto = new LockerDto();

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
        responses.setEmail(responses.getEmail());

        return responses;
    }

    private void validateOrder(LockerOrderRequest orderRequest) throws Exception {

//        Order request can't be null or empty
        if (orderRequest.getDataOrder() == null || orderRequest.getDataOrder().isEmpty()) throw new ValidationException("Order list can't be empty");

//        Cant Order more than 3 lockers
        if (orderRequest.getDataOrder().size() > 3) throw new ValidationException("Order has exceeding limit of 3 rentals");

//        Check active + total order can't be more than 3
        List<LockerOrders> activeOrders = getActiveOrders(orderRequest.getEmail());

        if (activeOrders != null && !activeOrders.isEmpty()){
            int totalActiveOrders = activeOrders.size();
            if (totalActiveOrders == 3) throw new ValidationException("User already has 3 active rentals");
            if (totalActiveOrders + orderRequest.getTotalOrder() > 3) throw new ValidationException("Total active orders and current order exceeding limit of 3 rentals");
        }

    }

    private List<LockerOrders> getActiveOrders(String email){

        return lockerOrdersRepository.findByUser_EmailIgnoreCaseAndStatusIgnoreCase(email,"ACTIVE");
    }
}
