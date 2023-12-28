package com.redbus.user.repository;

import com.redbus.user.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusBookingRepository extends JpaRepository<Booking,String> {

}
