package com.kalgooksoo.user.model;

import com.kalgooksoo.user.domain.User;

import java.util.List;

public record UserPrincipal(User user, List<String> authorities) {}