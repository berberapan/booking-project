package org.example.booking_project.service.impl;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.MiniBookingDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.example.booking_project.controllers.BookingController.handleConstraintViolationException;

@Service
public class BookingServiceImpl implements BookingService {

    BookingRepo bookingRepo;
    CustomerRepo customerRepo;
    RoomServiceImpl roomServiceImpl;
    CustomerServiceImpl customerServiceImpl;

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo, RoomServiceImpl roomServiceImpl,
                              CustomerServiceImpl customerServiceImpl) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomServiceImpl = roomServiceImpl;
        this.customerServiceImpl = customerServiceImpl;
    }

    @Override
    public BookingDTO bookingToBookingDTO(Booking b) {
        return BookingDTO.builder().id(b.getId()).bookingNr(b.getBookingNr())
                .customer(new CustomerDTO(b.getCustomer().getId(), b.getCustomer().getCustomerNumber(), b.getCustomer()
                        .getCustomerName(), b.getCustomer().getPhoneNumber(), b.getCustomer().getEmail()))
                .room(new RoomDTO(b.getRoom().getId(), b.getRoom().getRoomNumber(), b.getRoom().getMaxBeds(),
                        b.getRoom().getPricePerNight(), b.getRoom().getRoomType()))
                .bookedBeds(b.getBookedBeds()).checkInDate(b.getCheckInDate()).checkOutDate(b.getCheckOutDate()).build();
    }

    @Override
    public Booking bookingDTOToBooking(BookingDTO b, Customer customer, Room room) {
        return Booking.builder().id(b.getId()).bookingNr(b.getBookingNr()).customer(customer).room(room)
                .bookedBeds(b.getBookedBeds()).checkInDate(b.getCheckInDate()).checkOutDate(b.getCheckOutDate()).build();
    }

    @Override
    public BookingDTO addBooking(CustomerDTO customerDTO, MiniBookingDTO miniBookingDTO, String roomNumber) {
        Customer customer = customerRepo.findByEmail(customerDTO.getEmail());
        Room room = roomServiceImpl.getRoom(Integer.parseInt(roomNumber));
        Booking booking = new Booking(null, generateBookingNr(), customer, room, miniBookingDTO.getBookedBeds(), miniBookingDTO.getCheckInDate(), miniBookingDTO.getCheckOutDate());
        bookingRepo.save(booking);
        return bookingToBookingDTO(booking);
    }

    @Override
    public double calculatePrice(BookingDTO b) {

        double finalPrice = 0;
        double fullDiscount = 0;
        double discountSundayNight = 0.02;
        double discountOverTwoNights = 0.005;
        double discountOverTenNights = 0.02;

        double priceForThisDay;

        //Räknar ihop totalpriset samt ger 2% rabatt för eventuell söndagsnatt.
        for (LocalDate thisDate = b.getCheckInDate(); thisDate.isBefore(b.getCheckOutDate()); thisDate = thisDate.plusDays(1)) {
            if (thisDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                priceForThisDay = b.getRoom().getPricePerNight() * (1 - discountSundayNight);
            } else {
                priceForThisDay = b.getRoom().getPricePerNight();
            }

            finalPrice = finalPrice + priceForThisDay;
        }

        //Ger 0.5% rabatt på totalpriset vid bokningar minst 2 nätter.
        if (ChronoUnit.DAYS.between(b.getCheckInDate(), b.getCheckOutDate()) >= 2) {
            fullDiscount = fullDiscount + discountOverTwoNights;
        }

        //Ger 2% rabatt på totalpriset om kunden har bokat minst 10 nätter senaste året
        if (bookedNightsLastYear(b.getCustomer()) >= 10) {
            fullDiscount = fullDiscount + discountOverTenNights;
        }

        return finalPrice * (1 - fullDiscount);

    }

    @Override
    public long bookedNightsLastYear(CustomerDTO customer) {    //Returnerar antal bokade nätter senaste året från dagens datum
        long nights = 0;

        for (Booking b : bookingRepo.findAllByCustomerAndCheckInDateAfter(
                customerServiceImpl.customerDTOToCustomer(customer),
                LocalDate.now().minusYears(1).minusDays(1))) {
            nights = nights + ChronoUnit.DAYS.between(b.getCheckInDate(), b.getCheckOutDate());
        }

        return nights;
    }

    @Override
    public String generateBookingNr() {
        int nr = 100;
        String abbr = "BN";
        String[] res;

        for (Booking b : bookingRepo.findAll()) {
            res = b.getBookingNr().split("(?=\\d*$)", 2);
            if (isNumeric(res[1])) {
                int thisNr = Integer.parseInt(res[1]);
                if (thisNr >= nr) {
                    nr = thisNr + 1;
                }
            }
        }
        return abbr + nr;
    }

    public static boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String updateBooking(Long id, BookingDTO bookingDTO) {

        Booking existingBooking = bookingRepo.findById(id).orElse(null);

        if (existingBooking != null) {
            int amountOfBeds = bookingDTO.getBookedBeds();
            if (amountOfBeds > 0 && amountOfBeds <= existingBooking.getRoom().getMaxBeds()) {
                LocalDate newCheckInDate = bookingDTO.getCheckInDate();
                LocalDate newCheckOutDate = bookingDTO.getCheckOutDate();

                if (newCheckInDate.isAfter(newCheckOutDate)) {
                    return "DateError";
                }

                if (newCheckOutDate.isEqual(newCheckInDate)) {
                    return "DateError";
                }

                if (checkAvailabilityInRoom(existingBooking.getId(), existingBooking.getRoom().getId(), newCheckInDate, newCheckOutDate)) {
                    existingBooking.setBookedBeds(amountOfBeds);
                    existingBooking.setCheckInDate(newCheckInDate);
                    existingBooking.setCheckOutDate(newCheckOutDate);
                    bookingRepo.save(existingBooking);
                    return "Saved";
                } else {
                    return "DateError";
                }
            } else {
                return "BedsError";
            }
        }
        return "Error";
    }

    @Override
    public boolean checkAvailabilityInRoom(Long bookingID, Long roomID, LocalDate checkIn, LocalDate checkOut) {

        List<BookingDTO> listBDTO = getAllBookings();
        List<BookingDTO> filteredBookingList = listBDTO.stream().
                filter(r -> r.getRoom().getId() == roomID).
                filter(bookingDTO -> bookingDTO.getId() != bookingID).toList();

        for (BookingDTO booking : filteredBookingList) {
            if ((booking.getCheckInDate().isBefore(checkOut) || booking.getCheckInDate().isEqual(checkOut)) &&
                    (booking.getCheckOutDate().isAfter(checkIn) || booking.getCheckOutDate().isEqual(checkIn))
                    && !(checkIn.isEqual(booking.getCheckOutDate()))
            ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll().stream().map(this::bookingToBookingDTO).toList();
    }


    @Override
    public boolean existsBookingByBookingNr(String bookingNr) {
        return bookingRepo.existsByBookingNr(bookingNr);
    }

    @Override
    public BookingDTO getBookingByBookingNr(String bookingNr) {
        Booking booking = bookingRepo.findByBookingNr(bookingNr);
        return bookingToBookingDTO(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepo.findById(id).ifPresent(booking -> bookingRepo.deleteById(id));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String localExceptionHandler(ConstraintViolationException ex, Model model) {
        return handleConstraintViolationException(ex, model);
    }
}
