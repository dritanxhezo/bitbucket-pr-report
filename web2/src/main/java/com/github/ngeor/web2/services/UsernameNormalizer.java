package com.github.ngeor.web2.services;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UsernameNormalizer {
    public String normalize(String username) {
        var aliases = Map.ofEntries(Map.entry("wrong-alias", "correct-alias"));

        if (aliases.containsKey(username)) {
            return aliases.get(username);
        }

        return username;
    }
}
