package com.desabafo.desabafoatonimo.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IpHashUtil {

    private IpHashUtil() {
        // Construtor privado para classe utilitária
    }

    public static String hashIp(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            throw new IllegalArgumentException("IP não pode ser nulo ou vazio");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(ip.getBytes(StandardCharsets.UTF_8));

            // Converter bytes para hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // SHA-256 sempre está disponível na JVM
            throw new RuntimeException("Erro ao gerar hash do IP: SHA-256 não disponível", e);
        }
    }
}