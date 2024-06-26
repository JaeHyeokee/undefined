package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Booking;
import com.lec.spring.domain.ProvLodging;
import com.lec.spring.domain.Room;
import com.lec.spring.domain.User;
import com.lec.spring.service.BookingService;
import com.lec.spring.service.ProviderService;
import com.lec.spring.service.RoomService;
import com.lec.spring.service.UserService;
import com.lec.spring.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/mypage/provider")
public class ProviderController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/ProvBookingList")
    public String provBookingList(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // 인증되지 않은 사용자 처리
            return "redirect:/user/login"; // 로그인 페이지로 리다이렉트 또는 예외 처리
        }

        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof PrincipalDetails) {
            PrincipalDetails principalDetails = (PrincipalDetails) principal;
            user = principalDetails.getUser();
            model.addAttribute("user", user);
        } else if (principal instanceof String) {
            String username = (String) principal;
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        } else {
            // 다른 타입에 대한 처리
            throw new IllegalStateException("Unknown principal type: " + principal.getClass());
        }

        List<ProvLodging> lodgings = providerService.getLodgings(user.getUserId());

        model.addAttribute("lodgings", lodgings);

        return "mypage/provider/ProvBookingList";
    }

    @GetMapping("/ProvBookingList/books")
    public String provBookingListGetBooks(@RequestParam("lodgingId") Long lodgingId, Model model) {
        List<Room> rooms = roomService.findRoomsByLodgingId(lodgingId);
        rooms.forEach(room -> {
            room.setBookList(bookingService.findBooksByRoomId(room.getRoomId()));
            //room.DecimalFormat.getInstance().format(room.getRoomPrice());
        });
        model.addAttribute("rooms", rooms);

        return "mypage/provider/ProvBookingListInner :: bookingList";
    }

//    @GetMapping("/provlodginglist")
//    public String provlodginglist(Model model) {
//        List<ProvLodging> lodgings = providerService.getLodgings();
//        model.addAttribute("lodgings", lodgings);
//        return "mypage/provider/ProvLodgingList";
//    }

    @GetMapping("/provlodginglist")
    public String provlodginglist(Model model) {
        User loggedUser = U.getLoggedUser(); // 로그인된 사용자 정보
        Long userId = loggedUser.getUserId(); // 사용자 ID
        System.out.println("세션 userId: " + userId); // userId를 콘솔에 출력

        List<ProvLodging> lodgings = providerService.getLodgings(userId);
        System.out.println("가져온 숙소 목록: " + lodgings);

        List<ProvLodging> myLodgings = new ArrayList<>();
        for (ProvLodging item : lodgings) {
            System.out.println("숙소 userId: " + item.getUserId());
            if (item.getUserId() != null && item.getUserId().equals(userId)) {
                myLodgings.add(item);
            }
        }
        System.out.println("필터링된 숙소 목록: " + myLodgings);

        model.addAttribute("lodgings", myLodgings);
        return "mypage/provider/ProvLodgingList";
    }




    @RequestMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/provlodgingdetail/{lodgingId}")
    public String provlodgingdetail(@PathVariable Long lodgingId, Model model) {
        ProvLodging lodging = providerService.getAllDetails(lodgingId);
        model.addAttribute("lodging", lodging);
        return "mypage/provider/ProvLodgingDetail";
    }

    @GetMapping("/provlodgingupdate/{lodgingId}")
    public String provLodgingUpdate(@PathVariable Long lodgingId, Model model) {
        ProvLodging lodging = providerService.getAllDetails(lodgingId); // 숙소 정보 가져오기
        model.addAttribute("lodging", lodging); // 모델에 숙소 정보 추가
        return "mypage/provider/ProvLodgingUpdate"; // 숙소 업데이트 페이지로 이동
    }

    @PostMapping("/updateLodging")
    public String updateLodging(@ModelAttribute ProvLodging lodging) {
        // lodging 객체에서 lodgingId 추출
        Long lodgingId = lodging.getLodgingId();

        providerService.updateLodging(lodging);
        return "redirect:/mypage/provider/provlodgingdetail/" + lodgingId;
    }

    @PostMapping("/deleteLodging/{lodgingId}")
    public String deleteLodging(@PathVariable int lodgingId, Model model) {
        int result;
        try {
            providerService.deleteLodging(lodgingId);
            result = 1; // 삭제 성공
        } catch (Exception e) {
            result = 0; // 삭제 실패
        }
        model.addAttribute("result", result);
        return "mypage/provider/ProvLodgingDeleteOk";
    }


    @GetMapping("/provlodgingregister")
    public String provlodgingregister() {
        return "mypage/provider/ProvLodgingRegister";
    }

    @PostMapping("/saveLodging")
    public String saveLodging(@ModelAttribute ProvLodging lodging) {
        providerService.saveLodging(lodging);
        return "redirect:provlodginglist";
    }

    @PostMapping("/createRoom")
    public String createRoom(@ModelAttribute Room room){
        roomService.createRoom(room);
        return "redirect:ProvRoomList";
    }

    @GetMapping("/ProvRoomRegister/{lodgingId}")
    public String provRoomRegister(@PathVariable("lodgingId") Long lodgingId, Model model) {
        ProvLodging lodging = providerService.getAllDetails(lodgingId);
        model.addAttribute("lodging", lodging);
        return "mypage/provider/ProvRoomRegister";
    }

    @PostMapping("/ProvRoomRegister")
    public String provRoomRegisterOk(Room room, Model model) {
        model.addAttribute("result", roomService.createRoom(room));
        return "mypage/provider/ProvRoomRegisterOk";
    }

    @GetMapping("/ProvRoomList/{userId}")
    public String provRoomList(@PathVariable("userId") Long userId, Model model) {
        List<ProvLodging> roomList = providerService.getLodgingsAndRoomsByUserId(userId);
        model.addAttribute("rooms", roomList);
        return "mypage/provider/ProvRoomList";
    }

    @GetMapping("/ProvRoomDetail/{roomId}")
    public String provRoomDetail(@PathVariable("roomId") Long roomId, Model model) {
        Room room = roomService.findByRoomId(roomId);
        model.addAttribute("room", room);
        return "mypage/provider/ProvRoomDetail";
    }



    // 수정 폼을 보여주는 메서드
    @GetMapping("/ProvRoomUpdate/{roomId}")
    public String showUpdateForm(@PathVariable Long roomId, Model model) {
        Room room = roomService.findByRoomId(roomId);
        model.addAttribute("room", room);
        return "mypage/provider/ProvRoomUpdate";
    }

    // 수정 처리를 담당하는 메서드
    @PostMapping("/ProvRoomUpdate")
    public String provRoomUpdateOk(@ModelAttribute Room room) {
        roomService.updateRoom(room);
        return "redirect:/mypage/provider/ProvRoomDetail/" + room.getRoomId();
    }

    @PostMapping("/deleteRoom/{roomId}")
    public String deleteRoom(@PathVariable int roomId, Model model) {
        int result;
        Long userId = U.getLoggedUser().getUserId();
        try {
            roomService.deleteRoom(roomId);
            result = 1; // 삭제 성공
        } catch (Exception e) {
            result = 0; // 삭제 실패
        }
        model.addAttribute("result", result);
        model.addAttribute("userId", userId);
        return "mypage/provider/ProvRoomDeleteOk";
    }


}
