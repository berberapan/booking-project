package org.example.booking_project.service.impl;
import org.example.booking_project.Dtos.BookingDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.MiniBookingDTO;
import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    BookingRepo bookingRepo;
    CustomerRepo customerRepo;
    RoomServiceImpl roomServiceImpl;

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo, RoomServiceImpl roomServiceImpl) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomServiceImpl = roomServiceImpl;
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
    public BookingDTO addBooking(CustomerDTO customerDTO, MiniBookingDTO miniBookingDTO, String roomNumber){
        Customer customer = customerRepo.findByEmail(customerDTO.getEmail());
        Room room = roomServiceImpl.getRoom(Integer.parseInt(roomNumber));
        Booking booking = new Booking(null, generateBookingNr(), customer, room, miniBookingDTO.getBookedBeds(), miniBookingDTO.getCheckInDate(), miniBookingDTO.getCheckOutDate());
        bookingRepo.save(booking);
        return bookingToBookingDTO(booking);
    }

    @Override
    public double calculatePrice(BookingDTO b) {
        return ChronoUnit.DAYS.between(b.getCheckInDate(), b.getCheckOutDate()) * b.getRoom().getPricePerNight();
    }

    @Override
    public String generateBookingNr() {
        int nr = 100;
        String abbr = "BN";
        String[] res;

        for (Booking b : bookingRepo.findAll()) {
            res = b.getBookingNr().split("(?=\\d*$)", 2);
            if ( isNumeric(res[1])) {
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

    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll().stream().map(this::bookingToBookingDTO).toList();
    }

    @Override
    public void updateBooking(Long id, BookingDTO bookingDTO) {

        Booking existingBooking = bookingRepo.findById(id).orElse(null);
        if (existingBooking != null) {
            existingBooking.setBookedBeds(bookingDTO.getBookedBeds());
            existingBooking.setCheckInDate(bookingDTO.getCheckInDate());
            existingBooking.setCheckOutDate(bookingDTO.getCheckOutDate());
            calculatePrice(bookingToBookingDTO(existingBooking));
            bookingRepo.save(existingBooking);
        }
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
}
