package com.genetica.genetica_calc.Model.DTO;

import com.genetica.genetica_calc.Model.ContactMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactMessageDTO {
    @NotBlank(message = "Numele este obligatoriu")
    private String name;

    @NotBlank(message = "Emailul este obligatoriu")
    @Email(message = "Adresa de email nu este validă")
    private String email;

    @NotBlank(message = "Subiectul este obligatoriu")
    @Size(max = 255, message = "Subiectul nu poate depăși 255 de caractere")
    private String subject;

    @NotBlank(message = "Mesajul este obligatoriu")
    @Size(max = 2000, message = "Mesajul nu poate depăși 2000 de caractere")
    private String message;

    public ContactMessage mapToContactMessage() {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(this.name);
        contactMessage.setEmail(this.email);
        contactMessage.setSubject(this.subject);
        contactMessage.setMessage(this.message);
        return contactMessage;
    }
}
