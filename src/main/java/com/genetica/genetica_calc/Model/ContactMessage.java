package com.genetica.genetica_calc.Model;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "contact_messages")
@Setter
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String subject;

    @Column(length = 2000)
    private String message;


}
