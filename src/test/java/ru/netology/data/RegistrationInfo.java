package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
public class RegistrationInfo {
    private final String login;
    private final String password;
    private final String status;
}



