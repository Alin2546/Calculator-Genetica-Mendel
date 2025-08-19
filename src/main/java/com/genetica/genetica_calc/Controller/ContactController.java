package com.genetica.genetica_calc.Controller;

import com.genetica.genetica_calc.Model.ContactMessage;
import com.genetica.genetica_calc.Model.DTO.ContactMessageDTO;
import com.genetica.genetica_calc.Repository.ContactMessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ContactMessageRepository contactMessageRepository;

    @PostMapping("/contact/send")
    public String sendContactMessage(
            @Valid @ModelAttribute("contactMessageDTO") ContactMessageDTO contactMessageDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "contact";
        }

        contactMessageRepository.save(contactMessageDTO.mapToContactMessage());
        model.addAttribute("successMessage", "Mesajul a fost trimis cu succes!");
        return "redirect:/contact?success";
    }
}
