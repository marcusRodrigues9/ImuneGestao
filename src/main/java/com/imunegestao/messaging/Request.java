package com.imunegestao.messaging;

public record Request(
        String email,
        String paciente,
        String data,
        String hora
) {
}