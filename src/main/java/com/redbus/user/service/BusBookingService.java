package com.redbus.user.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.user.entity.Booking;
import com.redbus.user.payload.BusBookingDto;
import com.redbus.user.payload.PassengerDto;
import com.redbus.user.repository.BusBookingRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
public class BusBookingService {

    @Autowired
    private BusOperatorRepository busOperatorRepository;
    @Autowired
    private BusBookingRepository busBookingRepository;

    @Autowired
    private JavaMailSender javaMailSender; // Autowire JavaMailSender
    @Value("${stripe.secret-key}")
    private String stripeSecretKey;


    public BusBookingDto bookBus(String busId, PassengerDto passengerDto) {

        BusOperator bus = busOperatorRepository.findById(busId).get();

        double amount = bus.getTicketCost().getTicketPrice() - bus.getTicketCost().getDiscountPrice();

        String payment =createPaymentIntent(amount);

        if (payment!=null) {

                BusOperator busOperator = busOperatorRepository.findById(busId).get();
                Booking booking = new Booking();

                String bookingId = UUID.randomUUID().toString();
                booking.setBookingId(bookingId);
                booking.setBusId(busId);
                booking.setTicketId(busOperator.getTicketCost().getTicketId());
                booking.setFromCity(busOperator.getDepartureCity());
                booking.setToCity(busOperator.getArrivalCity());
                booking.setBusNumber(busOperator.getBusNumber());
                booking.setBusCompanyName(busOperator.getBusOperatorCompanyName());
                booking.setArrivalDate(busOperator.getArrivalDate());
                booking.setArrivalTime(busOperator.getArrivalTime());
                booking.setDepartureDate(busOperator.getDepartureDate());
                booking.setDepartureTime(busOperator.getDepartureTime());
                booking.setTotalTravelTime(busOperator.getTotalTravelTime());
                booking.setBusType(busOperator.getBusType());
                booking.setAmenities(busOperator.getAmenities());
                booking.setTicketPrice(busOperator.getTicketCost().getTicketPrice());
                booking.setFirstName(passengerDto.getFirstName());
                booking.setLastName(passengerDto.getLastName());
                booking.setEmail(passengerDto.getEmail());
                booking.setMobile(passengerDto.getMobile());

                Booking savedBooking = busBookingRepository.save(booking);
                // Payment succeeded
                // Continue with booking confirmation and email sending
                sendConfirmationEmailWithAttachment(savedBooking);

                BusBookingDto dto = new BusBookingDto();
                dto.setBookingId(savedBooking.getBookingId());
                dto.setBusNumber(savedBooking.getBusNumber());
                dto.setFirstName(savedBooking.getFirstName());
                dto.setBusType(savedBooking.getBusType());
                dto.setAmenities(savedBooking.getAmenities());
                dto.setArrivalDate(savedBooking.getArrivalDate());
                dto.setArrivalTime(savedBooking.getArrivalTime());
                dto.setBusCompanyName(savedBooking.getBusCompanyName());
                dto.setMobile(savedBooking.getMobile());
                dto.setDepartureDate(savedBooking.getDepartureDate());
                dto.setFromCity(savedBooking.getFromCity());
                dto.setToCity(savedBooking.getToCity());
                dto.setDepartureTime(savedBooking.getDepartureTime());
                dto.setTotalTravelTime(savedBooking.getTotalTravelTime());
                dto.setLastName(savedBooking.getLastName());
                dto.setEmail(savedBooking.getEmail());
                dto.setTicketPrice(savedBooking.getTicketPrice());
                dto.setBookingStatus("Confirmed");
                return dto;

            } else {
                System.out.println("error");
            }
       return null;
        }





    public String createPaymentIntent(double amount) {
        Stripe.apiKey = stripeSecretKey;

        try {
            PaymentIntent paymentIntent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency("usd")
                            .setAmount((long) (amount * 100)) // Convert to cents
                            .build()
            );
            return generateResponse(paymentIntent.getClientSecret());
        } catch (StripeException e) {
             return generateResponse("Error create PaymentIntent: " + e.getMessage());
        }
    }
    private String generateResponse(String clientSecret){
        return "{\"clientSecret\":\"" + clientSecret + "\"}";
    }


        private void sendConfirmationEmailWithAttachment (Booking booking){
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setTo(booking.getEmail());
                helper.setSubject("Booking Confirmation");
                helper.setText("Dear " + booking.getFirstName() + ",\n\n"
                        + "Your bus booking with booking ID " + booking.getBookingId() + " is confirmed.\n\n"
                        + "Thank you for choosing our service.\n\n"
                        + "Best regards,\nRedBus Team");

                // Attach the PDF file
                byte[] pdfContent = generatePdfDocument(booking);
                helper.addAttachment("BookingConfirmation.pdf", new ByteArrayResource(pdfContent),
                        MediaType.APPLICATION_PDF_VALUE);

                // Send the email
                javaMailSender.send(message);
            } catch (MessagingException e) {
                // Handle exception (e.g., log or throw a custom exception)
                e.printStackTrace();
            }
        }

        private byte[] generatePdfDocument (Booking booking){
            try {
                Document document = new Document();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);

                document.open();
                document.add(new Paragraph("Booking Confirmation"));
                document.add(new Paragraph("Booking ID: " + booking.getBookingId()));
                document.add(new Paragraph("Bus Number: " + booking.getBusNumber()));
                document.add(new Paragraph("Departure City: " + booking.getFromCity()));
                document.add(new Paragraph("Arrival City: " + booking.getToCity()));
                document.add(new Paragraph("Departure Date: " + booking.getDepartureDate()));
                document.add(new Paragraph("Departure Time: " + booking.getDepartureTime()));
                // Add more details as needed...

                document.close();

                return baos.toByteArray();
            } catch (Exception e) {
                // Handle exception (e.g., log or throw a custom exception)
                e.printStackTrace();
                return new byte[0];
            }
        }
    }
